package com.hyg.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hyg.bean.InStockDetailBean;

public interface InstockDetailDaoI {
	/** 
	 * 添加入库单明细
	 */
	public int insert(InStockDetailBean instockDetail) throws SQLException;

	/** 
	 * 修改入库单明细
	 */
	public int update(BigDecimal pk, InStockDetailBean instockDetail) throws SQLException ;

	/** 
	 * 删除入库单明细
	 */
	public int delete(BigDecimal pk) throws SQLException ;

	/** 
	 * 根据主键ID列查询
	 */
	public InStockDetailBean findByPrimaryKey(BigDecimal id) throws SQLException ;

	/** 
	 *查询所有入库单明细数据
	 */
	public InStockDetailBean[] findAll() throws SQLException ;

	/** 
	 * 动态列查询
	 */
	public InStockDetailBean[] findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException ;

	/** 
	 * where条件查询，注意每个查询条件都要以 and 或or 逻辑修饰符开头
	 */
	public InStockDetailBean[] findByDynamicWhere(String sql, Object[] sqlParams) throws SQLException ;

	/**
	 * 将Map对象转成Object对象
	 * @param list
	 * @return
	 */
	public InStockDetailBean[] MapToObject(List<Map<String, Object>> list);
	
	/**
	 * 分页进行动态条件查询
	 * @param whereSql 条件sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         仓库列表数组
	 * @throws SQLException 
	 */
	public InStockDetailBean[] findByDynamicSelectByPage(String sql, Object[] params,
			int page, int rows) throws SQLException;
	
	/**
	 * 分页进行动态条件查询
	 * @param whereSql 条件sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         入库单明细列表数组
	 * @throws SQLException 
	 */
	public InStockDetailBean[] findByDynamicWhereByPage(String whereSql, Object[] params,
			int page, int rows) throws SQLException;
	/**
	 * 根据查询条件获得记录总数
	 * @param whereSql  查询条件
	 * @param array
	 * @return
	 * @throws SQLException 
	 */
	public int getTotal(String whereSql, Object[] array) throws SQLException;

	public int getTotalBySql(String string, Object[] array) throws SQLException;

}