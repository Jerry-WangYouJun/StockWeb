package com.hyg.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hyg.bean.StockBean;

public interface StockDaoI {
	/** 
	 * 添加仓库
	 */
	public int insert(StockBean stock) throws SQLException;

	/** 
	 * 修改仓库
	 */
	public int update(BigDecimal pk, StockBean stock) throws SQLException ;

	/** 
	 * 删除仓库
	 */
	public int delete(BigDecimal pk) throws SQLException ;

	/** 
	 * 根据主键ID列查询
	 */
	public StockBean findByPrimaryKey(BigDecimal id) throws SQLException ;

	/** 
	 *查询所有仓库数据
	 */
	public StockBean[] findAll() throws SQLException ;

	/** 
	 * 动态列查询
	 */
	public StockBean[] findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException ;

	/** 
	 * where条件查询，注意每个查询条件都要以 and 或or 逻辑修饰符开头
	 */
	public StockBean[] findByDynamicWhere(String sql, Object[] sqlParams) throws SQLException ;

	/**
	 * 将Map对象转成Object对象
	 * @param list
	 * @return
	 */
	public StockBean[] MapToObject(List<Map<String, Object>> list);
	
	/**
	 * 分页进行动态条件查询
	 * @param whereSql 条件sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         仓库列表数组
	 * @throws SQLException 
	 */
	public StockBean[] findByDynamicSelectByPage(String sql, Object[] params,
			int page, int rows) throws SQLException;
	
	/**
	 * 分页进行动态条件查询
	 * @param whereSql 条件sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         仓库列表数组
	 * @throws SQLException 
	 */
	public StockBean[] findByDynamicWhereByPage(String whereSql, Object[] params,
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
	 * 仓库下拉框
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> findStockSelects() throws SQLException;
}
