package com.jk.controller;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.miaosha.vo.OrderDetailVo;
import com.jk.redis.RedisTools;
import com.jk.service.IGoodsService;
import com.jk.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private RedisTools redisTools;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IOrderService orderService;
	

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
									  @RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
		Result<OrderInfo> orderInfoResult = orderService.getOrderById(orderId);
		OrderInfo order = orderInfoResult.getData();
		if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
		Result<GoodsVo> goodsVOResult = goodsService.getGoodsVOById(goodsId);
		GoodsVo goods = goodsVOResult.getData();
		OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
