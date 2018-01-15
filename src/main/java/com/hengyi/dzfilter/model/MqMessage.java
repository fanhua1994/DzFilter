package com.hengyi.dzfilter.model;

import java.io.Serializable;

public class MqMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String host;
	private String message;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
