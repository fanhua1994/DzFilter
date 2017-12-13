package com.zhiweism.hanlp_demo;

import com.zhiweism.text.utils.TextUtils;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	String text = TextUtils.delSqlSymbol("我是女孩45update users4~!@#$%^select * from users&*(wqeqweqweqweqweqw)_+|}{:\">?<~！@#￥%……&*（）——+|{}“：？》《大个儿");
    	System.out.println(text);
    }
}
