package com.zhiweism.controller;

import java.util.Scanner;

import com.zhiweism.text.filter.WordFilter;
import com.zhiweism.text.utils.TextUtils;

public class App3 {
	
	public static void main(String[] args) {
		/**
		 * 过滤
		 */
		String s = TextUtils.filter("你好董志平,欢迎您");
		System.out.println(s);
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		WordFilter.resetInit();
		s = TextUtils.filter("你好董志平,欢迎您");
		System.out.println(s);
	}

}
