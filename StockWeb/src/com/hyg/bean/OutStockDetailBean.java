package com.hyg.bean;

public class OutStockDetailBean {
	private String id;
	private String outStockId;
	private String stockNo;
	private String stockName;
	private String productNo;
	private String productName;
	private String productStandard;
	private String productNum;
	private String unit;
	private String price;
	private String totalPrice;
	private String remark;
	public OutStockDetailBean(String id, String outStockId, String stockNo,
			String stockName, String productNo, String productName,String productStandard,
			String productNum,String unit, String price, String totalPrice, String remark) {
		super();
		this.id = id;
		this.outStockId = outStockId;
		this.stockNo = stockNo;
		this.stockName = stockName;
		this.productNo = productNo;
		this.productName = productName;
		this.productStandard = productStandard;
		this.productNum = productNum;
		this.unit = unit;
		this.price = price;
		this.totalPrice = totalPrice;
		this.remark = remark;
	}
	public OutStockDetailBean() {
		super();
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the outStockId
	 */
	public String getOutStockId() {
		return outStockId;
	}
	/**
	 * @param outStockId the outStockId to set
	 */
	public void setOutStockId(String outStockId) {
		this.outStockId = outStockId;
	}
	/**
	 * @return the stockNo
	 */
	public String getStockNo() {
		return stockNo;
	}
	/**
	 * @param stockNo the stockNo to set
	 */
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	/**
	 * @return the stockName
	 */
	public String getStockName() {
		return stockName;
	}
	/**
	 * @param stockName the stockName to set
	 */
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	/**
	 * @return the productNo
	 */
	public String getProductNo() {
		return productNo;
	}
	/**
	 * @param productNo the productNo to set
	 */
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the productStandard
	 */
	public String getProductStandard() {
		return productStandard;
	}
	/**
	 * @param productStandard the productStandard to set
	 */
	public void setProductStandard(String productStandard) {
		this.productStandard = productStandard;
	}
	/**
	 * @return the productNum
	 */
	public String getProductNum() {
		return productNum;
	}
	/**
	 * @param productNum the productNum to set
	 */
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the totalPrice
	 */
	public String getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
