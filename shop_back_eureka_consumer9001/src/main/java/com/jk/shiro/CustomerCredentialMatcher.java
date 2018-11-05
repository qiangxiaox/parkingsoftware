package com.jk.shiro;

import com.jk.util.MyPasswordEncrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @ClassName : CustomerCredentialMatcher
 * @Author : xiaoqiang
 * @Date : 2018/11/1 14:42:42
 * @Description :
 * @Version ： 1.0
 */
public class CustomerCredentialMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //取得原始的输入数据信息
        Object tokenCredentail = MyPasswordEncrypt.encrypPassword(super.toString(token.getCredentials())).getBytes();
        //取得认证数据库中的数据
        Object accountCredentail = super.getCredentials(info);
        return super.equals(tokenCredentail,accountCredentail);
    }
}
