package com.jk.miaosha;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RestGoodsService {

    @GetMapping("/goods/api/getAllGoodsVO")
    public Result<List<GoodsVo>> getAllGoodsVO();

    @GetMapping("/goods/api/getGoodsVOById")
    public Result<GoodsVo> getGoodsVOById(@RequestParam(value = "goodsId")long goodsId);
}
