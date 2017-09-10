package com.caijia.modle;

public class CaiModle {
	
	private String news_id;
	private String role;
	private String title;
	private String content;
	private String date;
	private String photo;
	
	public String getNewsid() {
		if (null == news_id) {
			return "NULL";
		}
		return news_id;
	}
	public void setNewsid(String news_id) {
		this.news_id = news_id;
	}
	
	public String getNewsrole() {
		if (null == role) {
			return "NULL";
		}
		return role;
	}
	public void setNewsrole(String role) {
		this.role = role;
	}
	
	public String getNewstitle() {
		if (null == title) {
			return "NULL";
		}
		return title;
	}
	public void setNewstitle(String title) {
		this.title = title;
	}
	
	public String getNewscontent() {
		if (null == content) {
			return "NULL";
		}
		return content;
	}
	public void setNewscontent(String content) {
		this.content = content;
	}
	
	public String getNewsdate() {
		if (null == date) {
			return "NULL";
		}
		return date;
	}
	public void setNewsdate(String date) {
		this.date = date;
	}
	
	public String getNewsphoto() {
		if (null == photo) {
			return "NULL";
		}
		return photo;
	}
	public void setNewsphoto(String photo) {
		this.photo = photo;
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
