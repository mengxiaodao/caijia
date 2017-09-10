package com.caijia.modle;

public class UserModle {
	private String wenhua_id;
	private String role;
	private String title;
	private String content;
	private String photo;
	
	public String getWenhuaId() {
		if (null == wenhua_id) {
			return "NULL";
		}
		return wenhua_id;
	}
	public void setWenhuaId(String wenhua_id) {
		this.wenhua_id = wenhua_id;
	}
	
	public String getWenhuaRole() {
		if (null == role) {
			return "NULL";
		}
		return role;
	}
	public void setWenhuaRole(String role) {
		this.role = role;
	}
	
	public String getWenhuaTitle() {
		if (null == title) {
			return "NULL";
		}
		return title;
	}
	public void setWenhuaTitle(String title) {
		this.title = title;
	}
	
	public String getWenhuaContent() {
		if (null == content) {
			return "NULL";
		}
		return content;
	}
	public void setWenhuaContent(String content) {
		this.content = content;
	}
	
	public String getWenhuaPhoto() {
		if (null == photo) {
			return "NULL";
		}
		return photo;
	}
	public void setWenhuaPhoto(String photo) {
		this.photo = photo;
	}

}
