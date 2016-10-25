package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hyg.bean.InStockBean;
import com.hyg.bean.InStockDetailBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.InstockDaoI;
import com.hyg.dao.impl.InstockDaoImpl;
import com.hyg.service.InStockServiceI;
import com.hyg.service.InstockDetailServiceI;
import com.hyg.service.InventoryServiceI;

public class InstockServiceImpl implements InStockServiceI {
	private InstockDaoI instockDao;
	@Override
	public JSONObject removeStock(String id) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		instockDao = new InstockDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "入库单主单id不能为空，请检查！");
		}else {
			try {
				int count  = instockDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除入库单主单成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除入库单主单操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}

	@Override
	public InStockBean findInstockById(String id) {
		// TODO Auto-generated method stub
		InStockBean[] instocks = null;
		InStockBean instock = null;
		String sql = "SELECT K.ID AS 'ID', K.INSTOCKNO AS 'INSTOCKNO',K.STOCKID AS 'STOCKID',"
				+ " ( SELECT S.STOCKNAME FROM K_STOCK S WHERE S.ID = K.STOCKID ) AS 'STOCKNAME',"
				+ " K.SUPPLIERID AS 'SUPPLIERID',"
				+ " ( SELECT T.SUPPLIERNAME FROM K_SUPPLIER T WHERE T.ID = K.SUPPLIERID ) AS 'SUPPLIERNAME',"
				+ " K.INSTOCKSTATE AS 'INSTOCKSTATE', K.INSTOCKNUM AS 'INSTOCKNUM', K.INSTOCKPRICE AS 'INSTOCKPRICE',"
				+ " K.INSTOCKDATE AS 'INSTOCKDATE', K.REMARK AS 'REMARK' "
				+ " FROM K_INSTOCK K "
				+ " WHERE K.ID = ? ";
		try {
			instockDao = new InstockDaoImpl();
			if (StringUtils.isNotEmpty(id)) {
				Object[] params = new Object[1];
				params[0] = id;
				
				instocks = instockDao.findByDynamicSelect(sql, params);
			}
			
			if (instocks != null) {
				instock = instocks[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instock;
	}

	@Override
	public JSONObject updateInstock(InStockBean instock) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		instockDao = new InstockDaoImpl();
		int count;
		try {
			//先根据ID到数据库查询出记录，
			//然后将页面上显示的字段内容赋值给从数据库查询出来的POJO中对应的属性 
			count = instockDao.update(new BigDecimal(instock.getId()),instock);
			
			if (count > 0) {
				json.put("msg", "恭喜你成功修改" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，修改入库单主单操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONObject insertInstock(InStockBean instock) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		instockDao = new InstockDaoImpl();
		int count;
		try {
			count = instockDao.insert(instock);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功添加" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，添加入库单主单操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONObject searchInstocks(InStockBean instock, int page, int rows) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		
		String sql = "SELECT K.ID AS 'ID', K.INSTOCKNO AS 'INSTOCKNO',K.STOCKID AS 'STOCKID',"
				+ " ( SELECT S.STOCKNAME FROM K_STOCK S WHERE S.ID = K.STOCKID ) AS 'STOCKNAME',"
				+ " K.SUPPLIERID AS 'SUPPLIERID',"
				+ " ( SELECT T.SUPPLIERNAME FROM K_SUPPLIER T WHERE T.ID = K.SUPPLIERID ) AS 'SUPPLIERNAME',"
				+ " K.INSTOCKSTATE AS 'INSTOCKSTATE', K.INSTOCKNUM AS 'INSTOCKNUM', K.INSTOCKPRICE AS 'INSTOCKPRICE',"
				+ " K.INSTOCKDATE AS 'INSTOCKDATE', K.REMARK AS 'REMARK' "
				+ " FROM K_INSTOCK K "
				+ " WHERE 1=1 ";
		String totalSql = " FROM K_INSTOCK K WHERE 1=1 ";
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		
		if (StringUtils.isNotEmpty(instock.getInStockNo())) {
			whereSql += " AND K.INSTOCKNO LIKE ?";
			params.add("%" + instock.getInStockNo() + "%");
		}
		if (StringUtils.isNotEmpty(instock.getStockId())) {
			whereSql += " AND K.STOCKID = ?";
			params.add(instock.getStockId());
		}
		if (StringUtils.isNotEmpty(instock.getSupplierId())) {
			whereSql += " AND K.SUPPLIERID = ?";
			params.add(instock.getSupplierId());
		}
		if (StringUtils.isNotEmpty(instock.getInStockState())) {
			whereSql += " AND K.INSTOCKSTATE = ?";
			params.add(instock.getInStockState());
		}
		if (StringUtils.isNotEmpty(instock.getInstockDateStart())) {
			whereSql += " AND K.INSTOCKDATE >= ?";
			params.add(instock.getInstockDateStart());
		}
		if (StringUtils.isNotEmpty(instock.getInstockDateEnd())) {
			whereSql += " AND K.INSTOCKDATE <= ?";
			params.add(instock.getInstockDateEnd());
		}
		//初始化stockDao
		instockDao = new InstockDaoImpl();
		//根据dao查找仓库，返回多条记录
		InStockBean[] instocks = null;
		//获取根据查询条件查出来的记录总数
		int total = 0;
		try {
			instocks = instockDao.findByDynamicSelectByPage(sql + whereSql, params.toArray(),page,rows);
			total = instockDao.getTotalBySql(totalSql + whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//key=rows 关键字，是EasyUI框架中datagrid组件解析数据所必须的
		json.put("rows",instocks);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
	}

	@Override
	public JSONObject confrimInstock(String id) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		InstockDetailServiceI instockDetailService = new InstockDetailServiceImpl();
		InventoryServiceI inventoryService = new InventoryServiceImpl();
		Map<String,String> map = new HashMap<String,String>();

		
		InStockBean instock = null;
		instockDao = new InstockDaoImpl();
		try {
			instock = instockDao.findByPrimaryKey(new BigDecimal(id));
		
			if (instock == null) {
				json.put("msg", "ID号为[" + id + "]的入库单不存在，请检查！");
				json.put("success", false);
				return json;
			} else if ("02".equals(instock.getInStockState())) {
				json.put("msg", "ID号为[" + id + "]的入库单状态不是【新建】，请检查！");
				json.put("success", false);
				return json;
			}
			
			InStockDetailBean[] instockDetails = instockDetailService.findInstockDetailsByInstockId(instock.getId().toString());
			if (instockDetails.length <= 0) {
				json.put("msg", "申请单编号为[" + instock.getInStockNo() + "]的记录没有任何产品明细记录，该申请单无效，请检查！");
				json.put("success", false);
				return json;
			}
			String stockId = instock.getStockId();//获取仓库ID
			for (int i = 0; i < instockDetails.length; i++) {
				InStockDetailBean instockDetail = instockDetails[i];
				map.put("stockId", stockId);
				map.put("productNo", instockDetail.getProductNo());
				map.put("productName", instockDetail.getProductName());
				map.put("productStandard", instockDetail.getProductStandard());
				map.put("productNum", instockDetail.getProductNum().toString());
				map.put("totalPrice", instockDetail.getTotalPrice().toString());
				map.put("flag", "in");
				inventoryService.saveOrUpdate(map);
			}
			//2、改变入库申请单的状态 创建-->记账
		
			instock.setInStockState("02");
			int count = instockDao.update(new BigDecimal(instock.getId()), instock);
			if (count > 0 ) {
				json.put("msg", "恭喜您，申请单编号为[" + instock.getInStockNo() + "]的记录[记账]操作成功！");
				json.put("success", true);
				return json;
			}
		} catch (Exception e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}

}
