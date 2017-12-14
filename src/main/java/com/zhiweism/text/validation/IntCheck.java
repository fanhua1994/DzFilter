package com.zhiweism.text.validation;

public @interface IntCheck {
	String message();
	int[] value() default {};  //参数
}
