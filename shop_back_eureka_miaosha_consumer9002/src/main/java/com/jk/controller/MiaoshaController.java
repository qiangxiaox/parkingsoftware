package com.jk.controller;

import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.rabbitmq.miaosha.MQSender;
import com.jk.rabbitmq.miaosha.MiaoshaMessage;
import com.jk.redis.RedisTools;
import com.jk.redis.key.GoodsKey;
import com.jk.service.IGoodsService;
import com.jk.service.IMiaoshaService;
import com.jk.service.IOrderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 *
 *覆写InitializingBean 中的afterPropertiesSet()方法，在系统初始化的时候加载一些内容
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private RedisTools redisTools;
    @Autowired
    private IMiaoshaService miaoshaService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    MQSender sender;

    /**内存标记，标记是否还有库存，标记key=goodsId,value=true,false,某商品是否还有秒杀库存**/
    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();


    @Override
    public void afterPropertiesSet() throws Exception {
        Result<List<GoodsVo>> allGoodsVOResult = this.goodsService.getAllGoodsVO();
        List<GoodsVo> goodsVoList = allGoodsVOResult.getData();
        if(goodsVoList == null){
            return ;
        }
    //系统初始化时，将秒杀商品的库存放到mq中
        for(GoodsVo goodsVo : goodsVoList){
            this.redisTools.set(GoodsKey.getMiaoshaGoodsStock, ""+goodsVo.getId(), goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(), false);
        }
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
    public Result<Integer> doMiaosha2(Model model,MiaoshaUser user,
                            @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
    //预减库存
        Long stock = this.redisTools.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if(stock <= 0){
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

    //判断是否已经秒杀到了，(②两个请求req1,req2判断，发现都没有秒杀到)
        Result<MiaoshaOrder> MiaoshaOrderResult = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        MiaoshaOrder order = MiaoshaOrderResult.getData();
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
    //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);

    //返回结果
        return Result.success(0);//排队中
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model,MiaoshaUser user,
                                      @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        return miaoshaService.getMiaoshaResult(user.getId(), goodsId);
    }
}
