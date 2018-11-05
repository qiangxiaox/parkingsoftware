package com.jk.shiro;

import com.jk.resource.model.AdminUser;
import com.jk.service.AdminUserService;
import com.jk.util.MyPasswordEncrypt;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName : AdminRealm
 * @Author : xiaoqiang
 * @Date : 2018/10/31 19:08:08
 * @Description :
 * @Version ： 1.0
 */
public class AdminRealm extends AuthorizingRealm {

    @Resource
    AdminUserService userService;

    @SuppressWarnings("unchecked")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("***********************1、用户登录认证：doGetAuthenticationInfo()");
        //1、登录认证的方法需要先执行，需要用他来判断登录的用户信息是否合法
        String username = (String) token.getPrincipal();	//取得用户名
        //需要通过用户名取得用户的完整信息，利用业务层操作
        AdminUser vo = null;
        try {
            vo = this.userService.getUserInfoByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }	//需要取得的是用户的信息
        if(vo == null){
            throw new UnknownAccountException("该用户名称不存在");
        }else{ //进行密码的验证处理
           // String password = new String((char[])token.getCredentials());
            String password = MyPasswordEncrypt.encrypPassword(new String((char[])token.getCredentials()));
            //将数据库中的密码与输入的密码进行比较，这样就可以确定当前用户是否可以正常登陆
            if(vo.getPassword().equals(password)){
                AuthenticationInfo auth = new SimpleAuthenticationInfo(username,password,"AdminRealm");
                return auth;
            }else{
                throw new IncorrectCredentialsException("密码错误！");
            }
        }
    }



}
