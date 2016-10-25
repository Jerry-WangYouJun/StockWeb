package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONObject;

import com.hyg.bean.GoodsBean;
import com.hyg.bean.UserBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.GoodsDaoI;
import com.hyg.dao.impl.GoodsDaoImpl;
import com.hyg.dao.impl.UserDaoImpl;
import com.hyg.service.GoodsServiceI;


public class GoodsServiceImpl implements GoodsServiceI {

	private GoodsDaoI goodsDao;
	@Override
	public JSONObject removeGoods(String id) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		goodsDao = new GoodsDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "产品id不能为空，请检查！");
		}else {
			try {
				int count  = goodsDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除产品成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除产品操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	@Override
	public GoodsBean findGoodsById(String id) {
		// TODO Auto-generated method stub
		GoodsBean goodsBean = null;
		try {
			goodsDao = new GoodsDaoImpl();
			if (StringUtils.isNotEmpty(id)) {
				goodsBean = goodsDao.findByPrimaryKey(new BigDecimal(id));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return goodsBean;
	}
	@Override
	public JSONObject updateGoods(GoodsBean goodsBean) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		//初始化UserDao
		goodsDao = new GoodsDaoImpl();
		int count;
		try {
			//先根据ID到数据库查询出记录，
			//然后将页面上显示的字段内容赋值给从数据库查询出来的POJO中对应的属性 
			// eg： userDB.setUserNo(userForm.getUserNo()) 
			count = goodsDao.update(new BigDecimal(goodsBean.getId()),goodsBean);
			
			if (count > 0) {
				json.put("msg", "恭喜你成功修改" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，修改产品操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject insertGoods(GoodsBean goodsBean) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		//初始化UserDao
		goodsDao = new GoodsDaoImpl();
		int count;
		try {
			//先根据ID到数据库查询出记录，
			//然后将页面上显示的字段内容赋值给从数据库查询出来的POJO中对应的属性 
			// eg： userDB.setUserNo(userForm.getUserNo()) 
			count = goodsDao.insert(goodsBean);
			
			if (count > 0) {
				json.put("msg", "恭喜你成功添加" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，添加产品操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject searchGoods(String whereSql, List<String> params,
			int page, int rows) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		String sql =  "SELECT ID,PRODUCTNO,PRODUCTNAME,PRODUCTTYPECODE,"
				+ " ( SELECT DICVALUE FROM K_DICTIONARY WHERE DICCODE = PRODUCTTYPECODE ) AS 'PRODUCTTYPENAME',"
				+ " PRODUCTSTANDARD,SUPPLIERNO,PRODUCTDATE,PRODUCTNUM,UNIT,PRICE,REMARK "
				+ "FROM K_GOODS "
				+ "WHERE 1=1 ";
		//初始化userDao
		goodsDao = new GoodsDaoImpl();
		//根据dao查找用户，返回多条记录
		GoodsBean[] goods = null;
		//获取根据查询条件查出来的记录总数
		int total = 0;
		try {
			goods = goodsDao.findByDynamicSelectByPage(sql + whereSql, params.toArray(),page,rows);
			total = goodsDao.getTotal(whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//key=rows 关键字，是EasyUI框架中datagrid组件解析数据所必须的
		json.put("rows",goods);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
	}

}
