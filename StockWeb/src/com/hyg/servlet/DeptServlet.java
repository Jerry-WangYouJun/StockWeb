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

import com.hyg.bean.DeptBean;
import com.hyg.bean.UserBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.DeptServiceI;
import com.hyg.service.impl.DeptServiceImpl;
import com.hyg.service.impl.DictionaryServiceImpl;
import com.hyg.service.impl.UserServiceImpl;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/DeptServlet")
public class DeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private final  String SEARCH_METHOD = "search";
    private final  String INSERT_METHOD = "insert";
    private final  String UPDATEINIT_METHOD = "updateInit";
    private final  String UPDATE_METHOD = "update";
    private final  String DELETE_METHOD = "delete";
    private final String FIND_DEPT_METHOD = "findById";
	private final String DEPT_SELECTS_METHOD = "findDeptSelects";
	
	private DeptServiceI deptService;
	
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeptServlet() {
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
		
		switch (method) {
		case SEARCH_METHOD:
			doSearch(request,response);
			break;
		case INSERT_METHOD:
			doInsert(request,response);
			break;
		case UPDATEINIT_METHOD:
			updateInit(request,response);
			break;
		case UPDATE_METHOD:
			doUpdate(request,response);
			break;
		case DELETE_METHOD:
			doRemove(request,response);
			break;
		case FIND_DEPT_METHOD:
			findDeptById(request,response);
			break;
		case DEPT_SELECTS_METHOD:
			findDeptSelects(request,response);
			break;
		default:
			break;
		}
		
	}

	private void findDeptById(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		deptService = new DeptServiceImpl();
		
		JSONObject json = new JSONObject();
		DeptBean deptBean = deptService.findDeptById(id);
		json.put("result", deptBean);
		json.put("success", true);
		writeToJson(response, json);
	}

	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		deptService = new DeptServiceImpl();
		JSONObject json = deptService.deleteDept(id);
		
		writeToJson(response, json);
	}
	
	public void updateInit(HttpServletRequest request,
			HttpServletResponse response) {
		
		String id = request.getParameter("id");
		deptService = new DeptServiceImpl();
		DeptBean dept = deptService.findDeptById(id);
		request.setAttribute("dept", dept);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/dept/dept_update.jsp");
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
		String deptNo = request.getParameter("deptNo");
		String deptName = request.getParameter("deptName");
		String deptLeader = request.getParameter("deptLearder");
		String deptTel = request.getParameter("deptTel");
		String parentDeptNo = request.getParameter("parentDeptNo");
		String deptDesc = request.getParameter("deptDesc");
		String remark = request.getParameter("remark");
		
		DeptBean dept = new DeptBean();
		dept.setId(id);
		dept.setDeptNo(deptNo);
		dept.setDeptName(deptName);
		dept.setDeptLeader(deptLeader);
		dept.setDeptTel(deptTel);
		dept.setParentDeptNo(parentDeptNo);
		dept.setDeptDesc(deptDesc);
		dept.setRemark(remark);
		
		JSONObject json = deptService.updateDept(dept);
		
		writeToJson(response, json);
		
	}

	private void doInsert(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String deptNo = request.getParameter("deptNo");
		String deptName = request.getParameter("deptName");
		String deptLeader = request.getParameter("deptLearder");
		String deptTel = request.getParameter("deptTel");
		String parentDeptNo = request.getParameter("parentDeptNo");
		String deptDesc = request.getParameter("deptDesc");
		String remark = request.getParameter("remark");
		
		DeptBean dept = new DeptBean();
		dept.setDeptNo(deptNo);
		dept.setDeptName(deptName);
		dept.setDeptLeader(deptLeader);
		dept.setDeptTel(deptTel);
		dept.setParentDeptNo(parentDeptNo);
		dept.setDeptDesc(deptDesc);
		dept.setRemark(remark);
		
		deptService = new DeptServiceImpl();
		JSONObject json = deptService.insertDept(dept);
		
		writeToJson(response, json);
		
	}

	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		String deptNo = request.getParameter("deptNo");
		String deptName = request.getParameter("deptName");
		String parentDeptNo = request.getParameter("parentDeptNo");
		
		DeptBean dept = new DeptBean();
		dept.setDeptNo(deptNo);
		dept.setDeptName(deptName);
		dept.setParentDeptNo(parentDeptNo);
		
		deptService = new DeptServiceImpl();
		JSONObject json = deptService.searchDept(dept,page,rows);
		
		writeToJson(response, json);
		
	}
	
	private void findDeptSelects(HttpServletRequest request,
			HttpServletResponse response) {
		deptService = new DeptServiceImpl();
		JSONObject json = deptService.findDeptDics();
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
