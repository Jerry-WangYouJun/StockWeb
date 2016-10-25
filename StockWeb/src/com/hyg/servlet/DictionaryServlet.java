package com.hyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import net.sf.json.JSONObject;

import com.hyg.bean.DictionaryBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.DictionaryServiceI;
import com.hyg.service.impl.DictionaryServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/DictionaryServlet")
public class DictionaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String SEARCH = "search";
	private final String INSERT = "insert";
	private final String UPDATE_INIT = "updateInit";
	private final String UPDATE = "update";
	private final String DELETE = "delete";
	private final String DIC_SELECTS = "findDictionarySelects";
	private final String DIC_VALUE = "findDictionaryValue";
	
	private DictionaryServiceI dictionaryService;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DictionaryServlet() {
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
		}else if (DIC_SELECTS.equals(method)) {
			findDictionarySelects(request,response);
		}else if (DIC_VALUE.equals(method)) {
			findDictionaryValue(request,response);
		} 
		
	}

	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		dictionaryService = new DictionaryServiceImpl();
		JSONObject json = dictionaryService.removeDic(id);
		writeToJson(response, json);
	}
	
	public void updateInit(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		dictionaryService = new DictionaryServiceImpl();
		DictionaryBean dicBean = dictionaryService.findDicById(id);
		request.setAttribute("dictionary", dicBean);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/dic/dictionary_update.jsp");
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
		String dicId = request.getParameter("dicId");
		String dicType = request.getParameter("dicType");
		String dicCode = request.getParameter("dicCode");
		String dicValue = request.getParameter("dicValue");
		String dicSort = request.getParameter("dicSort");
		String validateFlag = request.getParameter("validateFlag");
		String remark = request.getParameter("remark");
		
		
		DictionaryBean dicBean = new DictionaryBean();
		dicBean.setId(id);
		dicBean.setDicId(dicId);
		dicBean.setDicType(dicType);
		dicBean.setDicCode(dicCode);
		dicBean.setDicValue(dicValue);
		dicBean.setDicSort(dicSort);
		dicBean.setValidateFlag(validateFlag);
		dicBean.setRemark(remark);
		
		dictionaryService = new DictionaryServiceImpl();
		JSONObject json  = dictionaryService.updateDic(dicBean);
		writeToJson(response, json);
	}

	private void doInsert(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String dicId = request.getParameter("dicId");
		String dicType = request.getParameter("dicType");
		String dicCode = request.getParameter("dicCode");
		String dicValue = request.getParameter("dicValue");
		String dicSort = request.getParameter("dicSort");
		String remark = request.getParameter("remark");
		DictionaryBean dicBean = new DictionaryBean();
		dicBean.setDicId(dicId);
		dicBean.setDicType(dicType);
		dicBean.setDicCode(dicCode);
		dicBean.setDicValue(dicValue);
		dicBean.setDicSort(dicSort);
		dicBean.setRemark(remark);
		
		dictionaryService = new DictionaryServiceImpl();
		JSONObject json  = dictionaryService.insertDic(dicBean);
		writeToJson(response, json);
	}

	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		String dicType = request.getParameter("dicType");
		String dicCode = request.getParameter("dicCode");
		String dicValue = request.getParameter("dicValue");
		DictionaryBean dicBean = new DictionaryBean();
		dicBean.setDicType(dicType);
		dicBean.setDicCode(dicCode);
		dicBean.setDicValue(dicValue);
		

		dictionaryService = new DictionaryServiceImpl();
		JSONObject json  = dictionaryService.searchDic(dicBean,page,rows);
		writeToJson(response, json);
	}
	
	private void findDictionarySelects(HttpServletRequest request,
			HttpServletResponse response) {
		String dicId = request.getParameter("dicId");
		
		dictionaryService = new DictionaryServiceImpl();
		JSONObject json  = dictionaryService.findDictionarySeletsByDicId(dicId);
		writeToJson(response, json);
	}
	private void findDictionaryValue(HttpServletRequest request,
			HttpServletResponse response) {
		String dicId = request.getParameter("dicId");
		String dicCode = request.getParameter("dicCode");
		
		dictionaryService = new DictionaryServiceImpl();
		JSONObject json  = dictionaryService.findDictionarySelectsByDicIdAndDicCode(dicId,dicCode);
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
