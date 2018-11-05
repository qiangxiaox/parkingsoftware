package com.jk.controller;

import com.jk.miaosha.domain.User;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.redis.RedisTools;
import com.jk.redis.key.UserKey;
import com.jk.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName : DemoController
 * @Author : xiaoqiang
 * @Date : 2018/11/1 23:44:44
 * @Description :
 * @Version ： 1.0
 */
@Controller
public class DemoController {
    @Autowired
    private IDemoService demoService;
    @Autowired
    private RedisTools redisTools;

    @RequestMapping("/demo/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello xiaoqiang");
    }

    @RequestMapping("/demo/error")
    @ResponseBody
    public Result<CodeMsg> error(){
        return Result.error(CodeMsg.SESSION_ERROR);
    }

    @RequestMapping("/hello/themaleaf")
    public String themaleaf(Model model) {
        model.addAttribute("name", "Joshua");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        return demoService.getById(1);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        return demoService.tx();
    }

    @RequestMapping("redist/get")
    @ResponseBody
    public Result<User> redisGet(){
       return Result.success(this.redisTools.get(UserKey.getById, ""+2, User.class));
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(2);
        user.setName("xiaoqiang");
        return Result.success(this.redisTools.set(UserKey.getById, "" +user.getId(), user));
    }

}
