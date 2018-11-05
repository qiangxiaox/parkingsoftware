package com.jk.miaosha.controller;

import com.jk.miaosha.UserService;
import com.jk.miaosha.domain.User;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : DemoController
 * @Author : xiaoqiang
 * @Date : 2018/11/1 23:37:37
 * @Description :
 * @Version ： 1.0
 */
@RestController
public class DemoController implements UserService {
    @Autowired
    private ServiceImpl userService;

    @Override
    public Result<User> getById(int id) {
        User user = this.userService.getById(id);
        return Result.success(user);
    }

    @Override
    public Result<Boolean> tx() {
        return Result.success(this.userService.tx());
    }
}
