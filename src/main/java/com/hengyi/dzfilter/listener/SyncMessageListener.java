package com.hengyi.dzfilter.listener;

import javax.jms.Message;
import javax.jms.MessageListener;

import com.hengyi.dzfilter.utils.PropertiesUtils;
import com.hengyi.dzfilter.wordfilter.WordFilter;

public class SyncMessageListener implements MessageListener{

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if(PropertiesUtils.getValue("dzfilter.cluster.type").equals("slave")) {
			WordFilter.resetInit();
		}
	}

}
