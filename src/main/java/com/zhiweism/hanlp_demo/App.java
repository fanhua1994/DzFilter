package com.zhiweism.hanlp_demo;

import com.zhiweism.text.database.FilterDao;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	int result = FilterDao.getInstance().getFilterCount();
    	System.out.println(result);
    }
}
