package com.ibs.zj.multi.model;

import java.io.Serializable;

public class UserGoods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private String userName;

	private String goodName;

	private int goodPrice;

	private String goodInfo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public int getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(int goodPrice) {
		this.goodPrice = goodPrice;
	}

	public String getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(String goodInfo) {
		this.goodInfo = goodInfo;
	}

	@Override
	public String toString() {
		return "UserGoods [id=" + id + ", userName=" + userName + ", goodName="
				+ goodName + ", goodPrice=" + goodPrice + ", goodInfo="
				+ goodInfo + "]";
	}
}
