package com.jk.miaosha.controller;

import com.jk.miaosha.RestOrderService;
import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.service.impl.OrderServiceImpl;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : RestOrderController
 * @Author : xiaoqiang
 * @Date : 2018/11/5 15:07:07
 * @Description :
 * @Version ： 1.0
 */
@RestController
public class RestOrderController implements RestOrderService {
    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public Result<MiaoshaOrder> getMiaoshaOrderByUserIdAndGoodsId(@RequestParam(value = "userId")long userId,@RequestParam(value = "goodsId") long goodsId) {
        return Result.success(this.orderService.getMiaoshaOrderByUserIdAndGoodsId(userId,goodsId));
    }

    @Override
    public Result<OrderInfo> getOrderById(@RequestParam(value = "orderId") long orderId) {
        return Result.success(this.orderService.getOrderById(orderId));
    }
}
