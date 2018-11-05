package com.jk.framework.database;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * 动态字段
 * @author xiaoxiaoqiang
 * 2018-10-19
 */
public class DynamicField {
	
	private Map<String,Object> fields;
	public DynamicField(){
		fields = new HashMap<String, Object>();
	}
	
	public void addField(String name,Object value){
		fields.put(name, value);
	}
	
	@NotDbField
	public Map<String,Object> getFields(){
		return fields;
	}
}
