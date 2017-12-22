package com.zhiweism.controller;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class App4 { 
	 private final static Integer NUM=5; 
	 /** 截取关键字在几个单词以上的数量 */
	 private final static Integer QUANTITY=1; 
	 /** 
	  * 传入String类型的文章，智能提取单词放入list中 
	  * @param article 
	  * @param a 
	  * @return 
	  * @throws IOException 
	  */
	 private static List<String> extract(String article,Integer a) throws IOException { 
		List<String> list =new ArrayList<String>(); //定义一个list来接收将要截取出来单词 
		Analyzer analyzer = new IKAnalyzer(true);  
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(article));  
        CharTermAttribute term= tokenStream.addAttribute(CharTermAttribute.class);    
        tokenStream.reset();  
        while(tokenStream.incrementToken()){    
           // System.out.println(term.toString());    
            list.add(term.toString());
        }    
	    tokenStream.end();  
	    tokenStream.close();  
	    analyzer.close();
	  return list; 
	 } 
	 /** 
	  * 将list中的集合转换成Map中的key，value为数量默认为1 
	  * @param list 
	  * @return 
	  */
	 private static Map<String, Integer> list2Map(List<String> list){ 
		  Map<String, Integer> map=new HashMap<String, Integer>(); 
		  for(String key:list){ //循环获得的List集合 
		   if (list.contains(key)) { //判断这个集合中是否存在该字符串 
		    map.put(key, map.get(key) == null ? 1 : map.get(key)+1); 
		   } //将集中获得的字符串放在map的key键上 
		  } //并计算其value是否有值，如有则+1操作 
		  return map; 
	 } 
	 /** 
	  * 提取关键字方法 
	  * @param article 
	  * @param a 
	  * @param n 
	  * @return 
	  * @throws IOException 
	  */
	 public static String[] getKeyWords(String content,Integer quantity,Integer num) throws IOException { 
		  List<String> keyWordsList= extract(content,quantity);//调用提取单词方法 
		  Map<String, Integer> map=list2Map(keyWordsList); //list转map并计次数 
		  //使用Collections的比较方法进行对map中value的排序 
		  ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String,Integer>>(map.entrySet()); 
		  Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() { 
		   public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) { 
			   return (o2.getValue() - o1.getValue()); 
		   } 
		  }); 
		  if (list.size() < num) num = list.size(); //排序后的长度，以免获得到null的字符 
		  String[] keyWords = new String[num]; //设置将要输出的关键字数组空间 
		  for(int i=0; i< list.size(); i++) { //循环排序后的数组 
			   if (i < num) {
			    keyWords[i]=list.get(i).getKey(); //设置关键字进入数组 
			   } 
		  } 
		  return keyWords; 
	 } 
	 /** 
	  * 
	  * @param article 
	  * @return 
	  * @throws IOException 
	  */
	 public static String[] getKeyWords(String content,int num) throws IOException{ 
		 return getKeyWords(content,2,num); 
	 } 
	 
	 public static void main(String[] args) { 
		  try { 
			  String keyWord = "我想找个音乐酒吧";
			   String [] keywords = getKeyWords(keyWord,10); 
			   for(int i=0; i<keywords.length; i++){ 
			    System.out.println(keywords[i]); 
			   }
		  } catch (IOException e) { 
		   e.printStackTrace(); 
		  } 
	 } 
}