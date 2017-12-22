package com.zhiweism.text.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件工具类
 * 
 * @author Administrator
 * 
 */

public class PropertiesUtils {
	private static Properties properties = new Properties();  
	  
    /** 
     * 读取properties配置文件信息 
     */
    static{
        try {
        	properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("java_filter_config.properties"));  
        } catch (IOException e) {  
            e.printStackTrace();   
        }  
    }  
    
    /**
     * 获取值
     * @param key
     * @return
     */
    public static String getValue(String key)  
    {  
        return properties.getProperty(key);  
    } 
    
    public static Boolean getBooleanValue(String key) {
    	return Boolean.parseBoolean(getValue(key));
    }
	
}
