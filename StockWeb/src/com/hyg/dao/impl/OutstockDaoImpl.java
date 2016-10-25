package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.OutStockBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.OutstockDaoI;

public class OutstockDaoImpl extends DBUtil implements OutstockDaoI {
	protected Connection outstockConn;
	protected final String SQL_SELECT = "SELECT ID, OUTSTOCKNO, STOCKID, SUPPLIERID, OUTSTOCKSTATE, OUTSTOCKNUM,OUTSTOCKPRICE,OUTSTOCKDATE,REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( OUTSTOCKNO, STOCKID, SUPPLIERID, OUTSTOCKSTATE, OUTSTOCKNUM,OUTSTOCKPRICE,OUTSTOCKDATE,REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET OUTSTOCKNO = ?, STOCKID = ?, SUPPLIERID = ?, OUTSTOCKSTATE = ?, OUTSTOCKNUM = ?, OUTSTOCKPRICE=?, OUTSTOCKDATE=?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";
	
	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_OUTSTOCK";
	}
	
	@Override
	public int insert(OutStockBean outstock) throws SQLException {
		// TODO Auto-generated method stub
		outstockConn = this.getConnection();
		List params = new ArrayList();
		params.add(outstock.getOutStockNo());
		params.add(outstock.getStockId());
		params.add(outstock.getSupplierId());
		params.add("00");
		params.add(outstock.getOutStockNum());
		params.add(outstock.getOutStockPrice());
		params.add(outstock.getOutStockDate());
		params.add(outstock.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(outstockConn, SQL_INSERT, params.toArray());
		this.closeConnection(outstockConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, OutStockBean outstock) throws SQLException {
		// TODO Auto-generated method stub
		outstockConn = this.getConnection();
		List params = new ArrayList();
		params.add(outstock.getOutStockNo());
		params.add(outstock.getStockId());
		params.add(outstock.getSupplierId());
		params.add(outstock.getOutStockState());
		params.add(outstock.getOutStockNum());
		params.add(outstock.getOutStockPrice());
		params.add(outstock.getOutStockDate());
		params.add(outstock.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(outstockConn, SQL_UPDATE, params.toArray());
		this.closeConnection(outstockConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		outstockConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(outstockConn, SQL_DELETE, params.toArray());
		this.closeConnection(outstockConn);
		return rows;
	}

	@Override
	public OutStockBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		OutStockBean stock = null;
		//1、获取数据库连接
		outstockConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(outstockConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			OutStockBean[] stocks = this.MapToObject(result);
			if (stocks.length > 0) {
				stock = stocks[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(outstockConn);
		}
		return stock;
	}

	@Override
	public OutStockBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}

	@Override
	public OutStockBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		outstockConn = this.getConnection();
		resultList = this.executeQuery(outstockConn, sql, sqlParams);
		if (outstockConn != null) {
			this.closeConnection(outstockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public OutStockBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		outstockConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(outstockConn, SQL, sqlParams);
		
		if (outstockConn != null) {
			this.closeConnection(outstockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public OutStockBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		OutStockBean[] outStockBeans =  new OutStockBean[list.size()];
		List<OutStockBean> results = new ArrayList<OutStockBean>();
		for (Map map:list) {
			if (map != null) {
				OutStockBean outStockBean = new OutStockBean();
				outStockBean.setId(map.get("ID") == null ? null:map.get("ID").toString());
				outStockBean.setOutStockNo(map.get("OUTSTOCKNO") == null ? null:map.get("OUTSTOCKNO").toString());
				outStockBean.setStockId(map.get("STOCKID") == null ? null:map.get("STOCKID").toString());
				outStockBean.setStockName(map.get("STOCKNAME") == null ? null:map.get("STOCKNAME").toString());
				outStockBean.setSupplierId(map.get("SUPPLIERID") == null ? null:map.get("SUPPLIERID").toString());
				outStockBean.setSupplierName(map.get("SUPPLIERNAME") == null ? null:map.get("SUPPLIERNAME").toString());
				outStockBean.setOutStockState(map.get("OUTSTOCKSTATE") == null ? null:map.get("OUTSTOCKSTATE").toString());
				outStockBean.setOutStockNum(map.get("OUTSTOCKNUM") == null ? null:map.get("OUTSTOCKNUM").toString());
				outStockBean.setOutStockPrice(map.get("OUTSTOCKPRICE") == null ? null:map.get("OUTSTOCKPRICE").toString());
				outStockBean.setOutStockDate(map.get("OUTSTOCKDATE") == null ? null:map.get("OUTSTOCKDATE").toString());
				outStockBean.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
				results.add(outStockBean);
			}
		}
		return results.toArray(outStockBeans);
	}

	@Override
	public OutStockBean[] findByDynamicSelectByPage(String sql, Object[] params,
			int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		outstockConn = this.getConnection();
		// construct the SQL statement
	
		resultList = this.queryByPage(outstockConn, sql, page, rows, params);
		
		if (outstockConn != null) {
			this.closeConnection(outstockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public OutStockBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		outstockConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(outstockConn, SQL, page, rows, params);
		
		if (outstockConn != null) {
			this.closeConnection(outstockConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		outstockConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(outstockConn, SQL, array);
		if (outstockConn != null) {
			this.closeConnection(outstockConn);
		}
		return count;
	}

	@Override
	public int getTotalBySql(String sql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		outstockConn = this.getConnection();
		
		int count = this.getResultTotal(outstockConn, sql, array);
		if (outstockConn != null) {
			this.closeConnection(outstockConn);
		}
		return count;
	}

}
