package com.zhiweism.controller;

import java.io.IOException;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.zhiweism.text.keywords.KeywordUtils;

public class App4 { 
	 
	 public static void main(String[] args) { 
		 String key = "今天是我的生日，我想吃生日蛋糕";
		 List<String> data = HanLP.extractKeyword(key, 5);
		 System.out.println("HanKp:" + data.toString());
		 try {
			List<String> data2 = KeywordUtils.getKeyWords(key, 5);
			System.out.println("IK:" + data2.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
}