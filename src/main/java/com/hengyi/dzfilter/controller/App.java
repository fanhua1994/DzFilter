package com.hengyi.dzfilter.controller;

import com.hengyi.dzfilter.utils.TextUtils;

/**
 * Hello world!
 */
public class App
{
    public static void main( String[] args )
    {
    	String res = TextUtils.filter("我是董志平，你好");
    	System.out.println(res);
    }
}
