package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.InStockBean;
import com.hyg.bean.InStockDetailBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.InstockDetailDaoI;

public class InstockDetailDaoImpl extends DBUtil implements InstockDetailDaoI {
	
	protected Connection instockDetailConn;
	protected final String SQL_SELECT = "SELECT ID, INSTOCKID, PRODUCTNO, PRODUCTNAME, PRODUCTSTANDARD, PRODUCTNUM,UNIT,PRICE,TOTALPRICE,REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( INSTOCKID, PRODUCTNO, PRODUCTNAME, PRODUCTSTANDARD, PRODUCTNUM,UNIT,PRICE,TOTALPRICE,REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET INSTOCKID = ?, PRODUCTNO = ?, PRODUCTNAME = ?, PRODUCTSTANDARD = ?, PRODUCTNUM = ?, UNIT=?, PRICE=?,TOTALPRICE=?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";
	
	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_INSTOCK_DETAIL";
	}

	@Override
	public int insert(InStockDetailBean instockDetail) throws SQLException {
		// TODO Auto-generated method stub
		instockDetailConn = this.getConnection();
		List params = new ArrayList();
		params.add(instockDetail.getInStockId());
		params.add(instockDetail.getProductNo());
		params.add(instockDetail.getProductName());
		params.add(instockDetail.getProductStandard());
		params.add(instockDetail.getProductNum());
		params.add(instockDetail.getUnit());
		params.add(instockDetail.getPrice());
		params.add(instockDetail.getTotalPrice());
		params.add(instockDetail.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(instockDetailConn, SQL_INSERT, params.toArray());
		this.closeConnection(instockDetailConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, InStockDetailBean instockDetail) throws SQLException {
		// TODO Auto-generated method stub
		instockDetailConn = this.getConnection();
		List params = new ArrayList();
		params.add(instockDetail.getInStockId());
		params.add(instockDetail.getProductNo());
		params.add(instockDetail.getProductName());
		params.add(instockDetail.getProductStandard());
		params.add(instockDetail.getProductNum());
		params.add(instockDetail.getUnit());
		params.add(instockDetail.getPrice());
		params.add(instockDetail.getTotalPrice());
		params.add(instockDetail.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(instockDetailConn, SQL_UPDATE, params.toArray());
		this.closeConnection(instockDetailConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		instockDetailConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(instockDetailConn, SQL_DELETE, params.toArray());
		this.closeConnection(instockDetailConn);
		return rows;
	}

	@Override
	public InStockDetailBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		InStockDetailBean instockDetail = null;
		//1、获取数据库连接
		instockDetailConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(instockDetailConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			InStockDetailBean[] instockDetails = this.MapToObject(result);
			if (instockDetails.length > 0) {
				instockDetail = instockDetails[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(instockDetailConn);
		}
		return instockDetail;
	}

	@Override
	public InStockDetailBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}

	@Override
	public InStockDetailBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		instockDetailConn = this.getConnection();
		resultList = this.executeQuery(instockDetailConn, sql, sqlParams);
		if (instockDetailConn != null) {
			this.closeConnection(instockDetailConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InStockDetailBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		instockDetailConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(instockDetailConn, SQL, sqlParams);
		
		if (instockDetailConn != null) {
			this.closeConnection(instockDetailConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InStockDetailBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		InStockDetailBean[] inStockDetailBeans = new InStockDetailBean[list.size()];
		List<InStockDetailBean> results = new ArrayList<InStockDetailBean>();
		for (Map map:list) {
			if (map != null) {
				InStockDetailBean inStockDetailBean = new InStockDetailBean();
				inStockDetailBean.setId(map.get("ID") == null ? null:map.get("ID").toString());
				inStockDetailBean.setInStockId(map.get("INSTOCKID") == null ? null:map.get("INSTOCKID").toString());
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
	public InStockDetailBean[] findByDynamicSelectByPage(String sql, Object[] params,
			int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		instockDetailConn = this.getConnection();
		// construct the SQL statement
	
		resultList = this.queryByPage(instockDetailConn, sql, page, rows, params);
		
		if (instockDetailConn != null) {
			this.closeConnection(instockDetailConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InStockDetailBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		instockDetailConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(instockDetailConn, SQL, page, rows, params);
		
		if (instockDetailConn != null) {
			this.closeConnection(instockDetailConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		instockDetailConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(instockDetailConn, SQL, array);
		if (instockDetailConn != null) {
			this.closeConnection(instockDetailConn);
		}
		return count;
	}

	@Override
	public int getTotalBySql(String sql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		instockDetailConn = this.getConnection();
		
		int count = this.getResultTotal(instockDetailConn, sql, array);
		if (instockDetailConn != null) {
			this.closeConnection(instockDetailConn);
		}
		return count;
	}

}
