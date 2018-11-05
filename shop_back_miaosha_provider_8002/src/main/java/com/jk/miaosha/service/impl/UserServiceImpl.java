package com.jk.miaosha.service.impl;

import com.jk.miaosha.dao.IUserDAO;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.util.MD5Util;
import com.jk.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : MiaoshaUserServiceImpl
 * @Author : xiaoqiang
 * @Date : 2018/11/2 14:37:37
 * @Description :
 * @Version ： 1.0
 */
@Service
public class UserServiceImpl {
    @Autowired
    private IUserDAO misoshaUserDAO;

    public MiaoshaUser getById(long id){
        return this.misoshaUserDAO.getById(id);
    }

    public CodeMsg login(LoginVo loginVo){
        if(loginVo == null){
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        //判断手机号是否存在
        MiaoshaUser user = this.getById(Long.valueOf(mobile));
        if(user == null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
    //验证密码
        //数据库中的密码
        String dbPass = user.getPassword();
        //数据库中的盐
        String saltDB = user.getSalt();
        //计算表单传过来的加密后的密码
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }

}
