package com.hyg.bean;

public class UserBean {
	private String id;
	private String userNo;
	private String userName;
	private String pwd;
	private String deptNo;
	private String sex;
	private String age;
	private String position;
	private String hometown;
	private String telphone;
	private String email;
	private String roleNo;
	private String remark;
	
	public UserBean() {
		super();
	}
	
	public UserBean(String id, String userNo, String userName, String pwd,
			String deptNo, String sex, String age, String position,String homeTown, 
			String telphone, String email, String roleNo,String remark) {
		super();
		this.id = id;
		this.userNo = userNo;
		this.userName = userName;
		this.pwd = pwd;
		this.deptNo = deptNo;
		this.sex = sex;
		this.age = age;
		this.position = position;
		this.hometown = homeTown;
		this.telphone = telphone;
		this.email = email;
		this.roleNo = roleNo;
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
	 * @return the userNo
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * @param userNo the userNo to set
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the homeTown
	 */
	public String getHometown() {
		return hometown;
	}

	/**
	 * @param homeTown the homeTown to set
	 */
	public void setHometown(String homeTown) {
		this.hometown = homeTown;
	}

	/**
	 * @return the telphone
	 */
	public String getTelphone() {
		return telphone;
	}

	/**
	 * @param telphone the telphone to set
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the roleNo
	 */
	public String getRoleNo() {
		return roleNo;
	}

	/**
	 * @param roleNo the roleNo to set
	 */
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
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
