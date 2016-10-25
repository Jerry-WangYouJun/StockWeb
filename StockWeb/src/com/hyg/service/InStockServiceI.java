package com.hyg.service;

import com.hyg.bean.InStockBean;

import net.sf.json.JSONObject;

public interface InStockServiceI {

	public JSONObject removeStock(String id);

	public InStockBean findInstockById(String id);

	public JSONObject updateInstock(InStockBean instock);

	public JSONObject insertInstock(InStockBean instock);

	public JSONObject searchInstocks(InStockBean instockBean, int page, int rows);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public JSONObject confrimInstock(String id);

}
