package com.hyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.hyg.bean.UserBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.service.UserServiceI;
import com.hyg.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final  String SEARCH_METHOD = "search";
    private final  String INSERT_METHOD = "insert";
    private final  String DELETE_METHOD = "delete";
    private final  String UPDATEINIT_METHOD = "updateInit";
    private final  String UPDATE_METHOD = "update";
    
    private UserServiceI userService = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
		//确保前后台编码一致，不会出现中文乱码的情况
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
			initUpdate(request,response);
			break;
		case UPDATE_METHOD:
			doUpdate(request,response);
			break;
		case DELETE_METHOD:
			doRemove(request,response);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 进行用户查询,预处理操作  （封装UserBean类）
	 * 调用UserSercie中查询用户的方法
	 * @param request
	 * @param response
	 */
	private void doSearch(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		//获取前台传过来的参数  查询条件
		String userNo = request.getParameter("userNo");
		String userName = request.getParameter("userName");
		String deptNo = request.getParameter("deptNo");
		String position = request.getParameter("position");
		
		//获取EasyUI组件中的分页信息，包含每页显示行数 和当前页码
		int page = StringUtils.isEmpty(request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page")); //当前页码
		int rows = StringUtils.isEmpty(request.getParameter("rows"))?10:Integer.parseInt(request.getParameter("rows"));//每页显示行数
		
		
		UserBean userForm = new UserBean();
		userForm.setUserNo(userNo);
		userForm.setUserName(userName);
		userForm.setDeptNo(deptNo);
		userForm.setPosition(position);
		
		//用户服务类初始化
		userService = new UserServiceImpl();
		
		//调用查询用户的方法,返回json字符串
		JSONObject json = userService.searchUser(userForm,page,rows);
		//响应到前台
		writeToJson(response,json);
		
	}
	
	private void doInsert(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userNo = request.getParameter("userNo");
		String userName = request.getParameter("userName");
		String telphone = request.getParameter("telphone");
		String email = request.getParameter("email");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String position = request.getParameter("position");
		String hometown = request.getParameter("hometown");
		String deptNo = request.getParameter("deptNo");
		String remark = request.getParameter("remark");
		UserBean userForm = new UserBean();
		userForm.setUserNo(userNo);
		userForm.setUserName(userName);
		userForm.setTelphone(telphone);
		userForm.setEmail(email);
		userForm.setSex(sex);
		userForm.setAge(age);
		userForm.setPosition(position);
		userForm.setHometown(hometown);
		userForm.setDeptNo(deptNo);
		userForm.setRemark(remark);
		//用户服务类初始化
		userService = new UserServiceImpl();
		//返回JSON对象
		JSONObject json = userService.insert(userForm);
		
		writeToJson(response,json);		
	}

	/**
	 * 初始化要修改的用户记录，并将查询出来的用户转发到user_update.jsp
	 * @param request
	 * @param response
	 */
	
	private void initUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		userService = new UserServiceImpl();
		UserBean user = userService.findUserById(id);
		request.setAttribute("user", user);
		try {
			RequestDispatcher rd = request.getRequestDispatcher("/user/user_update.jsp");
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
		String userNo = request.getParameter("userNo");
		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");
		String telphone = request.getParameter("telphone");
		String email = request.getParameter("email");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String position = request.getParameter("position");
		String hometown = request.getParameter("hometown");
		String deptNo = request.getParameter("deptNo");
		String remark = request.getParameter("remark");
		UserBean userForm = new UserBean();
		userForm.setId(id);
		userForm.setUserNo(userNo);
		userForm.setUserName(userName);
		userForm.setPwd(pwd);
		userForm.setTelphone(telphone);
		userForm.setEmail(email);
		userForm.setSex(sex);
		userForm.setAge(age);
		userForm.setPosition(position);
		userForm.setHometown(hometown);
		userForm.setDeptNo(deptNo);
		userForm.setRemark(remark);
		//用户服务类初始化
		userService = new UserServiceImpl();
		//返回JSON对象
		JSONObject json = userService.update(userForm);
		
		writeToJson(response,json);	
	}
	
	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		userService = new UserServiceImpl();
		String id = request.getParameter("id");
		
		JSONObject json = userService.delete(id);
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
