package com.jk.user.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
	
//	private static Properties props;
//
//	static {
//		try {
//			InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.yml");
//			props = new Properties();
//			props.load(in);
//			in.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	public static Connection getConn() throws Exception{
		String url = "jdbc:mysql://localhost:3306/miaosha";
		String username = "root";
		String password = "mysqladmin";
		String driver = "org.gjt.mm.mysql.Driver";
		Class.forName(driver);
		return DriverManager.getConnection(url,username, password);
	}
}
