package com.hyg.bean;

public class StockBean {
	private String id;
	private String stockNo;
	private String stockName;
	private String provinceCode;
	private String provinceCodeName;
	private String stockAddress;
	private String stockTel;
	private String userId;
	private String userName;
	private String remark;
	
	public StockBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StockBean(String id, String stockNo, String stockName,
			String provinceCode, String provinceCodeName, String stockAddress,
			String stockTel, String userId, String userName, String remark) {
		super();
		this.id = id;
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.provinceCode = provinceCode;
		this.provinceCodeName = provinceCodeName;
		this.stockAddress = stockAddress;
		this.stockTel = stockTel;
		this.userId = userId;
		this.userName = userName;
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceCodeName() {
		return provinceCodeName;
	}
	public void setProvinceCodeName(String provinceCodeName) {
		this.provinceCodeName = provinceCodeName;
	}
	public String getStockAddress() {
		return stockAddress;
	}
	public void setStockAddress(String stockAddress) {
		this.stockAddress = stockAddress;
	}
	public String getStockTel() {
		return stockTel;
	}
	public void setStockTel(String stockTel) {
		this.stockTel = stockTel;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
