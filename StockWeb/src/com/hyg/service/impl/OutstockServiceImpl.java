package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hyg.bean.OutStockBean;
import com.hyg.bean.OutStockDetailBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.OutstockDaoI;
import com.hyg.dao.impl.OutstockDaoImpl;
import com.hyg.service.OutstockDetailServiceI;
import com.hyg.service.InventoryServiceI;
import com.hyg.service.OutstockServiceI;

public class OutstockServiceImpl implements OutstockServiceI {
	private OutstockDaoI outstockDao;
	@Override
	public JSONObject removeStock(String id) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		outstockDao = new OutstockDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "出库单主单id不能为空，请检查！");
		}else {
			try {
				int count  = outstockDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除出库单主单成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除出库单主单操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}

	@Override
	public OutStockBean findOutstockById(String id) {
		// TODO Auto-generated method stub
		OutStockBean[] outstocks = null;
		OutStockBean outstock = null;
		String sql = "SELECT K.ID AS 'ID', K.OUTSTOCKNO AS 'OUTSTOCKNO',K.STOCKID AS 'STOCKID',"
				+ " ( SELECT S.STOCKNAME FROM K_STOCK S WHERE S.ID = K.STOCKID ) AS 'STOCKNAME',"
				+ " K.SUPPLIERID AS 'SUPPLIERID',"
				+ " ( SELECT T.SUPPLIERNAME FROM K_SUPPLIER T WHERE T.ID = K.SUPPLIERID ) AS 'SUPPLIERNAME',"
				+ " K.OUTSTOCKSTATE AS 'OUTSTOCKSTATE', K.OUTSTOCKNUM AS 'OUTSTOCKNUM', K.OUTSTOCKPRICE AS 'OUTSTOCKPRICE',"
				+ " K.OUTSTOCKDATE AS 'OUTSTOCKDATE', K.REMARK AS 'REMARK' "
				+ " FROM K_OUTSTOCK K "
				+ " WHERE K.ID = ? ";
		try {
			outstockDao = new OutstockDaoImpl();
			if (StringUtils.isNotEmpty(id)) {
				Object[] params = new Object[1];
				params[0] = id;
				
				outstocks = outstockDao.findByDynamicSelect(sql, params);
			}
			
			if (outstocks != null) {
				outstock = outstocks[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outstock;
	}

	@Override
	public JSONObject updateOutstock(OutStockBean outstock) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		outstockDao = new OutstockDaoImpl();
		int count;
		try {
			//先根据ID到数据库查询出记录，
			//然后将页面上显示的字段内容赋值给从数据库查询出来的POJO中对应的属性 
			count = outstockDao.update(new BigDecimal(outstock.getId()),outstock);
			
			if (count > 0) {
				json.put("msg", "恭喜你成功修改" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，修改出库单主单操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONObject insertOutstock(OutStockBean outstock) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		outstockDao = new OutstockDaoImpl();
		int count;
		try {
			count = outstockDao.insert(outstock);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功添加" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，添加出库单主单操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONObject searchOutstocks(OutStockBean outstock, int page, int rows) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		
		String sql = "SELECT K.ID AS 'ID', K.OUTSTOCKNO AS 'OUTSTOCKNO',K.STOCKID AS 'STOCKID',"
				+ " ( SELECT S.STOCKNAME FROM K_STOCK S WHERE S.ID = K.STOCKID ) AS 'STOCKNAME',"
				+ " K.SUPPLIERID AS 'SUPPLIERID',"
				+ " ( SELECT T.SUPPLIERNAME FROM K_SUPPLIER T WHERE T.ID = K.SUPPLIERID ) AS 'SUPPLIERNAME',"
				+ " K.OUTSTOCKSTATE AS 'OUTSTOCKSTATE', K.OUTSTOCKNUM AS 'OUTSTOCKNUM', K.OUTSTOCKPRICE AS 'OUTSTOCKPRICE',"
				+ " K.OUTSTOCKDATE AS 'OUTSTOCKDATE', K.REMARK AS 'REMARK' "
				+ " FROM K_OUTSTOCK K "
				+ " WHERE 1=1 ";
		String totalSql = " FROM K_OUTSTOCK K WHERE 1=1 ";
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		
		if (StringUtils.isNotEmpty(outstock.getOutStockNo())) {
			whereSql += " AND K.OUTSTOCKNO LIKE ?";
			params.add("%" + outstock.getOutStockNo() + "%");
		}
		if (StringUtils.isNotEmpty(outstock.getStockId())) {
			whereSql += " AND K.STOCKID = ?";
			params.add(outstock.getStockId());
		}
		if (StringUtils.isNotEmpty(outstock.getSupplierId())) {
			whereSql += " AND K.SUPPLIERID = ?";
			params.add(outstock.getSupplierId());
		}
		if (StringUtils.isNotEmpty(outstock.getOutStockState())) {
			whereSql += " AND K.OUTSTOCKSTATE = ?";
			params.add(outstock.getOutStockState());
		}
		if (StringUtils.isNotEmpty(outstock.getOutstockDateStart())) {
			whereSql += " AND K.OUTSTOCKDATE >= ?";
			params.add(outstock.getOutstockDateStart());
		}
		if (StringUtils.isNotEmpty(outstock.getOutstockDateEnd())) {
			whereSql += " AND K.OUTSTOCKDATE <= ?";
			params.add(outstock.getOutstockDateEnd());
		}
		//初始化stockDao
		outstockDao = new OutstockDaoImpl();
		//根据dao查找仓库，返回多条记录
		OutStockBean[] outstocks = null;
		//获取根据查询条件查出来的记录总数
		int total = 0;
		try {
			outstocks = outstockDao.findByDynamicSelectByPage(sql + whereSql, params.toArray(),page,rows);
			total = outstockDao.getTotalBySql(totalSql + whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//key=rows 关键字，是EasyUI框架中datagrid组件解析数据所必须的
		json.put("rows",outstocks);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
	}

	@Override
	public JSONObject confrimOutstock(String id) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		OutstockDetailServiceI outstockDetailService = new OutstockDetailServiceImpl();
		InventoryServiceI inventoryService = new InventoryServiceImpl();
		Map<String,String> map = new HashMap<String,String>();

		
		OutStockBean outstock = null;
		outstockDao = new OutstockDaoImpl();
		try {
			outstock = outstockDao.findByPrimaryKey(new BigDecimal(id));
		
			if (outstock == null) {
				json.put("msg", "ID号为[" + id + "]的出库单不存在，请检查！");
				json.put("success", false);
				return json;
			} else if ("02".equals(outstock.getOutStockState())) {
				json.put("msg", "ID号为[" + id + "]的出库单状态不是【新建】，请检查！");
				json.put("success", false);
				return json;
			}
			
			OutStockDetailBean[] outstockDetails = outstockDetailService.findOutstockDetailsByOutstockId(outstock.getId().toString());
			if (outstockDetails.length <= 0) {
				json.put("msg", "申请单编号为[" + outstock.getOutStockNo() + "]的记录没有任何产品明细记录，该申请单无效，请检查！");
				json.put("success", false);
				return json;
			}
			String stockId = outstock.getStockId();//获取仓库ID
			for (int i = 0; i < outstockDetails.length; i++) {
				OutStockDetailBean outstockDetail = outstockDetails[i];
				map.put("stockId", stockId);
				map.put("productNo", outstockDetail.getProductNo());
				map.put("productName", outstockDetail.getProductName());
				map.put("productStandard", outstockDetail.getProductStandard());
				map.put("productNum", outstockDetail.getProductNum().toString());
				map.put("totalPrice", outstockDetail.getTotalPrice().toString());
				map.put("flag", "out");
				inventoryService.saveOrUpdate(map);
			}
			//2、改变入库申请单的状态 创建-->记账
		
			outstock.setOutStockState("02");
			int count = outstockDao.update(new BigDecimal(outstock.getId()), outstock);
			if (count > 0 ) {
				json.put("msg", "恭喜您，申请单编号为[" + outstock.getOutStockNo() + "]的记录[记账]操作成功！");
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
