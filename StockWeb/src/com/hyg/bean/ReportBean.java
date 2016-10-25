package com.hyg.bean;

public class ReportBean {
	private String stockId;
	private String stockName;
	private String productNo;
	private String productName;
	private String productStandard;
	
	private String startNum;
	private String startTotal;
	private String inNum;
	private String inTotal;
	private String outNum;
	private String outTotal;
	private String endNum;
	private String endTotal;
	private String operateDate;
	public ReportBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReportBean(String stockId, String stockName, String productNo,
			String productName, String productStandard, String startNum,
			String startTotal, String inNum, String inTotal, String outNum,
			String outTotal, String endNum, String endTotal,String operateDate) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.productNo = productNo;
		this.productName = productName;
		this.productStandard = productStandard;
		this.startNum = startNum;
		this.startTotal = startTotal;
		this.inNum = inNum;
		this.inTotal = inTotal;
		this.outNum = outNum;
		this.outTotal = outTotal;
		this.endNum = endNum;
		this.endTotal = endTotal;
		this.operateDate = operateDate;
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
	 * @return the startNum
	 */
	public String getStartNum() {
		return startNum;
	}
	/**
	 * @param startNum the startNum to set
	 */
	public void setStartNum(String startNum) {
		this.startNum = startNum;
	}
	/**
	 * @return the startTotal
	 */
	public String getStartTotal() {
		return startTotal;
	}
	/**
	 * @param startTotal the startTotal to set
	 */
	public void setStartTotal(String startTotal) {
		this.startTotal = startTotal;
	}
	/**
	 * @return the inNum
	 */
	public String getInNum() {
		return inNum;
	}
	/**
	 * @param inNum the inNum to set
	 */
	public void setInNum(String inNum) {
		this.inNum = inNum;
	}
	/**
	 * @return the inTotal
	 */
	public String getInTotal() {
		return inTotal;
	}
	/**
	 * @param inTotal the inTotal to set
	 */
	public void setInTotal(String inTotal) {
		this.inTotal = inTotal;
	}
	/**
	 * @return the outNum
	 */
	public String getOutNum() {
		return outNum;
	}
	/**
	 * @param outNum the outNum to set
	 */
	public void setOutNum(String outNum) {
		this.outNum = outNum;
	}
	/**
	 * @return the outTotal
	 */
	public String getOutTotal() {
		return outTotal;
	}
	/**
	 * @param outTotal the outTotal to set
	 */
	public void setOutTotal(String outTotal) {
		this.outTotal = outTotal;
	}
	/**
	 * @return the endNum
	 */
	public String getEndNum() {
		return endNum;
	}
	/**
	 * @param endNum the endNum to set
	 */
	public void setEndNum(String endNum) {
		this.endNum = endNum;
	}
	/**
	 * @return the endTotal
	 */
	public String getEndTotal() {
		return endTotal;
	}
	/**
	 * @param endTotal the endTotal to set
	 */
	public void setEndTotal(String endTotal) {
		this.endTotal = endTotal;
	}
	/**
	 * @return the operateDate
	 */
	public String getOperateDate() {
		return operateDate;
	}
	/**
	 * @param operateDate the operateDate to set
	 */
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
}
