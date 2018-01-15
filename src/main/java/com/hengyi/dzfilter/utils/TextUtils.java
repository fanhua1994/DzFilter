package com.hengyi.dzfilter.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hengyi.dzfilter.database.FilterDao;
import com.hengyi.dzfilter.keywords.ChineseToPinyin;
import com.hengyi.dzfilter.keywords.KeywordUtils;
import com.hengyi.dzfilter.wordfilter.WordFilter;

/**
 * 关键词辅助类
 * @author Administrator
 *
 */
public class TextUtils {
	
	/**
	 * 过滤
	 * @param key
	 * @return
	 */
	public static String delSqlSymbol(String key) {
		String regEx="(select|insert|update|delete)"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(key);
		return m.replaceAll("").trim();
	}
	
	
	/**
	 * 删除所有符号  包括中文和英文
	 * @param key
	 * @return
	 */
	public static String delAllSymbol(String key){
		String regEx="[~！@#￥%……&*（）——+|{}：。”“？》《~!@#$%^&*()_+|{}:\"<>?.]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(key);
		return m.replaceAll("").trim();
	}

	/**
	 * 过滤中文所有符号
	 * @param key
	 * @return
	 */
	public static String delChineseSymbol(String key){
		String regEx="[~！@#￥%……&*（）——+|{}：。”“？》《]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(key);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 过滤英文符号
	 * @param key
	 * @return
	 */
	public static String delEnglishSymbol(String key){
		String regEx="[~!@#$%^&*()_+|{}:\\\"<>?.]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(key);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 过滤字母
	 * @param key
	 * @return
	 */
	public static String delWordChar(String key){
		String regEx="[A-Za-z]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(key);
		return m.replaceAll("").trim();
	}
	
	
	/**
	 * 过滤中文汉字
	 * @param key
	 * @return
	 */
	public static String delChineseChar(String key){
		String regEx="[\\u4e00-\\u9fa5]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(key);
		return m.replaceAll("").trim();
	}
	
	public static String delNumberChar(String key) {
		String regEx="[0-9]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(key);
		return m.replaceAll("").trim();
	}
	
	
	/**
	 * 过滤所有标签
	 * @param htmlStr
	 * @return
	 */
	public static String delTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script = p_script.matcher(htmlStr); 
        htmlStr = m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style = p_style.matcher(htmlStr); 
        htmlStr = m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html = p_html.matcher(htmlStr); 
        htmlStr = m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串
    }

	
	/**
	 * 过滤Js标签
	 * @param htmlStr
	 * @return
	 */
	public static String delJsTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
         
        Pattern p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script = p_script.matcher(htmlStr); 
        htmlStr = m_script.replaceAll(""); //过滤script标签 
        return htmlStr.trim(); //返回文本字符串 
    }
	
	/**
	 * 提取关键词  返回关键词
	 * @param content
	 * @param count
	 * @param isPinyin
	 * @param separator
	 * @return
	 */
	public static String extractKeyword(String content,int count,boolean isPinyin,String separator) {
		List<String> data = extractKeyword(content,count);
		if(data == null)
			return null;
		StringBuffer sb = new StringBuffer();
		String keyword = null;
		for(int i = 0;i<data.size();i++) {
			keyword = data.get(i);
			if(isPinyin)
				sb.append(ChineseToPinyin.getFullSpell(keyword));
			else
				sb.append(keyword);
			sb.append(separator);
		}
		data.clear();
		if(sb.length() > 0)
			return sb.toString().substring(0,sb.length() - 1);
		else
			return ChineseToPinyin.getFullSpell(content);
	}
	
	public static List<String> extractKeyword(String content,int count){
		List<String> data = null;
		try {
			data = KeywordUtils.getKeyWords(content, count);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 过滤敏感词
	 * @param content
	 * @return
	 */
	public static String filter(String content) {
		return WordFilter.doFilter(content);
	}
	
	/**
	 * 添加敏感词
	 * @param keywords
	 * @return
	 */
	public static boolean addFilter(String keywords) {
		boolean res =  FilterDao.getInstance().addFilter(keywords);
		if(res) {
			if(PropertiesUtils.getBooleanValue("dzfilter.cluster.open")) {
				ActivemqUtils.SendObjectMessage(1,PropertiesUtils.getValue("dzfilter.cluster.host"),"resetInit");
			}
		}
		sync();
		return res;
	}
	
	
	/**
	 * 删除敏感词
	 * @param keywords
	 * @return
	 */
	public static boolean delFilter(String keywords) {
		boolean res =  FilterDao.getInstance().delFilter(keywords);
		if(res) {
			if(PropertiesUtils.getBooleanValue("dzfilter.cluster.open")) {
				ActivemqUtils.SendObjectMessage(1,PropertiesUtils.getValue("dzfilter.cluster.host"),"resetInit");
			}
		}
		sync();
		return res;
	}
	
	/**
	 * 分页获取敏感词
	 * @param page
	 * @param num
	 * @return
	 */
	public static List<Map<String,Object>> getDataPage(int page,int num){
		return FilterDao.getInstance().getFilterKeywords(page, num);
	}
	
	/**
	 * 分页获取敏感词
	 * @param page
	 * @param num
	 * @return
	 */
	public static List<Map<String,Object>> getDataOffset(int offset,int limit){
		return FilterDao.getInstance().getFilterKeywords2(offset, limit);
	}
	
	
	/***
	 * 获取敏感词总数
	 * @return
	 */
	public static int getDataTotal() {
		return FilterDao.getInstance().getFilterCount();
	}
	
	/**
	 * 同步关键词  适用于分布式同步
	 */
	public static void sync() {
		WordFilter.resetInit();
	}
}
