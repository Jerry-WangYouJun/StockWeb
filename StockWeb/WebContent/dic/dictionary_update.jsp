<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典修改页面</title>
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
	    	url : "${basePath}/admin/DictionaryServlet?method=update",
       		type:'post',  
       		data: $("#dictionaryForm").serialize(),
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
		$("#validateFlag option[value='" + $("#validateFlagDB").val() + "']").attr("selected","selected");
	}); 
</script>
</head>
<body>
	<form id="dictionaryForm" action="${basePath}/admin/DictionaryServlet?method=update" method="post">
	 <table width="100%">
	  	<tr>
	  		<td>字典类型ID：</td>
	  		<td>
	  			<input type="text" name="dicId" size="14"  value="${requestScope.dictionary.dicId }"/>
	  			<input type="hidden" name="id" size="14"  value="${requestScope.dictionary.id }"/>
	  		</td>
	  		<td>字典类型名称：</td>
	  		<td>
	  			<input type="text" name="dicType" size="20" value="${requestScope.dictionary.dicType }" />
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>字典项编码：</td>
	  		<td>
	  			<input type="text" id="dicCode" name="dicCode" size="14"  value="${requestScope.dictionary.dicCode }" />
	  		</td>
	  		<td>字典项值：</td>
	  		<td>
	  			<input type="text" id="dicValue" name="dicValue" size="20"  value="${requestScope.dictionary.dicValue }" />
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>字典排序：</td>
	  		<td>
	  			<input type="text" name="dicSort" size="14"  value="${requestScope.dictionary.dicSort }"/>
	  		</td>
	  		<td>是否生效：</td>
	  		<td>
	  			<input type="hidden" id="validateFlagDB" name="validateFlagDB" size="14"  value="${requestScope.dictionary.validateFlag }"/>
	  			<select id="validateFlag" name="validateFlag" >
	  				<option value="">---请选择---</option>
	  				<option value="0">生效</option>
	  				<option value="1">失效</option>
	  			</select>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td valign="top" >备注：</td>
			<td colspan="3">
				<textarea rows="2" cols="60" name="remark"> ${requestScope.dictionary.remark }</textarea>
			</td>
	  	</tr>
	  </table>
  </form>
</body>
</html>