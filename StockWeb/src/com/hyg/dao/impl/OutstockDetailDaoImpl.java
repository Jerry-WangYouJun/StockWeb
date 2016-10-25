package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.OutStockBean;
import com.hyg.bean.OutStockDetailBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.OutstockDetailDaoI;

public class OutstockDetailDaoImpl extends DBUtil implements OutstockDetailDaoI {
	
	protected Connection outstockDetailConn;
	protected final String SQL_SELECT = "SELECT ID, OUTSTOCKID, PRODUCTNO, PRODUCTNAME, PRODUCTSTANDARD, PRODUCTNUM,UNIT,PRICE,TOTALPRICE,REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( OUTSTOCKID, PRODUCTNO, PRODUCTNAME, PRODUCTSTANDARD, PRODUCTNUM,UNIT,PRICE,TOTALPRICE,REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET OUTSTOCKID = ?, PRODUCTNO = ?, PRODUCTNAME = ?, PRODUCTSTANDARD = ?, PRODUCTNUM = ?, UNIT=?, PRICE=?,TOTALPRICE=?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";
	
	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_OUTSTOCK_DETAIL";
	}

	@Override
	public int insert(OutStockDetailBean outstockDetail) throws SQLException {
		// TODO Auto-generated method stub
		outstockDetailConn = this.getConnection();
		List params = new ArrayList();
		params.add(outstockDetail.getOutStockId());
		params.add(outstockDetail.getProductNo());
		params.add(outstockDetail.getProductName());
		params.add(outstockDetail.getProductStandard());
		params.add(outstockDetail.getProductNum());
		params.add(outstockDetail.getUnit());
		params.add(outstockDetail.getPrice());
		params.add(outstockDetail.getTotalPrice());
		params.add(outstockDetail.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(outstockDetailConn, SQL_INSERT, params.toArray());
		this.closeConnection(outstockDetailConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, OutStockDetailBean outstockDetail) throws SQLException {
		// TODO Auto-generated method stub
		outstockDetailConn = this.getConnection();
		List params = new ArrayList();
		params.add(outstockDetail.getOutStockId());
		params.add(outstockDetail.getProductNo());
		params.add(outstockDetail.getProductName());
		params.add(outstockDetail.getProductStandard());
		params.add(outstockDetail.getProductNum());
		params.add(outstockDetail.getUnit());
		params.add(outstockDetail.getPrice());
		params.add(outstockDetail.getTotalPrice());
		params.add(outstockDetail.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(outstockDetailConn, SQL_UPDATE, params.toArray());
		this.closeConnection(outstockDetailConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		outstockDetailConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(outstockDetailConn, SQL_DELETE, params.toArray());
		this.closeConnection(outstockDetailConn);
		return rows;
	}

	@Override
	public OutStockDetailBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		OutStockDetailBean outstockDetail = null;
		//1、获取数据库连接
		outstockDetailConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(outstockDetailConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			OutStockDetailBean[] outstockDetails = this.MapToObject(result);
			if (outstockDetails.length > 0) {
				outstockDetail = outstockDetails[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(outstockDetailConn);
		}
		return outstockDetail;
	}

	@Override
	public OutStockDetailBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}

	@Override
	public OutStockDetailBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		outstockDetailConn = this.getConnection();
		resultList = this.executeQuery(outstockDetailConn, sql, sqlParams);
		if (outstockDetailConn != null) {
			this.closeConnection(outstockDetailConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public OutStockDetailBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		outstockDetailConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(outstockDetailConn, SQL, sqlParams);
		
		if (outstockDetailConn != null) {
			this.closeConnection(outstockDetailConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public OutStockDetailBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		OutStockDetailBean[] inStockDetailBeans = new OutStockDetailBean[list.size()];
		List<OutStockDetailBean> results = new ArrayList<OutStockDetailBean>();
		for (Map map:list) {
			if (map != null) {
				OutStockDetailBean inStockDetailBean = new OutStockDetailBean();
				inStockDetailBean.setId(map.get("ID") == null ? null:map.get("ID").toString());
				inStockDetailBean.setOutStockId(map.get("OUTSTOCKID") == null ? null:map.get("OUTSTOCKID").toString());
				inStockDetailBean.setStockNo(map.get("STOCKNO") == null ? null:map.get("STOCKNO").toString());
				inStockDetailBean.setStockName(map.get("STOCKNAME") == null ? null:map.get("STOCKNAME").toString());
				inStockDetailBean.setProductNo(map.get("PRODUCTNO") == null ? null:map.get("PRODUCTNO").toString());
				inStockDetailBean.setProductName(map.get("PRODUCTNAME") == null ? null:map.get("PRODUCTNAME").toString());
				inStockDetailBean.setProductStandard(map.get("PRODUCTSTANDARD") == null ? null:map.get("PRODUCTSTANDARD").toString());
				inStockDetailBean.setProductNum(map.get("PRODUCTNUM") == null ? null:map.get("PRODUCTNUM").toString());
				inStockDetailBean.setUnit(map.get("UNIT") == null ? null:map.get("UNIT").toString());
				inStockDetailBean.setPrice(map.get("PRICE") == null ? null:map.get("PRICE").toString());
				inStockDetailBean.setTotalPrice(map.get("TOTALPRICE") == null ? null:map.get("TOTALPRICE").toString());
				inStockDetailBean.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
				results.add(inStockDetailBean);
			}
		}
		return results.toArray(inStockDetailBeans);
	}

	@Override
	public OutStockDetailBean[] findByDynamicSelectByPage(String sql, Object[] params,
			int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		outstockDetailConn = this.getConnection();
		// construct the SQL statement
	
		resultList = this.queryByPage(outstockDetailConn, sql, page, rows, params);
		
		if (outstockDetailConn != null) {
			this.closeConnection(outstockDetailConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public OutStockDetailBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		outstockDetailConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(outstockDetailConn, SQL, page, rows, params);
		
		if (outstockDetailConn != null) {
			this.closeConnection(outstockDetailConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		outstockDetailConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(outstockDetailConn, SQL, array);
		if (outstockDetailConn != null) {
			this.closeConnection(outstockDetailConn);
		}
		return count;
	}

	@Override
	public int getTotalBySql(String sql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		outstockDetailConn = this.getConnection();
		
		int count = this.getResultTotal(outstockDetailConn, sql, array);
		if (outstockDetailConn != null) {
			this.closeConnection(outstockDetailConn);
		}
		return count;
	}

}
