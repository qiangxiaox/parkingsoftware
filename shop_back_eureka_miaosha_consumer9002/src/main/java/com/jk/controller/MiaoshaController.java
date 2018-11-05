package com.jk.controller;

import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.redis.RedisTools;
import com.jk.service.IGoodsService;
import com.jk.service.IMiaoshaService;
import com.jk.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName : MiaoshaController
 * @Author : xiaoqiang
 * @Date : 2018/11/5 14:22:22
 * @Description :
 * @Version ： 1.0
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private RedisTools redisTools;
    @Autowired
    private IMiaoshaService miaoshaService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/do_miaosha")
    public String doMiaosha(Model model,MiaoshaUser user,
                            @RequestParam("goodsId") long goodsId) {
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        Result<GoodsVo> goodsVoResult = this.goodsService.getGoodsVOById(goodsId);
        GoodsVo goods = goodsVoResult.getData();
        int miaoshastock = goods.getStockCount();
    //1.判断秒杀库存量
        if(miaoshastock <= 0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
    //2.判断是否秒杀过
        Result<MiaoshaOrder> orderResult = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(),goodsId);
        MiaoshaOrder order = orderResult.getData();
        if(order != null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
    //3.减库存 下订单 写入秒杀订单
        OrderInfo  orderInfo = miaoshaService.doMiaosha(user.getId(),goods);
    //4.将下好的订单传回到页面中
        model.addAttribute("goods", goods);
        model.addAttribute("orderInfo",orderInfo);
        return "order_detail";
    }
}
