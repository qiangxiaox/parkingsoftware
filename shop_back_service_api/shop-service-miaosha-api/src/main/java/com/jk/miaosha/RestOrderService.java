package com.jk.miaosha;

import com.jk.miaosha.domain.MiaoshaOrder;
import com.jk.miaosha.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RestOrderService {

    @GetMapping("/order/api/getMiaoshaOrderByUserIdAndGoodsId")
    public Result<MiaoshaOrder> getMiaoshaOrderByUserIdAndGoodsId(@RequestParam(value = "userId")long userId,@RequestParam(value = "goodsId") long goodsId);
}
