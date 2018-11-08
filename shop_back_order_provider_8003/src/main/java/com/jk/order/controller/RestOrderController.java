package com.jk.order.controller;

import com.jk.miaosha.vo.GoodsVo;
import com.jk.order.RestOrderService;
import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.order.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Result<OrderInfo> getOrderById(@RequestParam(value = "orderId") long orderId) {
        return Result.success(this.orderService.getOrderById(orderId));
    }

    @Override
    public Result<CodeMsg> deleteOrders() {
        this.orderService.deleteOrders();
        return  Result.success(CodeMsg.SUCCESS);
    }

    @Override
    public Result<OrderInfo> createOrder(@RequestParam(value = "userId")long userId, @RequestBody GoodsVo goodsVo) {
        return Result.success(this.orderService.createOrder(userId, goodsVo));
    }
}
