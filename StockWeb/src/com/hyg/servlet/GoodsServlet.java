package com.hyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.hyg.bean.GoodsBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.GoodsServiceI;
import com.hyg.service.impl.GoodsServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH = "search";
	private final String INSERT = "insert";
	private final String UPDATE_INIT = "updateInit";
	private final String UPDATE = "update";
	private final String DELETE = "delete";
	
	private GoodsServiceI goodsService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsServlet() {
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
		}
		
	}

	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		
		goodsService = new GoodsServiceImpl();
		JSONObject json = goodsService.removeGoods(id);
		writeToJson(response, json);
	}
	
	public void updateInit(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		goodsService = new GoodsServiceImpl();
		GoodsBean goodsBean = goodsService.findGoodsById(id);
		request.setAttribute("goods",goodsBean);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/goods/goods_update.jsp");
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
		String productNo = request.getParameter("productNo");
		String productName = request.getParameter("productName");
		String productTypeCode = request.getParameter("productTypeCode");
		String productStandard = request.getParameter("productStandard");
		String supplierNo = request.getParameter("supplierNo");
		String productDate = request.getParameter("productDate");
		String productNum = request.getParameter("productNum");
		String unit = request.getParameter("unit");
		String price = request.getParameter("price");
		String remark = request.getParameter("remark");
		
		GoodsBean goodsBean = new GoodsBean();
		goodsBean.setId(id);
		goodsBean.setProductNo(productNo);
		goodsBean.setProductName(productName);
		goodsBean.setProductTypeCode(productTypeCode);
		goodsBean.setProductStandard(productStandard);
		goodsBean.setSupplierNo(supplierNo);
		goodsBean.setProductDate(productDate);
		goodsBean.setProductNum(Double.parseDouble(productNum));
		goodsBean.setUnit(unit);
		goodsBean.setPrice(Double.parseDouble(price));
		goodsBean.setRemark(remark);

		goodsService = new GoodsServiceImpl();
		JSONObject json = goodsService.updateGoods(goodsBean);
		writeToJson(response, json);
	}

	private void doInsert(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String productNo = request.getParameter("productNo");
		String productName = request.getParameter("productName");
		String productTypeCode = request.getParameter("productTypeCode");
		String productStandard = request.getParameter("productStandard");
		String supplierNo = request.getParameter("supplierNo");
		String productDate = request.getParameter("productDate");
		String productNum = request.getParameter("productNum");
		String unit = request.getParameter("unit");
		String price = request.getParameter("price");
		String remark = request.getParameter("remark");
		
		GoodsBean goodsBean = new GoodsBean();
		goodsBean.setProductNo(productNo);
		goodsBean.setProductName(productName);
		goodsBean.setProductTypeCode(productTypeCode);
		goodsBean.setProductStandard(productStandard);
		goodsBean.setSupplierNo(supplierNo);
		goodsBean.setProductDate(productDate);
		goodsBean.setProductNum(Double.parseDouble(productNum));
		goodsBean.setUnit(unit);
		goodsBean.setPrice(Double.parseDouble(price));
		goodsBean.setRemark(remark);

		goodsService = new GoodsServiceImpl();
		JSONObject json = goodsService.insertGoods(goodsBean);
		writeToJson(response, json);
	}

	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		String productNo = request.getParameter("productNo");
		String productName = request.getParameter("productName");
		String supplierNo = request.getParameter("supplierNo");
		String productStandard = request.getParameter("productStandard");
		String productTypeCode = request.getParameter("productTypeCode");
		String productDateStart = request.getParameter("productDateStart");
		String productDateEnd = request.getParameter("productDateEnd");
		String queryParam = request.getParameter("q");
		
		
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		
		if (StringUtils.isNotEmpty(queryParam)) {
			whereSql += " and productNo like ?";
			params.add("%" + queryParam.trim() + "%");
		}
		if (StringUtils.isNotEmpty(productNo)) {
			whereSql += " and productNo like ?";
			params.add("%" + productNo + "%");
		}
		if (StringUtils.isNotEmpty(productName)) {
			whereSql += " and productName like ?";
			params.add("%" + productName + "%");
		}
		if (StringUtils.isNotEmpty(supplierNo)) {
			whereSql += " and supplierNo = ?";
			params.add(supplierNo);
		}
		if (StringUtils.isNotEmpty(productStandard)) {
			whereSql += " and productStandard = ?";
			params.add(productStandard);
		}
		if (StringUtils.isNotEmpty(productTypeCode)) {
			whereSql += " and productTypeCode = ?";
			params.add(productTypeCode);
		}
		if (StringUtils.isNotEmpty(productDateStart)) {
			whereSql += " and productDate > ?";
			params.add(productDateStart);
		}
		if (StringUtils.isNotEmpty(productDateEnd)) {
			whereSql += " and productDate < ?";
			params.add(productDateEnd);
		}
		
		
		goodsService = new GoodsServiceImpl();
		JSONObject json = goodsService.searchGoods(whereSql,params,page,rows);
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
