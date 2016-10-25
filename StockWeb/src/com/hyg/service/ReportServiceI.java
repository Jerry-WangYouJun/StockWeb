package com.hyg.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public interface ReportServiceI {
	
	/**
	 * 月报查询
	 * @param stockId
	 * @param productNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONObject searchMonthReport(String stockId, String productNo,
			String startDate, String endDate);
	
	
	/**
	 * 返回期初的入出库信息
	 * @param startDate  期初日期
	 * @param stockId  仓库ID
	 * @param flag  出入库标记
	 * @return
	 */
	public List<Map<String, Object>> findInitTotalNum(String startDate,String stockId,String flag);
	
	/**
	 * 某时间段内的出入库信息
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param stockId 仓库ID
	 * @param flag 出入库标记
	 * @return
	 */
	public List<Map<String, Object>> findTotalNum(String startDate,String endDate,String stockId,String flag);
	
    /**
     * 物资台账查询
     * @param productNo 产品编码
     * @param startDate 开始时间
     * @param endDate  结束时间
     * @return
     */
	public JSONObject searchProductReport(String productNo, String startDate,
			String endDate);
	
	/**
	 * 返回产品期初的入出库信息
	 * @param startDate  期初日期
	 * @param stockId  仓库ID
	 * @param flag  出入库标记
	 * @return
	 */
	public List<Map<String, Object>> findInitProductTotalNum(String startDate,String productNo,String flag);
	
	/**
	 * 某时间段内产品的出入库信息
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param stockId 仓库ID
	 * @param flag 出入库标记
	 * @return
	 */
	public List<Map<String, Object>> findProductTotalNum(String startDate,String endDate,String productNo,String flag);

	/**
	 * 时间段内的物资流水
	 * @param productNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONObject searchProductDetailReport(String productNo,
			String startDate, String endDate);
	
   

}
