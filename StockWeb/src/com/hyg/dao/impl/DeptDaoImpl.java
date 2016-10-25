package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.DeptBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.DeptDaoI;

public class DeptDaoImpl extends DBUtil  implements DeptDaoI {

	protected Connection deptConn;
	protected final String SQL_SELECT = "SELECT ID, DEPTNO, DEPTNAME, DEPTLEADER, DEPTTEL, PARENTDEPTNO, DEPTDESC, REMARK FROM " + getTableName() + "";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( ID, DEPTNO, DEPTNAME, DEPTLEADER, DEPTTEL, PARENTDEPTNO, DEPTDESC, REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET  DEPTNO = ?, DEPTNAME = ?, DEPTLEADER = ?, DEPTTEL = ?, PARENTDEPTNO = ?, DEPTDESC = ?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_DEPT";
	}
	

	@Override
	public int insert(DeptBean dept) throws SQLException {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		int rows = 0;
		deptConn = this.getConnection();
		params.add(dept.getId() );
		params.add(dept.getDeptNo() );
		params.add(dept.getDeptName() );
		params.add(dept.getDeptLeader() );
		params.add(dept.getDeptTel() );
		params.add(dept.getParentDeptNo() );
		params.add(dept.getDeptDesc() );
		params.add(dept.getRemark() );
		rows = this.executeUpdate(deptConn,SQL_INSERT,params.toArray());
		if (deptConn != null) {
			this.closeConnection(deptConn);
		}
		return rows;
	}

	@Override
	public int update(BigDecimal pk, DeptBean dept) throws SQLException {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		int rows = 0;
		deptConn = this.getConnection();
		params.add(dept.getDeptNo() );
		params.add(dept.getDeptName() );
		params.add(dept.getDeptLeader() );
		params.add(dept.getDeptTel() );
		params.add(dept.getParentDeptNo() );
		params.add(dept.getDeptDesc() );
		params.add(dept.getRemark() );
		params.add(pk);
		rows = this.executeUpdate(deptConn,SQL_UPDATE,params.toArray());
		if (deptConn != null) {
			this.closeConnection(deptConn);
		}
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		int rows = 0;
		deptConn = this.getConnection();
		params.add(pk);
		rows = this.executeUpdate(deptConn,SQL_DELETE,params.toArray());
		if (deptConn != null) {
			this.closeConnection(deptConn);
		}
		return rows;
	}

	@Override
	public DeptBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		DeptBean ret[] = findByDynamicSelect( SQL_SELECT + " WHERE ID = ?", new Object[] { id } );
		return ret.length==0 ? null : ret[0];
	}

	@Override
	public DeptBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0] );
	}

	@Override
	public DeptBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		deptConn = this.getConnection();
		resultList = this.executeQuery(deptConn, sql, sqlParams);
		if (deptConn != null) {
			this.closeConnection(deptConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public DeptBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		deptConn = this.getConnection();
		final String SQL = SQL_SELECT + " WHERE " + sql;
		resultList = this.executeQuery(deptConn, SQL, sqlParams);
		if (deptConn != null) {
			this.closeConnection(deptConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public DeptBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		DeptBean[] depts = new DeptBean[list.size()];
		List<DeptBean> deptList = new ArrayList<DeptBean>();
		for (Map<String, Object> map : list) {
			DeptBean dept = new DeptBean();
			dept.setId(map.get("ID") == null ? null:map.get("ID").toString());
			dept.setDeptNo(map.get("DEPTNO") == null ? null:map.get("DEPTNO").toString());
			dept.setDeptName(map.get("DEPTNAME") == null ? null:map.get("DEPTNAME").toString());
			dept.setDeptLeader(map.get("DEPTLEADER") == null ? null:map.get("DEPTLEADER").toString());
			dept.setDeptTel(map.get("DEPTTEL") == null ? null:map.get("DEPTTEL").toString());
			dept.setDeptDesc(map.get("DEPTDESC") == null ? null:map.get("DEPTDESC").toString());
			dept.setParentDeptNo(map.get("PARENTDEPTNO") == null ? null:map.get("PARENTDEPTNO").toString());
			dept.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());

			deptList.add(dept);
		}
		return deptList.toArray(depts);
	}

	@Override
	public DeptBean[] findByDynamicWhereByPage(String whereSql, int page,
			int rows, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		deptConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(deptConn, SQL, page, rows, array);
		
		if (deptConn != null) {
			this.closeConnection(deptConn);
		}
		return MapToObject(resultList);

	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		deptConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(deptConn, SQL, array);
		if (deptConn != null) {
			this.closeConnection(deptConn);
		}
		return count;
	}


	@Override
	public List<Map<String, Object>> findDeptDics(String sql, Object[] objects) throws SQLException {
		// TODO Auto-generated method stub
		deptConn = this.getConnection();
		List<Map<String,Object>> results = this.executeQuery(deptConn, sql, objects);
		if (deptConn != null) {
			this.closeConnection(deptConn);
		}
		return results;
	}

}
