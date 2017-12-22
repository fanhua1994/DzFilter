package com.zhiweism.controller;

import java.io.IOException;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.zhiweism.text.keywords.KeywordUtils;

public class App4 { 
	 
	 public static void main(String[] args) { 
		 String key = "威远县隶属四川省内江市，地处内江市西北部，位于四川盆地中南部，地跨北纬29°22′～29°47′，东经104°16′～104°53′之间。东邻内江市市中区，南连自贡市大安区和贡井区，西界自贡市荣县，北衔资中县，西北与眉山市仁寿县、乐山市井研县接壤。";
		 List<String> data = HanLP.extractKeyword(key, 10);
		 System.out.println("HanKp:" + data.toString());
		 try {
			List<String> data2 = KeywordUtils.getKeyWords(key, 10);
			System.out.println("IK:" + data2.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
}