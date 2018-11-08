package com.jk.user;

import com.jk.miaosha.domain.MiaoshaUser;
import com.jk.miaosha.result.CodeMsg;
import com.jk.miaosha.result.Result;
import com.jk.miaosha.vo.LoginVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface RestUserService {

    @PostMapping("/user/api/login")
    public CodeMsg login(@RequestBody LoginVo loginVo);

    @GetMapping("/user/api/getById")
    public Result<MiaoshaUser> getById(@RequestParam(value = "id")String id);


}
