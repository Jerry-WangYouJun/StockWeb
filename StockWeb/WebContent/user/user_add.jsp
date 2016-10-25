<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加页面</title>
<link rel="stylesheet" type="text/css"
	href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${basePath}/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript"
	src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>	
<script type="text/javascript" src="${basePath}/js/common.js"></script>
<style type="text/css">
	table{
		font-size:12px;
	}
	textarea{
		font-size:12px;
	}
</style>
<script type="text/javascript">
	function doServlet(){
		if(checkInfo()){
			$.ajax({
				//指定传到后台的url
		    	url : "${basePath}/admin/UserServlet?method=insert",
	       		type:'post',  
	       		data: $("#userForm").serialize(),
	       		dataType: 'json',
	       		success: function(data){
		           	parent.$.messager.alert('提示',data.msg);
		       		if(data.success == true) {
		       			parent.doSearch();
		       			parent.$('#dlg-frame').dialog("close");
			       	}
	       		},
	       		error: function(transport) { 
	       			$.messager.alert('提示',"系统产生错误,请联系管理员!","error");
	        	} 
	       	});
		}
	}
	$(function(){
		initDictionarySelect("province", "#hometown");
		initDeptSelect("#deptNo");
	});
	function checkInfo(){
		if($("#userNo").val() == ""){
			$.messager.alert("提示","用户名不能为空!","error");
			return false;
		}
		
		return true;
	}
</script>
</head>
<body>
	<form id="userForm" action="${basePath}/admin/UserServlet?flag=insert" method="post">
	  <table width="100%">
	  	<tr>
	  		<td>用户名：</td>
	  		<td>
	  			<input type="text" id="userNo" name="userNo" size="14" />
	  		</td>
	  		<td>籍贯：</td>
	  		<td>
	  			<select id="hometown"  name="hometown" >
	  				<option value="">---请选择---</option>
	  			</select>
	  		</td>
	  		<td>所在部门：</td>
	  		<td>
	  			<select id="deptNo" name="deptNo" >
	  				<option value="">---请选择---</option>
	  			</select>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>姓名：</td>
	  		<td>
	  			<input type="text" id="userName" name="userName" size="14"  />
	  		</td>
	  		<td>手机号：</td>
	  		<td>
	  			<input type="text" id="mobile" name="telphone" size="14"  />
	  		</td>
	  		<td>邮箱：</td>
	  		<td>
	  			<input type="text" name="email" size="14"  />
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>性别：</td>
	  		<td>
	  			<input type="radio" name="sex" value="0" >男</input>
	  			<input type="radio"  name="sex" value="1" >女</input>
	  		</td>
	  		<td>年龄：</td>
	  		<td>
	  			<input type="text" id="age" name="age" size="14"></input>
	  		</td>
	  		<td>岗位：</td>
	  		<td>
	  			<input type="text" name="position" size="14"></input>
	  		</td>
	  	</tr>
	  	<tr>
	  		
	  		<!-- <td>所属角色：</td>
	  		<td>
	  			<select name="roleNo" >
	  				<option>---请选择---</option>
	  				<option value="admin">管理员</option>
	  			</select>
	  		</td> -->
	  	</tr>
	  	<tr>
	  		<td valign="top" colspan="">备注：</td>
			<td colspan="5">
				<textarea rows="2" cols="60" name="remark"></textarea>
			</td>
	  	</tr>
	  </table>
  </form>
</body>
</html>