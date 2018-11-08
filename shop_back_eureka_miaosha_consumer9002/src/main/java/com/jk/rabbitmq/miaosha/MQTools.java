package com.jk.rabbitmq.miaosha;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName : MQTools
 * @Author : xiaoqiang
 * @Date : 2018/11/6 13:26:26
 * @Description :
 * @Version ： 1.0
 */
public class MQTools {
    /**
     * 将对象转换为对象格式的String
     * @param objectValue
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T objectValue) {
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
        }else{
            return JSON.toJSONString(objectValue);
        }
    }

    /**
     * 将类格式的String转换成类对象
     * */
    public static <T> T stringToBean(String value, Class<T> clazz) {
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
        }else{
            //return JSON.parseObject(value, clazz);
            return JSON.toJavaObject(JSON.parseObject(value),clazz);
        }

    }

}
