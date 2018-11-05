package com.jk.miaosha.service.impl;

import com.jk.miaosha.dao.IUserDAO;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.util.MD5Util;
import com.jk.miaosha.vo.LoginVo;
import com.jk.redis.RedisTools;
import com.jk.redis.key.MiaoshaUserKey;
import com.jk.redis.key.UserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private RedisTools redisTools;

    public MiaoshaUser getById(long id){
    //取缓存
        MiaoshaUser user = this.redisTools.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if(user != null){
            return user;
        }
    //取数据库
        user = this.misoshaUserDAO.getById(id);
        if(user != null){ //放入缓存
            this.redisTools.set(UserKey.getById, ""+id, user);
        }
        return user;
    }

    /**
     * 上面加入了对象缓存之后，一定要注意后面对于用户的操作
     * 如果修改了用户的信息，那么就必须要修改缓存中的内容，否则就会出现数据不一致的情况
     * 而此时我们加入的缓存有User信息，和Token信息，但是此时Token只能更新，不能删除，否则重新登录了
     * @param token 用户的原Token
     * @param id 用户的ID
     * @param formPass 表单提交过来的新密码
     * @return
     */
    @Transactional
    public CodeMsg updatePassword(String token,long id,String formPass){
        //取user，判断是否存在
        MiaoshaUser user = this.getById(id);
        if(user == null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        //更新数据库,尽量是改什么字段单独改，而不是将所有的信息都改一遍
       String password = MD5Util.formPassToDBPass(formPass, user.getSalt());
        int num = this.misoshaUserDAO.doUpdateMiaoshaUserPassword(id,password);
        //处理缓存
        redisTools.delete(MiaoshaUserKey.getById, ""+id);
        user.setPassword(password);
        redisTools.set(MiaoshaUserKey.token, token, user);

        return CodeMsg.SUCCESS;
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
