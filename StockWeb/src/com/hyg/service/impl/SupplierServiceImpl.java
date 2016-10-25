package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hyg.bean.SupplierBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.SupplierDaoI;
import com.hyg.dao.impl.SupplierDaoImpl;
import com.hyg.service.SupplierServiceI;

public class SupplierServiceImpl implements SupplierServiceI {
	private SupplierDaoI supplierDao;
	@Override
	public JSONObject removeSupplier(String id) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		supplierDao = new SupplierDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "供应商id不能为空，请检查！");
		}else {
			try {
				int count  = supplierDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除供应商成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除供应商操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	@Override
	public SupplierBean findSupplierById(String id) {
		// TODO Auto-generated method stub
		SupplierBean supplier = null;
		try {
			supplierDao = new SupplierDaoImpl();
			if (StringUtils.isNotEmpty(id)) {
				supplier = supplierDao.findByPrimaryKey(new BigDecimal(id));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return supplier;
	}
	@Override
	public JSONObject update(SupplierBean supplierBean) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		supplierDao = new SupplierDaoImpl();
		int count;
		try {
			//先根据ID到数据库查询出记录，
			//然后将页面上显示的字段内容赋值给从数据库查询出来的POJO中对应的属性 
			count = supplierDao.update(new BigDecimal(supplierBean.getId()),supplierBean);
			
			if (count > 0) {
				json.put("msg", "恭喜你成功修改" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，修改供应商操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject insert(SupplierBean supplierBean) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		supplierDao = new SupplierDaoImpl();
		int count;
		try {
			count = supplierDao.insert(supplierBean);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功添加" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，添加供应商操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject search(SupplierBean supplierBean,int page,int rows) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		
		//判断供应商输入的查询条件，根据查询条件去查找供应商信息
		String sql = "SELECT ID,SUPPLIERNO,SUPPLIERNAME,PROVINCECODE,"
				+ " (SELECT DICVALUE FROM K_DICTIONARY WHERE DICCODE = PROVINCECODE ) AS 'PROVINCENAME',"
				+ "SUPPLIERADDRESS,SUPPLIEREMAIL,"
				+ " SUPPLIERTEL,SUPPLIERTAX,USERNAME,USERTEL,REMARK "
				+ " FROM K_SUPPLIER "
				+ " WHERE 1=1 ";
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(supplierBean.getSupplierNo())) {
			whereSql += " AND SUPPLIERNO  LIKE ?";
			params.add("%" + supplierBean.getSupplierNo() + "%");
		}
		if (StringUtils.isNotEmpty(supplierBean.getSupplierName())) {
			whereSql += " AND SUPPLIERNAME LIKE ?";
			params.add("%" + supplierBean.getSupplierName() + "%");
		}
		if (StringUtils.isNotEmpty(supplierBean.getProvinceCode())) {
			whereSql += " AND PROVINCECODE = ?";
			params.add(supplierBean.getProvinceCode());
		}
		if (StringUtils.isNotEmpty(supplierBean.getUserName())) {
			whereSql += " ";
			params.add("%" + supplierBean.getUserName() + "%");
		}
		
		//初始化supplierDao
		supplierDao = new SupplierDaoImpl();
		//根据dao查找供应商，返回多条记录
		SupplierBean[] suppliers = null;
		//获取根据查询条件查出来的记录总数
		int total = 0;
		try {
			suppliers = supplierDao.findByDynamicSelectByPage(sql + whereSql, params.toArray(),page,rows);
			total = supplierDao.getTotal(whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//key=rows 关键字，是EasyUI框架中datagrid组件解析数据所必须的
		json.put("rows",suppliers);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
	}
	@Override
	public JSONObject findSupplierSelects() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		supplierDao = new SupplierDaoImpl();
		List<Map<String, Object>> results = null;
		try {
			results = supplierDao.findSupplierSelects();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("success", true);
		json.put("results", results);
		return json;
	}

}
