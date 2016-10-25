package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hyg.bean.DeptBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.DeptDaoI;
import com.hyg.dao.impl.DeptDaoImpl;
import com.hyg.dao.impl.UserDaoImpl;
import com.hyg.service.DeptServiceI;

public class DeptServiceImpl implements DeptServiceI {
	private DeptDaoI deptDao;
	@Override
	public JSONObject searchDept(DeptBean dept, int page, int rows) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(dept.getDeptNo())) {
			whereSql += " and deptNo = ?";
			params.add(dept.getDeptNo());
		}
		if (StringUtils.isNotEmpty(dept.getDeptName())) {
			whereSql += " and deptName like ?";
			params.add("%" + dept.getDeptName() + "%");
		}
		if (StringUtils.isNotEmpty(dept.getParentDeptNo())) {
			whereSql += " and supplierNo = ?";
			params.add(dept.getParentDeptNo());
		}
		DeptBean[] depts = null;
		int total  = 0;
		try {
			deptDao = new DeptDaoImpl();
			depts = deptDao.findByDynamicWhereByPage(whereSql, page, rows, params.toArray());
					
			total = deptDao.getTotal(whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("rows",depts);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
	}
	@Override
	public JSONObject insertDept(DeptBean dept) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		//初始化UserDao
		deptDao = new DeptDaoImpl();
		int count;
		try {
			count = deptDao.insert(dept);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功添加" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，添加部门操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public DeptBean findDeptById(String id) {
		// TODO Auto-generated method stub
		DeptBean dept = null;
		deptDao = new DeptDaoImpl();
		try {
			dept = deptDao.findByPrimaryKey(new BigDecimal(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dept;
	}
	@Override
	public JSONObject updateDept(DeptBean dept) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		deptDao = new DeptDaoImpl();
		int count;
		try {
			count = deptDao.update(new BigDecimal(dept.getId()),dept);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功修改" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，修改部门操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject deleteDept(String id) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		deptDao = new DeptDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "部门id不能为空，请检查！");
		}else {
			try {
				int count  = deptDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除部门成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除部门操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	@Override
	public JSONObject findDeptDics() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		deptDao = new DeptDaoImpl();
		String sql = " select id,deptName from k_dept ";
		List<Map<String, Object>> resultList = null;
		try {
			resultList = deptDao.findDeptDics(sql, new Object[0]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("success", true);
		json.put("results", resultList);
		return json;
	}

}
