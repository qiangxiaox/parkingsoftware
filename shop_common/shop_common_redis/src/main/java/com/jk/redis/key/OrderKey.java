package com.jk.redis.key;

import com.jk.redis.rediskey.BasePrefix;

/**
 * @ClassName : OrderKey
 * @Author : xiaoqiang
 * @Date : 2018/11/2 0:56:56
 * @Description :
 * @Version ： 1.0
 */
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}
