package com.jk.service.auth;

import com.jk.resource.model.AdminUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pages/back/admin/")
public interface IAdminUserService {

    /**
     * 系统登录
     * @param username
     * @return 返回登录成功的用户id
     */
    @GetMapping("/login")
    public AdminUser getUserInfoByUsername(String username);

}
