package com.hengyi.dzfilter.controller;

import java.io.IOException;
import java.util.List;

import com.hengyi.dzfilter.keywords.KeywordUtils;
import com.hengyi.dzfilter.utils.HttpHelper;
import com.hengyi.dzfilter.utils.TextUtils;

public class App4 { 
	 
	 public static void main(String[] args) { 
		 String key = HttpHelper.getInstance().httpGet("https://www.jianshu.com/p/33235e6e7c53");
		 key = TextUtils.delHtmlTag(key);
		 try {
			List<String> data2 = KeywordUtils.getKeyWords(key, 20);
			System.out.println("IK:" + data2.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	 } 
}