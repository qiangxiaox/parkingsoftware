package com.jk.springcloud.dao;

import com.jk.resource.model.AdminUser;
import com.jk.resource.model.AuthAction;
import com.jk.resource.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface IAdminUserDAO {

    public AdminUser findById(@Param("username")String username);

    public Set<Role> findAllRoleByMember(@Param("userid")String userid);

    public Set<AuthAction> findAllActionByMember(@Param("userid")String userid);

}
