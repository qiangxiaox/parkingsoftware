package com.jk.redis.key;

import com.jk.redis.rediskey.BasePrefix;

/**
 * @ClassName : MiaoshaKey
 * @Author : xiaoqiang
 * @Date : 2018/11/6 14:57:57
 * @Description :
 * @Version ： 1.0
 */
public class MiaoshaKey extends BasePrefix {

    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    /**秒杀是否结束的标志**/
    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    /**接口限流的路径缓存**/
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");
}
