package com.jk.framework.util;

import com.alibaba.fastjson.JSON;
import com.jk.eop.resource.model.AuthAction;
import com.jk.framework.database.DynamicField;
import com.jk.framework.database.NotDbField;
import com.jk.framework.database.PrimaryKeyField;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtil {

	/**
	 * 根据方法名及其参数以及方法所在的类，反射调用并执行方法，并返回对应的结果
	 * @param className 方法所在的类名（全类名也就是指定包结构）：可以类比JDBC连接数据库时加载驱动
	 * @param methodName 方法名
	 * @param args 方法的参数
	 * @return 方法的执行后返回的结果
	 */
	public static Object invokeMethod(String className, String methodName,
			Object[] args) {

		try {

			Class serviceClass = Class.forName(className);
			Object service = serviceClass.newInstance();

			Class[] argsClass = new Class[args.length];
			//循环里，我们一般会将参数的长度直接去比较如：i < args.length ，一直没有考虑到，这样其实是很浪费空间的，每次循环都需要调用类的方法
			//for (int i = 0, j = args.length; i < j; i++) 而这种思路就会很好的解决一部分的程序逻辑
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}

			Method method = serviceClass.getMethod(methodName, argsClass);
			return method.invoke(service, args);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 根据全类名，以及该类构造时需要的参数（参数可有可无） 通过反射获取到对应的构造方法，然后进行初始化
	 * @param className 全类名
	 * @param _args 构造方法的参数
	 * @return 创建对应的实例
	 */
	public static Object newInstance(String className,Object... _args ){

		try {
			  Class[] argsClass = new Class[_args.length];                                  
			                                                                                 
			   for (int i = 0, j = _args.length; i < j; i++) {   
					   
				   if(_args[i]==null){
					   argsClass[i]=null;
				   }
				   else{
					    
					   argsClass[i] = _args[i].getClass();
				   }
			    }      
			   
			   
			 Class newoneClass  = Class.forName(className);
			 Constructor cons = newoneClass.getConstructor(argsClass);                    
             
			 Object obj= cons.newInstance(_args);
			 return obj;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
	
		 
		 return null;
	 
	}

	/**
	 * 将po对象中属性和值转换成map
	 * 
	 * @param po
	 * @return
	 */
	public static Map po2Map(Object po) {
		Map poMap = new HashMap();
		Map map = new HashMap();
		try {
			map = BeanUtils.describe(po);
		} catch (Exception ex) {
		}
		Object[] keyArray = map.keySet().toArray();
		for (int i = 0; i < keyArray.length; i++) {
			String str = keyArray[i].toString();
			if (str != null && !str.equals("class")) {
				if (map.get(str) != null) {
					poMap.put(str, map.get(str));
				}
			}
		}

		//获取类中的所有方法，判断是否是标注过@NotDbField,@PrimaryKeyField注解的方法（这两个注解的作用为标识不是数据库读写的字段）
		//如果标注过，说明不存数据库，所以截取掉该属性
		Method[] ms =po.getClass().getMethods();
		for(Method m:ms){
			String name = m.getName();
			
			if(name.startsWith("get")||name.startsWith("is")){
				if(m.getAnnotation(NotDbField.class)!=null||m.getAnnotation(PrimaryKeyField.class)!=null){
					poMap.remove(getFieldName(name)); 
				} 
			}

		}
		
		/**
		 * 如果此实体为动态字段实体，将动态字段加入
		 */
		if(po instanceof DynamicField){
			DynamicField dynamicField = (DynamicField) po;
			Map fields = dynamicField.getFields();
			poMap.putAll(fields);
		}
		return poMap;
	}

	/**
	 *通过截取getter方法的名称，来获得getter方法的属性
	 * @param methodName 方法名称 <eg>比如getFieldName()方法</eg>
	 * @return 返回getter方法对应的属性名
	 */
	private static String getFieldName(String methodName){
		 
		methodName = methodName.substring(3);
		methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
		return methodName;
	}
	
	public static void main(String[] args){
		//String methodName = "getWidgetList";
		//System.out.println(getFieldName(methodName));
		AuthAction action = (AuthAction)newInstance("com.jk.eop.resource.model.AuthAction");
		action.setActid(1);
		action.setChoose(1);
		action.setName("啦啦啦");
		action.setObjvalue("啦啦啦");
		action.setType("啦啦啦");

		action.addField("我是你的小可爱", "是的，是的");

		Map map = po2Map(action);
		System.out.println(JSON.toJSONString(map));
		System.out.println(map);

	}
}
