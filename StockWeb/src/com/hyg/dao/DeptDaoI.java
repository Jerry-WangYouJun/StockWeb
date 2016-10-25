package com.hyg.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hyg.bean.DeptBean;

public interface DeptDaoI {
	
	/** 
	 * 添加部门
	 */
	public int insert(DeptBean dept) throws SQLException;

	/** 
	 * 修改部门
	 */
	public int update(BigDecimal pk, DeptBean dept) throws SQLException ;

	/** 
	 * 删除部门
	 */
	public int delete(BigDecimal pk) throws SQLException ;

	/** 
	 * 根据主键ID列查询
	 */
	public DeptBean findByPrimaryKey(BigDecimal id) throws SQLException ;

	/** 
	 *查询所有部门数据
	 */
	public DeptBean[] findAll() throws SQLException ;

	/** 
	 * 动态列查询
	 */
	public DeptBean[] findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException ;

	/** 
	 * where条件查询，注意每个查询条件都要以 and 或or 逻辑修饰符开头
	 */
	public DeptBean[] findByDynamicWhere(String sql, Object[] sqlParams) throws SQLException ;

	/**
	 * 将Map对象转成Object对象
	 * @param list
	 * @return
	 */
	public DeptBean[] MapToObject(List<Map<String, Object>> list);

	/**
	 * 分页动态where条件查询
	 * @param whereSql
	 * @param page
	 * @param rows
	 * @param array
	 * @return
	 * @throws SQLException 
	 */
	public DeptBean[] findByDynamicWhereByPage(String whereSql, int page, int rows,
			Object[] array) throws SQLException;
	/**
	 * 查询记录总数
	 * @param whereSql
	 * @param array
	 * @return
	 */
	public int getTotal(String whereSql, Object[] array)  throws SQLException;
	/**
	 * 查找部门字典
	 * @param sql
	 * @param objects
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> findDeptDics(String sql, Object[] objects) throws SQLException;

}
