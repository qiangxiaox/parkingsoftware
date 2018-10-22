package com.jk.context;

import com.jk.framework.context.ThreadContextHolder;
import com.jk.resource.model.AdminUser;

import javax.servlet.http.HttpSession;

/**
 * 用户上下文
 * @author kingapex
 *
 */
public abstract class UserConext {
	public static final String CURRENT_ADMINUSER_KEY="curr_adminuser";

	/**
	 * 获取当前登录的管理员
	 * @return 如果没有登录返回null
	 */
	public static AdminUser getCurrentAdminUser(){

		HttpSession sessonContext = ThreadContextHolder.getSession();
		if(sessonContext!=null){
			return (AdminUser) sessonContext.getAttribute(UserConext.CURRENT_ADMINUSER_KEY);
		}
		return null;
	}
}
