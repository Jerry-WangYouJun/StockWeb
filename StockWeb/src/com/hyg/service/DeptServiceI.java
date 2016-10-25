package com.hyg.service;

import net.sf.json.JSONObject;

import com.hyg.bean.DeptBean;

public interface DeptServiceI {

	public JSONObject searchDept(DeptBean dept, int page, int rows);

	public JSONObject insertDept(DeptBean dept);

	public DeptBean findDeptById(String id);

	public JSONObject updateDept(DeptBean dept);

	public JSONObject deleteDept(String id);
	/**
	 * 部门字典
	 * @return
	 */
	public JSONObject findDeptDics();

}
