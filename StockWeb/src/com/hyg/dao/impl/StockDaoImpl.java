package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.StockBean;
import com.hyg.bean.UserBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.StockDaoI;

public class StockDaoImpl extends DBUtil implements StockDaoI {

	protected Connection stockConn;
	protected final String SQL_SELECT = "SELECT ID, STOCKNO, STOCKNAME, PROVINCECODE, STOCKADDRESS, STOCKTEL, REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( STOCKNO, STOCKNAME, PROVINCECODE, STOCKADDRESS, STOCKTEL, REMARK ) VALUES ( ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET STOCKNO = ?, STOCKNAME = ?, PROVINCECODE = ?, STOCKADDRESS = ?, STOCKTEL = ?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";
	
	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_STOCK";
	}
	
	@Override
	public int insert(StockBean stock) throws SQLException {
		// TODO Auto-generated method stub
		stockConn = this.getConnection();
		List params = new ArrayList();
		params.add(stock.getStockNo());
		params.add(stock.getStockName());
		params.add(stock.getProvinceCode());
		params.add(stock.getStockAddress() );
		params.add(stock.getStockTel() );
		params.add(stock.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(stockConn, SQL_INSERT, params.toArray());
		this.closeConnection(stockConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, StockBean stock) throws SQLException {
		// TODO Auto-generated method stub
		stockConn = this.getConnection();
		List params = new ArrayList();
		params.add(stock.getStockNo());
		params.add(stock.getStockName());
		params.add(stock.getProvinceCode());
		params.add(stock.getStockAddress() );
		params.add(stock.getStockTel() );
		params.add(stock.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(stockConn, SQL_UPDATE, params.toArray());
		this.closeConnection(stockConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		stockConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(stockConn, SQL_DELETE, params.toArray());
		this.closeConnection(stockConn);
		return rows;
	}

	@Override
	public StockBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		StockBean stock = null;
		//1、获取数据库连接
		stockConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(stockConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			StockBean[] stocks = this.MapToObject(result);
			if (stocks.length > 0) {
				stock = stocks[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(stockConn);
		}
		return stock;
	}

	@Override
	public StockBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}

	@Override
	public StockBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		stockConn = this.getConnection();
		resultList = this.executeQuery(stockConn, sql, sqlParams);
		if (stockConn != null) {
			this.closeConnection(stockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public StockBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		stockConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(stockConn, SQL, sqlParams);
		
		if (stockConn != null) {
			this.closeConnection(stockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public StockBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		StockBean[] stocks = new StockBean[list.size()];
		
		List<StockBean> results = new ArrayList<StockBean>();
		for (Map map:list) {
			if (map != null) {
				StockBean stockBean = new StockBean();
				stockBean.setId(map.get("ID") == null ? null:map.get("ID").toString());
				stockBean.setStockNo(map.get("STOCKNO") == null ? null:map.get("STOCKNO").toString());
				stockBean.setStockName(map.get("STOCKNAME") == null ? null:map.get("STOCKNAME").toString());
				stockBean.setProvinceCode(map.get("PROVINCECODE") == null ? null:map.get("PROVINCECODE").toString());
				stockBean.setProvinceCodeName(map.get("PROVINCECODENAME") == null ? null:map.get("PROVINCECODENAME").toString());
				stockBean.setStockAddress(map.get("STOCKADDRESS") == null ? null:map.get("STOCKADDRESS").toString());
				stockBean.setStockTel(map.get("STOCKTEL") == null ? null:map.get("STOCKTEL").toString());
				stockBean.setUserId(map.get("USERID") == null ? null:map.get("USERID").toString());
				stockBean.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
				results.add(stockBean);
			}
		}
		return results.toArray(stocks);
	}

	@Override
	public StockBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		stockConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(stockConn, SQL, page, rows, params);
		
		if (stockConn != null) {
			this.closeConnection(stockConn);
		}
		return MapToObject(resultList);

	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		stockConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(stockConn, SQL, array);
		if (stockConn != null) {
			this.closeConnection(stockConn);
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> findStockSelects() throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		stockConn = this.getConnection();
		resultList = this.executeQuery(stockConn, SQL_SELECT, new Object[0]);
		if (stockConn != null) {
			this.closeConnection(stockConn);
		}
		return resultList;
	}

	@Override
	public StockBean[] findByDynamicSelectByPage(String sql, Object[] params,
			int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		stockConn = this.getConnection();
		// construct the SQL statement
	
		resultList = this.queryByPage(stockConn, sql, page, rows, params);
		
		if (stockConn != null) {
			this.closeConnection(stockConn);
		}
		return MapToObject(resultList);
	}

}
