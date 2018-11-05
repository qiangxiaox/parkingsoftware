package com.jk.controller;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.GoodsDetailVo;
import com.jk.miaosha.vo.GoodsVo;
import com.jk.redis.RedisTools;
import com.jk.redis.key.GoodsKey;
import com.jk.service.IGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private RedisTools redisTools;

    //因为本次的模板是Thymeleaf，所以容器加载时会加载ThymeleafViewResolver的视图解析器，我们这里引入之后可以直接使用
    //你想，视图解析器的作用就是用来自动解析视图的，那么我们是不是也可以利用其进行手动渲染视图嘞？
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String list(Model model, MiaoshaUser user, HttpServletRequest request, HttpServletResponse response) {
        //登录页面后期准备使用modelAttribute来分离出去，此时就不做缓存了
        if(user == null){
            return "login";
        }

    //从redis中取出页面缓存
        String html = this.redisTools.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){ //有缓存，则直接返回缓存
            return html;
        }

        model.addAttribute("user", user);
        Result<List<GoodsVo>> result = this.goodsService.getAllGoodsVO();
        List<GoodsVo> allGoodsVO = result.getData();
        model.addAttribute("goodsList", allGoodsVO);

    //没有缓存，则手动渲染页面
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model
                .asMap(), applicationContext);

        html = this.thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);

        if(!StringUtils.isEmpty(html)){ //保存到redis中
            this.redisTools.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(Model model, MiaoshaUser user, @PathVariable(value = "goodsId") long goodsId, HttpServletRequest request, HttpServletResponse response) {
        if(user == null){
            return "login";
        }
    //从redis中取出页面缓存
        String html = this.redisTools.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html)){ //有缓存，则直接返回缓存
            return html;
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
//        return "goods_detail";


        //没有缓存，则手动渲染页面
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model
                .asMap(), applicationContext);

        html = this.thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);

        if(!StringUtils.isEmpty(html)){ //保存到redis中
            this.redisTools.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        return html;
    }


    /**
     * 页面静态化重构
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/to_detail_static/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail2(Model model, MiaoshaUser user, @PathVariable(value = "goodsId") long goodsId) {

        Result<GoodsVo> goodsVoResult = this.goodsService.getGoodsVOById(goodsId);
        GoodsVo goodsVo = goodsVoResult.getData();

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
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setMiaoshaStatus(miaoshaStatus);
        vo.setRemainSeconds(remainSeconds);
        vo.setGoodsVo(goodsVo);
        vo.setUser(user);
        return Result.success(vo);
    }

}
