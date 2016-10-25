<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商修改页面</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
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
		$.ajax({
	    	url : "${basePath}/admin/SupplierServlet?method=update",
       		type:'post',  
       		data: $("#supplierForm").serialize(),
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
	$(function(){
		initDictionarySelect("province", "#provinceCode",$("#provinceCodeDB").val());
	});
</script>
</head>
<body> 
	<form id="supplierForm" action="${basePath}/admin/SupplierServlet?method=update" method="post">
	  <table width="100%">
	  	<tr>
	  		<td>供应商编号：</td>
	  		<td>
	  			<input type="hidden" id="id" name="id" value="${requestScope.supplier.id }"/>
	  			<input type="text" name="supplierNo" size="14" value="${requestScope.supplier.supplierNo }"/>
	  		</td>
	  		<td>供应商名称：</td>
	  		<td>
	  			<input type="text" name="supplierName" size="14" value="${requestScope.supplier.supplierName }" />
	  		</td>
	  		<td>所在地区：</td>
	  		<td>
	  			<input type="hidden" id="provinceCodeDB" value="${requestScope.supplier.provinceCode }"/>
	  			<select id="provinceCode" name="provinceCode" >
	  				<option value="">---请选择---</option>
	  			</select>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>联系方式：</td>
	  		<td>
	  			<input type="text" id="supplierTel" name="supplierTel" size="14" value="${requestScope.supplier.supplierTel }"/>
	  		</td>
	  		<td>邮箱：</td>
	  		<td>
	  			<input type="text" name="supplierEmail" size="20"  value="${requestScope.supplier.supplierEmail }"/>
	  		</td>
	  		<td>传真：</td>
	  		<td>
	  			<input type="text" name="supplierTax" size="14" value="${requestScope.supplier.supplierTax }"></input>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>联系人姓名：</td>
	  		<td>
	  			<input type="text" name="userName" size="14" value="${requestScope.supplier.userName }"></input>
	  		</td>
	  		<td>联系人手机：</td>
	  		<td><input type="text" name="userTel" size="14"  value="${requestScope.supplier.userTel }"/></td>
	  	</tr>
	  		<tr>
	  		<td>供应商详细地址：</td>
	  		<td colspan="5">
	  			<input type="text" name="supplierAddress" size="60"  value="${requestScope.supplier.supplierAddress }" />
	  		</td>
	  	</tr>
	  	<tr>
	  		<td valign="top" colspan="">备注：</td>
			<td colspan="5">
				<textarea rows="2" cols="60" name="remark">${requestScope.supplier.remark }</textarea>
			</td>
	  	</tr>
	  </table>
  </form>
</body>
</html>