package com.hyg.service;

import java.util.Map;

import net.sf.json.JSONObject;

import com.hyg.bean.InventoryBean;

public interface InventoryServiceI {

	public JSONObject search(InventoryBean inventoryBean, int page, int rows);

	public void saveOrUpdate(Map<String, String> map);

	/**
	 * 根据仓库Id和产品编号到库存表中查询产品
	 * @param stockId
	 * @param productNo
	 * @return
	 */
	public JSONObject searchDynamicProducts(String stockId, String productNo);
	
}
