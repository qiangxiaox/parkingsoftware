package com.jk.springcloud.controller;

import com.jk.resource.model.AdminUser;
import com.jk.service.auth.IAdminUserService;
import com.jk.springcloud.service.IRestAdminuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName : AdminUserController
 * @Author : xiaoqiang
 * @Date : 2018/10/22 15:08:08
 * @Description :
 * @Version ： 1.0
 */
@RestController
public class RestAdminUserController implements IAdminUserService {

    @Autowired
    private IRestAdminuserService restAdminuserService;


    @Override
    public AdminUser getUserInfoByUsername(String username) {
        try {
            return this.restAdminuserService.getUsesInfoByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getAuthcMessage(String userid){
        try {
            return this.restAdminuserService.listByAdminUser(userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
