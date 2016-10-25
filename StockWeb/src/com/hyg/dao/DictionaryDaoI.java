package com.hyg.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hyg.bean.DictionaryBean;

public interface DictionaryDaoI {
	
	/** 
	 * 添加用户
	 */
	public int insert(DictionaryBean dic) throws SQLException;

	/** 
	 * 修改用户
	 */
	public int update(BigDecimal pk, DictionaryBean dic) throws SQLException ;

	/** 
	 * 删除用户
	 */
	public int delete(BigDecimal pk) throws SQLException ;

	/** 
	 * 根据主键ID列查询
	 */
	public DictionaryBean findByPrimaryKey(BigDecimal id) throws SQLException ;

	/** 
	 *查询所有用户数据
	 */
	public DictionaryBean[] findAll() throws SQLException ;

	/** 
	 * 动态列查询
	 */
	public DictionaryBean[] findByDynamicSelect(String sql, Object[] sqlParams) throws SQLException ;

	/** 
	 * where条件查询，注意每个查询条件都要以 and 或or 逻辑修饰符开头
	 */
	public DictionaryBean[] findByDynamicWhere(String sql, Object[] sqlParams) throws SQLException ;

	/**
	 * 将Map对象转成Object对象
	 * @param list
	 * @return
	 */
	public DictionaryBean[] MapToObject(List<Map<String, Object>> list);
	/**
	 * 分页进行动态条件查询
	 * @param whereSql 条件sql
	 * @param params   sql对应的参数
	 * @param page     当前页码
	 * @param rows     每页显示行数
	 * @return         用户列表数组
	 * @throws SQLException 
	 */
	public DictionaryBean[] findByDynamicWhereByPage(String whereSql,
			int page, int rows ,Object[] params) throws SQLException;
	/**
	 * 根据查询条件获得记录总数
	 * @param whereSql  查询条件
	 * @param array
	 * @return
	 * @throws SQLException 
	 */
	public int getTotal(String whereSql, Object[] array) throws SQLException;
	/**
	 * 根据字典项找字典
	 * @param whereSql
	 * @param array
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> findDicByDicId(String whereSql,
			Object[] array) throws SQLException;
	
	/**
	 * 根据字典项和字典编码查找字典
	 * @param whereSql
	 * @param array
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Object> findDicsByWhere(String whereSql, Object[] array) throws SQLException;

}
