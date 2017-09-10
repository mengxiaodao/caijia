package com.caijia.modle;

public class CommunicateModle {
	
	private int com_id;
	private int com_role;
	private String com_name;
	private String com_phone;
	private String com_public;
	private String com_title;
	private String com_content;
	private String com_reply;
	private String write_time;
	private String reply_time;
	
	public int getComid() {
		
			
		
		return com_id;
	}
	public void setComid(int com_id) {
		this.com_id = com_id;
	}
		
	public synchronized int getCom_role() {
		return com_role;
	}
	public synchronized void setCom_role(int com_role) {
		this.com_role = com_role;
	}
	public String getComname() {
		if (null == com_name) {
			return "NULL";
		}
		return com_name;
	}
	public void setComname(String com_name) {
		this.com_name = com_name;
	}
	
	public String getComphone() {
		if (null == com_phone) {
			return "NULL";
		}
		return com_phone;
	}
	public void setComphone(String com_phone) {
		this.com_phone = com_phone;
	}
	
	public String getCompublic() {
		if (null == com_public) {
			return "NULL";
		}
		return com_public;
	}
	public void setCompublic(String com_public) {
		this.com_public = com_public;
	}
	
	public String getComtitle() {
		if (null == com_title) {
			return "NULL";
		}
		return com_title;
	}
	public void setComtitle(String com_title) {
		this.com_title = com_title;
	}
	
	
	public String getComcontent() {
		if (null == com_content) {
			return "NULL";
		}
		return com_content;
	}
	public void setComcontent(String com_content) {
		this.com_content = com_content;
	}
	
	
	public String getComreply() {
		if (null == com_reply) {
			return "NULL";
		}
		return com_reply;
	}
	public void setComreply(String com_reply) {
		this.com_reply = com_reply;
	}
	
	public String getWritetime() {
		if (null == write_time) {
			return "NULL";
		}
		return write_time;
	}
	public void setWritetime(String write_time) {
		this.write_time = write_time;
	}
	
	public String getReplytime() {
		if (null == reply_time) {
			return "NULL";
		}
		return reply_time;
	}
	public void setReplytime(String reply_time) {
		this.reply_time = reply_time;
	}
	
	/*public String getPrice() {
		if (null == price) {
			return "NULL";
		}
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}*/
	

}
