package com.hengyi.dzfilter.controller;

import com.hengyi.dzfilter.utils.TextUtils;

/**
 * Hello world!
 */
public class App
{
    public static void main( String[] args )
    {
    	String res = TextUtils.delSqlSymbol("ddUPDATE666666select&&&&&&dd");
    	System.out.println(res);
    }
}
