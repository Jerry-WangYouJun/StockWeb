package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.InStockBean;
import com.hyg.bean.StockBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.InstockDaoI;

public class InstockDaoImpl extends DBUtil implements InstockDaoI {
	protected Connection instockConn;
	protected final String SQL_SELECT = "SELECT ID, INSTOCKNO, STOCKID, SUPPLIERID, INSTOCKSTATE, INSTOCKNUM,INSTOCKPRICE,INSTOCKDATE,REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( INSTOCKNO, STOCKID, SUPPLIERID, INSTOCKSTATE, INSTOCKNUM,INSTOCKPRICE,INSTOCKDATE,REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET INSTOCKNO = ?, STOCKID = ?, SUPPLIERID = ?, INSTOCKSTATE = ?, INSTOCKNUM = ?, INSTOCKPRICE=?, INSTOCKDATE=?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";
	
	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_INSTOCK";
	}
	
	@Override
	public int insert(InStockBean instock) throws SQLException {
		// TODO Auto-generated method stub
		instockConn = this.getConnection();
		List params = new ArrayList();
		params.add(instock.getInStockNo());
		params.add(instock.getStockId());
		params.add(instock.getSupplierId());
		params.add("00");
		params.add(instock.getInStockNum());
		params.add(instock.getInStockPrice());
		params.add(instock.getInStockDate());
		params.add(instock.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(instockConn, SQL_INSERT, params.toArray());
		this.closeConnection(instockConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, InStockBean instock) throws SQLException {
		// TODO Auto-generated method stub
		instockConn = this.getConnection();
		List params = new ArrayList();
		params.add(instock.getInStockNo());
		params.add(instock.getStockId());
		params.add(instock.getSupplierId());
		params.add(instock.getInStockState());
		params.add(instock.getInStockNum());
		params.add(instock.getInStockPrice());
		params.add(instock.getInStockDate());
		params.add(instock.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(instockConn, SQL_UPDATE, params.toArray());
		this.closeConnection(instockConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		instockConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(instockConn, SQL_DELETE, params.toArray());
		this.closeConnection(instockConn);
		return rows;
	}

	@Override
	public InStockBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		InStockBean stock = null;
		//1、获取数据库连接
		instockConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(instockConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			InStockBean[] stocks = this.MapToObject(result);
			if (stocks.length > 0) {
				stock = stocks[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(instockConn);
		}
		return stock;
	}

	@Override
	public InStockBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}

	@Override
	public InStockBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		instockConn = this.getConnection();
		resultList = this.executeQuery(instockConn, sql, sqlParams);
		if (instockConn != null) {
			this.closeConnection(instockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InStockBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		instockConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(instockConn, SQL, sqlParams);
		
		if (instockConn != null) {
			this.closeConnection(instockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InStockBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		InStockBean[] inStockBeans =  new InStockBean[list.size()];
		List<InStockBean> results = new ArrayList<InStockBean>();
		for (Map map:list) {
			if (map != null) {
				InStockBean inStockBean = new InStockBean();
				inStockBean.setId(map.get("ID") == null ? null:map.get("ID").toString());
				inStockBean.setInStockNo(map.get("INSTOCKNO") == null ? null:map.get("INSTOCKNO").toString());
				inStockBean.setStockId(map.get("STOCKID") == null ? null:map.get("STOCKID").toString());
				inStockBean.setStockName(map.get("STOCKNAME") == null ? null:map.get("STOCKNAME").toString());
				inStockBean.setSupplierId(map.get("SUPPLIERID") == null ? null:map.get("SUPPLIERID").toString());
				inStockBean.setSupplierName(map.get("SUPPLIERNAME") == null ? null:map.get("SUPPLIERNAME").toString());
				inStockBean.setInStockState(map.get("INSTOCKSTATE") == null ? null:map.get("INSTOCKSTATE").toString());
				inStockBean.setInStockNum(map.get("INSTOCKNUM") == null ? null:map.get("INSTOCKNUM").toString());
				inStockBean.setInStockPrice(map.get("INSTOCKPRICE") == null ? null:map.get("INSTOCKPRICE").toString());
				inStockBean.setInStockDate(map.get("INSTOCKDATE") == null ? null:map.get("INSTOCKDATE").toString());
				inStockBean.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
				results.add(inStockBean);
			}
		}
		return results.toArray(inStockBeans);
	}

	@Override
	public InStockBean[] findByDynamicSelectByPage(String sql, Object[] params,
			int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		instockConn = this.getConnection();
		// construct the SQL statement
	
		resultList = this.queryByPage(instockConn, sql, page, rows, params);
		
		if (instockConn != null) {
			this.closeConnection(instockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InStockBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		instockConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(instockConn, SQL, page, rows, params);
		
		if (instockConn != null) {
			this.closeConnection(instockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		instockConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(instockConn, SQL, array);
		if (instockConn != null) {
			this.closeConnection(instockConn);
		}
		return count;
	}

	@Override
	public int getTotalBySql(String sql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		instockConn = this.getConnection();
		
		int count = this.getResultTotal(instockConn, sql, array);
		if (instockConn != null) {
			this.closeConnection(instockConn);
		}
		return count;
	}

}
