package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hyg.bean.StockBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.StockDaoI;
import com.hyg.dao.impl.StockDaoImpl;
import com.hyg.service.StockServiceI;

public class StockServiceImpl implements StockServiceI {
	private StockDaoI stockDao;
	@Override
	public JSONObject removeStock(String id) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		stockDao = new StockDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "仓库id不能为空，请检查！");
		}else {
			try {
				int count  = stockDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除仓库成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除仓库操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	@Override
	public StockBean findStockById(String id) {
		// TODO Auto-generated method stub
		
		StockBean stock = null;
		try {
			stockDao = new StockDaoImpl();
			if (StringUtils.isNotEmpty(id)) {
				stock = stockDao.findByPrimaryKey(new BigDecimal(id));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stock;
	}
	@Override
	public JSONObject updateStock(StockBean stockBean) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		stockDao = new StockDaoImpl();
		int count;
		try {
			//先根据ID到数据库查询出记录，
			//然后将页面上显示的字段内容赋值给从数据库查询出来的POJO中对应的属性 
			count = stockDao.update(new BigDecimal(stockBean.getId()),stockBean);
			
			if (count > 0) {
				json.put("msg", "恭喜你成功修改" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，修改仓库操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject insertStock(StockBean stockBean) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		stockDao = new StockDaoImpl();
		int count;
		try {
			count = stockDao.insert(stockBean);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功添加" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，添加仓库操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject searchStock(StockBean stock, int page, int rows) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		
		//判断仓库输入的查询条件，根据查询条件去查找仓库信息
		String sql = "SELECT ID,STOCKNO,STOCKNAME,PROVINCECODE,"
				+ " ( SELECT DICVALUE FROM K_DICTIONARY WHERE DICCODE = PROVINCECODE ) AS 'PROVINCECODENAME',"
				+ " STOCKADDRESS,STOCKTEL,USERID,REMARK "
				+ " FROM K_STOCK "
				+ "WHERE 1=1 ";
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(stock.getStockNo())) {
			whereSql += " AND STOCKNO = ?";
			params.add(stock.getStockNo());
		}
		if (StringUtils.isNotEmpty(stock.getStockName())) {
			whereSql += " AND STOCKNAME LIKE ?";
			params.add("%" + stock.getStockName() + "%");
		}
		if (StringUtils.isNotEmpty(stock.getUserId())) {
			whereSql += " AND USERID = ?";
			params.add(stock.getUserId());
		}
		
		//初始化stockDao
		stockDao = new StockDaoImpl();
		//根据dao查找仓库，返回多条记录
		StockBean[] stocks = null;
		//获取根据查询条件查出来的记录总数
		int total = 0;
		try {
			stocks = stockDao.findByDynamicSelectByPage(sql + whereSql, params.toArray(),page,rows);
			total = stockDao.getTotal(whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//key=rows 关键字，是EasyUI框架中datagrid组件解析数据所必须的
		json.put("rows",stocks);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
	}
	@Override
	public JSONObject findStockSelects() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		stockDao = new StockDaoImpl();
		List<Map<String, Object>> results = null;
		try {
			results = stockDao.findStockSelects();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("success", true);
		json.put("results", results);
		return json;
	}
	
	

}
