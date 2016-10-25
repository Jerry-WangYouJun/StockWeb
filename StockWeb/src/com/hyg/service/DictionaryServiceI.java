package com.hyg.service;

import com.hyg.bean.DictionaryBean;

import net.sf.json.JSONObject;

public interface DictionaryServiceI {

	public JSONObject removeDic(String id);

	public DictionaryBean findDicById(String id);

	public JSONObject updateDic(DictionaryBean dicBean);

	public JSONObject insertDic(DictionaryBean dicBean);

	public JSONObject searchDic(DictionaryBean dicBean, int page, int rows);

	public JSONObject findDictionarySeletsByDicId(String dicId);

	public JSONObject findDictionarySelectsByDicIdAndDicCode(String dicId,
			String dicCode);

}
