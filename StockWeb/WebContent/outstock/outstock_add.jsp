<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库单添加</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/js/common.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/js/jquery.edatagrid.js"></script>
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
		    	url : "${basePath}/stock/OutStockServlet?method=insert",
	       		type:'post',  
	       		data: $("#outStockForm").serialize(),
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
			//初始化仓库
			initStockSelect("#stockId");
			//初始化供应商
			initSupplierSelect("#supplierId");
		});
		
	</script>

</head>
<body>
	<center>
		<h1>出库申请单</h1>
	</center>
	<form id="outStockForm" action="${basePath}/stock/OutStockServlet?method=insert" method="post">
	  <table width="100%" class="table" cellpadding="0" cellspacing="0">
	  	<tr>
	  		<td class="td">出库申请单编号：</td>
	  		<td class="td">
	  			<input type="text" id="outStockNo" name="outStockNo" size="20" />
	  		</td>
	  		<td class="td">出库仓库：</td>
	  		<td class="td">
	  			<select id="stockId" name="stockId" >
	  				<option value="">---请选择---</option>
	  			</select>
	  		</td>
	  		<td class="td">出库日期：</td>
	  		<td class="td">
	  			<input id="outStockDate" class="easyui-datebox" type="text" name="outStockDate" size="14"
	  				data-options="currentText:'今日',closeText:'关闭',editable:false"></input>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td class="td">供货商：</td>
	  		<td class="td">
	  			<select id="supplierId" name="supplierId"  >
	  				<option value="">---请选择---</option>
	  			</select>
	  		</td>
	  		<td class="td">出库总量：</td>
	  		<td class="td">
	  			<input type="text" id="outStockNum" name="outStockNum" size="20" readonly="readonly" value="0" />
	  		</td>
	  		<td class="td">出库总金额：</td>
	  		<td class="td">
	  			<input type="text" id="outStockPrice" name="outStockPrice" size="20" readonly="readonly" value="0"/>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td class="td">备注：</td>
	  		<td class="td" colspan="5" >
	  			<textarea id="remark" name="remark" rows="3" cols="80"></textarea>
	  		</td>
	  	</tr>
	  </table>
  </form>
	
</body>
</html>