package com.hengyi.dzfilter.controller;

import java.util.Scanner;

import com.hengyi.dzfilter.utils.TextUtils;

public class App3 {
	
	public static void main(String[] args) {
		/**
		 * 过滤
		 */
		String s = TextUtils.filter("你好董志平,欢迎您");
		System.out.println(s);
		
		TextUtils.addFilter("哈哈哈哈");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		
	}

}
