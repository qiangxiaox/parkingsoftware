package com.jk.user.controller;

import com.jk.user.RestUserService;
import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.user.service.impl.UserServiceImpl;
import com.jk.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : RestMiaoshaUserController
 * @Author : xiaoqiang
 * @Date : 2018/11/2 14:48:48
 * @Description :
 * @Version ： 1.0
 */
@RestController
public class RestUserController implements RestUserService {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public CodeMsg login(@RequestBody LoginVo loginVo) {
        return this.userService.login(loginVo);
    }

    @Override
    public Result<MiaoshaUser> getById(@RequestParam(value = "id") String id) {
        return Result.success(this.userService.getById(Long.valueOf(id)));
    }
}
