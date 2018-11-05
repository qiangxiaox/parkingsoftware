package com.jk.controller;

import com.jk.cookie.CookieUtil;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.util.UUIDUtil;
import com.jk.miaosha.vo.LoginVo;
import com.jk.redis.RedisTools;
import com.jk.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @ClassName : DemoController
 * @Author : xiaoqiang
 * @Date : 2018/11/1 23:44:44
 * @Description :
 * @Version ： 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private RedisTools redisTools;
    @Autowired
    private IUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo) {
        logger.info(loginVo.toString());
    //进行登录判断
      CodeMsg loginMsg = this.miaoshaUserService.login(loginVo);
       if(loginMsg.getCode() == 0){
            String token = UUIDUtil.uuid();
            //此时登录成功，所以该用户必然存在，所以无需进行非空判断
           Result<MiaoshaUser> userResult = this.miaoshaUserService.getById(loginVo.getMobile());
           CookieUtil.addCookie(redisTools,response, token,userResult.getData());
           return Result.success(true);
       }else{
           return Result.error(loginMsg);
       }
    }

}
