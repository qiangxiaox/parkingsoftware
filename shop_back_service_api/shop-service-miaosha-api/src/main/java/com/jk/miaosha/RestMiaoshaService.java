package com.jk.miaosha;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RestMiaoshaService {

    @PostMapping("/miaosha/api/domiaosha")
    public OrderInfo doMiaosha(@RequestParam("userId") long userId, @RequestBody GoodsVo goodsVo);

}
