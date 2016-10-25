<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门修改页面</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
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
		$.ajax({
	    	url : "${basePath}/admin/DeptServlet?method=update",
       		type:'post',  
       		data: $("#deptForm").serialize(),
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
	
	/* function chooseOption(obj){
		var optionValue = $(obj).find("option:selected").val();
		var optionText = $(obj).find("option:selected").text();
		if(optionValue == ""){
			$("#productTypeName").val("");
		}else{
			$("#productTypeName").val(optionText);
		}
		
	} */
	
	/* $(function(){
		$("#productTypeCode option[value='" + $("#productTypeCodeDB").val() + "']").attr("selected","selected");
		$("#supplierNo option[value='" + $("#supplierNoDB").val() + "']").attr("selected","selected");
	}); */
</script>
</head>
<body>
	<form id="deptForm" action="${basePath}/admin/DeptServlet?method=update" method="post">
	  <table width="100%">
	  	<tr>
	  		<td>部门编号：</td>
	  		<td>
	  			<input type="hidden" id="id" name="id" value="${requestScope.dept.id }" />
	  			<input type="text" name="deptNo" size="14" value="${requestScope.dept.deptNo }"/>
	  		</td>
	  		<td>部门名称：</td>
	  		<td>
	  			<input type="text" name="deptName" size="14" value="${requestScope.dept.deptName }"/>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>部门领导人：</td>
	  		<td>
	  			<input type="text" id="deptLearder" name="deptLearder" size="14" value="${requestScope.dept.deptLeader }" />
	  		</td>
	  		<td>部门联系方式：</td>
	  		<td>
	  			<input type="text" id="deptTel" name="deptTel" size="20" value="${requestScope.dept.deptTel }" />
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>上级部门：</td>
	  		<td>
	  			<input type="text" name="parentDeptNo" size="14" value="${requestScope.dept.parentDeptNo }" ></input>
	  		</td>
	  		<td>部门简介：</td>
	  		<td>
	  			<input type="text" name="deptDesc" size="20" value="${requestScope.dept.deptDesc }" ></input>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td valign="top" colspan="">备注：</td>
			<td colspan="3">
				<textarea rows="2" cols="60" name="remark">${requestScope.dept.remark }</textarea>
			</td>
	  	</tr>
	  </table>
  </form>
</body>
</html>