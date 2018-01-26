package com.hengyi.dzfilter.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.hengyi.dzfilter.config.Config;
import com.hengyi.dzfilter.model.MqMessage;
import com.hengyi.dzfilter.utils.PropertiesUtils;
import com.hengyi.dzfilter.utils.TextUtils;

public class SyncMessageListener implements MessageListener{

	public void onMessage(Message message) {
		System.out.println("收到Sync消息");
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			MqMessage mqMessage = (MqMessage) objectMessage.getObject();
			
			boolean is_mysql = PropertiesUtils.getBooleanValue("dzfilter.db.is_mysql");
			String server_id = PropertiesUtils.getValue("dzfilter.cluster.server_id");
			if(server_id.equals(mqMessage.getServer_id())) {
				System.out.println("自己收到自己的通知，作废");
				return ;
			}
			
			switch(mqMessage.getCmd()) {
			case Config.CMD_SYNC:
				if(is_mysql)
					TextUtils.sync(false);
				break;
			case Config.CMD_ADD:
				if(is_mysql) {
					TextUtils.sync(false);
				}else {
					TextUtils.addFilter(mqMessage.getMessage(),false);
				}
				break;
				
			case Config.CMD_DELETE:
				if(is_mysql) {
					TextUtils.sync(false);
				}else {
					TextUtils.delFilter(mqMessage.getMessage(),false);
				}
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
