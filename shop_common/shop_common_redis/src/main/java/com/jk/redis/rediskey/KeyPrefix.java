package com.jk.redis.rediskey;

public interface KeyPrefix {

    /**
     * 过期时间
     * @return
     */
    public int expireSeconds();

    /**
     * 获取redis的key的前缀，不同的子类，前缀不同
     * @return
     */
    public String getPrefix();

}
