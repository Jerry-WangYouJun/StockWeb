package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.net.aso.f;
import net.sf.json.JSONObject;

import com.hyg.bean.ReportBean;
import com.hyg.core.utils.DateUtils;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.ReportDaoI;
import com.hyg.dao.impl.ReportDaoImpl;
import com.hyg.service.ReportServiceI;

public class ReportServiceImpl implements ReportServiceI {
	private ReportDaoI reportDao;
	private final String  INSTOCK_FLAG = "inStock"; //入库标记
	private final String  OUTSTOCK_FLAG = "outStock";//出库标记
	
	@Override
	public JSONObject searchMonthReport(String stockId, String productNo,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//默认是查询当天的库存量
		if (StringUtils.isEmpty(startDate)) {
			startDate = DateUtils.formatDate("yyyy-MM-dd", new Date());
		}
		if (StringUtils.isEmpty(endDate)) {
			endDate = DateUtils.formatDate("yyyy-MM-dd", new Date());
		}
		
		List<ReportBean> resultList = new ArrayList<ReportBean>();
		
		//期初入库总量
		List<Map<String, Object>> instockInitList = this.findInitTotalNum(startDate, stockId, INSTOCK_FLAG);
		//期初出库总量
		List<Map<String, Object>> outstockInitList = this.findInitTotalNum(startDate, stockId, OUTSTOCK_FLAG);
		//波段入库总量
		List<Map<String, Object>> instockList = this.findTotalNum(startDate, endDate, stockId, INSTOCK_FLAG);
		//波段出库总量
		List<Map<String, Object>> outstockList = this.findTotalNum(startDate, endDate, stockId, OUTSTOCK_FLAG);
		
		//循环遍历期初入库量
		ReportBean reportBean = null;
		for (int i = 0; i < instockInitList.size(); i++) {
			reportBean = new ReportBean();
			Map<String, Object> startInTemp = instockInitList.get(i);
			String stockIdDB = startInTemp.get("STOCKID").toString();
			String stockName = startInTemp.get("STOCKNAME").toString();
			String productNoDB = startInTemp.get("PRODUCTNO").toString();
			String productName = startInTemp.get("PRODUCTNAME").toString();
			String productStandard = startInTemp.get("PRODUCTSTANDARD").toString();
			String startInNum = startInTemp.get("INNUM").toString(); //期初入库量
			String startInTotal = startInTemp.get("INPRICE").toString();//期初出库量
			reportBean.setStockId(stockIdDB);
			reportBean.setStockName(stockName);
			reportBean.setProductNo(productNoDB);
			reportBean.setProductName(productName);
			reportBean.setProductStandard(productStandard);
			reportBean.setStartNum(startInNum);
			reportBean.setStartTotal(startInTotal);
			reportBean.setInNum("0");
			reportBean.setInTotal("0");
			reportBean.setOutNum("0");
			reportBean.setOutTotal("0");
			//遍历循环期初出库量
			
			for (int m = 0; m < outstockInitList.size(); m++) {
				Map<String, Object> startOutTemp = outstockInitList.get(m);
				if (stockIdDB.equals(startOutTemp.get("STOCKID").toString()) 
						&& productNoDB.equals(startOutTemp.get("PRODUCTNO").toString())) {
					String startOutNum = startOutTemp.get("OUTNUM").toString(); //期初出库量
					String startOutTotal = startOutTemp.get("OUTPRICE").toString();//期初出库金额
					//期初结算量  ==  期初入库量-期初出库量
					reportBean.setStartNum(new BigDecimal(startInNum).subtract(new BigDecimal(startOutNum)).toString());
					reportBean.setStartTotal(new BigDecimal(startInTotal).subtract(new BigDecimal(startOutTotal)).toString());
				}
			}
			
			//遍历循环期中入库量
			for (int j = 0; j < instockList.size(); j++) {
				Map<String, Object> inTemp = instockList.get(j);
				if (stockIdDB.equals(inTemp.get("STOCKID").toString()) 
					&& productNoDB.equals(inTemp.get("PRODUCTNO").toString())) {
					String inNum = inTemp.get("INNUM").toString(); //期中入库量
					String inTotal = inTemp.get("INPRICE").toString();//期中入库金额
					//期中入库量
					reportBean.setInNum(inNum);
					reportBean.setInTotal(inTotal);
				}
			}
			
			//遍历循坏期中出库量
			for (int n = 0; n < outstockList.size(); n++) {
				Map<String, Object> outTemp = outstockList.get(n);
				if (stockIdDB.equals(outTemp.get("STOCKID").toString()) 
						&& productNoDB.equals(outTemp.get("PRODUCTNO").toString())) {
					String outNum = outTemp.get("OUTNUM").toString();//期中出库量
					String outTotal = outTemp.get("OUTPRICE").toString();//期中出库金额
					//期中出库量
					reportBean.setOutNum(outNum);
					reportBean.setOutTotal(outTotal);
				}
			}
			
			//期末结算量   = 期初结算量+期中入库量-期中出库量
			
			reportBean.setEndNum(new BigDecimal(reportBean.getStartNum()).add(new BigDecimal(reportBean.getInNum()).subtract(new BigDecimal(reportBean.getOutNum()))).toString());
			reportBean.setEndTotal(new BigDecimal(reportBean.getStartTotal()).add(new BigDecimal(reportBean.getInTotal()).subtract(new BigDecimal(reportBean.getOutTotal()))).toString());
			
			resultList.add(reportBean);
		}
		json.put("rows", resultList);
		
		return json;
	}

	@Override
	public List<Map<String, Object>> findInitTotalNum(String startDate,
			String stockId, String flag) {
		// TODO Auto-generated method stub
		reportDao = new ReportDaoImpl();
		List<Map<String, Object>> results = null;
		String whereSql = "" ;
		List param = new ArrayList();
		try {
			if (INSTOCK_FLAG.equals(flag)) {
				if (StringUtils.isNotEmpty(startDate)) {
					whereSql += " AND S.INSTOCKDATE < ? ";
					param.add(startDate);
				}
				if (StringUtils.isNotEmpty(stockId)) {
					whereSql += " AND S.STOCKID = ? ";
					param.add(stockId);
				}
				results = reportDao.findMonthInstock(whereSql, param.toArray());
			}else if (OUTSTOCK_FLAG.equals(flag)) {
				if (StringUtils.isNotEmpty(startDate)) {
					whereSql += " AND S.OUTSTOCKDATE < ? ";
					param.add(startDate);
				}
				if (StringUtils.isNotEmpty(stockId)) {
					whereSql += " AND S.STOCKID = ? ";
					param.add(stockId);
				}
				results = reportDao.findMonthOutstock(whereSql, param.toArray());
			}else {
				System.out.println("出入库标志位不能识别，请检查！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	@Override
	public List<Map<String, Object>> findTotalNum(String startDate,
			String endDate, String stockId, String flag) {
		// TODO Auto-generated method stub
		String whereSql = "";
		List param = new ArrayList();
		
		reportDao = new ReportDaoImpl();
		List<Map<String, Object>> results = null;
		try {
			if (INSTOCK_FLAG.equals(flag)) {
				if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
					whereSql += " AND S.INSTOCKDATE >= ?  AND S.INSTOCKDATE <= ?";
					param.add(startDate);
					param.add(endDate);
				}
				if (StringUtils.isNotEmpty(stockId)) {
					whereSql += " AND S.STOCKID = ? ";
					param.add(stockId);
				}
				results = reportDao.findMonthInstock(whereSql, param.toArray());
			}else if (OUTSTOCK_FLAG.equals(flag)) {
				if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
					whereSql += " AND S.OUTSTOCKDATE >= ? AND S.OUTSTOCKDATE <= ?";
					param.add(startDate);
					param.add(endDate);
				}
				if (StringUtils.isNotEmpty(stockId)) {
					whereSql += " AND S.STOCKID = ? ";
					param.add(stockId);
				}
				results = reportDao.findMonthOutstock(whereSql, param.toArray());
			}else {
				System.out.println("出入库标志位不能识别，请检查！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;	
	}

	@Override
	public JSONObject searchProductReport(String productNo, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//默认是查询当天的库存量
		if (StringUtils.isEmpty(startDate)) {
			startDate = DateUtils.formatDate("yyyy-MM-dd", new Date());
		}
		if (StringUtils.isEmpty(endDate)) {
			endDate = DateUtils.formatDate("yyyy-MM-dd", new Date());
		}
		
		List<ReportBean> resultList = new ArrayList<ReportBean>();
		
		//期初入库总量
		List<Map<String, Object>> instockInitList = this.findInitProductTotalNum(startDate,productNo, INSTOCK_FLAG);
		//期初出库总量
		List<Map<String, Object>> outstockInitList = this.findInitProductTotalNum(startDate,productNo, OUTSTOCK_FLAG);
		//波段入库总量
		List<Map<String, Object>> instockList = this.findProductTotalNum(startDate, endDate,productNo, INSTOCK_FLAG);
		//波段出库总量
		List<Map<String, Object>> outstockList = this.findProductTotalNum(startDate, endDate,productNo,OUTSTOCK_FLAG);
		
		//遍历期初入库量
		ReportBean reportBean = null;
		for (int i = 0; i < instockInitList.size(); i++) {
			reportBean = new ReportBean();
			reportBean.setOperateDate(startDate); //默认开始日期就是结算期初日期
			Map<String, Object> startInTemp = instockInitList.get(i);
			String productNoDB = startInTemp.get("PRODUCTNO").toString();
			
			String productName = startInTemp.get("PRODUCTNAME").toString();
			String productStandard = startInTemp.get("PRODUCTSTANDARD").toString();
			String startInNum = startInTemp.get("NUM").toString(); //期初入库量
			String startInTotal = startInTemp.get("TOTAL").toString();//期初入库金额
			reportBean.setProductNo(productNoDB);
			reportBean.setProductName(productName);
			reportBean.setProductStandard(productStandard);
			//默认期初和期末结余量一致
			reportBean.setStartNum(startInNum);
			reportBean.setStartTotal(startInTotal);
			reportBean.setEndNum(startInNum);
			reportBean.setEndTotal(startInTotal);
			//遍历循初出库量
			
			for (int m = 0; m < outstockInitList.size(); m++) {
				Map<String, Object> startOutTemp = outstockInitList.get(m);
				if (productNoDB.equals(startOutTemp.get("PRODUCTNO").toString())) {
					String startOutNum = startOutTemp.get("NUM").toString();
					String startOutTotal = startOutTemp.get("TOTAL").toString();
					//期初结算量  ==  期初入库量-期初出库量
					reportBean.setStartNum(new BigDecimal(startInNum).subtract(new BigDecimal(startOutNum)).toString());
					reportBean.setStartTotal(new BigDecimal(startInTotal).subtract(new BigDecimal(startOutTotal)).toString());
					reportBean.setEndNum(reportBean.getStartNum());
					reportBean.setEndTotal(reportBean.getEndTotal());
				}
			}
			//期末结算量  ==  期末入库量-期末出库量
			for (int n = 0; n < instockList.size(); n++) {
				Map<String, Object> endInTemp = instockList.get(n);
				if (productNoDB.equals(endInTemp.get("PRODUCTNO").toString())) {
					String endInNum = endInTemp.get("NUM").toString();
					String endInTotal = endInTemp.get("TOTAL").toString();
					reportBean.setEndNum(new BigDecimal(reportBean.getEndNum()).add(new BigDecimal(endInNum)).toString());
					reportBean.setEndTotal(new BigDecimal(reportBean.getEndTotal()).add(new BigDecimal(endInTotal)).toString());
				}
			}
			for (int j = 0; j < outstockList.size(); j++) {
				Map<String, Object> endOutTemp = outstockList.get(j);
				if (productNoDB.equals(endOutTemp.get("PRODUCTNO").toString())) {
					String endOutNum = endOutTemp.get("NUM").toString();
					String endOutTotal = endOutTemp.get("TOTAL").toString();
					reportBean.setEndNum(new BigDecimal(reportBean.getEndNum()).subtract(new BigDecimal(endOutNum)).toString());
					reportBean.setEndTotal(new BigDecimal(reportBean.getEndTotal()).subtract(new BigDecimal(endOutTotal)).toString());
				}
			}
			resultList.add(reportBean);
		}
		json.put("rows", resultList);
		return json;
	}

	@Override
	public List<Map<String, Object>> findInitProductTotalNum(String startDate,
			String productNo, String flag) {
		// TODO Auto-generated method stub
		reportDao = new ReportDaoImpl();
		List<Map<String, Object>> results = null;
		String whereSql = "" ;
		List param = new ArrayList();
		
		try {
			if (INSTOCK_FLAG.equals(flag)) {
				if (StringUtils.isNotEmpty(startDate)) {
					whereSql += " AND S.INSTOCKDATE <= ? ";
					param.add(startDate);
				}
				if (StringUtils.isNotEmpty(productNo)) {
					whereSql += " AND T.PRODUCTNO LIKE  ? ";
					param.add("%" + productNo + "%");
				}
				results = reportDao.findMonthProductInstock(whereSql, param.toArray());
			}else if (OUTSTOCK_FLAG.equals(flag)) {
				if (StringUtils.isNotEmpty(startDate)) {
					whereSql += " AND S.OUTSTOCKDATE <= ? ";
					param.add(startDate);
				}
				if (StringUtils.isNotEmpty(productNo)) {
					whereSql += " AND T.PRODUCTNO LIKE  ? ";
					param.add("%" + productNo + "%");
				}
				results = reportDao.findMonthProductOutstock(whereSql, param.toArray());
			}else {
				System.out.println("出入库标志位不能识别，请检查！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	@Override
	public List<Map<String, Object>> findProductTotalNum(String startDate,
			String endDate, String productNo, String flag) {
		// TODO Auto-generated method stub
		reportDao = new ReportDaoImpl();
		List<Map<String, Object>> results = null;
		String whereSql = "" ;
		List param = new ArrayList();
		
		try {
			if (INSTOCK_FLAG.equals(flag)) {
				if (StringUtils.isNotEmpty(startDate)) {
					whereSql += " AND S.INSTOCKDATE > ? ";
					param.add(startDate);
				}
				if (StringUtils.isNotEmpty(endDate)) {
					whereSql += " AND S.INSTOCKDATE <= ? ";
					param.add(endDate);
				}
				if (StringUtils.isNotEmpty(productNo)) {
					whereSql += " AND T.PRODUCTNO LIKE  ? ";
					param.add("%" + productNo + "%");
				}
				results = reportDao.findMonthProductInstock(whereSql, param.toArray());
			}else if (OUTSTOCK_FLAG.equals(flag)) {
				if (StringUtils.isNotEmpty(startDate)) {
					whereSql += " AND S.OUTSTOCKDATE > ? ";
					param.add(startDate);
				}
				if (StringUtils.isNotEmpty(endDate)) {
					whereSql += " AND S.OUTSTOCKDATE <= ? ";
					param.add(endDate);
				}
				if (StringUtils.isNotEmpty(productNo)) {
					whereSql += " AND T.PRODUCTNO LIKE  ? ";
					param.add("%" + productNo + "%");
				}
				results = reportDao.findMonthProductOutstock(whereSql, param.toArray());
			}else {
				System.out.println("出入库标志位不能识别，请检查！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	@Override
	public JSONObject searchProductDetailReport(String productNo,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		if (StringUtils.isEmpty(productNo)) {
			json.put("msg", "查看产品流水时[产品编号]不能为空,请检查!");
			json.put("success", true);
			return json;
		}
		//默认是查询当天的库存量
		if (StringUtils.isEmpty(startDate)) {
			startDate = DateUtils.formatDate("yyyy-MM-dd", new Date());
		}
		if (StringUtils.isEmpty(endDate)) {
			endDate = DateUtils.formatDate("yyyy-MM-dd", new Date());
		}
		
		List<ReportBean> resultList = new ArrayList<ReportBean>();
		
		reportDao = new ReportDaoImpl();
		
		try {
			resultList = reportDao.findMonthProductDetail(productNo,startDate,endDate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		json.put("rows", resultList);
		return json;
		
	}

}
