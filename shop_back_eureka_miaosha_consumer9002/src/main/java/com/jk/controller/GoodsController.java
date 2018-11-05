package com.jk.controller;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName : DemoController
 * @Author : xiaoqiang
 * @Date : 2018/11/1 23:44:44
 * @Description :
 * @Version ： 1.0
 */
@Controller
@RequestMapping("/goods/")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/to_list")
    public String list(Model model,MiaoshaUser user) {
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        Result<List<GoodsVo>> result = this.goodsService.getAllGoodsVO();
        List<GoodsVo> allGoodsVO = result.getData();
        model.addAttribute("goodsList", allGoodsVO);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable(value = "goodsId") long goodsId) {
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);

        Result<GoodsVo> goodsVoResult = this.goodsService.getGoodsVOById(goodsId);
        GoodsVo goodsVo = goodsVoResult.getData();
        model.addAttribute("goods", goodsVo);

        long startTime = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0; //秒杀状态 0表示还没开始 1 进行中 2结束
        int remainSeconds = 0; //秒杀剩余时间 0进行中 -1已结束

        if(now < startTime ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startTime - now )/1000);
        }else  if(now > endTime){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

}
