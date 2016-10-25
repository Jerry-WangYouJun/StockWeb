package com.hyg.bean;

public class InventoryBean {
	private String  id;
	private String stockId;
	private String productNo;
	private String productName;
	private String productStandard;
	private String inventoryNum;
	private String inventoryPrice;
	private String inStockNum;
	private String inStockPrice;
	private String outStockNum;
	private String outStockPrice;
	private String maxNum;
	private String minNum;
	private String remark;
	
	//库房名称，用于界面显示
	private String stockName;
	//产品规格，用于界面显示
	private String productUnit;
	
	public InventoryBean() {
		super();
	}
	public InventoryBean(String id, String stockId, String productNo,
			String productName,  String productStandard,
			String inventoryNum, String inventoryPrice, String inStockNum,
			String inStockPrice, String outStockNum, String outStockPrice,
			String maxNum, String minNum, String remark) {
		super();
		this.id = id;
		this.stockId = stockId;
		this.productNo = productNo;
		this.productName = productName;
		this.productStandard = productStandard;
		this.inventoryNum = inventoryNum;
		this.inventoryPrice = inventoryPrice;
		this.inStockNum = inStockNum;
		this.inStockPrice = inStockPrice;
		this.outStockNum = outStockNum;
		this.outStockPrice = outStockPrice;
		this.maxNum = maxNum;
		this.minNum = minNum;
		this.remark = remark;
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
	 * @return the stockId
	 */
	public String getStockId() {
		return stockId;
	}
	/**
	 * @param stockId the stockId to set
	 */
	public void setStockId(String stockId) {
		this.stockId = stockId;
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
	 * @return the inventoryNum
	 */
	public String getInventoryNum() {
		return inventoryNum;
	}
	/**
	 * @param inventoryNum the inventoryNum to set
	 */
	public void setInventoryNum(String inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
	/**
	 * @return the inventoryPrice
	 */
	public String getInventoryPrice() {
		return inventoryPrice;
	}
	/**
	 * @param inventoryPrice the inventoryPrice to set
	 */
	public void setInventoryPrice(String inventoryPrice) {
		this.inventoryPrice = inventoryPrice;
	}
	/**
	 * @return the inStockNum
	 */
	public String getInStockNum() {
		return inStockNum;
	}
	/**
	 * @param inStockNum the inStockNum to set
	 */
	public void setInStockNum(String inStockNum) {
		this.inStockNum = inStockNum;
	}
	/**
	 * @return the inStockPrice
	 */
	public String getInStockPrice() {
		return inStockPrice;
	}
	/**
	 * @param inStockPrice the inStockPrice to set
	 */
	public void setInStockPrice(String inStockPrice) {
		this.inStockPrice = inStockPrice;
	}
	/**
	 * @return the outStockNum
	 */
	public String getOutStockNum() {
		return outStockNum;
	}
	/**
	 * @param outStockNum the outStockNum to set
	 */
	public void setOutStockNum(String outStockNum) {
		this.outStockNum = outStockNum;
	}
	/**
	 * @return the outStockPrice
	 */
	public String getOutStockPrice() {
		return outStockPrice;
	}
	/**
	 * @param outStockPrice the outStockPrice to set
	 */
	public void setOutStockPrice(String outStockPrice) {
		this.outStockPrice = outStockPrice;
	}
	/**
	 * @return the maxNum
	 */
	public String getMaxNum() {
		return maxNum;
	}
	/**
	 * @param maxNum the maxNum to set
	 */
	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}
	/**
	 * @return the minNum
	 */
	public String getMinNum() {
		return minNum;
	}
	/**
	 * @param minNum the minNum to set
	 */
	public void setMinNum(String minNum) {
		this.minNum = minNum;
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
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	
}
