package com.jk.controller;

import com.jk.access.AccessLimit;
import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.rabbitmq.miaosha.MQSender;
import com.jk.rabbitmq.miaosha.MiaoshaMessage;
import com.jk.redis.RedisTools;
import com.jk.redis.key.GoodsKey;
import com.jk.redis.key.MiaoshaKey;
import com.jk.redis.key.MiaoshaOrderKey;
import com.jk.service.IGoodsService;
import com.jk.service.IMiaoshaService;
import com.jk.service.IOrderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<GoodsVo> goodsVoList= this.goodsService.getAllGoodsVO().getData();
        if(goodsVoList == null){
            return ;
        }
    //系统初始化时，将秒杀商品的库存放到mq中
        for(GoodsVo goodsVo : goodsVoList){
            //设置每件商品的秒杀库存
            this.redisTools.set(GoodsKey.getMiaoshaGoodsStock, ""+goodsVo.getId(), goodsVo.getStockCount());
            //设置每件商品的秒杀状态，初始为false
            this.redisTools.set(MiaoshaKey.isGoodsOver, ""+goodsVo.getId(), false);
            localOverMap.put(goodsVo.getId(), false);
        }
    }

    @AccessLimit(seconds=1, maxCount=5000, needLogin=true)
    @RequestMapping(value = "/domiaosha",method = RequestMethod.GET)
    @ResponseBody
    public Result<Integer> doMiaosha(Model model,MiaoshaUser user,
                                      @RequestParam("goodsId") long goodsId
                                      ) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了，(②两个请求req1,req2判断，发现都没有秒杀到)
        MiaoshaOrder order = miaoshaService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId).getData();
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //预减库存
        Long stock = this.redisTools.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if(stock <= 0){
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
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
     *  GET POST有什么区别？
     *  Get  操作是幂等，发多次请求服务器执行结果一样，比如查询
     *  Post 一般操作非幂等，修改，删除，新增等等
     *  压测过程中发现有超卖现象产生
     *      在实际的开发中，秒杀到订单之后肯定会由验证码防止一个用户发起两次秒杀请求
     *      但是为了防止超卖发生，最好还是在数据库中添加唯一索引，当插入相同的数据时，报错，则事务回滚
     * */
    @RequestMapping(value = "/{path}/domiaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> doMiaosha2(Model model,MiaoshaUser user,
                            @RequestParam("goodsId") long goodsId,
                            @PathVariable("path") String path) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
    //验证路径是否存在
        boolean check = this.miaoshaService.checkPath(user, goodsId, path).getData();
        if(!check){ //设置请求为不合法
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
    //判断是否已经秒杀到了，(②两个请求req1,req2判断，发现都没有秒杀到)
        MiaoshaOrder order = miaoshaService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId).getData();
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

    //预减库存
        Long stock = this.redisTools.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if(stock <= 0){
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
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
     * 查询秒杀结果
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

    /**
     * 重置数据库和缓存
     * @param model
     * @return
     */
    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        Result<List<GoodsVo>> allGoodsVOResult = this.goodsService.getAllGoodsVO();
        List<GoodsVo> goodsList = allGoodsVOResult.getData();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisTools.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
            this.redisTools.set(MiaoshaKey.isGoodsOver, ""+goods.getId(), false);
        }
        this.redisTools.delete(MiaoshaOrderKey.getMiaoshaOrderByUidGid);
        miaoshaService.doMiaoshaReset(goodsList);
        return Result.success(true);
    }

    /**
     * 获取路径，并限制路径在seconds秒内能访问的maxCount次数，以及是否需要登录
     * @param request
     * @param user
     * @param goodsId
     * @return
     */
    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping(value="/path", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(MiaoshaUser user, @RequestParam("goodsId")long goodsId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        String path = miaoshaService.createMiaoshaPath(user, goodsId).getData();
        return Result.success(path);
    }

}
