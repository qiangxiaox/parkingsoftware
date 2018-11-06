package com.jk.redis.key;

import com.jk.redis.rediskey.BasePrefix;

/**
 * @ClassName : GoodsKey
 * @Author : xiaoqiang
 * @Date : 2018/11/5 19:09:09
 * @Description :
 * @Version ： 1.0
 */
public class GoodsKey  extends BasePrefix {

    /**
     * 此时需要设置该key在Redis中的失效时间
     *
     */
    /**页面缓存失效时间**/
    public static final int PAGE_EXPIRE = 60;
    /**URL缓存失效时间**/
    public static final int PAGEURL_EXPIRE = 60;


    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    /**商品列表页面的缓存key，因为是通用，单独的。所以set、get中不需要加key**/
    public static GoodsKey getGoodsList = new GoodsKey(PAGE_EXPIRE, "golist");
    /**商品详情页面的缓存key，因为不同的商品有不同的详情，所以在set、get中需要加key**/
    public static GoodsKey getGoodsDetail = new GoodsKey(PAGEURL_EXPIRE, "godetail");

    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "goodstock");

}
