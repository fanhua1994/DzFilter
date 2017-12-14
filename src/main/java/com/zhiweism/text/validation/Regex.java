package com.zhiweism.text.validation;

public @interface Regex {
	String message();
	String value() default "";//正则匹配
}
