package com.hyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.catalina.connector.Request;

import com.hyg.bean.InventoryBean;
import com.hyg.bean.UserBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.InventoryServiceI;
import com.hyg.service.impl.InventoryServiceImpl;


@WebServlet("/InventoryServlet")
public class InventoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH = "search";
	private final String SEARCH_GOODS = "searchGoods";
	private InventoryServiceI inventoryService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String method = req.getParameter("method");
		if(SEARCH.equals(method)){
			doSearch(req, resp);			
		}else if (SEARCH_GOODS.equals(method)) {
			searchProducts(req,resp);
		}
	}
	private void searchProducts(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		String stockId = req.getParameter("stockId");
		String productNo = req.getParameter("q");
		
		inventoryService = new InventoryServiceImpl();
		JSONObject json = inventoryService.searchDynamicProducts(stockId,productNo);
		writeToJson(resp, json);
		
		
		
	}
	private void doSearch(HttpServletRequest request, HttpServletResponse response) {
		//当前页码
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); 
		//每页显示行数
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));
		String itemNo = request.getParameter("itemNo");
		String itemName = request.getParameter("itemName");
		String stockNo = request.getParameter("stockNo");
		
		InventoryBean inventoryBean = new InventoryBean();
		inventoryBean.setProductNo(itemNo);
		inventoryBean.setProductName(itemName);
		inventoryBean.setStockId(stockNo);		
		
		inventoryService = new InventoryServiceImpl();
		JSONObject json = inventoryService.search(inventoryBean,page,rows);
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
