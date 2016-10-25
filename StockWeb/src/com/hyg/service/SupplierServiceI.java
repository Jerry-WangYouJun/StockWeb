package com.hyg.service;

import com.hyg.bean.SupplierBean;

import net.sf.json.JSONObject;

public interface SupplierServiceI {

	public JSONObject removeSupplier(String id);

	public SupplierBean findSupplierById(String id);

	public JSONObject update(SupplierBean supplierBean);

	public JSONObject insert(SupplierBean supplierBean);

	public JSONObject search(SupplierBean supplierBean, int page, int rows);

	public JSONObject findSupplierSelects();

}
