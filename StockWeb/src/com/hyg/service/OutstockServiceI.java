package com.hyg.service;

import net.sf.json.JSONObject;

import com.hyg.bean.OutStockBean;

public interface OutstockServiceI {

	public JSONObject removeStock(String id);

	public OutStockBean findOutstockById(String id);

	public JSONObject updateOutstock(OutStockBean outstock);

	public JSONObject insertOutstock(OutStockBean outstock);

	public JSONObject searchOutstocks(OutStockBean outstockBean, int page, int rows);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public JSONObject confrimOutstock(String id);

}
