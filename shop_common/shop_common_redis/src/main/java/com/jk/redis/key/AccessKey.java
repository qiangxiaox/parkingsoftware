package com.jk.redis.key;

import com.jk.redis.rediskey.BasePrefix;

/**
 * @ClassName : AccessKey
 * @Author : xiaoqiang
 * @Date : 2018/11/6 19:11:11
 * @Description :路径访问限制
 * @Version ： 1.0
 */
public class AccessKey extends BasePrefix {
    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**动态设置过期时间，路径访问限制次数的key**/
    public static AccessKey withExpire(int expireSeconds){
        return new AccessKey(expireSeconds, "access");
    }

}
