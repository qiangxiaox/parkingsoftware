package com.jk.miaosha.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName : ValidatorUtil
 * @Author : xiaoqiang
 * @Date : 2018/11/2 14:28:28
 * @Description :
 * @Version ： 1.0
 */
public class ValidatorUtil {
    private static final Pattern mobole_pattern = Pattern.compile( "1\\d{10}");

    public static boolean isMobile(String str){
        if(StringUtils.isEmpty(str)){
            return false;
        }
        Matcher matcher =  mobole_pattern.matcher(str);
        return matcher.matches();
    }


    public static void main(String[] args) {
        System.out.println(isMobile("13333333333"));
        System.out.println(isMobile("23333333333"));
    }
}
