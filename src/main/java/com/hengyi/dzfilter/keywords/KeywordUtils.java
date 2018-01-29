package com.hengyi.dzfilter.keywords;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.hengyi.dzfilter.utils.TextUtils;

public class KeywordUtils {
	 /** 
	  * 传入String类型的文章，智能提取单词放入list中 
	  * @param article 
	  * @param a 
	  * @return 
	  * @throws IOException 
	  */
	 private static List<String> extract(String article) throws IOException { 
		List<String> list =new ArrayList<String>();
		IKAnalyzer analyzer = new IKAnalyzer(false);  
		analyzer.setUseSmart(true);//开启智能分词
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(article));  
        CharTermAttribute term= tokenStream.addAttribute(CharTermAttribute.class);    
        tokenStream.reset();  
        String keyword = null;
        while(tokenStream.incrementToken()){ 
        	keyword = term.toString();
        	if(keyword.length() > 1)
            list.add(keyword);
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
	 public static List<String> getKeyWords(String content,Integer num) throws IOException { 
		 //进行文字过滤
		 content = TextUtils.delAllSymbol(content);
		 content = TextUtils.delEnglishSymbol(content);
		 content = TextUtils.delNumberChar(content);
		 
		 List<String> keyWordsList = extract(content);//调用提取单词方法 
		  Map<String, Integer> map = list2Map(keyWordsList); //list转map并计次数 
		  keyWordsList.clear();
		  ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String,Integer>>(map.entrySet()); 
		  Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() { 
		   public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) { 
			   return (o2.getValue() - o1.getValue()); 
		   } 
		  }); 
		  if (list.size() < num) num = list.size(); //排序后的长度，以免获得到null的字符 
		  List<String> keywords = new ArrayList<String>();
		  for(int i=0; i< list.size(); i++) { //循环排序后的数组 
			   if (i < num) {
				   keywords.add(list.get(i).getKey()); //设置关键字进入数组 
				   //System.out.println(list.get(i).getKey() +":投票数 = " + list.get(i).getValue());
			   } 
		  } 
		  return keywords; 
	 } 

	 
	 public static void main(String[] args) { 
		  try { 
			  String keyWord = "今天是我的生日，我想吃生日蛋糕";
			   List<String> data = getKeyWords(keyWord,10); 
			   for(String key : data){ 
			    System.out.println(key); 
			   }
		  } catch (IOException e) { 
		   e.printStackTrace(); 
		  } 
	 } 
	
}
