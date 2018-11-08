package com.jk.miaosha.controller;

import com.jk.miaosha.RestMiaoshaService;
import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.service.impl.MiaoshaServiceImpl;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : RestMiaoshaController
 * @Author : xiaoqiang
 * @Date : 2018/11/5 15:08:08
 * @Description :
 * @Version ： 1.0
 */
@RestController
public class RestMiaoshaController implements RestMiaoshaService {
    @Autowired
    private MiaoshaServiceImpl miaoshaService;

    @Override
    public Result<OrderInfo> doMiaosha(@RequestParam("userId") long userId, @RequestBody GoodsVo goodsVo) {
        return Result.success(this.miaoshaService.doMiaosha(userId, goodsVo));
    }

    @Override
    public Result<Long> getMiaoshaResult(@RequestParam("userId")long userId,@RequestParam("goodsId") long goodsId) {
        return Result.success(miaoshaService.getMiaoshaResult(userId, goodsId));
    }

    @Override
    public Result<Boolean> doMiaoshaReset(@RequestBody List<GoodsVo> goodsVoList) {
        this.miaoshaService.reset(goodsVoList);
        return Result.success(true);
    }

    @Override
    public Result<String> createMiaoshaPath(@RequestBody MiaoshaUser user,@RequestParam("goodsId") long goodsId) {
        return Result.success(this.miaoshaService.createMiaoshaPath(user, goodsId));
    }

    @Override
    public Result<Boolean> checkPath(@RequestBody MiaoshaUser user,@RequestParam("goodsId") long goodsId,@RequestParam("path") String path) {
        return Result.success(this.miaoshaService.checkPath(user, goodsId, path));
    }

    @Override
    public Result<MiaoshaOrder> getMiaoshaOrderByUserIdAndGoodsId(@RequestParam(value = "userId")long userId, @RequestParam(value = "goodsId") long goodsId) {
        return Result.success(this.miaoshaService.getMiaoshaOrderByUserIdAndGoodsId(userId,goodsId));
    }

    @Override
    public Result<CodeMsg> removeAllMiaoshaOrders() {
        this.miaoshaService.removeAllMiaoshaOrders();
        return Result.success(CodeMsg.SUCCESS);
    }
}
