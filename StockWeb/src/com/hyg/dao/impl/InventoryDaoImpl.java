package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.GoodsBean;
import com.hyg.bean.InventoryBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.InventoryDaoI;

public class InventoryDaoImpl extends DBUtil implements InventoryDaoI {
	protected Connection inventoryConn;
	protected final String SQL_SELECT = "SELECT ID, STOCKID, PRODUCTNO, PRODUCTNAME, PRODUCTSTANDARD, INVENTORYNUM, INVENTORYPRICE, INSTOCKNUM, INSTOCKPRICE, OUTSTOCKNUM, OUTSTOCKPRICE, MAXNUM,MINNUM, REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( STOCKID, PRODUCTNO, PRODUCTNAME, PRODUCTSTANDARD, INVENTORYNUM, INVENTORYPRICE, INSTOCKNUM, INSTOCKPRICE, OUTSTOCKNUM, OUTSTOCKPRICE, MAXNUM,MINNUM, REMARK  ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET STOCKID = ?, PRODUCTNO = ?, PRODUCTNAME = ?, PRODUCTSTANDARD = ?, INVENTORYNUM = ?, INVENTORYPRICE = ?, INSTOCKNUM = ?, INSTOCKPRICE = ?, OUTSTOCKNUM = ?, OUTSTOCKPRICE = ?, MAXNUM = ?, MINNUM = ?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";
	
	/**
	 * 获得表名
	 * @return
	 */
	public String getTableName()
	{
		return "K_INVENTORY";
	}

	@Override
	public int insert(InventoryBean inventory) throws SQLException {
		// TODO Auto-generated method stub
		inventoryConn = this.getConnection();
		List params = new ArrayList();
		params.add(inventory.getStockId() );
		params.add(inventory.getProductNo() );
		params.add(inventory.getProductName());
		params.add(inventory.getProductStandard());
		params.add(inventory.getInventoryNum());
		params.add(inventory.getInventoryPrice() );
		params.add(inventory.getInStockNum() );
		params.add(inventory.getInStockPrice());
		params.add(inventory.getOutStockNum());
		params.add(inventory.getOutStockPrice());
		params.add(inventory.getMaxNum());
		params.add(inventory.getMinNum());
		params.add(inventory.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(inventoryConn, SQL_INSERT, params.toArray());
		this.closeConnection(inventoryConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, InventoryBean inventory)
			throws SQLException {
		// TODO Auto-generated method stub
		inventoryConn = this.getConnection();
		List params = new ArrayList();
		params.add(inventory.getStockId() );
		params.add(inventory.getProductNo() );
		params.add(inventory.getProductName());
		params.add(inventory.getProductStandard());
		params.add(inventory.getInventoryNum());
		params.add(inventory.getInventoryPrice() );
		params.add(inventory.getInStockNum() );
		params.add(inventory.getInStockPrice());
		params.add(inventory.getOutStockNum());
		params.add(inventory.getOutStockPrice());
		params.add(inventory.getMaxNum());
		params.add(inventory.getMinNum());
		params.add(inventory.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(inventoryConn, SQL_UPDATE, params.toArray());
		this.closeConnection(inventoryConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		// TODO Auto-generated method stub
		inventoryConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(inventoryConn, SQL_DELETE, params.toArray());
		this.closeConnection(inventoryConn);
		return rows;
	}

	@Override
	public InventoryBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		InventoryBean inventory = null;
		//1、获取数据库连接
		inventoryConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(inventoryConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			InventoryBean[] inventorys = this.MapToObject(result);
			if (inventorys.length > 0) {
				inventory = inventorys[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(inventoryConn);
		}
		return inventory;
	}

	@Override
	public InventoryBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
		
	}

	@Override
	public InventoryBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		inventoryConn = this.getConnection();
		resultList = this.executeQuery(inventoryConn, sql, sqlParams);
		if (inventoryConn != null) {
			this.closeConnection(inventoryConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InventoryBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		inventoryConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(inventoryConn, SQL, sqlParams);
		
		if (inventoryConn != null) {
			this.closeConnection(inventoryConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InventoryBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		InventoryBean[] inventoryBeans = new InventoryBean[list.size()];
		List<InventoryBean> results = new ArrayList<InventoryBean>();
		for (Map map:list) {
			if (map != null) {
				InventoryBean inventoryBean = new InventoryBean();
				inventoryBean.setId(map.get("ID") == null ? null:map.get("ID").toString());
				inventoryBean.setStockId(map.get("STOCKID") == null ? null:map.get("STOCKID").toString());
				inventoryBean.setProductNo(map.get("PRODUCTNO") == null ? null:map.get("PRODUCTNO").toString());
				inventoryBean.setProductName(map.get("PRODUCTNAME") == null ? null:map.get("PRODUCTNAME").toString());
				inventoryBean.setProductStandard(map.get("PRODUCTSTANDARD") == null ? null:map.get("PRODUCTSTANDARD").toString());
				inventoryBean.setInventoryNum(map.get("INVENTORYNUM") == null ? null:map.get("INVENTORYNUM").toString());
				inventoryBean.setInventoryPrice(map.get("INVENTORYPRICE") == null ? null:map.get("INVENTORYPRICE").toString());
				inventoryBean.setInStockNum(map.get("INSTOCKNUM") == null ? null:map.get("INSTOCKNUM").toString());
				inventoryBean.setInStockPrice(map.get("INSTOCKPRICE") == null ? null:map.get("INSTOCKPRICE").toString());
				inventoryBean.setOutStockNum(map.get("OUTSTOCKNUM") == null ? null:map.get("OUTSTOCKNUM").toString());
				inventoryBean.setOutStockPrice(map.get("OUTSTOCKPRICE") == null ? null:map.get("OUTSTOCKPRICE").toString());
				inventoryBean.setMinNum(map.get("MINNUM") == null ? null:map.get("MINNUM").toString());
				inventoryBean.setMaxNum(map.get("MAXNUM") == null ? null:map.get("MAXNUM").toString());
				inventoryBean.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
				inventoryBean.setStockName(map.get("STOCKNAME") == null ? null:map.get("STOCKNAME").toString());
				inventoryBean.setProductUnit(map.get("PRODUCTUNIT") == null ? null:map.get("PRODUCTUNIT").toString());
				results.add(inventoryBean);
			}
		}
		return results.toArray(inventoryBeans);
	}

	@Override
	public InventoryBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		inventoryConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(inventoryConn, SQL, page, rows, params);
		
		if (inventoryConn != null) {
			this.closeConnection(inventoryConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public InventoryBean[] findByDynamicSelectByPage(String sql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		inventoryConn = this.getConnection();
		// construct the SQL statement
	
		resultList = this.queryByPage(inventoryConn, sql, page, rows, params);
		
		if (inventoryConn != null) {
			this.closeConnection(inventoryConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		inventoryConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(inventoryConn, SQL, array);
		if (inventoryConn != null) {
			this.closeConnection(inventoryConn);
		}
		return count;
	}

	@Override
	public int getTotalBySql(String sql, Object[] params) throws SQLException {
		// TODO Auto-generated method stub
		inventoryConn = this.getConnection();
		int count = this.getResultTotal(inventoryConn, sql, params);
		if (inventoryConn != null) {
			this.closeConnection(inventoryConn);
		}
		return count;
	}

	@Override
	public List<GoodsBean> searchDynamicProducts(String sql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		inventoryConn = this.getConnection();
		List<Map<String, Object>> results = this.executeQuery(inventoryConn, sql, array);
		List<GoodsBean> products = new ArrayList<GoodsBean>();
		for (Map<String, Object> map : results) {
			GoodsBean goods = new GoodsBean();
			goods.setId(map.get("ID") == null ? null:map.get("ID").toString());
			goods.setProductNo(map.get("PRODUCTNO") == null ? null:map.get("PRODUCTNO").toString());
			goods.setProductName(map.get("PRODUCTNAME") == null ? null:map.get("PRODUCTNAME").toString());
			goods.setProductTypeCode(map.get("PRODUCTTYPECODE") == null ? null:map.get("PRODUCTTYPECODE").toString());
			goods.setProductTypeName(map.get("PRODUCTTYPENAME") == null ? null:map.get("PRODUCTTYPENAME").toString());
			goods.setProductStandard(map.get("PRODUCTSTANDARD") == null ? null:map.get("PRODUCTSTANDARD").toString());
			goods.setSupplierNo(map.get("SUPPLIERNO") == null ? null:map.get("SUPPLIERNO").toString());
			goods.setProductDate(map.get("PRODUCTDATE") == null ? null: map.get("PRODUCTDATE").toString());
			goods.setProductNum(map.get("PRODUCTNUM") == null ? 0:Double.parseDouble(map.get("PRODUCTNUM").toString()));
			goods.setUnit(map.get("UNIT") == null ? null:map.get("UNIT").toString());
			goods.setPrice(map.get("PRICE") == null ? 0.0:Double.parseDouble(map.get("PRICE").toString()));
			goods.setRemark(map.get("REMARK") == null ? null:map.get("REMARK").toString());
			products.add(goods);
		}
		if (inventoryConn != null) {
			this.closeConnection(inventoryConn);
		}
		return products;
	}


}
