package com.jk.order;

import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RestOrderService {

    @GetMapping("/order/api/getOrderInfoByOrderId")
    public Result<OrderInfo> getOrderById(@RequestParam(value = "orderId") long orderId);

    @PostMapping("/order/api/deleteOrders")
    public Result<CodeMsg>  deleteOrders();

    @PostMapping("/order/api/createOrder")
    public Result<OrderInfo> createOrder(@RequestParam(value = "userId")long userId, @RequestBody GoodsVo goodsVo);
}
