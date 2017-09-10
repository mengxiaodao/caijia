package com.caijia.modle;

public class CityModle {
	private String cb_id;
	private String role;
	private String title;
	private String content;
	private String date;
	private String photo;
	
	public String getCityid() {
		if (null == cb_id) {
			return "NULL";
		}
		return cb_id;
	}
	public void setCityid(String cb_id) {
		this.cb_id = cb_id;
	}
	
	public String getCityrole() {
		if (null == role) {
			return "NULL";
		}
		return role;
	}
	public void setCityrole(String role) {
		this.role = role;
	}
	
	public String getCitytitle() {
		if (null == title) {
			return "NULL";
		}
		return title;
	}
	public void setCitytitle(String title) {
		this.title = title;
	}
	
	public String getCitycontent() {
		if (null == content) {
			return "NULL";
		}
		return content;
	}
	public void setCitycontent(String content) {
		this.content = content;
	}
	
	public String getCitydate() {
		if (null == date) {
			return "NULL";
		}
		return date;
	}
	public void setCitydate(String date) {
		this.date = date;
	}
	
	public String getCityphoto() {
		if (null == photo) {
			return "NULL";
		}
		return photo;
	}
	public void setCityphoto(String photo) {
		this.photo = photo;
	}

}
