package com.hyg.bean;

public class DictionaryBean {
	private String id;
	private String dicId;
	private String dicType;
	private String dicCode;
	private String dicValue;
	private String dicSort;
	private String validateFlag;
	private String remark;
	
	public DictionaryBean() {
		super();
	}
	public DictionaryBean(String id, String dicId, String dicType,
			String dicCode, String dicValue, String dicSort,
			String validateFlag, String remark) {
		super();
		this.id = id;
		this.dicId = dicId;
		this.dicType = dicType;
		this.dicCode = dicCode;
		this.dicValue = dicValue;
		this.dicSort = dicSort;
		this.validateFlag = validateFlag;
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
	 * @return the dicId
	 */
	public String getDicId() {
		return dicId;
	}
	/**
	 * @param dicId the dicId to set
	 */
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	/**
	 * @return the dicType
	 */
	public String getDicType() {
		return dicType;
	}
	/**
	 * @param dicType the dicType to set
	 */
	public void setDicType(String dicType) {
		this.dicType = dicType;
	}
	/**
	 * @return the dicCode
	 */
	public String getDicCode() {
		return dicCode;
	}
	/**
	 * @param dicCode the dicCode to set
	 */
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	/**
	 * @return the dicValue
	 */
	public String getDicValue() {
		return dicValue;
	}
	/**
	 * @param dicValue the dicValue to set
	 */
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}
	/**
	 * @return the dicSort
	 */
	public String getDicSort() {
		return dicSort;
	}
	/**
	 * @param dicSort the dicSort to set
	 */
	public void setDicSort(String dicSort) {
		this.dicSort = dicSort;
	}
	/**
	 * @return the validateFlag
	 */
	public String getValidateFlag() {
		return validateFlag;
	}
	/**
	 * @param validateFlag the validateFlag to set
	 */
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
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
