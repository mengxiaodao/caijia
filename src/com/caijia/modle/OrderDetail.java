package com.caijia.modle;

public class OrderDetail {
	private String orderDetailUuid;
	private String orderUuid;
	private String caiUuid;
	private String amount;
	private String price;
	public String getOrderDetailUuid() {
		if (null == orderDetailUuid) {
			return "NULL";
		}
		return orderDetailUuid;
	}
	public void setOrderDetailUuid(String orderDetailUuid) {
		this.orderDetailUuid = orderDetailUuid;
	}
	public String getOrderUuid() {
		if (null == orderUuid) {
			return "NULL";
		}
		return orderUuid;
	}
	public void setOrderUuid(String orderUuid) {
		this.orderUuid = orderUuid;
	}
	public String getCaiUuid() {
		if (null == caiUuid) {
			return "NULL";
		}
		return caiUuid;
	}
	public void setCaiUuid(String caiUuid) {
		this.caiUuid = caiUuid;
	}
	public String getAmount() {
		if (null == amount) {
			return "NULL";
		}
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPrice() {
		if (null == price) {
			return "NULL";
		}
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

}
