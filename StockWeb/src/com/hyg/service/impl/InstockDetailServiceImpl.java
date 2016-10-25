package com.hyg.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hyg.bean.InStockDetailBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.InstockDetailDaoI;
import com.hyg.dao.impl.InstockDaoImpl;
import com.hyg.dao.impl.InstockDetailDaoImpl;
import com.hyg.service.InstockDetailServiceI;

public class InstockDetailServiceImpl implements InstockDetailServiceI {
	private InstockDetailDaoI detailDao;
	@Override
	public InStockDetailBean[] findInstockDetailsByInstockId(String instockId) {
		// TODO Auto-generated method stub
		
		detailDao = new InstockDetailDaoImpl();
		String whereSql = " AND INSTOCKID=? ";
		Object[] params = new Object[1];
		params[0] =  instockId;
		
		InStockDetailBean[] detailBeans = null;
		try {
			detailBeans = detailDao.findByDynamicWhere(whereSql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return detailBeans;
	}
	@Override
	public JSONObject searchInstocks(String instockId) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		InStockDetailBean[] detailBeans = this.findInstockDetailsByInstockId(instockId);
		//key=rows 关键字，是EasyUI框架中datagrid组件解析数据所必须的
		json.put("rows",detailBeans);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		return json;
	}
	@Override
	public JSONObject saveOrUpdate(JSONArray jsonArray, String inStockId,
			String inStockState){
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		int count = 0;
		detailDao = new InstockDetailDaoImpl();
		if (!"00".equals(inStockState)) {
			json.put("msg", "很抱歉，保存明细信息失败，原因是【当前入库申请单状态不是创建状态】!");
			json.put("success", false);
		}else {
			for (int i = 0; i < jsonArray.size() ; i++) {
				String id = (String) jsonArray.getJSONObject(i).get("id");
				String productNo = (String) jsonArray.getJSONObject(i).get("productNo");
				String productName = (String) jsonArray.getJSONObject(i).get("productName");
				String productStandard = (String) jsonArray.getJSONObject(i).get("productStandard");
				String productNum =  jsonArray.getJSONObject(i).get("productNum").toString();
				String unit = (String) jsonArray.getJSONObject(i).get("unit");
				String price = jsonArray.getJSONObject(i).get("price").toString();
				
				if (StringUtils.isEmpty(productNo)) {
					json.put("msg", "产品编码不能为空!");
					json.put("success", false);
					return json;
				}
				
				if (StringUtils.isEmpty(productNum)) {
					json.put("msg", "产品数量不能为空!");
					json.put("success", false);
					return json;
				}
				
				if (StringUtils.isEmpty(price)) {
					json.put("msg", "产品单价不能小于0!");
					json.put("success", false);
					return json;
				}
				
				BigDecimal totalPrice = new BigDecimal(productNum).multiply(new BigDecimal(price)).setScale(2, RoundingMode.HALF_UP);
				
				InStockDetailBean instockDetail = new InStockDetailBean();
				instockDetail.setInStockId(inStockId);
				instockDetail.setProductNo(productNo);
				instockDetail.setProductName(productName);
				instockDetail.setProductStandard(productStandard);
				instockDetail.setProductNum(productNum);
				instockDetail.setUnit(unit);
				instockDetail.setPrice(price);
				instockDetail.setTotalPrice(totalPrice.toString());
				instockDetail.setRemark("");
				
				try {
					if (StringUtils.isEmpty(id)) {
						count += detailDao.insert(instockDetail);
					}else{
						count += detailDao.update(new BigDecimal(id), instockDetail);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
		}
		
		if (count > 0) {
			json.put("msg", "恭喜您，保存明细信息成功!");
			json.put("success", true);
		}else {
			json.put("msg", "很抱歉，保存明细信息失败，请查看后台!");
			json.put("success", false);
		}
		
		return json;
	}
	@Override
	public JSONObject searchInstocksByPage(String instockId, int page, int rows) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		
		detailDao = new InstockDetailDaoImpl();
		String whereSql = " AND INSTOCKID=? ";
		Object[] params = new Object[1];
		params[0] =  instockId;
		
		InStockDetailBean[] detailBeans = null;
		int total = 0;
		try {
			detailBeans = detailDao.findByDynamicWhereByPage(whereSql, params, page, rows);
			total = detailDao.getTotal(whereSql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//key=rows 关键字，是EasyUI框架中datagrid组件解析数据所必须的
		json.put("rows",detailBeans);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total",total);
		return json;
	}
	@Override
	public JSONObject removeDetail(String id) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		detailDao = new InstockDetailDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "入库单主单id不能为空，请检查！");
		}else {
			try {
				int count  = detailDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除入库单明细成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除入库单明细操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
		
	}

}
