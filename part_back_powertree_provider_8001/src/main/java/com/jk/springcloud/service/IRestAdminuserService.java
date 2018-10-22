package com.jk.springcloud.service;

import com.jk.resource.model.AdminUser;

import java.util.List;

public interface IRestAdminuserService {

    /**
     * 根据用户名查询用户信息
     * @param username ： 用户名
     * @return 用户存在则返回用户信息，否则返回null
     */
    public AdminUser getUsesInfoByUsername(String username);

}
