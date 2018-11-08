package com.jk.goods;

import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RestGoodsService {

    @GetMapping("/goods/api/getAllGoodsVO")
    public Result<List<GoodsVo>> getAllGoodsVO();

    @GetMapping("/goods/api/getGoodsVOById")
    public Result<GoodsVo> getGoodsVOById(@RequestParam(value = "goodsId")long goodsId);

    @PostMapping("/goods/api/reduceMiaoshaGoodsStock")
    public Result<Integer> reduceMiaoshaGoodsStock(@RequestParam(value = "goodsId")long goodsId);

    @PostMapping("/goods/api/resetStock")
    public Result<CodeMsg> resetStock(@RequestBody List<GoodsVo> goodsList);

}
