package com.hyg.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hyg.bean.UserBean;
import com.hyg.dao.UserDaoI;
import com.hyg.dao.impl.UserDaoImpl;
import com.hyg.service.UserServiceI;
import com.hyg.service.impl.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		//校验用户名密码是否正确
		//如果用户名和密码输入正确则跳转到首页，并显示当前登录的用户姓名，否则跳转会login.jsp页面
		UserDaoI userDao = new UserDaoImpl();
		//1、获取前台的值
		String userNo = request.getParameter("userNo");
		String pwd = request.getParameter("pwd");
		UserBean user = new UserBean();
		user.setUserNo(userNo);
		user.setPwd(pwd);
		//2、到数据库进行校验
		UserServiceI userService = new UserServiceImpl();
		UserBean userDB =	userService.checkUser(user);
		
		String url = "index.jsp";
		if (userDB == null) {
			request.setAttribute("msg", "用户名密码错误!");
			url = "login.jsp";
		}else{
			HttpSession session = request.getSession();
			session.setAttribute("userName", userDB.getUserName());
		}
		
		//3、根据校验结果返回到相应的页面 
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
		
	}

}
