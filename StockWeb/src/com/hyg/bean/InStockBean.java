package com.hyg.bean;

public class InStockBean {
	private String id;
	private String inStockNo;
	private String stockId;
	private String stockName;
	private String supplierId;
	private String supplierName;
	private String inStockState;
	private String inStockNum;
	private String inStockPrice;
	private String inStockDate;
	
	private String instockDateStart;
	private String instockDateEnd;
	
	private String remark;
	public InStockBean(String id, String inStockNo, String stockId,
			String stockName, String supplierId, String supplierName,
			String inStockState, String inStockNum, String inStockPrice,
			String inStockDate, String remark) {
		super();
		this.id = id;
		this.inStockNo = inStockNo;
		this.stockId = stockId;
		this.stockName = stockName;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.inStockState = inStockState;
		this.inStockNum = inStockNum;
		this.inStockPrice = inStockPrice;
		this.inStockDate = inStockDate;
		this.remark = remark;
	}
	public InStockBean() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the inStockNo
	 */
	public String getInStockNo() {
		return inStockNo;
	}
	/**
	 * @param inStockNo the inStockNo to set
	 */
	public void setInStockNo(String inStockNo) {
		this.inStockNo = inStockNo;
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
	 * @return the inStockState
	 */
	public String getInStockState() {
		return inStockState;
	}
	/**
	 * @param inStockState the inStockState to set
	 */
	public void setInStockState(String inStockState) {
		this.inStockState = inStockState;
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
	 * @return the inStockDate
	 */
	public String getInStockDate() {
		return inStockDate;
	}
	/**
	 * @param inStockDate the inStockDate to set
	 */
	public void setInStockDate(String inStockDate) {
		this.inStockDate = inStockDate;
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
	public String getInstockDateStart() {
		return instockDateStart;
	}
	public void setInstockDateStart(String instockDateStart) {
		this.instockDateStart = instockDateStart;
	}
	public String getInstockDateEnd() {
		return instockDateEnd;
	}
	public void setInstockDateEnd(String instockDateEnd) {
		this.instockDateEnd = instockDateEnd;
	}
	
}
