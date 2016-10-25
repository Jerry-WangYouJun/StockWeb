package com.hyg.service;

import java.util.List;

import com.hyg.bean.GoodsBean;

import net.sf.json.JSONObject;

public interface GoodsServiceI {

	public JSONObject removeGoods(String id);

	public GoodsBean findGoodsById(String id);

	public JSONObject updateGoods(GoodsBean goodsBean);

	public JSONObject insertGoods(GoodsBean goodsBean);

	public JSONObject searchGoods(String whereSql, List<String> params,
			int page, int rows);

}
