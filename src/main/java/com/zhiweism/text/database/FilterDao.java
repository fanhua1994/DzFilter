package com.zhiweism.text.database;

import java.util.List;
import java.util.Map;

public class FilterDao {
	private static FilterDao instance = null;
	
	
	public static synchronized FilterDao getInstance() {
		synchronized (FilterDao.class) {
			if(instance == null)
				instance = new FilterDao();
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
		return DbHelper.getInstance().executeUpdate("insert into filter_wd(keywords)values('%s')", keywords);
	}

	/**
	 * 删除关键词
	 * @param keywords
	 * @return
	 */
	public boolean delFilter(String keywords) {
		return DbHelper.getInstance().executeUpdate("delete from filter_wd where keywords='%s'", keywords);
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
	
	public int getFilterCount() {
		return DbHelper.getInstance().fieldInt("select count(*) as total from filter_wd");
	}
}
