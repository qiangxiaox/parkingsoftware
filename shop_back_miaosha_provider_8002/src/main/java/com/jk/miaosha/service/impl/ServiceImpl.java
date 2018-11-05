package com.jk.miaosha.service.impl;

import com.jk.miaosha.dao.DAO;
import com.jk.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName : UserServiceImpl
 * @Author : xiaoqiang
 * @Date : 2018/11/1 23:35:35
 * @Description :
 * @Version ： 1.0
 */
@Service
public class ServiceImpl {
    @Autowired
    private DAO userDAO;

    public User getById(int id) {
        return this.userDAO.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1= new User();
        u1.setId(2);
        u1.setName("2222");
        userDAO.insert(u1);

        User u2= new User();
        u2.setId(1);
        u2.setName("11111");
        userDAO.insert(u2);

        return true;
    }
}
