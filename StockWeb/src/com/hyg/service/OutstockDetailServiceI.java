package com.hyg.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hyg.bean.OutStockDetailBean;

public interface OutstockDetailServiceI {

	public OutStockDetailBean[] findOutstockDetailsByOutstockId(String string);
	
	public JSONObject searchOutstocks(String inStockId);
	/**
	 * 保存或更新明信息
	 * @param jsonArray  产品明细信息json数组
	 * @param inStockId  入库单ID
	 * @param inStockState 入库单状态
	 * @return
	 */
	public JSONObject saveOrUpdate(JSONArray jsonArray, String inStockId,
			String inStockState);
	
	public JSONObject searchOutstocksByPage(String inStockId, int page, int rows);

	public JSONObject removeDetail(String id);

}
