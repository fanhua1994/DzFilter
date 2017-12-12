package com.zhiweism.text.database;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * Mysql SQLite数据库通用操作类。
 * @author 繁华
 *
 */
public class DbHelper {
	
	private static DbHelper instance = null;
    private static String DBDRIVER = null;  
    private static String DBURL = null;  
    private static String DBUSER = null;  //MYSQL 专享
    private static String DBPASS = null;//MYSQL 专享
    private static Boolean ISMYSQL = false;
	
	/**
	 * 单列模式
	 * @return
	 */
	public static synchronized DbHelper getInstance(){
		synchronized(DbHelper.class) {
			if(instance == null) {
				Properties prop = new Properties();
	     		try {
					prop.load(DbHelper.class.getClassLoader().getResourceAsStream("filter_config.properties"));
					DBURL = prop.getProperty("dbhelper.dburl");
					DBDRIVER = prop.getProperty("dbhelper.driver");
					DBUSER = prop.getProperty("dbhelper.dbuser");
					DBPASS = prop.getProperty("dbhelper.dbpass");
					ISMYSQL = Boolean.parseBoolean(prop.getProperty("dbhelper.is_mysql"));
					instance = new DbHelper();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 是否是mysql数据库
	 * @return
	 */
	public boolean isMYSQL() {
		return ISMYSQL;
	}
	
	/**
	 * 创建服务器连接
	 * @return
	 */
	public Connection connection(){
		Connection c = null;
		 try {
		      Class.forName(DBDRIVER);
		      if(ISMYSQL)
		    	  c = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
		      else
		    	  c = DriverManager.getConnection(DBURL);
		 } catch ( Exception e ) {
			 e.printStackTrace();
		      c = null;
		      System.out.println("连接失败");
		 }
		 return c;
	}
	
	
	/**
	 * 执行一条更新
	 * @param sql
	 * @return
	 */
	public boolean executeUpdate(String sql,Object... args){
		Connection conn = connection();
		if(conn == null){
			return false;
		}
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(String.format(sql, args));
			stmt.close();
			conn.close();
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 查询一条数据
	 * @param sql
	 * @return
	 */
	public Map<String,Object> find(String sql,Object...objects){
		Connection conn = connection();
		if(conn == null){
			return null;
		}
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format(sql, objects));
			ResultSetMetaData rsm = rs.getMetaData();
			Map<String,Object> data = null;

			if(rs.next()){
				data = new HashMap<String,Object>();
				
				for (int i = 1; i <= rsm.getColumnCount(); i++) {// 数据库里从 1 开始  
	                String c = rsm.getColumnName(i);  
	                String v = rs.getString(c);  
	                data.put(c, v);  
	            }  
			}
			
			stmt.close();
			conn.close();
			rs.close();

			return data;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 查询多条数据
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> select(String sql,Object...objects){
		Connection conn = connection();
		if(conn == null){
			return null;
		}
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format(sql, objects));
			ResultSetMetaData rsm = rs.getMetaData();
			Map<String,Object> data = null;
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

			while(rs.next()){
				data = new HashMap<String,Object>();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {// 数据库里从 1 开始  
	                String c = rsm.getColumnName(i);  
	                String v = rs.getString(c);   
	                data.put(c, v); 
	            }  
				list.add(data);
			}
			
			stmt.close();
			conn.close();
			rs.close();

			return list;
		}catch(Exception e){
			return null;
		}
	}
	

	/**
	 * 查询一个字段的数据
	 * @param sql
	 * @return
	 */
	public int fieldInt(String sql,Object...objects){
		Connection conn = connection();
		if(conn == null){
			return 0;
		}
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format(sql, objects));
			
			if(rs.next()){
				result = rs.getInt(1);
			}
			
			stmt.close();
			conn.close();
			rs.close();

			return result;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 查询一个字段的数据
	 * @param sql
	 * @return
	 */
	public String fieldString(String sql,Object...objects){
		Connection conn = connection();
		if(conn == null){
			return null;
		}
		Statement stmt = null;
		ResultSet rs = null;
		String result = null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format(sql, objects));
			
			if(rs.next()){
				result = rs.getString(1);
			}
			
			stmt.close();
			conn.close();
			rs.close();

			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 判断sqlite表是否存在
	 * @param table
	 * @return
	 */
	public boolean existTable(String table) {
		int res = fieldInt("select count(*)  from sqlite_master where type='table' and name = '%s'", table);
		return res == 1;
	}
}
