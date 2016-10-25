package com.hyg.bean;

import java.util.Date;

public class GoodsBean {
	private String id;
	private String productNo;
	private String productName;
	private String productTypeCode;
	private String productTypeName;
	private String productStandard;
	private String supplierNo;
	private String productDate;
	private double productNum;
	private String unit;
	private double price;
	private String remark;
	
	public GoodsBean() {
		super();
	}
	
	public GoodsBean(String id, String productNo, String productName,
			String productTypeCode, String productTypeName,
			String productStandard, String supplierNo, String productDate,
			double productNum, String unit, double price, String remark) {
		super();
		this.id = id;
		this.productNo = productNo;
		this.productName = productName;
		this.productTypeCode = productTypeCode;
		this.productTypeName = productTypeName;
		this.productStandard = productStandard;
		this.supplierNo = supplierNo;
		this.productDate = productDate;
		this.productNum = productNum;
		this.unit = unit;
		this.price = price;
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
	 * @return the productTypeCode
	 */
	public String getProductTypeCode() {
		return productTypeCode;
	}
	/**
	 * @param productTypeCode the productTypeCode to set
	 */
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	/**
	 * @return the productTypeName
	 */
	public String getProductTypeName() {
		return productTypeName;
	}
	/**
	 * @param productTypeName the productTypeName to set
	 */
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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
	 * @return the supplierNo
	 */
	public String getSupplierNo() {
		return supplierNo;
	}
	/**
	 * @param supplierNo the supplierNo to set
	 */
	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
	/**
	 * @return the productDate
	 */
	public String getProductDate() {
		return productDate;
	}
	/**
	 * @param productDate the productDate to set
	 */
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	/**
	 * @return the productNum
	 */
	public double getProductNum() {
		return productNum;
	}
	/**
	 * @param productNum the productNum to set
	 */
	public void setProductNum(double productNum) {
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
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
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
