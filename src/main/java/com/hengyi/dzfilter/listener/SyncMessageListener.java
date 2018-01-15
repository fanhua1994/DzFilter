package com.hengyi.dzfilter.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.hengyi.dzfilter.model.MqMessage;
import com.hengyi.dzfilter.utils.PropertiesUtils;
import com.hengyi.dzfilter.utils.TextUtils;

public class SyncMessageListener implements MessageListener{

	public void onMessage(Message message) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			MqMessage mqMessage = (MqMessage) objectMessage.getObject();
			if(!mqMessage.getHost().equals(PropertiesUtils.getValue("dzfilter.cluster.host")))
				TextUtils.sync();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
