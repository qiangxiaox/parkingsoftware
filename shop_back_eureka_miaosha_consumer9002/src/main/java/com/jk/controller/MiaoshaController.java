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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    /**
     *  GET POST有什么区别？
     *  Get  操作是幂等，发多次请求服务器执行结果一样，比如查询
     *  Post 一般操作非幂等，修改，删除，新增等等
     *  压测过程中发现有超卖现象产生
     *      在实际的开发中，秒杀到订单之后肯定会由验证码防止一个用户发起两次秒杀请求
     *      但是为了防止超卖发生，最好还是在数据库中添加唯一索引，当插入相同的数据时，报错，则事务回滚
     * */
    @RequestMapping(value = "/domiaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> doMiaosha2(Model model,MiaoshaUser user,
                            @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        Result<GoodsVo> goodsVoResult= goodsService.getGoodsVOById(goodsId);//10个商品，①此时一个用户发起两个请求，req1 req2
        GoodsVo goods = goodsVoResult.getData();
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了，(②两个请求req1,req2判断，发现都没有秒杀到)
        Result<MiaoshaOrder> MiaoshaOrderResult = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        MiaoshaOrder order = MiaoshaOrderResult.getData();
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //减库存 下订单 写入秒杀订单  (③两个请求同时发起该操作)，于是超卖现象产生了
        OrderInfo orderInfo = miaoshaService.doMiaosha(user.getId(),goods);
        return Result.success(orderInfo);
    }
}
