package com.zhiweism.text.validation;
/**
 * 公用的正则表达式
 * @author Administrator
 *
 */
public class Regexs {
	public static final String MAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	public static final String PHONE = "^1\\d{10}$";
	public static final String PASSWORD = "^\\w{6,20}$";
}
