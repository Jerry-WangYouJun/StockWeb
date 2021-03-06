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

import com.hyg.bean.SupplierBean;
import com.hyg.bean.SupplierBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.SupplierServiceI;
import com.hyg.service.impl.StockServiceImpl;
import com.hyg.service.impl.SupplierServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/SupplierServlet")
public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH = "search";
	private final String INSERT = "insert";
	private final String UPDATE_INIT = "updateInit";
	private final String UPDATE = "update";
	private final String DELETE = "delete";
	private final String SUPPLIER_SELECTS = "findSuppliersSelects";
	
	private SupplierServiceI supplierService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplierServlet() {
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
		}else if (SUPPLIER_SELECTS.equals(method)) {
			findSuppliersSelects(request,response);
		}else {
			request.setAttribute("msg", "操作数据有误，系统不能识别操作标记！");
		}
		
	}

	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		supplierService = new SupplierServiceImpl();
		JSONObject json = supplierService.removeSupplier(id);
		writeToJson(response, json);
	}
	
	public void updateInit(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		supplierService = new SupplierServiceImpl();
		SupplierBean supplier = supplierService.findSupplierById(id);
		request.setAttribute("supplier", supplier);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/supplier/supplier_update.jsp");
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
		String supplierNo = request.getParameter("supplierNo");
		String supplierName = request.getParameter("supplierName");
		String provinceCode = request.getParameter("provinceCode");
		String supplierTel = request.getParameter("supplierTel");
		String supplierEmail = request.getParameter("supplierEmail");
		String supplierTax = request.getParameter("supplierTax");
		String userName = request.getParameter("userName");
		String userTel = request.getParameter("userTel");
		String supplierAddress = request.getParameter("supplierAddress");
		String remark = request.getParameter("remark");
		
		SupplierBean supplierBean = new SupplierBean();
		supplierBean.setId(id);
		supplierBean.setSupplierNo(supplierNo);
		supplierBean.setSupplierName(supplierName);
		supplierBean.setProvinceCode(provinceCode);
		supplierBean.setSupplierTel(supplierTel);
		supplierBean.setSupplierEmail(supplierEmail);
		supplierBean.setSupplierTax(supplierTax);
		supplierBean.setUserName(userName);
		supplierBean.setUserTel(userTel);
		supplierBean.setSupplierAddress(supplierAddress);
		supplierBean.setRemark(remark);
		
		supplierService = new SupplierServiceImpl();
		JSONObject json = supplierService.update(supplierBean);
		writeToJson(response, json);
	}

	private void doInsert(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String supplierNo = request.getParameter("supplierNo");
		String supplierName = request.getParameter("supplierName");
		String provinceCode = request.getParameter("provinceCode");
		String supplierTel = request.getParameter("supplierTel");
		String supplierEmail = request.getParameter("supplierEmail");
		String supplierTax = request.getParameter("supplierTax");
		String userName = request.getParameter("userName");
		String userTel = request.getParameter("userTel");
		String supplierAddress = request.getParameter("supplierAddress");
		String remark = request.getParameter("remark");
		SupplierBean supplierBean = new SupplierBean();
		supplierBean.setSupplierNo(supplierNo);
		supplierBean.setSupplierName(supplierName);
		supplierBean.setProvinceCode(provinceCode);
		supplierBean.setSupplierTel(supplierTel);
		supplierBean.setSupplierEmail(supplierEmail);
		supplierBean.setSupplierTax(supplierTax);
		supplierBean.setUserName(userName);
		supplierBean.setUserTel(userTel);
		supplierBean.setSupplierAddress(supplierAddress);
		supplierBean.setRemark(remark);
		
		supplierService = new SupplierServiceImpl();
		JSONObject json = supplierService.insert(supplierBean);
		writeToJson(response, json);
	}

	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		String supplierNo = request.getParameter("supplierNo");
		String supplierName = request.getParameter("supplierName");
		String provinceCode = request.getParameter("provinceCode");
		String userName = request.getParameter("userName");
		
		SupplierBean supplierBean = new SupplierBean();
		supplierBean.setSupplierNo(supplierNo);
		supplierBean.setSupplierName(supplierName);
		supplierBean.setProvinceCode(provinceCode);
		supplierBean.setUserName(userName);
		
		supplierService = new SupplierServiceImpl();
		JSONObject json = supplierService.search(supplierBean,page,rows);
		writeToJson(response, json);
	}
	
	private void findSuppliersSelects(HttpServletRequest request,
			HttpServletResponse response) {
		supplierService = new SupplierServiceImpl();
		JSONObject json = supplierService.findSupplierSelects();
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
