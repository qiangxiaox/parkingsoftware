package com.jk.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface AccessLimit {
    /**访问限制时间，比如5秒内最大访问次数**/
    int seconds();
    /**最大访问次数**/
    int maxCount();
    /**是否需要登录，默认为true**/
    boolean needLogin() default true;
}
