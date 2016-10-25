<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入库单添加</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/js/common.js"></script>
	<script type="text/javascript" src="${basePath}/jquery.edatagrid.js"></script>
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
	    $('#goods').edatagrid({
	    	url: "${basePath}/stock/InStockDetailServlet?method=search",
	    	saveUrl:"${basePath}/stock/InStockDetailServlet?method=insert",
	    	updateUrl: "${basePath}/stock/InStockDetailServlet?method=update",
	    	destroyUrl: "${basePath}/stock/InStockDetailServlet?method=delete",
	    	rownumbers : true,
			autoRowHeight : true,
			singleSelect:true,
			nowrap: false,
			//autoSave:true,
			toolbar: [{
				text:'添加',
				iconCls: 'icon-add',
				handler: function(){
					 $('#goods').edatagrid('addRow');
				}
			},'-',{
				text:'取消',
				iconCls: 'icon-remove',
				handler: function(){
					 $('#goods').edatagrid('cancelRow');
				}
			}],
			columns:[[
						{field : 'id',title : '产品ID',halign:'center',width : 180,hidden:true},
					    {field : 'productNo',title : '产品编号',halign:'center',width : 180,editor:{type:'combogrid',options:{
					    	panelWidth:700,
					    	url: '${basePath}/admin/GoodsServlet?method=search',
					    	idField:'id',
					    	textField:'productNo',
					    	mode:'remote',
					    	fitColumns:true,
					    	columns:[[
					    		{field : 'productNo',title:'产品编码',halign:'center',width:100},
					    		{field : 'productName',title : '产品名称',halign:'center',width : 160},
							    {field : 'productStandard',title : '产品规格',halign:'center',width : 120}, 
							    {field : 'productNum',title : '产品数量',halign:'center',width : 100},
							    {field : 'unit',title : '计量单位',halign:'center',width : 100},
							    {field : 'price',title : '产品单价',halign:'center',width : 100}
					    	]],
					    	onClickRow:function(index,rowData){
					    		var rowIndex=$('#goods').datagrid('getRowIndex',$('#goods').datagrid('getSelected'));
			                    $('#goods').edatagrid("updateRow",{index:rowIndex,row:rowData});
							}
					    }}},
					    {field : 'productName',title : '产品名称',halign:'center',width : 160,editor:{type:'validatebox',options:{required:true}}},
					    {field : 'productStandard',title : '产品规格',halign:'center',width : 180,editor:{type:'validatebox',options:{required:true}}}, 
					    {field : 'productNum',title : '产品数量',halign:'center',width : 100,editor:{type:'numberbox',options:{precision:2}}},
					    {field : 'unit',title : '计量单位',halign:'center',width : 100,editor:{type:'validatebox',options:{required:true}}},
					    {field : 'price',title : '产品单价',halign:'center',width : 100,editor:{type:'numberbox',options:{precision:2}}}
					]],
					onDblClickCell: function(index,field,value){
						$(this).edatagrid('beginEdit', index);
						var ed = $(this).datagrid('getEditor', {index:index,field:field});
						$(ed.target).focus();
					}
	    });
	});
		function doServlet(){
			var data = "";
			 $.ajax({
		    	url : "${basePath}/stock/InStockServlet?method=insert",
	       		type:'post',  
	       		data: data,
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
		
		function getDetails(){
			var inStockNo = $('#inStockNo').val();
			var stockNo = $('#stockNo').val();
			var inStockDate = $("#inStockDate").datebox("getValue");
			var supplierNo = $('#supplierNo').val();
			var remark = $('#remark').val();
			var rows = $('#goods').datagrid('getRows');
			
			/* var datas = "{ inStockNo:" + inStockNo + ",stockNo:" + stockNo + ",inStockDate:" + inStockDate 
				    + ",supplierNo:" + supplierNo + ",remark:" + remark + ",goodsList:" + rows +"}"; */
					
			var datas = $("#inStockForm").serialize() + "&goodsList=" + rows;
			return datas;
			//$('#goods').edatagrid('saveRow');
			
			 $.ajax({
			    	url : "${basePath}/stock/InStockDetailServlet?method=insert",
		       		type:'post',  
		       		data: rows,
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
	<form id="inStockForm" action="${basePath}/stock/InStockServlet?method=insert" method="post">
	  <table width="100%">
	  	<tr>
	  		<td>入库申请单编号：</td>
	  		<td>
	  			<input type="text" id="inStockNo" name="inStockNo" size="20" />
	  		</td>
	  		<td>入库仓库：</td>
	  		<td>
	  			<select id="stockNo" name="stockNo" onchange="chooseOption(this,'#stockName');" >
	  				<option value="">---请选择---</option>
	  				<option value="0001">一号码头</option>
	  				<option value="0002">葛洲坝</option>
	  			</select>
	  			<input id="stockName" type="hidden" name="stockName"/> 
	  		</td>
	  		<td>入库日期：</td>
	  		<td>
	  			<input id="inStockDate" class="easyui-datebox" type="text" name="inStockDate" size="14"
	  				data-options="currentText:'今日',closeText:'关闭',editable:false"></input>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>供货商：</td>
	  		<td>
	  			<select id="supplierNo" name="supplierNo" onchange="chooseOption(this,'#supplierName');" >
	  				<option value="">---请选择---</option>
	  				<option value="0001">青岛荟英谷</option>
	  				<option value="0002">青岛二啤</option>
	  			</select>
	  			<input id="supplierName" type="hidden" name="supplierName"/> 
	  		</td>
	  		<td>入库总量：</td>
	  		<td>
	  			<input type="text" id="inStockNum" name="inStockNum" size="20" readonly="readonly" value="0" />
	  		</td>
	  		<td>入库总金额：</td>
	  		<td>
	  			<input type="text" id="inStockPrice" name="inStockPrice" size="20" readonly="readonly" value="0"/>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>备注：</td>
	  		<td colspan="5">
	  			<textarea id="remark" name="remark" rows="3" cols="80"></textarea>
	  		</td>
	  	</tr>
	  </table>
	  
	  <table id="goods" class="easyui-edatagrid"  title="产品列表"></table>
  </form>
	
</body>
</html>