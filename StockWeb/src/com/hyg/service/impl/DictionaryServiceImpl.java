package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hyg.bean.DictionaryBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.DictionaryDaoI;
import com.hyg.dao.impl.DictionaryDaoImpl;
import com.hyg.service.DictionaryServiceI;

public class DictionaryServiceImpl implements DictionaryServiceI {
	private DictionaryDaoI dictionaryDao;
	
	@Override
	public JSONObject removeDic(String id) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		dictionaryDao = new DictionaryDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "字典id不能为空，请检查！");
		}else {
			try {
				int count  = dictionaryDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除字典成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除字典操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}

	@Override
	public DictionaryBean findDicById(String id) {
		// TODO Auto-generated method stub
		DictionaryBean dicBean = null;
		dictionaryDao = new DictionaryDaoImpl();
		try {
			dicBean = dictionaryDao.findByPrimaryKey(new BigDecimal(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dicBean;
	}

	@Override
	public JSONObject updateDic(DictionaryBean dicBean) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		dictionaryDao = new DictionaryDaoImpl();
		int count;
		try {
			count = dictionaryDao.update(new BigDecimal(dicBean.getId()),dicBean);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功修改" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，修改字典操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONObject insertDic(DictionaryBean dicBean) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		dictionaryDao = new DictionaryDaoImpl();
		int count;
		try {
			count = dictionaryDao.insert(dicBean);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功添加" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，添加字典操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONObject searchDic(DictionaryBean dicBean, int page, int rows) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(dicBean.getDicType())) {
			whereSql += " AND DICTYPE = ?";
			params.add(dicBean.getDicType());
		}
		if (StringUtils.isNotEmpty(dicBean.getDicCode())) {
			whereSql += " AND DICCODE LIKE ?";
			params.add("%" + dicBean.getDicCode() + "%");
		}
		if (StringUtils.isNotEmpty(dicBean.getDicValue())) {
			whereSql += " AND DICVALUE = ?";
			params.add(dicBean.getDicValue());
		}
		DictionaryBean[] dics = null;
		int total  = 0;
		try {
			dictionaryDao = new DictionaryDaoImpl();
			dics = dictionaryDao.findByDynamicWhereByPage(whereSql, page, rows, params.toArray());
					
			total = dictionaryDao.getTotal(whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("rows",dics);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
	}

	@Override
	public JSONObject findDictionarySeletsByDicId(String dicId) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isEmpty(dicId)) {
			json.put("success", true);
			json.put("results", null);
		}else{
			whereSql +=  " and dicId = ? ";
			params.add(dicId);
			dictionaryDao = new DictionaryDaoImpl();
			
			List<Map<String, Object>> resultList = null;
			try {
				resultList = dictionaryDao.findDicByDicId(whereSql,params.toArray());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			json.put("success", true);
			json.put("results", resultList);
		}
		return json;
	}

	@Override
	public JSONObject findDictionarySelectsByDicIdAndDicCode(String dicId,
			String dicCode) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		
		
		if (StringUtils.isEmpty(dicId) || StringUtils.isEmpty(dicCode)) {
			json.put("success", true);
			json.put("results", null);
		}else{
			whereSql +=  " AND DICID = ? " ;
			params.add(dicId);
			
			whereSql +=  " AND DICCODE = ? ";
			dictionaryDao = new DictionaryDaoImpl();
			
			Map<String,Object> dic = new HashMap<String,Object>();
			try {
				dic = dictionaryDao.findDicsByWhere(whereSql,params.toArray());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			json.put("success", true);
			json.put("result", dic);
		}
		return json;
	}

}
