package com.hyg.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.hyg.bean.UserBean;
import com.hyg.core.utils.StringUtils;
import com.hyg.dao.UserDaoI;
import com.hyg.dao.impl.UserDaoImpl;
import com.hyg.service.UserServiceI;

public class UserServiceImpl implements UserServiceI {
	private UserDaoI userDao;
	@Override
	public UserBean checkUser(UserBean user) {
		// TODO Auto-generated method stub
		String wheresql = "";
		List param = new ArrayList();
		
		if (StringUtils.isNotEmpty(user.getUserNo())) {
			wheresql += " and userNo = ? ";
			param.add(user.getUserNo());
		}
		
		if (StringUtils.isNotEmpty(user.getPwd())) {
			wheresql += " and pwd = ? ";
			param.add(user.getPwd());
		}
		userDao = new UserDaoImpl();
		UserBean userDB = null;
		try {
			UserBean[] users = userDao.findByDynamicWhere(wheresql, param.toArray());
			if (users.length > 0) {
				userDB = users[0];
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userDB;
	}
	@Override
	public JSONObject searchUser(UserBean userForm, int page, int rows) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		
		//判断用户输入的查询条件，根据查询条件去查找用户信息
		String whereSql = "";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(userForm.getUserNo())) {
			whereSql += " and userNo like ?";
			params.add("%" + userForm.getUserNo() + "%");
		}
		if (StringUtils.isNotEmpty(userForm.getUserName())) {
			whereSql += " and userName like ?";
			params.add("%" + userForm.getUserName() + "%");
		}
		if (StringUtils.isNotEmpty(userForm.getPosition())) {
			whereSql += " and position = ?";
			params.add(userForm.getPosition());
		}
		if (StringUtils.isNotEmpty(userForm.getDeptNo())) {
			whereSql += " and deptNo = ?";
			params.add(userForm.getDeptNo());
		}
		
		//初始化userDao
		userDao = new UserDaoImpl();
		//根据dao查找用户，返回多条记录
		UserBean[] users = null;
		//获取根据查询条件查出来的记录总数
		int total = 0;
		try {
			users = userDao.findByDynamicWhereByPage(whereSql, params.toArray(),page,rows);
			total = userDao.getTotal(whereSql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//key=rows 关键字，是EasyUI框架中datagrid组件解析数据所必须的
		json.put("rows",users);
		//key=total关键字，是EasyUI框架中datagrid组件的分页组件进行统计总行数所必须的
		json.put("total", total);
		return json;
	}
	@Override
	public JSONObject insert(UserBean userForm) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		userDao = new UserDaoImpl();
		int count;
		try {
			count = userDao.insert(userForm);
		
			if (count > 0) {
				json.put("msg", "恭喜你成功添加" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，添加用户操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
		
	}
	
	@Override
	public JSONObject update(UserBean userForm) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		//初始化UserDao
		userDao = new UserDaoImpl();
		int count;
		try {
			//先根据ID到数据库查询出记录，
			//然后将页面上显示的字段内容赋值给从数据库查询出来的POJO中对应的属性 
			// eg： userDB.setUserNo(userForm.getUserNo()) 
			count = userDao.update(new BigDecimal(userForm.getId()),userForm);
			
			if (count > 0) {
				json.put("msg", "恭喜你成功修改" + count + "条记录!");
				json.put("success", true);
			}else{
				json.put("msg", "很抱歉，修改用户操作失败，请联系管理员!");
				json.put("success", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject delete(String id) {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		userDao = new UserDaoImpl();

		if (StringUtils.isEmpty(id)) {
			json.put("success", false);
			json.put("msg", "用户id不能为空，请检查！");
		}else {
			try {
				int count  = userDao.delete(new BigDecimal(id));
				if (count >= 0) {
					json.put("success", true);
					json.put("msg", "恭喜你删除用户成功！");
				}else{
					json.put("success", false);
					json.put("msg", "很抱歉删除用户操作失败，请检查！");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	@Override
	public UserBean findUserById(String id) {
		// TODO Auto-generated method stub
		UserBean user = null;
		try {
			userDao = new UserDaoImpl();
			if (StringUtils.isNotEmpty(id)) {
				user = userDao.findByPrimaryKey(new BigDecimal(id));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}


}
