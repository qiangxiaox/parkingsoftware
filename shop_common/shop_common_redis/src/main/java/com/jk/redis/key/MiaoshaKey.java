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

    private MiaoshaKey(String prefix) {
        super(prefix);
    }
    /**秒杀是否结束的标志**/
    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
