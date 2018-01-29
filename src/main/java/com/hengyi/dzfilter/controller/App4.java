package com.hengyi.dzfilter.controller;

import java.io.IOException;
import java.util.List;

import com.hengyi.dzfilter.keywords.KeywordUtils;

public class App4 { 
	 
	 public static void main(String[] args) { 
		 String key = "rabbit配置文件：\r\n" + 
		 		"\r\n" + 
		 		"<!--这里是连接rabbitmq的服务器-->\r\n" + 
		 		"\r\n" + 
		 		"<rabbit:connection-factoryid=\"connectionFactory\"host=\"localhost\"username=\"guest\"password=\"guest\"/>\r\n" + 
		 		"\r\n" + 
		 		"<!--这是用来发送消息的时候用到，在rabbitmq里面实际上只能用Message,内部将调用\r\n" + 
		 		"\r\n" + 
		 		"send(exchange, routingKey, convertMessageIfNecessary(object),correlationData);\r\n" + 
		 		"\r\n" + 
		 		"来将用户的对象转成message，这个在转换里面就用到了-->\r\n" + 
		 		" <beanid=\"jsonMessageConverter\"class=\"org.springframework.amqp.support.converter.JsonMessageConverter\"/>\r\n" + 
		 		"\r\n" + 
		 		"<!-- 用来发送消息，这里制定了交换区的名字、路由关键字的名字以及消息的“转换器”-->";
		
		 try {
			List<String> data2 = KeywordUtils.getKeyWords(key, 20);
			System.out.println("IK:" + data2.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	 } 
}