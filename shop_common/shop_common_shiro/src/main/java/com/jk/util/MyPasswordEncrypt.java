package com.jk.util;

import java.util.Base64;

/**
 * @ClassName : MyPasswordEncrypt
 * @Author : xiaoqiang
 * @Date : 2018/11/1 14:36:36
 * @Description :
 * @Version ： 1.0
 */
public class MyPasswordEncrypt {


    private static final String saltVal = "www.mldn.cn";
    /**
     * 提供有密码的加盐处理操作
     * @param password
     * @return
     */
    public static String encrypPassword(String password){
        String SALT = new String(Base64.getEncoder().encode(saltVal.getBytes()));
        return new MD5Encoding().getMD5ofStr(password  + "{{" + SALT + "}}");
    }

    public static void main(String[] args) {
        System.out.println(encrypPassword("123"));
    }
}
