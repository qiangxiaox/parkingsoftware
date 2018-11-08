package com.jk.order.service.impl;

import com.jk.api.InvokeMiaoshaAPI;
import com.jk.order.dao.IOrderDAO;
import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.redis.RedisTools;
import com.jk.redis.key.MiaoshaOrderKey;
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

    @Autowired
    private InvokeMiaoshaAPI miaoshaAPI;


    /**
     * 根据用户信息和商品信息创建对应的订单信息和秒杀订单信息
     * 这里就体现出了分表的好处，如果一个用户发起两个秒杀订单
     * 那么我们只需要防止miaosha_order中插入同样的两条数据即可，也就是添加唯一索引
     * @param user
     * @param goodsVo
     * @return
     */
    public OrderInfo createOrder(long userId, GoodsVo goodsVo) {
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
        this.orderDAO.doCreateOrder(orderInfo);
        //这样取值得到的是SQL执行的个数，并不是id long orderId =  this.orderDAO.doCreateOrder(orderInfo);
        //获取sql执行完之后的ID，则需要调用对象的方式来获取,之前能成功，是因为id为1
        return orderInfo;
    }

    public OrderInfo getOrderById(long orderId) {
        return this.orderDAO.findById(orderId);
    }

    public void deleteOrders() {
        this.orderDAO.doRemoveAllOrders();
    }
}
