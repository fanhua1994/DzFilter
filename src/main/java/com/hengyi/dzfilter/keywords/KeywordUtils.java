package com.hengyi.dzfilter.keywords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		//将文字存到目录里面
		IKAnalyzer analyzer = new IKAnalyzer(true);
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        // 索引的存储路径
        Directory directory = null;
        // 索引的增删改由indexWriter创建
        IndexWriter indexWriter = null;
        directory = FSDirectory.open(Paths.get("indexdir"));
        indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 新建FieldType,用于指定字段索引时的信息
        FieldType type = new FieldType();
        // 索引时保存文档、词项频率、位置信息、偏移信息
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        type.setStored(true);// 原始字符串全部被保存在索引中
        type.setStoreTermVectors(true);// 存储词项量
        type.setTokenized(true);// 词条化
        Document doc1 = new Document();
        Field field1 = new Field("content", text1, type);
        doc1.add(field1);
        indexWriter.addDocument(doc1);
        indexWriter.close();
        directory.close();
     
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
