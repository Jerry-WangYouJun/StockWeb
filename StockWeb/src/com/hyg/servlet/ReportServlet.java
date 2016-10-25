package com.hyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.hyg.bean.ReportBean;
import com.hyg.core.utils.DateUtils;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.ReportServiceI;
import com.hyg.service.impl.ReportServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/report/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH_BY_STOCK = "searchByStock";
	private final String SEARCH_BY_GOODS = "searchByGoods";
	private final String SEARCH_BY_GOODS_DETAIL = "searchByGoodsDetail";
	private final String EXPORT = "export";
	
	private ReportServiceI reportService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if (SEARCH_BY_STOCK.equals(method)) {
			doSearchByStock(request,response);
		}else if (SEARCH_BY_GOODS.equals(method)) {
			doSearchByGoods(request,response);
		}else if (SEARCH_BY_GOODS_DETAIL.equals(method)) {
			doSearchByGoodsDetail(request,response);
		}else if (EXPORT.equals(method)) {
			doExport(request,response);
		}
		
	}

	
	private void doExport(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("未完待续......");
	}
	
	private void doSearchByGoods(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String productNo = request.getParameter("productNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		reportService = new ReportServiceImpl();
		JSONObject json = new JSONObject();
		json = reportService.searchProductReport(productNo,startDate,endDate);
		writeToJson(response, json);
	}
	
	private void doSearchByGoodsDetail(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String productNo = request.getParameter("productNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		reportService = new ReportServiceImpl();
		JSONObject json = new JSONObject();
		json = reportService.searchProductDetailReport(productNo,startDate,endDate);
		writeToJson(response, json);
	}

	/**
	 * 月报
	 * @param request
	 * @param response
	 */
	private void doSearchByStock(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String stockId = request.getParameter("stockId");
		String productNo = request.getParameter("productNo");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		reportService = new ReportServiceImpl();
		JSONObject json = new JSONObject();
		json = reportService.searchMonthReport(stockId,productNo,startDate,endDate);
		writeToJson(response, json);
	}
	
	/**
	 * 以JSON格式响应到前台
	 * @param response
	 * @param json
	 */
	public void  writeToJson(HttpServletResponse response ,JSONObject json) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (json == null) {
				json.put("success", false);
			}
			out.println(json);// 向客户端输出JSONObject字符串
	        out.flush();
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
