package com.zhiweism.hanlp_demo;

import com.zhiweism.text.common.ValidMsg;
import com.zhiweism.text.utils.Validation;

public class ValidationTest {
	
	public static void main(String[] args) {
		User user = new User();
		user.setAddress("四川");
		user.setAge(90);
		user.setName("董志平");
		user.setSex("男");
		user.setMail("18383219470@163.com");
		user.setIdcard("511024199410133117");
		
		ValidMsg msg = Validation.AutoVerifiy(user);
		
		if(msg.isPass()){
			System.out.println("验证通过");
		}else{
			System.out.println("验证失败："+msg.getMsg());
		}
	}
}
