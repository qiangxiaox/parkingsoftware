package com.jk.config;

import com.jk.cookie.CookieUtil;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.redis.RedisTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : UserArgumentResolver
 * @Author : xiaoqiang
 * @Date : 2018/11/2 20:26:26
 * @Description :
 * @Version ： 1.0
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisTools redisTools;

    /**
     * 判断对什么类型的参数进行解析
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return parameterType == MiaoshaUser.class;
    }

    /**
     * 进行实际的参数解析操作
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        HttpServletResponse response = (HttpServletResponse) nativeWebRequest.getNativeResponse();

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
