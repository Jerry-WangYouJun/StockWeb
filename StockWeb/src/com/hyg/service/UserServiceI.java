package com.hyg.service;

import net.sf.json.JSONObject;

import com.hyg.bean.UserBean;

public interface UserServiceI {
	public UserBean checkUser(UserBean user);
	/**
	 * 根据查询条件查询用户，并返回json字符串
	 * @param userForm  前台封装的JAVABEAN对象
	 * @param rows      每页显示行数
	 * @param page      当前页码
	 * @return
	 */
	public JSONObject searchUser(UserBean userForm, int page, int rows);
	/**
	 * 添加用户
	 * @param userForm
	 * @return
	 */
	public JSONObject insert(UserBean userForm);
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public JSONObject delete(String id);
	/**
	 * 根据ID查找用户
	 * @param id
	 * @return
	 */
	public UserBean findUserById(String id);
	/**
	 * 修改用户
	 * @param userForm
	 * @return
	 */
	public JSONObject update(UserBean userForm);
}
