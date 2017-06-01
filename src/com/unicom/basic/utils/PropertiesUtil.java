package com.unicom.basic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {
	static Properties ps;
	
	static{
		ps = new Properties();
		InputStream in = PropertiesUtil.class.getResourceAsStream("config.properties");
		try {
			ps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key){
		return ps.getProperty(key);
	}
}
