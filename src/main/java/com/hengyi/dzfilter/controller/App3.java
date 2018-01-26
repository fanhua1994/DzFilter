package com.hengyi.dzfilter.controller;

import com.hengyi.dzfilter.utils.TextUtils;

public class App3 {
	
	public static void main(String[] args) {
		String s = TextUtils.filter("你好董志平,欢迎您");
		boolean ss = TextUtils.existFilter("你好董志平");
		int addres = TextUtils.addFilter("MMP234");
		System.out.println(s + ":" + ss +":::"+addres);
	}
}
