package com.hyg.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hyg.bean.ReportBean;
import com.hyg.core.utils.DBUtil;
import com.hyg.dao.ReportDaoI;

public class ReportDaoImpl extends DBUtil implements ReportDaoI {
	private Connection reportConn;
	@Override
	public List<Map<String, Object>> findMonthInstock(String sql, Object[] param) throws SQLException {
		// TODO Auto-generated method stub
		reportConn =  this.getConnection();
		String instockSql = " SELECT A.STOCKID,(SELECT P.STOCKNAME FROM K_STOCK P WHERE P.ID = A.STOCKID) AS STOCKNAME, "
				+ "	A.PRODUCTNO AS PRODUCTNO,A.PRODUCTNAME AS PRODUCTNAME,A.PRODUCTSTANDARD AS PRODUCTSTANDARD, "
				+ " IFNULL(M.PRODUCTNUM,0) AS INNUM, "
				+ " IFNULL(M.TOTALPRICE,0) AS INPRICE "
				+ " FROM K_INVENTORY A "
				+ " LEFT JOIN  "
				+ " (SELECT S.STOCKID AS STOCKID, "
				+ " K.PRODUCTNO AS PRODUCTNO, "
				+ " K.PRODUCTNAME AS PRODUCTNAME, "
				+ " K.PRODUCTSTANDARD AS PRODUCTSTANDARD, "
				+ " SUM(K.PRODUCTNUM) AS PRODUCTNUM, "
				+ " SUM(K.TOTALPRICE) AS TOTALPRICE "
				+ " FROM  K_INSTOCK S, K_INSTOCK_DETAIL K  "
				+ " WHERE S.ID = K.INSTOCKID "
				+ sql
				+ " GROUP BY S.STOCKID, "
				+ "     K.PRODUCTNO, "
				+ "     K.PRODUCTNAME, "
				+ "     K.PRODUCTSTANDARD "
				+ " ORDER BY S.STOCKID,K.PRODUCTNO) M "
				+ " ON A.STOCKID = M.STOCKID "
				+ " WHERE A.PRODUCTNO = M.PRODUCTNO ";
		List<Map<String, Object>> resultList = this.executeQuery(reportConn, instockSql, param);
		this.closeConnection(reportConn);
		return resultList;
	}
	@Override
	public List<Map<String, Object>> findMonthOutstock(String sql,
			Object[] param) throws SQLException {
		// TODO Auto-generated method stub
		reportConn =  this.getConnection();
		String outstockSql =  " SELECT A.STOCKID,(SELECT P.STOCKNAME FROM K_STOCK P WHERE P.ID = A.STOCKID) AS STOCKNAME, "
				+ "	A.PRODUCTNO AS PRODUCTNO,A.PRODUCTNAME AS PRODUCTNAME,A.PRODUCTSTANDARD AS PRODUCTSTANDARD, "
				+ " IFNULL(M.PRODUCTNUM,0) AS OUTNUM, "
				+ " IFNULL(M.TOTALPRICE,0) AS OUTPRICE "
				+ " FROM K_INVENTORY A "
				+ " LEFT JOIN "
				+ " (SELECT S.STOCKID AS STOCKID, "
				+ " K.PRODUCTNO AS PRODUCTNO, "
				+ " K.PRODUCTNAME AS PRODUCTNAME, "
				+ " K.PRODUCTSTANDARD AS PRODUCTSTANDARD, "
				+ " SUM(K.PRODUCTNUM) AS PRODUCTNUM, "
				+ " SUM(K.TOTALPRICE) AS TOTALPRICE "
				+ " FROM  K_OUTSTOCK S, K_OUTSTOCK_DETAIL K  "
				+ " WHERE S.ID = K.OUTSTOCKID "
				+ sql
				+ " GROUP BY S.STOCKID, "
				+ "     K.PRODUCTNO, "
				+ "     K.PRODUCTNAME, "
				+ "     K.PRODUCTSTANDARD "
				+ " ORDER BY S.STOCKID,K.PRODUCTNO) M "
				+ " ON A.STOCKID = M.STOCKID  "
				+ " WHERE  A.PRODUCTNO = M.PRODUCTNO ";
		List<Map<String, Object>> resultList = this.executeQuery(reportConn, outstockSql, param);
		this.closeConnection(reportConn);
		return resultList;
	}
	@Override
	public List<Map<String, Object>> findMonthProductInstock(String whereSql,
			Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		reportConn =  this.getConnection();
		String sql = "SELECT S.INSTOCKDATE AS 'OPERATEDATE',"
				+ " T.PRODUCTNO AS 'PRODUCTNO',"
				+ " T.PRODUCTNAME AS 'PRODUCTNAME',"
				+ " T.PRODUCTSTANDARD AS 'PRODUCTSTANDARD',"
				+ " IFNULL(SUM(T.PRODUCTNUM), '0') AS 'NUM',"
				+ "	IFNULL(SUM(T.TOTALPRICE), '0') AS 'TOTAL'"
				+ " FROM K_INSTOCK S,K_INSTOCK_DETAIL T "
				+ " WHERE S.ID = T.INSTOCKID "
				+ " AND S.INSTOCKSTATE = '02' "
				+ whereSql
				+ " GROUP BY T.PRODUCTNO ";
		List<Map<String, Object>> resultList = this.executeQuery(reportConn, sql, array);
		this.closeConnection(reportConn);
		return resultList;
	}
	@Override
	public List<Map<String, Object>> findMonthProductOutstock(String whereSql,
			Object[] array) throws SQLException {
		// TODO Auto-generated method stub
		reportConn =  this.getConnection();
		String sql = "SELECT S.OUTSTOCKDATE AS 'OPERATEDATE',"
				+ " T.PRODUCTNO AS 'PRODUCTNO',"
				+ " T.PRODUCTNAME AS 'PRODUCTNAME',"
				+ " T.PRODUCTSTANDARD AS 'PRODUCTSTANDARD',"
				+ " IFNULL(SUM(T.PRODUCTNUM), '0') AS 'NUM',"
				+ "	IFNULL(SUM(T.TOTALPRICE), '0') AS 'TOTAL'"
				+ " FROM K_OUTSTOCK S,K_OUTSTOCK_DETAIL T "
				+ " WHERE S.ID = T.OUTSTOCKID "
				+ " AND S.OUTSTOCKSTATE = '02' "
				+ whereSql
				+ " GROUP BY T.PRODUCTNO ";
		List<Map<String, Object>> resultList = this.executeQuery(reportConn, sql, array);
		this.closeConnection(reportConn);
		return resultList;
	}
	@Override
	public List<ReportBean> findMonthProductDetail(String productNo,
			String startDate, String endDate) throws SQLException {
		// TODO Auto-generated method stub
		reportConn =  this.getConnection();
		String sql = " SELECT A.OPERATEDATE AS 'OPERATEDATE', "
				+ " A.PRODUCTNO AS 'PRODUCTNO',"
				+ " A.PRODUCTNAME AS 'PRODUCTNAME',"
				+ " A.PRODUCTSTANDARD AS 'PRODUCTSTANDARD',"
				+ " A.INNUM AS 'INNUM',"
				+ " A.INTOTAL AS 'INTOTAL',"
				+ " A.OUTNUM AS 'OUTNUM',"
				+ " A.OUTTOTAL AS 'OUTTOTAL'"
				+ " FROM "
				+ " ( SELECT S.INSTOCKDATE AS 'OPERATEDATE',"
				+ " 	T.PRODUCTNO AS 'PRODUCTNO',"
				+ "		T.PRODUCTNAME AS 'PRODUCTNAME',"
				+ "		T.PRODUCTSTANDARD AS 'PRODUCTSTANDARD',"
				+ "		T.PRODUCTNUM AS 'INNUM',"
				+ "		T.TOTALPRICE AS 'INTOTAL',"
				+ "		0 AS 'OUTNUM',"
				+ "		0 AS 'OUTTOTAL'"
				+ "	  FROM "
				+ " 	K_INSTOCK S, K_INSTOCK_DETAIL T "
				+ "   WHERE S.ID = T.INSTOCKID "
				+ "   	AND S.INSTOCKSTATE = '02' "
				+ "  	AND S.INSTOCKDATE >= '" + startDate + "'"
				+ "  	AND S.INSTOCKDATE <= '" + endDate + "'"
				+ " 	AND T.PRODUCTNO LIKE '%" + productNo + "%'"
				+ " UNION"
				+ "   SELECT S.OUTSTOCKDATE AS 'OPERATEDATE',"
				+ " 	T.PRODUCTNO AS 'PRODUCTNO',"
				+ " 	T.PRODUCTNAME AS 'PRODUCTNAME',"
				+ " 	T.PRODUCTSTANDARD AS 'PRODUCTSTANDARD',"
				+ " 	0 AS 'INNUM',"
				+ " 	0 AS 'INTOTAL',"
				+ " 	T.PRODUCTNUM AS 'OUTNUM',"
				+ " 	T.TOTALPRICE AS 'OUTTOTAL'"
				+ "	  FROM "
				+ "     K_OUTSTOCK S,K_OUTSTOCK_DETAIL T "
				+ "   WHERE S.ID = T.OUTSTOCKID "
				+ "    	AND S.OUTSTOCKSTATE = '02' "
				+ "   	AND S.OUTSTOCKDATE >= '" + startDate + "'"
				+ "  	AND S.OUTSTOCKDATE <= '" + endDate + "'"
				+ " 	AND T.PRODUCTNO LIKE '%" + productNo + "%' "
				+ " ) A "
				+ " ORDER BY A.OPERATEDATE DESC,A.PRODUCTNO "; 
		List<Map<String, Object>> resultList = this.executeQuery(reportConn, sql, new Object[0]);
		List<ReportBean> goodsList = new ArrayList<ReportBean>();
		for (Map<String, Object> map : resultList) {
			ReportBean reportBean = new ReportBean();
			reportBean.setOperateDate(map.get("OPERATEDATE").toString());
			reportBean.setProductNo(map.get("PRODUCTNO").toString());
			reportBean.setProductName(map.get("PRODUCTNAME").toString());
			reportBean.setProductStandard(map.get("PRODUCTSTANDARD").toString());
			reportBean.setInNum(map.get("INNUM").toString());
			reportBean.setInTotal(map.get("INTOTAL").toString());
			reportBean.setOutNum(map.get("OUTNUM").toString());
			reportBean.setOutTotal(map.get("OUTTOTAL").toString());
			goodsList.add(reportBean);
		}
		this.closeConnection(reportConn);
		return goodsList;
	}

}
