package com.hyg.bean;

public class DeptBean {
	private String id;
	private String deptNo;
	private String deptName;
	private String deptLeader;
	private String deptTel;
	private String parentDeptNo;
	private String deptDesc;
	private String remark;
	
	public DeptBean() {
		super();
	}
	public DeptBean(String id, String deptNo, String deptName,
			String deptLeader, String deptTel, String parentDeptNo,
			String deptDesc, String remark) {
		super();
		this.id = id;
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.deptLeader = deptLeader;
		this.deptTel = deptTel;
		this.parentDeptNo = parentDeptNo;
		this.deptDesc = deptDesc;
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
	 * @return the deptNo
	 */
	public String getDeptNo() {
		return deptNo;
	}
	/**
	 * @param deptNo the deptNo to set
	 */
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return the deptLeader
	 */
	public String getDeptLeader() {
		return deptLeader;
	}
	/**
	 * @param deptLeader the deptLeader to set
	 */
	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
	}
	/**
	 * @return the deptTel
	 */
	public String getDeptTel() {
		return deptTel;
	}
	/**
	 * @param deptTel the deptTel to set
	 */
	public void setDeptTel(String deptTel) {
		this.deptTel = deptTel;
	}
	/**
	 * @return the parentDeptNo
	 */
	public String getParentDeptNo() {
		return parentDeptNo;
	}
	/**
	 * @param parentDeptNo the parentDeptNo to set
	 */
	public void setParentDeptNo(String parentDeptNo) {
		this.parentDeptNo = parentDeptNo;
	}
	/**
	 * @return the deptDesc
	 */
	public String getDeptDesc() {
		return deptDesc;
	}
	/**
	 * @param deptDesc the deptDesc to set
	 */
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
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
