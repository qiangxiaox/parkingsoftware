package com.jk.interceptor;

import com.alibaba.fastjson.JSON;
import com.jk.access.AccessLimit;
import com.jk.cookie.CookieUtil;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.usercontext.UserContext;
import com.jk.redis.RedisTools;
import com.jk.redis.key.AccessKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName : AccessInterceptor
 * @Author : xiaoqiang
 * @Date : 2018/11/6 18:40:40
 * @Description :
 * @Version ： 1.0
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private RedisTools redisTools;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if(handler instanceof HandlerMethod){
           MiaoshaUser user = getUser(response, request);
           //将获取到的用户存放到用户上下文中
           UserContext.setUserHolder(user);

           HandlerMethod hm = (HandlerMethod)handler;
           AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){ //获取访问限制的自定义注解
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
        //设置访问路径
            String key = request.getRequestURI();
           if (needLogin) {
               if (user == null) {
                  //返回给浏览器信息
                   render(response,CodeMsg.SESSION_ERROR);
                   return false;
               }
               key += "_" + user.getId();
           }else {
               //do nothing
           }
           //martine flwer ，重构，改善既有代码的设计
           AccessKey accessKey = AccessKey.withExpire(seconds);
           Integer count = redisTools.get(accessKey, key, Integer.class);
           if(count == null){
               redisTools.set(accessKey, key, 1);
           }else if(count < maxCount){
               redisTools.incr(accessKey, key);
           }else{
               render(response,CodeMsg.ACCESS_LIMIT_REACHED);
               return false;
           }
       }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg sessionError) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(sessionError));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private MiaoshaUser getUser(HttpServletResponse response,HttpServletRequest request){
        String paramToken = request.getParameter(CookieUtil.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request,CookieUtil.COOKI_NAME_TOKEN);

        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return CookieUtil.getByToken(redisTools, response, token);
    }

    /**
     * 获取Cookie中对应cookiNameToken值的Cookie并返回其value值
     * @param request
     * @param cookiNameToken
     * @return
     */
    private String getCookieValue(HttpServletRequest request, String cookiNameToken) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for (Cookie cookie : cookies){
            if(cookiNameToken.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
