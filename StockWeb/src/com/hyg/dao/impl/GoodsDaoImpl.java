package com.hyg.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.GoodsBean;
import com.hyg.bean.GoodsBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.GoodsDaoI;

public class GoodsDaoImpl extends DBUtil implements GoodsDaoI {
	protected Connection goodsConn;
	protected final String SQL_SELECT = "SELECT ID, PRODUCTNO, PRODUCTNAME, PRODUCTTYPECODE, PRODUCTSTANDARD, SUPPLIERNO, PRODUCTDATE, PRODUCTNUM, UNIT, PRICE,REMARK FROM " + getTableName() + " ";
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( PRODUCTNO, PRODUCTNAME, PRODUCTTYPECODE, PRODUCTSTANDARD, SUPPLIERNO, PRODUCTDATE, PRODUCTNUM, UNIT, PRICE,REMARK ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET PRODUCTNO = ?, PRODUCTNAME = ?, PRODUCTTYPECODE = ?, PRODUCTSTANDARD = ?, SUPPLIERNO = ?, PRODUCTDATE = ?, PRODUCTNUM = ?, UNIT = ?, PRICE = ?, REMARK = ? WHERE ID = ?";
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE ID = ?";

	
	private String getTableName() {
		// TODO Auto-generated method stub
		return "K_GOODS";
	}

	@Override
	public int insert(GoodsBean goods) throws SQLException {
		// TODO Auto-generated method stub
		goodsConn = this.getConnection();
		List params = new ArrayList();
		params.add(goods.getProductNo());
		params.add(goods.getProductName() );
		params.add(goods.getProductTypeCode());
		params.add(goods.getProductStandard() );
		params.add(goods.getSupplierNo() );
		params.add(goods.getProductDate());
		params.add(goods.getProductNum() );
		params.add(goods.getUnit() );
		params.add(goods.getPrice() );
		params.add(goods.getRemark() );
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(goodsConn, SQL_INSERT, params.toArray());
		this.closeConnection(goodsConn);
		return rows;
	}

	@Override
	public int update(BigDecimal pk, GoodsBean goods) throws SQLException {
		// TODO Auto-generated method stub
		goodsConn = this.getConnection();
		List params = new ArrayList();
		params.add(goods.getProductNo());
		params.add(goods.getProductName() );
		params.add(goods.getProductTypeCode());
		params.add(goods.getProductStandard() );
		params.add(goods.getSupplierNo() );
		params.add(goods.getProductDate());
		params.add(goods.getProductNum() );
		params.add(goods.getUnit() );
		params.add(goods.getPrice() );
		params.add(goods.getRemark() );
		params.add(pk);
		//注意executeUpdate传参数params的时候 不能用List集合类型的，需要将该List集合转成数组
		int rows = this.executeUpdate(goodsConn, SQL_UPDATE, params.toArray());
		this.closeConnection(goodsConn);
		return rows;
	}

	@Override
	public int delete(BigDecimal pk) throws SQLException {
		goodsConn = this.getConnection();
		List params = new ArrayList();
		params.add(pk);
		int rows = this.executeUpdate(goodsConn, SQL_DELETE, params.toArray());
		this.closeConnection(goodsConn);
		return rows;
	}

	@Override
	public GoodsBean findByPrimaryKey(BigDecimal id) throws SQLException {
		// TODO Auto-generated method stub
		GoodsBean good = null;
		//1、获取数据库连接
		goodsConn = this.getConnection();
		//2、根据主键id = id值 去到数据库查询
		//2.1 先写预编译的sql语句 
		String sql = SQL_SELECT + " where id=? ";
		//2.2 对于有？占位符的sql语句要进行？赋值的定义
		List param = new ArrayList();
		param.add(id);
		//2.3 调用DBUtil类中的executeQuery方法执行查询操作
		try {
			List<Map<String, Object>> result = this.executeQuery(goodsConn, sql, param.toArray());
			//2.4 将查询出来的List结果转成Object
			GoodsBean[] goods = this.MapToObject(result);
			if (goods.length > 0) {
				good = goods[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//3、关闭数据库连接
			this.closeConnection(goodsConn);
		}
		return good;
	}

	@Override
	public GoodsBean[] findAll() throws SQLException {
		// TODO Auto-generated method stub
		return this.findByDynamicSelect( SQL_SELECT + " ORDER BY ID", new Object[0]);
	}

	@Override
	public GoodsBean[] findByDynamicSelect(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		goodsConn = this.getConnection();
		resultList = this.executeQuery(goodsConn, sql, sqlParams);
		if (goodsConn != null) {
			this.closeConnection(goodsConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public GoodsBean[] findByDynamicWhere(String sql, Object[] sqlParams)
			throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		goodsConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + sql;
	
		resultList = this.executeQuery(goodsConn, SQL, sqlParams);
		
		if (goodsConn != null) {
			this.closeConnection(goodsConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public GoodsBean[] MapToObject(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		GoodsBean[] goodsBeans = new GoodsBean[list.size()];
		List<GoodsBean> results = new ArrayList<GoodsBean>();
		for (Map map:list) {
			if (map != null) {
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
				results.add(goods);
			}
		}
		return results.toArray(goodsBeans);
	}

	@Override
	public GoodsBean[] findByDynamicWhereByPage(String whereSql,
			Object[] params, int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		goodsConn = this.getConnection();
		// construct the SQL statement
		final String SQL = SQL_SELECT + " WHERE  1=1 " + whereSql;
	
		resultList = this.queryByPage(goodsConn, SQL, page, rows, params);
		
		if (goodsConn != null) {
			this.closeConnection(goodsConn);
		}
		return MapToObject(resultList);
	}

	@Override
	public int getTotal(String whereSql, Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		goodsConn = this.getConnection();
		final String SQL = " from " + this.getTableName() + " where 1=1 " + whereSql;
		
		int count = this.getResultTotal(goodsConn, SQL, array);
		if (goodsConn != null) {
			this.closeConnection(goodsConn);
		}
		return count;
	}

	@Override
	public GoodsBean[] findByDynamicSelectByPage(String sql, Object[] array,
			int page, int rows) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		goodsConn = this.getConnection();
		// construct the SQL statement
	
		resultList = this.queryByPage(goodsConn, sql, page, rows, array);
		
		if (goodsConn != null) {
			this.closeConnection(goodsConn);
		}
		return MapToObject(resultList);
	}

}
