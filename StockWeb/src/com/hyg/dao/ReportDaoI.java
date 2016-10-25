package com.hyg.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hyg.bean.ReportBean;

public interface ReportDaoI {
	/**
	 * 库存月报的入库信息
	 * @param whereSql
	 * @param array
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMonthInstock(String whereSql, Object[] array) throws SQLException;
	/**
	 * 库存月报的出库信息
	 * @param whereSql
	 * @param array
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMonthOutstock(String whereSql,
			Object[] array) throws SQLException;
	
	/**
	 * 物资台账的入库信息
	 * @param whereSql
	 * @param array
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMonthProductInstock(String whereSql, Object[] array) throws SQLException;
	/**
	 * 物资台账的出库信息
	 * @param whereSql
	 * @param array
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMonthProductOutstock(String whereSql,
			Object[] array) throws SQLException;
	/**
	 * 查询物资台账的流水
	 * @param productNo
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException 
	 */
	public List<ReportBean> findMonthProductDetail(String productNo,
			String startDate, String endDate) throws SQLException;

}
