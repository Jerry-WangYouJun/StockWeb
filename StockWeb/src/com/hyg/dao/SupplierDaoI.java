package com.hyg.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hyg.bean.SupplierBean;

public interface SupplierDaoI {
	/** 
	 * 添加供应商
	 */
	public int insert(SupplierBean supplier) throws SQLException;

	/** 
	 * 修改供应商
	 */
	public int update(BigDecimal pk, SupplierBean supplier) throws SQLException ;

	/** 
	 * 删除供应商
	 */
	public int delete(BigDecimal pk) throws SQLException ;

	/** 
	 * 根据主键ID列查询
	 */
	public SupplierBean findByPrimaryKey(BigDecimal id) throws SQLException ;

	/** 
	 *查询所有供应商数据
	 */
	public SupplierBean[] findAll() throws SQLException ;

	/** 
	 * 动态列查询
	 */
	public SupplierBean[] findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException ;

	/** 
	 * where条件查询，注意每个查询条件都要以 and 或or 逻辑修饰符开头
	 */
	public SupplierBean[] findByDynamicWhere(String sql, Object[] sqlParams) throws SQLException ;

	/**
	 * 将Map对象转成Object对象
	 * @param list
	 * @return
	 */
	public SupplierBean[] MapToObject(List<Map<String, Object>> list);
	/**
	 * 分页进行动态条件查询
	 * @param whereSql 条件sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         供应商列表数组
	 * @throws SQLException 
	 */
	public SupplierBean[] findByDynamicWhereByPage(String whereSql, Object[] params,
			int page, int rows) throws SQLException;
	
	/**
	 * 分页进行动态条件查询
	 * @param whereSql 条件sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         供应商列表数组
	 * @throws SQLException 
	 */
	public SupplierBean[] findByDynamicSelectByPage(String sql, Object[] params,
			int page, int rows) throws SQLException;
	/**
	 * 根据查询条件获得记录总数
	 * @param whereSql  查询条件
	 * @param array
	 * @return
	 * @throws SQLException 
	 */
	public int getTotal(String whereSql, Object[] array) throws SQLException;

	/**
	 * 供应商字典
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findSupplierSelects() throws SQLException;
}
