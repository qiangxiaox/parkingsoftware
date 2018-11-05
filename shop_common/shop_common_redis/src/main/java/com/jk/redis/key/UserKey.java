package com.jk.redis.key;

import com.jk.redis.rediskey.BasePrefix;

/**
 * @ClassName : UserKey
 * @Author : xiaoqiang
 * @Date : 2018/11/2 0:55:55
 * @Description :
 * @Version ： 1.0
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
