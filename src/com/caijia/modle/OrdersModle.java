package com.caijia.modle;

import java.util.List;

public class OrdersModle {
	
	private String orderUuid;
	private String orderTotal;
	private String address;
	private String creatTime;
	private String owner;
	private String phone;
	private List<OrderDetail> orderDetail;
	public String getOrderUuid() {
		if (null == orderUuid) {
			return "NULL";
		}
		return orderUuid;
	}
	public void setOrderUuid(String orderUuid) {
		this.orderUuid = orderUuid;
	}
	public String getOrderTotal() {
		if (null == orderTotal) {
			return "NULL";
		}
		return orderTotal;
	}
	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getAddress() {
		if (null == address) {
			return "NULL";
		}
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreatTime() {
		if (null == creatTime) {
			return "NULL";
		}
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public List<OrderDetail> getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(List<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}
	public String getOwner() {
		if (null == owner) {
			return "NULL";
		}
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getPhone() {
		if (null == phone) {
			return "NULL";
		}
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
