package com.jk.springcloud.service;

import com.jk.resource.model.AdminUser;

import java.util.Map;

public interface IRestAdminuserService {

    /**
     * 根据用户名查询用户信息
     * @param username ： 用户名
     * @return 用户存在则返回用户信息，否则返回null
     */
    public AdminUser getUsesInfoByUsername(String username) throws Exception;

    /**
     * 此方法是留给Realm实现授权处理的，主要是根据用户ID查询出所有的角色以及所有对应的权限
     * @param userid
     * @return 返回的数据包含有两个内容:<br>
     * <li>key=allRoles 、 value=所有的哟用户角色</li>
     * <li>key=allActions 、 value=所有用户权限</li>
     * @throws Exception
     */
    public Map<String,Object> listByAdminUser(String userid) throws Exception;


}
