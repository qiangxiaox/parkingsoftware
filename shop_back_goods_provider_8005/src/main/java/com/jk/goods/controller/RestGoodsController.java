package com.jk.goods.controller;

import com.jk.goods.service.impl.GoodsServiceImpl;
import com.jk.goods.RestGoodsService;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : RestMiaoshaGoodsController
 * @Author : xiaoqiang
 * @Date : 2018/11/5 11:44:44
 * @Description :
 * @Version ： 1.0
 */
@RestController
public class RestGoodsController implements RestGoodsService {
    @Autowired
    private GoodsServiceImpl goodsService;

    @Override
    public Result<List<GoodsVo>> getAllGoodsVO() {
        return Result.success(this.goodsService.getAllGoodsVO());
    }

    @Override
    public Result<GoodsVo> getGoodsVOById(@RequestParam(value = "goodsId") long goodsId) {
        return Result.success(this.goodsService.getGoodsVOById(goodsId));
    }

    @Override
    public Result<Integer> reduceMiaoshaGoodsStock(@RequestParam(value = "goodsId")long goodsId) {
        return Result.success(this.goodsService.reduceMiaoshaGoodsStock(goodsId));
    }

    @Override
    public Result<CodeMsg> resetStock(@RequestBody List<GoodsVo> goodsList) {
        this.goodsService.resetStock(goodsList);
        return Result.success(CodeMsg.SUCCESS);
    }
}
