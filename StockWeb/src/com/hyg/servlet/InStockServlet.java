package com.hyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

import com.hyg.bean.InStockBean;
import com.hyg.bean.InStockDetailBean;
import com.hyg.bean.InventoryBean;
import com.hyg.bean.StockBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.InStockServiceI;
import com.hyg.service.impl.InstockServiceImpl;
import com.hyg.service.impl.StockServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/stock/InStockServlet")
public class InStockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH = "search";
	private final String INSERT = "insert";
	private final String UPDATE_INIT = "updateInit";
	private final String UPDATE = "update";
	private final String DELETE = "delete";
	private final String DETAIL = "detail";
	private final String HANG = "hang";
	private final String CONFIRM = "confirm";
	
	private InStockServiceI instockService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InStockServlet() {
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
		}else if (DETAIL.equals(method)) {
			doDetail(request,response);
		}else if (HANG.equals(method)) {
			doHang(request,response);
		}else if (CONFIRM.equals(method)) {
			doConfirm(request,response);
		}else {
			request.setAttribute("msg", "操作数据有误，系统不能识别操作标记！");
		}
		
	}
	
	private void doDetail(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		instockService = new InstockServiceImpl();
		InStockBean inStockBean = instockService.findInstockById(id);
		request.setAttribute("inStockBean", inStockBean);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/instock/instock_detail.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 选择产品
	 * @param request
	 * @param response
	 */
	private void doHang(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		instockService = new InstockServiceImpl();
		InStockBean inStockBean = instockService.findInstockById(id);
		request.setAttribute("inStockBean", inStockBean);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/instock/instock_add_detail.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 记账操作
	 * @param request
	 * @param response
	 */
	private void doConfirm(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		instockService = new InstockServiceImpl();
		
		JSONObject json = instockService.confrimInstock(id);
		writeToJson(response, json);
		
	}

	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		instockService = new InstockServiceImpl();
		String id = request.getParameter("id");
		
		JSONObject json = instockService.removeStock(id);
		writeToJson(response, json);
	}
	
	public void updateInit(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		
		instockService = new InstockServiceImpl();
		InStockBean instock = instockService.findInstockById(id);
		request.setAttribute("inStockOrder", instock);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/instock/instock_update.jsp");
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
		String inStockNo = request.getParameter("inStockNo");
		String stockId = request.getParameter("stockId");
		String supplierId = request.getParameter("supplierId");
		String inStockState = request.getParameter("inStockState");
		String inStockNum = request.getParameter("inStockNum");
		String inStockPrice = request.getParameter("inStockPrice");
		String inStockDate = request.getParameter("inStockDate");
		String remark = request.getParameter("remark");
		
		InStockBean  instock = new InStockBean();
		instock.setId(id);
		instock.setInStockNo(inStockNo);
		instock.setStockId(stockId);
		instock.setSupplierId(supplierId);
		instock.setInStockState(inStockState);
		instock.setInStockNum(inStockNum);
		instock.setInStockPrice(inStockPrice);
		instock.setInStockDate(inStockDate);
		instock.setRemark(remark);

		instockService = new InstockServiceImpl();
		JSONObject json = instockService.updateInstock(instock);
		writeToJson(response, json);
	}

	private void doInsert(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String inStockNo = request.getParameter("inStockNo");
		String stockId = request.getParameter("stockId");
		String inStockDate = request.getParameter("inStockDate");
		String supplierId = request.getParameter("supplierId");
		String inStockNum = request.getParameter("inStockNum");
		String inStockPrice = request.getParameter("inStockPrice");
		String remark = request.getParameter("remark");
		
		InStockBean  instock = new InStockBean();
		instock.setInStockNo(inStockNo);
		instock.setStockId(stockId);
		instock.setSupplierId(supplierId);
		instock.setInStockNum(inStockNum);
		instock.setInStockPrice(inStockPrice);
		instock.setInStockDate(inStockDate);
		instock.setRemark(remark);

		instockService = new InstockServiceImpl();
		JSONObject json = instockService.insertInstock(instock);
		writeToJson(response, json);
	}

	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		String inStockNo = request.getParameter("inStockNo");
		String stockId = request.getParameter("stockId");
		String supplierId = request.getParameter("supplierId");
		String inStockState = request.getParameter("inStockState");
		String inStockDateStart = request.getParameter("inStockDateStart");
		String inStockDateEnd = request.getParameter("inStockDateEnd");
		
		InStockBean instockBean = new InStockBean();
		instockBean.setInStockNo(inStockNo);		
		instockBean.setStockId(stockId);
		instockBean.setSupplierId(supplierId);
		instockBean.setInStockState(inStockState);
		instockBean.setInstockDateStart(inStockDateStart);
		instockBean.setInstockDateEnd(inStockDateEnd);
		
		instockService = new InstockServiceImpl();
		JSONObject json = instockService.searchInstocks(instockBean,page,rows);
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
