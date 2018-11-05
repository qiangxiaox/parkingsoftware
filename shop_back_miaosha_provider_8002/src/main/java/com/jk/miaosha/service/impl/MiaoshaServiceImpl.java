package com.jk.miaosha.service.impl;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public OrderInfo doMiaosha(long userId, GoodsVo goodsVo){
        //减库存
        this.goodsService.reduceMiaoshaGoodsStock(goodsVo.getId());

        return this.orderService.createOrderAndMiaoshaOrder(userId,goodsVo);
    }

}
