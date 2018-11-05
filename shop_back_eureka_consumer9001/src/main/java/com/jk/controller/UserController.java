package com.jk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jk.service.AdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName : UserController
 * @Author : xiaoqiang
 * @Date : 2018/10/23 9:39:39
 * @Description :
 * @Version ： 1.0
 */
@Controller
public class UserController {
//    @Autowired
//    private AdminUserService userService;

    @RequestMapping("/core/admin/admin-user/login")
    @ResponseBody
    public String loginlll(String username,String password){

        JSONArray array = new JSONArray();
         return JSON.toJSONString(array);
    }

    @RequestMapping("/shiroLogin")
    public String login(HttpServletRequest request,Map<String,Object> map){
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        String msg = "";
        if (UnknownAccountException.class.getName().equals(exceptionClassName)|| AuthenticationException.class.getName().equals(exceptionClassName)) {
            msg = "账号不存在";
            //密码错误
        } else if (IncorrectCredentialsException.class.getName().equals(
                exceptionClassName)) {
           msg = "密码错误";
        }
        map.put("msg", msg);
        return "login";
    }


    @RequestMapping("/loginUrl")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "login";
    }


    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }
    @RequestMapping("/warning")
    public String toWarning(){
        return "warning";
    }

}
