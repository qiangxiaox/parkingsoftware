package com.jk.service.auth;

import com.jk.resource.model.AdminUser;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface IAdminUserService {

    /**
     * 系统登录
     * @param username
     * @return 返回登录成功的用户id
     */
    @GetMapping(value = "/pages/back/admin/userInfo")
    public AdminUser getUserInfoByUsername(@RequestParam(value = "username") String username);

    /**
     * 此方法是留给Realm实现授权处理的，主要是根据用户ID查询出所有的角色以及所有对应的权限
     * @param userid
     * @return 返回的数据包含有两个内容:<br>
     * <li>key=allRoles 、 value=所有的哟用户角色</li>
     * <li>key=allActions 、 value=所有用户权限</li>
     * @throws Exception
     */
    @GetMapping(value = "/pages/back/admin/userAuthc")
    public Map<String,Object> getAuthcMessage(@RequestParam(value = "userid") String userid);

}
