<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库详情</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/css/common.css">
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
	$(function(){
		var outStockId = $('#id').val();
		var stockId = $('#stockId').val();
	    $('#goods').datagrid({
	    	url: "${basePath}/stock/OutStockDetailServlet?method=search",
	    	queryParams:{
	    		outStockId:outStockId
	    	},		
	    	rownumbers : true,
			autoRowHeight : true,
			singleSelect:true,
			nowrap: false,
			columns:[[
						{field : 'id',title : '产品ID',halign:'center',width : 180,hidden:true},
					    {field : 'productNo',title : '产品编号',halign:'center',width : 180},
					    {field : 'productName',title : '产品名称',halign:'center',width : 160},
					    {field : 'productStandard',title : '产品规格',halign:'center',width : 180}, 
					    {field : 'productNum',title : '产品数量',halign:'center',width : 100},
					    {field : 'unit',title : '计量单位',halign:'center',width : 100},
					    {field : 'price',title : '产品单价',halign:'center',width : 100}
					]]
	    });
	});
	</script>

</head>
<body>
	<center>
	 	<h1>出库申请单</h1>
	 </center>
	 <div class="">
	  <table width="100%" >
	  	<tr>
	  		<td>出库申请单编号：</td>
	  		<td>
	  			${requestScope.outStockBean.outStockNo }
	  			<input id="id" type="hidden" name="id" value="${requestScope.outStockBean.id }"/> 
	  			<input id="stockId" type="hidden" name="stockId" value="${requestScope.outStockBean.stockId }"/> 
	  			<input id="outStockState" type="hidden" name="outStockState" value="${requestScope.outStockBean.outStockState }"/> 
	  		</td>
	  		<td>出库仓库：</td>
	  		<td>
	  			 ${requestScope.outStockBean.stockName }
	  		</td>
	  		<td>出库日期：</td>
	  		<td>
	  			${requestScope.outStockBean.outStockDate }
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>供货商：</td>
	  		<td>
	  			${requestScope.outStockBean.supplierName }
	  		</td>
	  		<td>出库单状态：</td>
	  		<td>
	  			<c:if test="${requestScope.outStockBean.outStockState eq '00' }">创建</c:if >
	  			<c:if test="${requestScope.outStockBean.outStockState eq '02' }">记账</c:if >
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>备注：</td>
	  		<td colspan="5">
	  			${requestScope.outStockBean.remark }
	  		</td>
	  	</tr>
	  </table>
	  </div>
	  <table id="goods"  title="产品列表"></table>
	
</body>
</html>