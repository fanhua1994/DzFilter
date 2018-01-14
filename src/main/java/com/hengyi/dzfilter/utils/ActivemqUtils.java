package com.hengyi.dzfilter.utils;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActivemqUtils {
	
	private static ConnectionFactory connectionFactory; 
	private static String ProjectName = null;
	
	static {
		String activemq_user = PropertiesUtils.getValue("dzfilter.cluster.username");
		String activemq_pass = PropertiesUtils.getValue("dzfilter.cluster.password");
		String activemq_url = PropertiesUtils.getValue("dzfilter.cluster.activemq");
		ProjectName = PropertiesUtils.getValue("dzfilter.cluster.project_name");
		connectionFactory = new ActiveMQConnectionFactory(activemq_user,activemq_pass, activemq_url);
	}
	
	
	/**
	 * 发送普通消息
	 */
	public static boolean SendTextMessage(String text) {
		Connection connection = null; 
        Session session = null; 
        Destination destination = null; 
        MessageProducer producer = null;  
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic(ProjectName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 构造消息，此处写死，项目就是参数，或者方法获取
            session.createTextMessage(text);
            session.commit();
            return true;
        } catch (Exception e) {
           return false;
        } finally {
            try {
                if (null != connection) {
                	session.close();
                    connection.close();
                }
            } catch (Throwable ignore) {
            }
        }
	}

}
