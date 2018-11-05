package com.jk.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName : JedisPoolConfig
 * @Author : xiaoqiang
 * @Date : 2018/11/2 0:24:24
 * @Description :
 * @Version ： 1.0
 */
@Configuration
public class JedisPoolFactory {

    @Bean
    public JedisPool jedisPool(@Autowired RedisConfig redisConfig){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        JedisPool pool = new JedisPool(poolConfig,redisConfig.getHost(),redisConfig.getPort(),
                                        redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);
        return pool;
    }
}
