package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hyg.bean.GoodsBean;
import com.hyg.bean.InventoryBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.InventoryDaoI;
import com.hyg.dao.impl.InventoryDaoImpl;
import com.hyg.service.InventoryServiceI;

public class InventoryServiceImpl implements InventoryServiceI {
	private InventoryDaoI inventoryDao;
	@Override
	public JSONObject search(InventoryBean inventoryBean, int page, int rows) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		String sql =  "SELECT I.ID,I.STOCKID, I.PRODUCTNO, S.STOCKNAME, "
			+ " G.PRODUCTNAME, G.PRODUCTSTANDARD, G.UNIT AS PRODUCTUNIT, "
			+ " I.INVENTORYNUM, I.INVENTORYPRICE " 
		    + " FROM K_INVENTORY I " 
			+ " LEFT JOIN K_STOCK S ON I.STOCKID = S.ID"  
			+ " LEFT JOIN K_GOODS G ON I.PRODUCTNO = G.PRODUCTNO  WHERE 1=1 ";
		String totalSql = " FROM K_INVENTORY I WHERE 1=1 ";
		String whereSql = "";
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(inventoryBean.getProductNo())) {
			whereSql += " AND  I.PRODUCTNO LIKE ? " ;
			params.add("%" + inventoryBean.getProductNo() + "%");
		}
		if (StringUtils.isNotEmpty(inventoryBean.getProductName())) {
			whereSql += " AND  I.PRODUCTNAME LIKE ? " ;
			params.add("%" + inventoryBean.getProductName() + "%");
		}
		if (StringUtils.isNotEmpty(inventoryBean.getStockId())) {
			whereSql += " AND  I.STOCKID = ? " ;
			params.add(inventoryBean.getStockId());
		}
		
		inventoryDao = new InventoryDaoImpl();
		
		InventoryBean[] beans = null;
		int total=0;
		try {
			beans = inventoryDao.findByDynamicSelectByPage(sql + whereSql, params.toArray(), page, rows);
			total = inventoryDao.getTotalBySql(totalSql + whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		json.put("rows",beans);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
		
	}
	@Override
	public void saveOrUpdate(Map<String, String> map) {
		// TODO Auto-generated method stub
		inventoryDao = new InventoryDaoImpl();
		String whereSql = " AND STOCKID=? AND PRODUCTNO=? ";
		Object[] params = new Object[2];
		params[0] = map.get("stockId");
		params[1] = map.get("productNo");
		String flag = map.get("flag");
		BigDecimal productNum = new BigDecimal(map.get("productNum"));
		BigDecimal totalPrice = new BigDecimal(map.get("totalPrice"));
		InventoryBean[] inventoryBeans = null;
		InventoryBean inventory = null;
		int count = 0;
		try {
			inventoryBeans = inventoryDao.findByDynamicWhere(whereSql, params);
			//如果根据仓库ID和产品编码不能查到库存信息，则进行增加操作
			if (inventoryBeans.length == 0 || inventoryBeans == null) {
				inventory = new InventoryBean();
				inventory.setStockId(map.get("stockId"));//仓库ID
				inventory.setProductNo(map.get("productNo"));//产品编码
				inventory.setProductName(map.get("productName"));//产品名称
				inventory.setProductStandard(map.get("productStandard"));//产品规格
				inventory.setInventoryNum(productNum.toString());//库存量
				inventory.setInventoryPrice(totalPrice.toString());//库存总金额
				if ("in".equals(flag)) {
					inventory.setInStockNum(productNum.toString());//入库量
					inventory.setInStockPrice(totalPrice.toString());//入库总金额
					inventory.setOutStockNum("0");//出库量
					inventory.setOutStockPrice("0");//出库总金额
				}else {
					inventory.setInStockNum("0");//入库量
					inventory.setInStockPrice("0");//入库总金额
					inventory.setOutStockNum(productNum.toString());//出库量
					inventory.setOutStockPrice(totalPrice.toString());//出库总金额
				}
				
				inventory.setMaxNum(new BigDecimal(inventory.getInventoryNum()).multiply(new BigDecimal(1.2)).toString());//最大库存量
				inventory.setMinNum(new BigDecimal(inventory.getInStockNum()).multiply(new BigDecimal(0.2)).toString());//最小库存量
				count += inventoryDao.insert(inventory);
			}else { //否则取出第一个库存明细，进行更新操作
				inventory = inventoryBeans[0];
				if ("in".equals(flag)) {
					inventory.setInStockNum(new BigDecimal(inventory.getInStockNum()).add(productNum).toString());//入库总量 = 本次入库量+当前入库总量
					inventory.setInStockPrice(new BigDecimal(inventory.getInStockPrice()).add(totalPrice).toString());//入库总金额 = 本次入库总金额 + 当前入库总金额
				}else {
					inventory.setOutStockNum(new BigDecimal(inventory.getOutStockNum()).add(productNum).toString());
					inventory.setOutStockPrice(new BigDecimal(inventory.getOutStockPrice()).add(totalPrice).toString());
				}
				
				inventory.setInventoryNum(new BigDecimal(inventory.getInStockNum()).subtract(new BigDecimal(inventory.getOutStockNum())).toString());//库存量 = 入库总量 -出库总量
				inventory.setInventoryPrice(new BigDecimal(inventory.getInventoryPrice()).subtract(new BigDecimal(inventory.getOutStockPrice())).toString());//库存金额 = 入库总金额-出库总金额
				count += inventoryDao.update(new BigDecimal(inventory.getId()), inventory);
			}
			
			if (count > 0) {
				System.out.println("恭喜成功保存了[" + count + "]条数据！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public JSONObject searchDynamicProducts(String stockId, String productNo) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		String sql = "SELECT S.PRODUCTNO AS 'PRODUCTNO',S.PRODUCTNAME AS 'PRODUCTNAME',S.PRODUCTSTANDARD AS 'PRODUCTSTANDARD',"
				+ " S.INVENTORYNUM AS 'PRODUCTNUM',T.UNIT AS 'UNIT',T.PRICE AS 'PRICE' "
				+ " FROM K_INVENTORY S,K_GOODS T "
				+ " WHERE S.PRODUCTNO = T.PRODUCTNO ";
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(stockId)) {
			sql += " and s.stockId = ? ";
			params.add(stockId);
		}else {
			json.put("msg", "仓库ID不能为空,请检查!");
			json.put("success", false);
			return json;
		}
		if (StringUtils.isNotEmpty(productNo)) {
			sql += " and s.productNo like ? ";
			params.add("%" + productNo + "%");
		}
		
		inventoryDao = new InventoryDaoImpl();
		List<GoodsBean> productsList = null;
		try {
			productsList = inventoryDao.searchDynamicProducts(sql,params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("rows", productsList);
		json.put("success", true);
		
		return json;
	}

}
