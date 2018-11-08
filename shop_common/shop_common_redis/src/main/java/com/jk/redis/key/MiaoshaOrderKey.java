package com.jk.redis.key;

import com.jk.redis.rediskey.BasePrefix;

/**
 * @ClassName : MiaoshaOrderKey
 * @Author : xiaoqiang
 * @Date : 2018/11/2 0:56:56
 * @Description :
 * @Version ： 1.0
 */
public class MiaoshaOrderKey extends BasePrefix {

    public MiaoshaOrderKey(String prefix) {
        super(prefix);
    }
    public static MiaoshaOrderKey getMiaoshaOrderByUidGid = new MiaoshaOrderKey("moug");
}
