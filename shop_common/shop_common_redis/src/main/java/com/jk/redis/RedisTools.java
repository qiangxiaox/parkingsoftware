package com.jk.redis;

import com.alibaba.fastjson.JSON;
import com.jk.redis.rediskey.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : RedisTools
 * @Author : xiaoqiang
 * @Date : 2018/11/2 0:31:31
 * @Description :
 * @Version ： 1.0
 */
@Component
public class RedisTools {
    @Autowired
    JedisPool jedisPool;

    /**
     * 获取当个对象
     * */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //获取redis中的真正的key值
            String realKey = prefix.getPrefix()+key;
            String value = jedis.get(realKey);
            T t = stringToBean(value,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }
    /**
     * 设置对象
     * */
    public <T> boolean set(KeyPrefix prefix, String key,  T ObjectValue) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String value = beanToString(ObjectValue);
            if(value == null || value.length() <= 0) {
                return false;
            }
            //获取redis中的真正的key值
            String realKey = prefix.getPrefix()+key;
            int expireSe = prefix.expireSeconds();
            if(expireSe <= 0) {
                jedis.set(realKey, value);
            }else {
                jedis.setex(realKey, expireSe, value);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 将对象转换为对象格式的String
     * @param objectValue
     * @param <T>
     * @return
     */
    private <T> String beanToString(T objectValue) {
        if(objectValue == null) {
            return null;
        }
        Class<?> clazz = objectValue.getClass();

        if(clazz == int.class || clazz == Integer.class){
            return "" + objectValue;
        }else if (clazz == long.class || clazz == Long.class){
            return "" + objectValue;
        }else if (clazz == double.class || clazz == Double.class){
            return "" + objectValue;
        }else if (clazz == String.class){
            return (String)objectValue;
        }else if (clazz == boolean.class || clazz == Boolean.class) {
            return "" + objectValue;
        }else{
            return JSON.toJSONString(objectValue);
        }
    }

    /**
     * 将类格式的String转换成类对象
     * */
    private <T> T stringToBean(String value, Class<T> clazz) {
        if(value == null || value.length() <= 0|| clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(value);
        }else if (clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(value);
        }else if (clazz == double.class || clazz == Double.class){
            return (T)Double.valueOf(value);
        }else if (clazz == String.class){
            return (T)value;
        }else if(clazz == boolean.class || clazz == Boolean.class){
            return (T)Boolean.valueOf(value);
        }else{
            //return JSON.parseObject(value, clazz);
            return JSON.toJavaObject(JSON.parseObject(value),clazz);
        }

    }

    /**
     * 删除对象
     * */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //获取redis中的真正的key值
            String realKey = prefix.getPrefix()+key;
            Long del = jedis.del(realKey);
            return del > 0;
        }finally {
            returnToPool(jedis);
        }
    }

    public boolean delete(KeyPrefix prefix) {
        if(prefix == null) {
            return false;
        }
        List<String> keys = scanKeys(prefix.getPrefix());
        if(keys==null || keys.size() <= 0) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys.toArray(new String[0]));
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 查询redis中包含key的所有key
     * @param key
     * @return
     */
    public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<String>();
            String cursor = "0";
            ScanParams sp = new ScanParams();
            sp.match("*"+key+"*");
            sp.count(100);
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(result!=null && result.size() > 0){
                    keys.addAll(result);
                }
                //再处理cursor
                cursor = ret.getStringCursor();
            }while(!cursor.equals("0"));
            return keys;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 判断key是否存在
     * */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }
}
