package com.jk.miaosha.service.impl;

import com.jk.miaosha.dao.IOrderDAO;
import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName : OrderServiceImpl
 * @Author : xiaoqiang
 * @Date : 2018/11/5 15:09:09
 * @Description :
 * @Version ： 1.0
 */
@Service
public class OrderServiceImpl {
    @Autowired
    private IOrderDAO orderDAO;


    /**
     * 根据用户Id和商品Id查询该用户是否存在有MiaoshaOrder订单数据
     * @param userId
     * @param goodsId
     * @return
     */
    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return this.orderDAO.findMiaoshaOrderByUserIdAndGoodsId(userId,goodsId);
    }

    /**
     * 根据用户信息和商品信息创建对应的订单信息和秒杀订单信息
     * @param user
     * @param goodsVo
     * @return
     */
    @Transactional
    public OrderInfo createOrderAndMiaoshaOrder(long userId, GoodsVo goodsVo) {
        //1.创建订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        //每次只能秒杀一件商品
        orderInfo.setGoodsCount(1);
        //获取秒杀价格
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setCreateDate(new Date());
        orderInfo.setOrderChannel(0);
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setStatus(0);
        orderInfo.setUserId(userId);

       long orderId =  this.orderDAO.doCreateOrder(orderInfo);
        //2.创建秒杀订单
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(userId);

        this.orderDAO.doCreateMiaoshaOrder(miaoshaOrder);

        return orderInfo;
    }
}
