package com.jk.miaosha.controller;

import com.jk.miaosha.RestMiaoshaService;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.domain.OrderInfo;
import com.jk.miaosha.service.impl.MiaoshaServiceImpl;
import com.jk.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public OrderInfo doMiaosha(@RequestParam("userId") long userId, @RequestBody GoodsVo goodsVo) {
        return this.miaoshaService.doMiaosha(userId, goodsVo);
    }
}
