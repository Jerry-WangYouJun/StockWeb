package com.hyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.hyg.bean.StockBean;
import com.hyg.bean.UserBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.StockServiceI;
import com.hyg.service.impl.StockServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/StockServlet")
public class StockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH = "search";
	private final String INSERT = "insert";
	private final String UPDATE_INIT = "updateInit";
	private final String UPDATE = "update";
	private final String DELETE = "delete";
	private final String STOCK_SELECTS = "findStockSelects";
	
	private StockServiceI stockService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockServlet() {
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
		}else if (INSERT.equals(method)) {
			doInsert(request,response);
		}else if (UPDATE_INIT.equals(method)) {
			updateInit(request,response);
		}else if (UPDATE.equals(method)) {
			doUpdate(request,response);
		}else if (DELETE.equals(method)) {
			doRemove(request,response);
		}else if (STOCK_SELECTS.equals(method)) {
			findStockSelects(request,response);
		}else {
			request.setAttribute("msg", "操作数据有误，系统不能识别操作标记！");
		}
		
	}

	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		stockService = new StockServiceImpl();
		String id = request.getParameter("id");
		
		JSONObject json = stockService.removeStock(id);
		writeToJson(response, json);
		
	}
	
	public void updateInit(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		
		stockService = new StockServiceImpl();
		StockBean stock = stockService.findStockById(id);
		request.setAttribute("stock", stock);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/stock/stock_update.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		String stockNo = request.getParameter("stockNo");
		String stockName = request.getParameter("stockName");
		String provinceCode = request.getParameter("provinceCode");
		String stockAddress = request.getParameter("stockAddress");
		String stockTel = request.getParameter("stockTel");
		String userId = request.getParameter("userId");
		String remark = request.getParameter("remark");
		
		StockBean stockBean = new StockBean();
		stockBean.setId(id);
		stockBean.setStockNo(stockNo);
		stockBean.setStockName(stockName);
		stockBean.setProvinceCode(provinceCode);
		stockBean.setStockAddress(stockAddress);
		stockBean.setStockTel(stockTel);
		stockBean.setUserId(userId);
		stockBean.setRemark(remark);
		
		stockService = new StockServiceImpl();
		JSONObject json = stockService.updateStock(stockBean);
		writeToJson(response, json);
	}

	private void doInsert(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String stockNo = request.getParameter("stockNo");
		String stockName = request.getParameter("stockName");
		String provinceCode = request.getParameter("provinceCode");
		String stockAddress = request.getParameter("stockAddress");
		String stockTel = request.getParameter("stockTel");
		String userId = request.getParameter("userId");
		String remark = request.getParameter("remark");
		
		StockBean stockBean = new StockBean();
		stockBean.setStockNo(stockNo);
		stockBean.setStockName(stockName);
		stockBean.setProvinceCode(provinceCode);
		stockBean.setStockAddress(stockAddress);
		stockBean.setStockTel(stockTel);
		stockBean.setUserId(userId);
		stockBean.setRemark(remark);
		
		stockService = new StockServiceImpl();
		JSONObject json = stockService.insertStock(stockBean);
		writeToJson(response, json);
	}

	
	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		String stockNo = request.getParameter("stockNo");
		String stockName = request.getParameter("stockName");
		String userId = request.getParameter("userId");
		
		StockBean stock = new StockBean();
		stock.setStockNo(stockNo);
		stock.setStockName(stockName);
		stock.setUserId(userId);
		
		stockService = new StockServiceImpl();
		JSONObject json = stockService.searchStock(stock,page,rows);
		writeToJson(response, json);
	}
	
	/**
	 * 查找仓库ID和仓库名称
	 * <option value=id>stockName</option>
	 * @param request
	 * @param response
	 */
	private void findStockSelects(HttpServletRequest request,
			HttpServletResponse response) {
		
		stockService = new StockServiceImpl();
		JSONObject json = stockService.findStockSelects();
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
