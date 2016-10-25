package com.hyg.service;

import com.hyg.bean.StockBean;

import net.sf.json.JSONObject;

public interface StockServiceI {

	public JSONObject removeStock(String id);

	public StockBean findStockById(String id);

	public JSONObject updateStock(StockBean stockBean);

	public JSONObject insertStock(StockBean stockBean);

	public JSONObject searchStock(StockBean stock, int page, int rows);

	public JSONObject findStockSelects();

}
