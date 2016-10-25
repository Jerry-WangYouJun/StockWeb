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

import com.hyg.bean.OutStockBean;
import com.hyg.bean.OutStockBean;
import com.hyg.bean.InventoryBean;
import com.hyg.bean.OutStockBean;
import com.hyg.bean.OutStockDetailBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.OutstockServiceI;
import com.hyg.service.impl.OutstockServiceImpl;
import com.hyg.service.impl.OutstockServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/stock/OutStockServlet")
public class OutStockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH = "search";
	private final String INSERT = "insert";
	private final String UPDATE_INIT = "updateInit";
	private final String UPDATE = "update";
	private final String DELETE = "delete";
	private final String DETAIL = "detail";
	private final String HANG = "hang";
	private final String CONFIRM = "confirm";
	
	private OutstockServiceI outstockService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OutStockServlet() {
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
		outstockService = new OutstockServiceImpl();
		OutStockBean outStockBean = outstockService.findOutstockById(id);
		request.setAttribute("outStockBean", outStockBean);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/outstock/outstock_detail.jsp");
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
		outstockService = new OutstockServiceImpl();
		OutStockBean outStockBean = outstockService.findOutstockById(id);
		request.setAttribute("outStockBean", outStockBean);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/outstock/outstock_add_detail.jsp");
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
		outstockService = new OutstockServiceImpl();
		
		JSONObject json = outstockService.confrimOutstock(id);
		writeToJson(response, json);
		
	}

	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		outstockService = new OutstockServiceImpl();
		String id = request.getParameter("id");
		
		JSONObject json = outstockService.removeStock(id);
		writeToJson(response, json);
	}
	
	public void updateInit(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		
		outstockService = new OutstockServiceImpl();
		OutStockBean outstock = outstockService.findOutstockById(id);
		request.setAttribute("outStockOrder", outstock);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/outstock/outstock_update.jsp");
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
		String outStockNo = request.getParameter("outStockNo");
		String stockId = request.getParameter("stockId");
		String supplierId = request.getParameter("supplierId");
		String outStockState = request.getParameter("outStockState");
		String outStockNum = request.getParameter("outStockNum");
		String outStockPrice = request.getParameter("outStockPrice");
		String outStockDate = request.getParameter("outStockDate");
		String remark = request.getParameter("remark");
		
		OutStockBean  outstock = new OutStockBean();
		outstock.setId(id);
		outstock.setOutStockNo(outStockNo);
		outstock.setStockId(stockId);
		outstock.setSupplierId(supplierId);
		outstock.setOutStockState(outStockState);
		outstock.setOutStockNum(outStockNum);
		outstock.setOutStockPrice(outStockPrice);
		outstock.setOutStockDate(outStockDate);
		outstock.setRemark(remark);

		outstockService = new OutstockServiceImpl();
		JSONObject json = outstockService.updateOutstock(outstock);
		writeToJson(response, json);
	}

	private void doInsert(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String outStockNo = request.getParameter("outStockNo");
		String stockId = request.getParameter("stockId");
		String outStockDate = request.getParameter("outStockDate");
		String supplierId = request.getParameter("supplierId");
		String outStockNum = request.getParameter("outStockNum");
		String outStockPrice = request.getParameter("outStockPrice");
		String remark = request.getParameter("remark");
		
		OutStockBean  outstock = new OutStockBean();
		outstock.setOutStockNo(outStockNo);
		outstock.setStockId(stockId);
		outstock.setSupplierId(supplierId);
		outstock.setOutStockNum(outStockNum);
		outstock.setOutStockPrice(outStockPrice);
		outstock.setOutStockDate(outStockDate);
		outstock.setRemark(remark);

		outstockService = new OutstockServiceImpl();
		JSONObject json = outstockService.insertOutstock(outstock);
		writeToJson(response, json);
	}

	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		String outStockNo = request.getParameter("outStockNo");
		String stockId = request.getParameter("stockId");
		String supplierId = request.getParameter("supplierId");
		String outStockState = request.getParameter("outStockState");
		String outStockDateStart = request.getParameter("outStockDateStart");
		String outStockDateEnd = request.getParameter("outStockDateEnd");
		
		OutStockBean outstockBean = new OutStockBean();
		outstockBean.setOutStockNo(outStockNo);		
		outstockBean.setStockId(stockId);
		outstockBean.setSupplierId(supplierId);
		outstockBean.setOutStockState(outStockState);
		outstockBean.setOutstockDateStart(outStockDateStart);
		outstockBean.setOutstockDateEnd(outStockDateEnd);
		
		outstockService = new OutstockServiceImpl();
		JSONObject json = outstockService.searchOutstocks(outstockBean,page,rows);
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
