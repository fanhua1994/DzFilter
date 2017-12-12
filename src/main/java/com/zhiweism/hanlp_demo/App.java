package com.zhiweism.hanlp_demo;

import com.zhiweism.text.database.DbHelper;
import com.zhiweism.text.database.FilterDao;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	boolean result = DbHelper.getInstance().existTable("filter_wd");
    	System.out.println(result);
    }
}
