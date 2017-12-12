package com.zhiweism.text.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hankcs.hanlp.HanLP;
import com.zhiweism.text.database.FilterDao;
import com.zhiweism.text.filter.WordFilter;
import com.zhiweism.text.keywords.ChineseToPinyin;

/**
 * 关键词辅助类
 * @author Administrator
 *
 */
public class TextUtils {
	
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
		List<String> data = HanLP.extractKeyword(content, count);
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
		List<String> data = HanLP.extractKeyword(content, count);
		return data;
	}
	
	public static String extractSummary(String content,int num,int length) {
		StringBuffer sb = new StringBuffer();
		String keyword = null;
		List<String> data = HanLP.extractSummary(content, num);
		for(int i = 0;i<data.size();i++) {
			keyword = data.get(i);
			if(sb.length() > length)
				break;
			sb.append(keyword);
		}
		data.clear();
		return sb.toString();
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
		return FilterDao.getInstance().addFilter(keywords);
	}
	
	
	/**
	 * 删除敏感词
	 * @param keywords
	 * @return
	 */
	public static boolean delFilter(String keywords) {
		return FilterDao.getInstance().delFilter(keywords);
	}
	
	/**
	 * 分页获取敏感词
	 * @param page
	 * @param num
	 * @return
	 */
	public static List<Map<String,Object>> getData(int page,int num){
		return FilterDao.getInstance().getFilterKeywords(page, num);
	}
	
	
	/***
	 * 获取敏感词总数
	 * @return
	 */
	public static int getDataTotal() {
		return FilterDao.getInstance().getFilterCount();
	}
}
