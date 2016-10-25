package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.UserBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.UserDaoI;


public class UserDaoImpl extends DBUtil implements UserDaoI {
	protected Connection userConn;
	protected final String SQL_SELECT = "SELECT ID, USERNO, USERNAME, PWD, DEPTNO, SEX, AGE, POSITION, HOMETOWN, TELPHONE, EMAIL, ROLENO, REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( USERNO, USERNAME, PWD, DEPTNO, SEX, AGE, POSITION, HOMETOWN, TELPHONE, EMAIL, ROLENO, REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET ID = ?, USERNO = ?, USERNAME = ?, PWD = ?, DEPTNO = ?, SEX = ?, AGE = ?, POSITION = ?, HOMETOWN = ?, TELPHONE = ?, EMAIL = ?, ROLENO = ?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_USER";
	}
	@Override
	public List<Map<String, Object>> findUserByWhere(UserBean user) {
		// TODO Auto-generated method stub
		//(1)、获取数据库连接
		userConn = this.getConnection();
		//(2)、编写预编译的sql语句
		String sql = SQL_SELECT + " where 1=1 ";
		//(3)、基于(2)进行判断是否需要添加参数
		List param = new ArrayList();
		
		if (StringUtils.isNotEmpty(user.getUserNo())) {
			sql += " and userNo = ? ";
			param.add(user.getUserNo());
		}
		
		if (StringUtils.isNotEmpty(user.getPwd())) {
			sql += " and pwd = ? ";
			param.add(user.getPwd());
		}
		List<Map<String, Object>> results = null;
		//(4)、通过DBUtil去执行 executeQuery()方法
		try {
			results = this.executeQuery(userConn, sql, param.toArray());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//(6)、关闭数据库连接
			this.closeConnection(userConn);
		}
		//(5)、进行业务逻辑操作
		return results;
	}

	@Override
	public int insert(UserBean user) throws SQLException {
		// TODO Auto-generated method stub
		userConn = this.getConnection();
		List params = new ArrayList();
		params.add(user.getUserNo() );
		params.add(user.getUserName() );
		params.add("123456" );
		params.add(user.getDeptNo() );
		params.add(user.getSex() );
		params.add(user.getAge() );
		params.add(user.getPosition() );
		params.add(user.getHometown() );
		params.add(user.getTelphone() );
		params.add(user.getEmail() );
		params.add("emp");
		params.add(user.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(userConn, SQL_INSERT, params.toArray());
		this.closeConnection(userConn);
		return rows;
	}
	@Override
	public int update(BigDecimal pk, UserBean user) throws SQLException {
		userConn = this.getConnection();
		List params = new ArrayList();
		params.add(user.getId());
		params.add(user.getUserNo() );
		params.add(user.getUserName() );
		params.add(user.getPwd() );
		params.add(user.getDeptNo() );
		params.add(user.getSex() );
		params.add(user.getAge() );
		params.add(user.getPosition() );
		params.add(user.getHometown() );
		params.add(user.getTelphone() );
		params.add(user.getEmail() );
		params.add(user.getRoleNo() );
		params.add(user.getRemark() );
		//加上更新的限制条件
		params.add(pk);
		
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(userConn, SQL_UPDATE, params.toArray());
		this.closeConnection(userConn);
		return rows;
	}
	@Override
	public int delete(BigDecimal pk)  throws SQLException{
		userConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(userConn, SQL_DELETE, params.toArray());
		this.closeConnection(userConn);
		return rows;
	}
	@Override
	public UserBean findByPrimaryKey(BigDecimal id) {
		// TODO Auto-generated method stub
		UserBean user = null;
		//1、获取数据库连接
		userConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(userConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			UserBean[] users = this.MapToObject(result);
			if (users.length > 0) {
				user = users[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(userConn);
		}
		return user;
	}
	@Override
	public UserBean[] findAll() throws SQLException {
		//如果参数列表不需要，则要定义一个new Object[0] 进行填充params参数，不能用null代替
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}
	@Override
	public UserBean[] findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		userConn = this.getConnection();
		resultList = this.executeQuery(userConn, sql, sqlParams);
		if (userConn != null) {
			this.closeConnection(userConn);
		}
		return MapToObject(resultList);
	}
	@Override
	public UserBean[] findByDynamicWhere(String sql, Object[] sqlParams) throws SQLException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		userConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(userConn, SQL, sqlParams);
		
		if (userConn != null) {
			this.closeConnection(userConn);
		}
		return MapToObject(resultList);
	}
	@Override
	public UserBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		UserBean[] userArr = new UserBean[list.size()];
		List<UserBean> users = new ArrayList<UserBean>();
		for (Map<String, Object> map : list) {
			UserBean user  = new UserBean();
			user.setId(map.get("ID") == null ? null:map.get("ID").toString());
			user.setUserNo(map.get("USERNO") == null ? null:map.get("USERNO").toString());
			user.setUserName(map.get("USERNAME") == null ? null:map.get("USERNAME").toString());
			user.setPwd(map.get("PWD") == null ? null:map.get("PWD").toString());
			user.setDeptNo(map.get("DEPTNO") == null ? null:map.get("DEPTNO").toString());
			user.setSex(map.get("SEX") == null ? null:map.get("SEX").toString());
			user.setAge(map.get("AGE") == null ? null:map.get("AGE").toString());
			user.setPosition(map.get("POSITION") == null ? null:map.get("POSITION").toString());
			user.setHometown(map.get("HOMETOWN") == null ? null:map.get("HOMETOWN").toString());
			user.setTelphone(map.get("TELPHONE") == null ? null:map.get("TELPHONE").toString());
			user.setEmail(map.get("EMAIL") == null ? null:map.get("EMAIL").toString());
			user.setRoleNo(map.get("ROLENO") == null ? null:map.get("ROLENO").toString());
			user.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
			users.add(user);
		}
		return users.toArray(userArr);
	}
	@Override
	public UserBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		userConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(userConn, SQL, page, rows, params);
		
		if (userConn != null) {
			this.closeConnection(userConn);
		}
		return MapToObject(resultList);

		
	}
	@Override
	public int getTotal(String whereSql, Object[] params) throws SQLException {
		// TODO Auto-generated method stub
		userConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(userConn, SQL, params);
		if (userConn != null) {
			this.closeConnection(userConn);
		}
		return count;
	}


}
