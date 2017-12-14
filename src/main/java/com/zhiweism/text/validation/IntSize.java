package com.zhiweism.text.validation;

public @interface IntSize {
	String message();
	int minvalue() default 0;   //最小长度
    int maxvalue() default 0;   //最大长度
}
