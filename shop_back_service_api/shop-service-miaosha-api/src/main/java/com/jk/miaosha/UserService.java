package com.jk.miaosha;

import com.jk.miaosha.domain.User;
import com.jk.miaosha.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {

    @GetMapping(value = "/test/getuser")
    public Result<User> getById(@RequestParam(value = "id") int id);

    @PostMapping(value = "/test/tx")
    public Result<Boolean> tx();

}
