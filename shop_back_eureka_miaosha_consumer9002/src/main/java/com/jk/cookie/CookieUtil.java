package com.jk.cookie;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.redis.RedisTools;
import com.jk.redis.key.MiaoshaUserKey;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : CookieUtil
 * @Author : xiaoqiang
 * @Date : 2018/11/2 19:02:02
 * @Description :
 * @Version ： 1.0
 */
public class CookieUtil {
    //默认的客户端浏览器的Cookie的key
    public static final String COOKI_NAME_TOKEN = "token";

    /**
     * 当有请求调用该方法时，则将redis中的session的key值延期
     * @param response
     * @param token
     * @return
     */
    public static MiaoshaUser getByToken(RedisTools redisTools,HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisTools.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if(user != null) {
            addCookie(redisTools,response, token, user);
        }
        return user;
    }

    public static void addCookie(RedisTools redisTools,HttpServletResponse response, String token, MiaoshaUser user){
        redisTools.set(MiaoshaUserKey.token, token,user);
        Cookie cookie = new Cookie(CookieUtil.COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
