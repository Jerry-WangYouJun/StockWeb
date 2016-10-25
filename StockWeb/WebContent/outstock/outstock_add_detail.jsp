<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品添加</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/js/common.js"></script>
	<script type="text/javascript" src="${basePath}/js/json2.js"></script>
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
			var outStockId = $('#id').val();
			var outStockState = $('#outStockState').val();
			if (outStockId == "") {
				$.messager.alert('提示',"当前出库申请单无效，请检查!","error");
				return;
			}
			if (outStockState != "00") {
				$.messager.alert('提示',"当前出库申请单状态不是【创建状态】，无法进行确认操作，请检查!","error");
				return;
			}
			
			var row = $('#goods').edatagrid('getSelected');
			if(row != null){
				var index = $('#goods').edatagrid('getRowIndex',row);
				$('#goods').edatagrid('endEdit',index);
			}
			
			var rows = $('#goods').datagrid('getRows');
			
			 $.ajax({
			    	url : "${basePath}/stock/OutStockDetailServlet?method=saveOrUpdate",
		       		type:'post',  
		       		data: {
		       			outStockId:outStockId,
		       			outStockState:outStockState,
		       			goodsList:JSON.stringify(rows)
		       			},
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
		
	</script>

</head>
<body>
	<center>
	 	<h1>出库申请单</h1>
	 </center>
	 <div class="">
	  <table width="100%">
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
	  			<c:if test="${requestScope.inStockBean.outStockState eq '00' }">创建</c:if >
	  			<c:if test="${requestScope.inStockBean.outStockState eq '02' }">记账</c:if >
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
	<script type="text/javascript">
	$(function(){
		var outStockId = $('#id').val();
		var stockId = $('#stockId').val();
	    $('#goods').edatagrid({
	    	url: "${basePath}/stock/OutStockDetailServlet?method=search",
	    	destroyUrl:	'${basePath}/stock/OutStockDetailServlet?method=delete',
	    	queryParams:{
	    		outStockId:outStockId
	    	},		
	    	rownumbers : true,
			autoRowHeight : true,
			singleSelect:true,
			nowrap: false,
			toolbar: [{
				text:'添加',
				iconCls: 'icon-add',
				handler: function(){
					var rows = $('#goods').datagrid('getRows');
					$('#goods').edatagrid('addRow');
				}
			},'-',{
				text:'取消编辑',
				iconCls: 'icon-remove',
				handler: function(){
					var row = $('#goods').edatagrid('getSelected');
					if(row == null)
						return;
					var index = $('#goods').edatagrid('getRowIndex',row);
					$('#goods').edatagrid('cancelRow');
				}
			},'-',{
				text:'删除',
				iconCls: 'icon-removeGoods',
				handler: function(){
					 $('#goods').edatagrid('destroyRow');
				}
			}],
			columns:[[
						{field : 'id',title : '产品ID',halign:'center',width : 180,hidden:true},
					    {field : 'productNo',title : '产品编号',halign:'center',width : 180,editor:{type:'combogrid',options:{
					    	panelWidth:700,
					    	url: '${basePath}/InventoryServlet?method=searchGoods&stockId=' + stockId,
					    	/* idField:'stockId',
					    	textField:'productNo', */
					    	mode:'remote',
					    	fitColumns:true,
					    	columns:[[
					    		{field : 'productNo',title:'产品编码',halign:'center',width:100},
					    		{field : 'productName',title : '产品名称',halign:'center',width : 160},
							    {field : 'productStandard',title : '产品规格',halign:'center',width : 120}, 
							    {field : 'productNum',title : '库存数量',halign:'center',width : 100},
							    {field : 'unit',title : '计量单位',halign:'center',width : 100},
							    {field : 'price',title : '产品单价',halign:'center',width : 100}
					    	]],
					    	onClickRow:function(index,rowData){
					    		rowData.id="";
					    		rowData.productNum="";
					    		var rowIndex=$('#goods').datagrid('getRowIndex',$('#goods').datagrid('getSelected'));
			                    $('#goods').edatagrid("updateRow",{index:rowIndex,row:rowData});
			                    $('#goods').datagrid("unselectRow",rowIndex);
							}
					    }}},
					    {field : 'productName',title : '产品名称',halign:'center',width : 160,editor:{type:'validatebox',options:{required:true}}},
					    {field : 'productStandard',title : '产品规格',halign:'center',width : 180,editor:{type:'validatebox',options:{required:true}}}, 
					    {field : 'productNum',title : '产品数量',halign:'center',width : 100,editor:{type:'numberbox',options:{precision:2}}},
					    {field : 'unit',title : '计量单位',halign:'center',width : 100,editor:{type:'validatebox',options:{required:true}}},
					    {field : 'price',title : '产品单价',halign:'center',width : 100,editor:{type:'numberbox',options:{precision:2}}}
					]],
	    		onDblClickCell:function(rowIndex, field, value){
						$(this).datagrid('beginEdit', rowIndex);
						var ed = $(this).datagrid('getEditor', {index:rowIndex,field:field});
						$(ed.target).focus();
					}
	    });
	});
	</script>
</body>
</html>