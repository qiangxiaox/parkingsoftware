package com.jk.miaosha;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RestMiaoshaService {

    @PostMapping("/miaosha/api/domiaosha")
    public Result<OrderInfo> doMiaosha(@RequestParam("userId") long userId, @RequestBody GoodsVo goodsVo);


    @GetMapping("/miaosha/api/getMiaoshaResult")
    public Result<Long> getMiaoshaResult(@RequestParam("userId") long userId, @RequestParam("goodsId") long goodsId);

}
