package com.zhiweism.controller;

import java.io.IOException;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.zhiweism.text.keywords.KeywordUtils;

public class App4 { 
	 
	 public static void main(String[] args) { 
		 String key = "韩国乐天集团会长辛东彬因涉嫌贪污1700亿韩元(约合人民币10.3亿元)和渎职被韩国检方提起诉讼。12月14日，韩国检方提请法院判处韩国前总统朴槿惠“亲信干政”事件核心人物崔顺实有期徒刑25年，检方同时要求法院判处涉嫌行贿的乐天集团会长辛东彬入狱4年。辛东彬曾表示自己无法逆父亲辛格浩的心意行事，所犯所有罪行都是辛格浩指示的。";
		 List<String> data = HanLP.extractKeyword(key, 20);
		 System.out.println("HanKp:" + data.toString());
		 try {
			List<String> data2 = KeywordUtils.getKeyWords(key, 20);
			System.out.println("IK:" + data2.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
}