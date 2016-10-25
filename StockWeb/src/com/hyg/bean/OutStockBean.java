package com.hyg.bean;

public class OutStockBean {
	private String id;
	private String outStockNo;
	private String stockId;
	private String stockName;
	private String supplierId;
	private String supplierName;
	private String outStockState;
	private String outStockNum;
	private String outStockPrice;
	private String outStockDate;
	private String remark;
	
	
	private String outstockDateStart;
	private String outstockDateEnd;
	
	public OutStockBean(String id, String outStockNo, String stockId,
			String stockName, String supplierId, String supplierName,
			String outStockState, String outStockNum, String outStockPrice,
			String outStockDate, String remark) {
		super();
		this.id = id;
		this.outStockNo = outStockNo;
		this.stockId = stockId;
		this.stockName = stockName;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.outStockState = outStockState;
		this.outStockNum = outStockNum;
		this.outStockPrice = outStockPrice;
		this.outStockDate = outStockDate;
		this.remark = remark;
	}
	public OutStockBean() {
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
	 * @return the outStockNo
	 */
	public String getOutStockNo() {
		return outStockNo;
	}
	/**
	 * @param outStockNo the outStockNo to set
	 */
	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
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
	 * @return the supplierId
	 */
	public String getSupplierId() {
		return supplierId;
	}
	/**
	 * @param supplierId the supplierId to set
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	/**
	 * @return the outStockState
	 */
	public String getOutStockState() {
		return outStockState;
	}
	/**
	 * @param outStockState the outStockState to set
	 */
	public void setOutStockState(String outStockState) {
		this.outStockState = outStockState;
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
	 * @return the outStockDate
	 */
	public String getOutStockDate() {
		return outStockDate;
	}
	/**
	 * @param outStockDate the outStockDate to set
	 */
	public void setOutStockDate(String outStockDate) {
		this.outStockDate = outStockDate;
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
	public String getOutstockDateStart() {
		return outstockDateStart;
	}
	public void setOutstockDateStart(String outstockDateStart) {
		this.outstockDateStart = outstockDateStart;
	}
	public String getOutstockDateEnd() {
		return outstockDateEnd;
	}
	public void setOutstockDateEnd(String outstockDateEnd) {
		this.outstockDateEnd = outstockDateEnd;
	}
	
}
