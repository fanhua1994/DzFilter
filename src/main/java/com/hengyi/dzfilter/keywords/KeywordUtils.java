package com.hengyi.dzfilter.keywords;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class KeywordUtils {
	
	 /** 
	  * 传入String类型的文章，智能提取单词放入list中 
	  * @param article 
	  * @param a 
	  * @return 
	  * @throws IOException 
	  */
	 private static List<String> parseKeywords(String article,int num) throws IOException { 
		
		Analyzer analyzer = new MyIkAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        // 索引的存储路径
        Directory directory = null;
        // 索引的增删改由indexWriter创建
        IndexWriter indexWriter = null;
        directory = FSDirectory.open(Paths.get("index"));
        
        indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 新建FieldType,用于指定字段索引时的信息
        FieldType type = new FieldType();
        // 索引时保存文档、词项频率、位置信息、偏移信息
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        type.setStored(true);// 原始字符串全部被保存在索引中
        type.setStoreTermVectors(true);// 存储词项量
        type.setTokenized(true);// 词条化
        Document doc1 = new Document();
        Field field1 = new Field("content", article, type);
        doc1.add(field1);
        indexWriter.addDocument(doc1);
        indexWriter.close();
        
        //directory = FSDirectory.open(Paths.get("index"));
        IndexReader reader = DirectoryReader.open(directory);
        // 因为只索引了一个文档，所以DocID为0，通过getTermVector获取content字段的词项
        Terms terms = reader.getTermVector(0, "content");

        // 遍历词项
        TermsEnum termsEnum = terms.iterator();
        BytesRef thisTerm = null;
        String termText = null;
        Map<String, Integer> map = new HashMap<String, Integer>();
        while ((thisTerm = termsEnum.next()) != null) {
            termText = thisTerm.utf8ToString();
            if(termText.length() > 1)
            	map.put(termText, (int) termsEnum.totalTermFreq());
        }

        // 按value排序
        List<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        
       
        reader.close();
        directory.close();

	  return getTopN(sortedMap,num); 
	 } 
	 
	// 获取top-n
    private static List<String> getTopN(List<Entry<String, Integer>> sortedMap, int N) {
    	List<String> list =new ArrayList<String>();
        for (int i = 0; i < N; i++) {
        	list.add(sortedMap.get(i).getKey());
        }
        return list;
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
		 return parseKeywords(content,num);
	 } 
}
