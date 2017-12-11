package com.zhiweism.hanlp_demo;

import com.zhiweism.text.filter.WordFilter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String result = WordFilter.doFilter("我是董志平，我为自己代言");
    	System.out.println(result);
    }
}
