package com.jk.miaosha.service.impl;

import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.util.MD5Util;
import com.jk.miaosha.util.UUIDUtil;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.redis.RedisTools;
import com.jk.redis.key.MiaoshaKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName : MiaoshaServiceImpl
 * @Author : xiaoqiang
 * @Date : 2018/11/5 15:29:29
 * @Description :
 * @Version ： 1.0
 */
@Service
public class MiaoshaServiceImpl {
    @Autowired
    GoodsServiceImpl goodsService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    private RedisTools redisTools;


    @Transactional
    public OrderInfo doMiaosha(long userId, GoodsVo goodsVo){
        //减库存
        boolean isSuccess =  this.goodsService.reduceMiaoshaGoodsStock(goodsVo.getId()) > 0;
        if(isSuccess){
            return this.orderService.createOrderAndMiaoshaOrder(userId,goodsVo);
        }
        //失败，则设置内存标记，秒杀结束
        setGoodsOver(goodsVo.getId());
        return null;
    }


    /**
     * 根据用户id和商品id查询是否存在秒杀订单
     * @param userId 用户id
     * @param goodsId 商品id
     * @return
     */
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }else { //查询秒杀是否结束
            boolean isOver = getGoodsOver(goodsId); //设置缓存key表示秒杀是否结束
            if(isOver) { //秒杀结束
                return -1;
            }else { //秒杀排队中
                return 0;
            }
        }
    }
    /**设置redis中的秒杀商品的已结束**/
    private void setGoodsOver(long mioashaGoodsId) {
        redisTools.set(MiaoshaKey.isGoodsOver, ""+mioashaGoodsId, true);
    }
    /**获取redis中的秒杀商品的活动是否结束**/
    private boolean getGoodsOver(long goodsId) {
        Boolean goodsover = this.redisTools.get(MiaoshaKey.isGoodsOver, "" + goodsId, boolean.class);
        return goodsover;
    }

    /**重置数据库秒杀订单和订单**/
    public void reset(List<GoodsVo> goodsList) {
        this.goodsService.resetStock(goodsList);
        this.orderService.deleteOrders();
    }


    public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
        if(user == null || path == null) {
            return false;
        }
        String pathOld = redisTools.get(MiaoshaKey.getMiaoshaPath, ""+user.getId() + "_"+ goodsId, String.class);
        return path.equals(pathOld);
    }

    public String createMiaoshaPath(MiaoshaUser user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisTools.set(MiaoshaKey.getMiaoshaPath, ""+user.getId() + "_"+ goodsId, str);
        return str;
    }
}
