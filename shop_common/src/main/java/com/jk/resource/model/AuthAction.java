package com.jk.resource.model;

import com.jk.framework.database.DynamicField;
import com.jk.framework.database.PrimaryKeyField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 权限点实体
 * @author xiaoxiaoqiang
 * 2018-10-19
 * @version 1.1
 */
public class AuthAction extends DynamicField implements Serializable{

	private static final long serialVersionUID = 1840480494125749543L;
	/**
	 * 权限点Id
	 */
	private Integer actid;
	
	/**
	 * 权限的名称
	 */
	private String name;
	
	/**
	 * 权限类型
	 */
	private String type;
	
	/**
	 * 对象值
	 */
	private String objvalue;	
	
	/**
	 * 是否为系统默认权限
	 */
	private int choose;
	
	@PrimaryKeyField
	public Integer getActid() {
		return actid;
	}
	public void setActid(Integer actid) {
		this.actid = actid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getObjvalue() {
		return objvalue;
	}
	public void setObjvalue(String objvalue) {
		this.objvalue = objvalue;
	}
	public int getChoose() {
		return choose;
	}
	public void setChoose(int choose) {
		this.choose = choose;
	}
}
