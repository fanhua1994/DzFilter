package com.hengyi.dzfilter.database;

import java.util.List;
import java.util.Map;
/**
 * 操作过滤库的Dao
 * @author Administrator
 *
 */
public class FilterDao {
	private static FilterDao instance = null;
	
	public static synchronized FilterDao getInstance() {
		synchronized (FilterDao.class) {
			if(instance == null) {
				instance = new FilterDao();
			}
		}
		return instance;
	}
	
	/**
	 * 
	 * 添加关键词
	 * @param keywords
	 * @return
	 */
	public boolean addFilter(String keywords) {
		boolean isok = DbHelper.getInstance().executeUpdate("insert into filter_wd(keywords)values('%s')", keywords);
		return isok;
	}
	
	/**
	 * 添加关键词 返回影响行数
	 * @param keywords
	 * @return
	 */
	public int addFilter2(String keywords) {
		return DbHelper.getInstance().executeUpdate2("insert into filter_wd(keywords)values('%s')", keywords);
	}

	/**
	 * 删除关键词
	 * @param keywords
	 * @return
	 */
	public boolean delFilter(String keywords) {
		boolean isok = DbHelper.getInstance().executeUpdate("delete from filter_wd where keywords='%s'", keywords);
		return isok;
	}
	
	public int delFilter2(String keywords) {
		return DbHelper.getInstance().executeUpdate2("delete from filter_wd where keywords='%s'", keywords);
	}
	
	/**
	 * 
	 * @param page	当前分页
	 * @param num	每页数量
	 * @return
	 */
	public List<Map<String,Object>> getFilterKeywords(int page,int num){
		int offset = (page - 1) * num;
		return DbHelper.getInstance().select("select keywords from filter_wd limit %d,%d", offset,num);
	}
	
	public List<Map<String,Object>> getFilterKeywords2(int offset,int limit){
		return DbHelper.getInstance().select("select keywords from filter_wd limit %d,%d", offset,limit);
	}
	
	public int getFilterCount() {
		return DbHelper.getInstance().fieldInt("select count(*) as total from filter_wd");
	}
}
