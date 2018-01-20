package com.hengyi.dzfilter.model;

import java.io.Serializable;

public class MqMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private int id;
	private String server_id;
	private String message;
	private int cmd;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServer_id() {
		return server_id;
	}
	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
}
