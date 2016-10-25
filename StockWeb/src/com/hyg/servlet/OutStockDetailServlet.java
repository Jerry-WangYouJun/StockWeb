package com.hyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hyg.bean.GoodsBean;
import com.hyg.bean.OutStockBean;
import com.hyg.bean.OutStockDetailBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.OutstockDetailServiceI;
import com.hyg.service.impl.OutstockDetailServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/stock/OutStockDetailServlet")
public class OutStockDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH = "search";
	private final String SAVEORUPDATE = "saveOrUpdate";
	private final String DELETE = "delete";
	
	private OutstockDetailServiceI detailService;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OutStockDetailServlet() {
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
		if (SEARCH.equals(method)) {
			doSearch(request,response);
		}else if (DELETE.equals(method)) {
			doRemove(request,response);
		}else if (SAVEORUPDATE.equals(method)) {
			saveOrUpdate(request, response);
		}
	}

	
	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		detailService = new OutstockDetailServiceImpl();
		JSONObject json = detailService.removeDetail(id);
		writeToJson(response, json);
		
	}
	
	/**
	 * 对明细信息进行保存操作     ---> 如果原来表中没有数据则进行添加操作，如果原来表中有数据则进行修改操作
	 * @param request
	 * @param response
	 */
	private void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		String goodsList = request.getParameter("goodsList");
		JSONArray jsonArray = JSONArray.fromObject(goodsList);
		
		String outStockId = request.getParameter("outStockId");
		String outStockState = request.getParameter("outStockState");
		
		
		detailService = new OutstockDetailServiceImpl();
		JSONObject json = detailService.saveOrUpdate(jsonArray,outStockId,outStockState);
		writeToJson(response, json);
		
	}
	
	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		String outStockId = request.getParameter("outStockId");
		
		detailService = new OutstockDetailServiceImpl();
		JSONObject json = detailService.searchOutstocksByPage(outStockId,page,rows);
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
