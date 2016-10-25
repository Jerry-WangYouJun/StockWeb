package com.hyg.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hyg.bean.GoodsBean;

public interface GoodsDaoI {
	/** 
	 * 添加产品
	 */
	public int insert(GoodsBean goods) throws SQLException;

	/** 
	 * 修改产品
	 */
	public int update(BigDecimal pk, GoodsBean goods) throws SQLException ;

	/** 
	 * 删除产品
	 */
	public int delete(BigDecimal pk) throws SQLException ;

	/** 
	 * 根据主键ID列查询
	 */
	public GoodsBean findByPrimaryKey(BigDecimal id) throws SQLException ;

	/** 
	 *查询所有产品数据
	 */
	public GoodsBean[] findAll() throws SQLException ;

	/** 
	 * 动态列查询
	 */
	public GoodsBean[] findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException ;

	/** 
	 * where条件查询，注意每个查询条件都要以 and 或or 逻辑修饰符开头
	 */
	public GoodsBean[] findByDynamicWhere(String sql, Object[] sqlParams) throws SQLException ;

	/**
	 * 将Map对象转成Object对象
	 * @param list
	 * @return
	 */
	public GoodsBean[] MapToObject(List<Map<String, Object>> list);
	/**
	 * 分页进行动态条件查询
	 * @param whereSql 条件sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         产品列表数组
	 * @throws SQLException 
	 */
	public GoodsBean[] findByDynamicWhereByPage(String whereSql, Object[] params,
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
	 * 分页进行动态条件查询
	 * @param sql  预编译的sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         产品列表数组
	 * @throws SQLException 
	 */
	public GoodsBean[] findByDynamicSelectByPage(String sql, Object[] array,
			int page, int rows) throws SQLException;

}
