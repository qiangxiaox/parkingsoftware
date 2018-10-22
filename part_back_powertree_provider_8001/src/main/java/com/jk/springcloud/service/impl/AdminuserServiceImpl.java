package com.jk.springcloud.service.impl;

import com.jk.context.UserConext;
import com.jk.framework.context.ThreadContextHolder;
import com.jk.resource.model.AdminUser;
import com.jk.springcloud.dao.IAdminUserDAO;
import com.jk.springcloud.service.IRestAdminuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : AdminuserServiceImpl
 * @Author : xiaoqiang
 * @Date : 2018/10/22 15:05:05
 * @Description :
 * @Version ： 1.0
 */
@Service
public class AdminuserServiceImpl implements IRestAdminuserService {

    @Autowired
    private IAdminUserDAO adminUserDAO;

    @Override
    public AdminUser getUsesInfoByUsername(String username) {
        return this.adminUserDAO.findById(username);
    }
}
