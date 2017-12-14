package com.zhiweism.hanlp_demo;

import com.zhiweism.text.utils.Validation;
import com.zhiweism.text.validation.ValidMsg;

public class ValidationTest {
	
	public static void main(String[] args) {
		User user = new User();
		user.setAddress("四川");
		user.setAge(90);
		user.setName("董志平");
		user.setSex("44444");
		user.setMail("dddddd.com");
		user.setIdcard("5141991013311X");
		
		ValidMsg msg = Validation.AutoVerifiy(user);
		//ValidMsg msg = Validation.StringSize(user.getName(), "用户姓名", 2, 10);//仅演示了校验长度，其他的方法请参考Method.java内部。
		
		if(msg.isPass()){
			System.out.println("验证通过");
		}else{
			System.out.println("验证失败："+msg.getMsg());
		}
	}
}
