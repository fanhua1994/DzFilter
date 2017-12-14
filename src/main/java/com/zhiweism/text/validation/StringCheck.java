package com.zhiweism.text.validation;

public @interface StringCheck {
	String message();
	String[] value() default {};  //参数
}
