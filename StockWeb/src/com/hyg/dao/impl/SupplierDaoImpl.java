package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.SupplierBean;
import com.hyg.bean.SupplierBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.SupplierDaoI;

public class SupplierDaoImpl extends DBUtil implements SupplierDaoI {
	protected Connection supplierConn;
	protected final String SQL_SELECT = "SELECT ID, SUPPLIERNO, SUPPLIERNAME, PROVINCECODE, SUPPLIERADDRESS, SUPPLIEREMAIL, SUPPLIERTEL, SUPPLIERTAX, USERNAME, USERTEL, REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( SUPPLIERNO, SUPPLIERNAME, PROVINCECODE, SUPPLIERADDRESS, SUPPLIEREMAIL, SUPPLIERTEL, SUPPLIERTAX, USERNAME, USERTEL, REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET SUPPLIERNO = ?, SUPPLIERNAME = ?, PROVINCECODE = ?, SUPPLIERADDRESS = ?, SUPPLIEREMAIL = ?, SUPPLIERTEL = ?, SUPPLIERTAX = ?, USERNAME = ?, USERTEL = ?,  REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	
	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_SUPPLIER";
	}

	@Override
	public int insert(SupplierBean supplier) throws SQLException {
		// TODO Auto-generated method stub
		supplierConn = this.getConnection();
		List params = new ArrayList();
		params.add(supplier.getSupplierNo() );
		params.add(supplier.getSupplierName() );
		params.add(supplier.getProvinceCode());
		params.add(supplier.getSupplierAddress() );
		params.add(supplier.getSupplierEmail() );
		params.add(supplier.getSupplierTel());
		params.add(supplier.getSupplierTax());
		params.add(supplier.getUserName());
		params.add(supplier.getUserTel() );
		params.add(supplier.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(supplierConn, SQL_INSERT, params.toArray());
		this.closeConnection(supplierConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, SupplierBean supplier) throws SQLException {
		// TODO Auto-generated method stub
		supplierConn = this.getConnection();
		List params = new ArrayList();
		params.add(supplier.getSupplierNo() );
		params.add(supplier.getSupplierName() );
		params.add(supplier.getProvinceCode());
		params.add(supplier.getSupplierAddress() );
		params.add(supplier.getSupplierEmail() );
		params.add(supplier.getSupplierTel());
		params.add(supplier.getSupplierTax());
		params.add(supplier.getUserName());
		params.add(supplier.getUserTel() );
		params.add(supplier.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(supplierConn, SQL_UPDATE, params.toArray());
		this.closeConnection(supplierConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		supplierConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(supplierConn, SQL_DELETE, params.toArray());
		this.closeConnection(supplierConn);
		return rows;
	}

	@Override
	public SupplierBean findByPrimaryKey(BigDecimal id) throws SQLException {
		SupplierBean supplier = null;
		//1、获取数据库连接
		supplierConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(supplierConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			SupplierBean[] suppliers = this.MapToObject(result);
			if (suppliers.length > 0) {
				supplier = suppliers[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(supplierConn);
		}
		return supplier;
	}

	@Override
	public SupplierBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}

	@Override
	public SupplierBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		supplierConn = this.getConnection();
		resultList = this.executeQuery(supplierConn, sql, sqlParams);
		if (supplierConn != null) {
			this.closeConnection(supplierConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public SupplierBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		supplierConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(supplierConn, SQL, sqlParams);
		
		if (supplierConn != null) {
			this.closeConnection(supplierConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public SupplierBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		SupplierBean[] suppliers = new SupplierBean[list.size()];
		
		List<SupplierBean> results = new ArrayList<SupplierBean>();
		for (Map map:list) {
			if (map != null) {
				SupplierBean supplierBean = new SupplierBean();
				supplierBean.setId(map.get("ID") == null ? null:map.get("ID").toString());
				supplierBean.setSupplierNo(map.get("SUPPLIERNO") == null ? null:map.get("SUPPLIERNO").toString());
				supplierBean.setSupplierName(map.get("SUPPLIERNAME") == null ? null:map.get("SUPPLIERNAME").toString());
				supplierBean.setProvinceCode(map.get("PROVINCECODE") == null ? null:map.get("PROVINCECODE").toString());
				supplierBean.setProvinceName(map.get("PROVINCENAME") == null ? null:map.get("PROVINCENAME").toString());
				supplierBean.setSupplierAddress(map.get("SUPPLIERADDRESS") == null ? null:map.get("SUPPLIERADDRESS").toString());
				supplierBean.setSupplierEmail(map.get("SUPPLIEREMAIL") == null ? null:map.get("SUPPLIEREMAIL").toString());
				supplierBean.setSupplierTel(map.get("SUPPLIERTEL") == null ? null:map.get("SUPPLIERTEL").toString());
				supplierBean.setSupplierTax(map.get("SUPPLIERTAX") == null ? null:map.get("SUPPLIERTAX").toString());
				supplierBean.setUserName(map.get("USERNAME") == null ? null:map.get("USERNAME").toString());
				supplierBean.setUserTel(map.get("USERTEL") == null ? null:map.get("USERTEL").toString());
				supplierBean.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
				results.add(supplierBean);
			}
		}
		return results.toArray(suppliers);
	}

	@Override
	public SupplierBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		supplierConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(supplierConn, SQL, page, rows, params);
		
		if (supplierConn != null) {
			this.closeConnection(supplierConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		supplierConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(supplierConn, SQL, array);
		if (supplierConn != null) {
			this.closeConnection(supplierConn);
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> findSupplierSelects() throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		supplierConn = this.getConnection();
		resultList = this.executeQuery(supplierConn, SQL_SELECT, new Object[0]);
		if (supplierConn != null) {
			this.closeConnection(supplierConn);
		}
		return resultList;
	}

	@Override
	public SupplierBean[] findByDynamicSelectByPage(String sql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		supplierConn = this.getConnection();
		// construct the SQL statement
	
		resultList = this.queryByPage(supplierConn, sql, page, rows, params);
		
		if (supplierConn != null) {
			this.closeConnection(supplierConn);
		}
		return MapToObject(resultList);
	}

}
