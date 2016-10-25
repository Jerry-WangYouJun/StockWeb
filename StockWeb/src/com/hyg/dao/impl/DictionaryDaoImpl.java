package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyg.bean.DictionaryBean;
import com.hyg.bean.DictionaryBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.DictionaryDaoI;

public class DictionaryDaoImpl extends DBUtil implements DictionaryDaoI {

	protected Connection dicConn;
	protected final String SQL_SELECT = "SELECT ID, DICID, DICTYPE, DICCODE, DICVALUE, DICSORT, VALIDATEFLAG, REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( DICID, DICTYPE, DICCODE, DICVALUE, DICSORT, VALIDATEFLAG, REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET DICID = ?, DICTYPE = ?, DICCODE = ?, DICVALUE = ?, DICSORT = ?, VALIDATEFLAG = ?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	

	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_DICTIONARY";
	}
	@Override
	public int insert(DictionaryBean dic) throws SQLException {
		// TODO Auto-generated method stub
		dicConn = this.getConnection();
		List params = new ArrayList();
		params.add(dic.getDicId());
		params.add(dic.getDicType() );
		params.add(dic.getDicCode());
		params.add(dic.getDicValue());
		params.add(dic.getDicSort());
		params.add("0");
		params.add(dic.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(dicConn, SQL_INSERT, params.toArray());
		this.closeConnection(dicConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, DictionaryBean dic) throws SQLException {
		// TODO Auto-generated method stub
		dicConn = this.getConnection();
		List params = new ArrayList();
		params.add(dic.getDicId());
		params.add(dic.getDicType() );
		params.add(dic.getDicCode());
		params.add(dic.getDicValue());
		params.add(dic.getDicSort());
		params.add(dic.getValidateFlag());
		params.add(dic.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(dicConn, SQL_UPDATE, params.toArray());
		this.closeConnection(dicConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		dicConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(dicConn, SQL_DELETE, params.toArray());
		this.closeConnection(dicConn);
		return rows;
	}

	@Override
	public DictionaryBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		DictionaryBean dic = null;
		//1、获取数据库连接
		dicConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(dicConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			DictionaryBean[] dics = this.MapToObject(result);
			if (dics.length > 0) {
				dic = dics[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(dicConn);
		}
		return dic;
	}

	@Override
	public DictionaryBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}

	@Override
	public DictionaryBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		dicConn = this.getConnection();
		resultList = this.executeQuery(dicConn, sql, sqlParams);
		if (dicConn != null) {
			this.closeConnection(dicConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public DictionaryBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		dicConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(dicConn, SQL, sqlParams);
		
		if (dicConn != null) {
			this.closeConnection(dicConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public DictionaryBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		DictionaryBean[] dicBeans = new DictionaryBean[list.size()];
		List<DictionaryBean> results = new ArrayList<DictionaryBean>();
		for (Map map:list) {
			if (map != null) {
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setId(map.get("ID") == null ? null:map.get("ID").toString());
				dictionaryBean.setDicId(map.get("DICID") == null ? null:map.get("DICID").toString());
				dictionaryBean.setDicType(map.get("DICTYPE") == null ? null:map.get("DICTYPE").toString());
				dictionaryBean.setDicCode(map.get("DICCODE") == null ? null:map.get("DICCODE").toString());
				dictionaryBean.setDicValue(map.get("DICVALUE") == null ? null:map.get("DICVALUE").toString());
				dictionaryBean.setDicSort(map.get("DICSORT") == null ? null:map.get("DICSORT").toString());
				dictionaryBean.setValidateFlag(map.get("VALIDATEFLAG") == null ? null:map.get("VALIDATEFLAG").toString());
				dictionaryBean.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
				results.add(dictionaryBean);
			}
		}
		return results.toArray(dicBeans);
	}

	@Override
	public DictionaryBean[] findByDynamicWhereByPage(String whereSql,
			 int page, int rows,Object[] params) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		dicConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(dicConn, SQL, page, rows, params);
		
		if (dicConn != null) {
			this.closeConnection(dicConn);
		}
		return MapToObject(resultList);

	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		dicConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(dicConn, SQL, array);
		if (dicConn != null) {
			this.closeConnection(dicConn);
		}
		return count;
	}
	@Override
	public List<Map<String, Object>> findDicByDicId(String whereSql,
			Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		dicConn = this.getConnection();
		
		String sql = " SELECT DICCODE,DICVALUE FROM K_DICTIONARY WHERE VALIDATEFLAG='0' " 
				+ whereSql + " order by dicSort ";
		List<Map<String, Object>> results = this.executeQuery(dicConn, sql, array);
		return results;
	}
	@Override
	public Map<String, Object> findDicsByWhere(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		dicConn = this.getConnection();
		
		String sql = " SELECT DICCODE,DICVALUE FROM K_DICTIONARY WHERE VALIDATEFLAG='0' " 
				+ whereSql + " order by dicSort ";
		List<Map<String, Object>> results = this.executeQuery(dicConn, sql, array);
		if (results.size() > 0) {
			return results.get(0);
		}else{
			return new HashMap<String, Object>();
		}
	}

}
