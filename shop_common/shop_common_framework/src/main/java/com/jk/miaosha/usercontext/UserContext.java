package com.jk.miaosha.usercontext;

import com.jk.miaosha.domain.MiaoshaUser;

/**
*用户上下文，用来保存用户信息，然后可以全局使用
 * @Version ： 1.0
 */
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<>();

    public static void setUserHolder(MiaoshaUser user){
        userHolder.set(user);
    }

    public static MiaoshaUser getUserHolder(){
        return userHolder.get();
    }

}
