<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库查询</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/js/datagrid-detailview.js"></script>
	<script type="text/javascript" src="${basePath}/js/common.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$('#data-table').datagrid( {
				view: detailview,
				url : '${basePath}/stock/OutStockServlet?method=search',
				rownumbers : true,
				autoRowHeight : true, 
				singleSelect : true,
				pagination : true,
				nowrap: false,
				toolbar: [{
					text:'添加',
					iconCls: 'icon-add',
					handler: function(){addOutStock();}
				},'-',{
					text:'修改',
					iconCls: 'icon-edit',
					handler: function(){updateOutStock();}
				},'-',{
					text:'删除',
					iconCls: 'icon-remove',
					handler: function(){deleteOutStock();}
				},'-',{
					text:'选择产品',
					iconCls: 'icon-addGoods',
					handler: function(){hangUpGoods();}
				},'-',{
					text:'记账',
					iconCls: 'icon-page-edit',
					handler: function(){confirmOutStock();}
				},'-',{
					text:'详情',
					iconCls: 'icon-detail',
					handler: function(){detailOutStock();}
				}],
				columns:[[
				    {field : 'id',align : 'center',halign:'center',checkbox : true}, 
				    {field : 'outStockNo',title : '出库单号',halign:'center',width : 180},
				    {field : 'stockName',title : '仓库名称',halign:'center',width : 180},
				    {field : 'supplierName',title : '供货供应商名称',halign:'center',width : 180},
				    {field : 'outStockState',title : '出库单状态',halign:'center',width : 80,formatter:function(value,rowData,rowIndex){
				    		if (value == "00") {
								return "创建";
							}else if (value == "02") {
								return "记账"
							} else {
								return value;
							}
				    }}, 
				    {field : 'outStockDate',title : '出库日期',halign:'center',width : 100},
				    {field : 'remark',title : '备注',halign:'center',width : 200}
				]],
				detailFormatter: function(rowIndex, rowData){
					return '<div style="padding:2px"><table id="goodsGrid-' + rowIndex + '"></table></div>';  
				},
				onExpandRow:function(rowIndex, rowData){
					$('#goodsGrid-' + rowIndex).datagrid( {
						url : '${basePath}/stock/OutStockDetailServlet?method=search',
						queryParams:{
							outStockId:rowData.id
						},
						autoRowHeight : true, 
						singleSelect : true,
						nowrap: false,
						checkOnSelect:false,
						columns:[[
								    {field : 'productNo',title : '产品编号',halign:'center',width : 180},
								    {field : 'productName',title : '产品名称',halign:'center',width : 120},
								    {field : 'productStandard',title : '产品规格',halign:'center',width : 180}, 
								    {field : 'productNum',title : '产品数量',halign:'center',width : 100 ,formatter:function(value,rowData,index){
								    	if(isNaN(value)){
								    		return value;
								    	}else{
								    		return formatNum(value,2);
								    	}
								    	
								    }},
								    {field : 'unit',title : '计量单位',halign:'center',width : 80},
								    {field : 'price',title : '产品单价',halign:'center',width : 100,formatter:function(value,rowData,index){
								    	if(isNaN(value)){
								    		return value;
								    	}else{
								    		return formatNum(value,2);
								    	}
								    }},
								    {field : 'totalPrice',title : '产品总价',halign:'center',width : 180,formatter:function(value,rowData,index){
								    	if(isNaN(value)){
								    		return value;
								    	}else{
								    		return formatNum(value,2);
								    	}
								    }},
								]],
						onLoadSuccess:function(){
							$('#goodsGrid-' + rowIndex).datagrid("appendRow",{
								productNo:'<span class="subtotal">合计：</span>',
								productNum:'<span class="subtotal">' + getTotal("goodsGrid-" + rowIndex,"productNum") + '</span>',
								totalPrice:'<span class="subtotal">' + getTotal("goodsGrid-" + rowIndex,"totalPrice")  + '</span>',
							});
						},
						onSelect:function(rowIndex, rowData){
							$('#data-table').datagrid("unselectAll");
						}
					});
				}
			});
			
			$('#dlg-frame').dialog( {
				title : '出库管理',
				width :  900,
				height : 520,
				top:20,
				left:100,
				closed : true,
				cache : false,
				modal : true,
				buttons : [ {
					text : '确定',
					iconCls : 'icon-ok',
					handler : function() {
						if (confirm("确定执行下一步操作？")) {
							frameContent.window.doServlet();
						}
					}
				},{
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#dlg-frame').dialog("close");
					}
				} ]
			});
			$('#dlg-detailFrame').dialog( {
				title : '出库管理',
				width :  900,
				height : 520,
				top:20,
				left:100,
				closed : true,
				cache : false,
				modal : true
			});
			
			//初始化下拉框
			
			//出库状态
			initDictionarySelect("stockState","#search-outStockState");
			//仓库
			initStockSelect("#search-stockId");
			//供应商
			initSupplierSelect("#search-supplierId");
			
		});
		
		function doSearch(){
			var outStockNo = $("#search-outStockNo").val();
			var stockId = $("#search-stockId").val();
			var supplierId = $("#search-supplierId").val();
			var outStockState = $("#search-outStockState").val();
			var outStockDateStart = $("#search-outStockDateStart").datebox("getValue");
			var outStockDateEnd = $("#search-outStockDateEnd").datebox("getValue");
			$('#data-table').datagrid('reload',{
				outStockNo:outStockNo,stockId:stockId,supplierId:supplierId,outStockState:outStockState,
				outStockDateStart:outStockDateStart,outStockDateEnd:outStockDateEnd
			} );
		}
		function doClear(){
			$("#search-outStockNo").val("");
			$("#search-stockId").val("");
			$("#search-supplierId").val("");
			$("#search-outStockState").val("");
			$("#search-outStockDateStart").combo("setText","");
			$("#search-outStockDateStart").combo("setValue","");
			$("#search-outStockDateEnd").combo("setText","");
			$("#search-outStockDateEnd").combo("setValue","");
		}
		function addOutStock(){
			var path = "${basePath}/outstock/outstock_add.jsp";
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function updateOutStock(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}		
			if(obj.outStockState != "00"){
				$.messager.alert('提示', "当前选中的出库申请单状态不是【创建状态】,无法进行[修改]操作！", 'error');
				return;
			}
			var path = "${basePath}/stock/OutStockServlet?method=updateInit&id=" + obj.id;
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function deleteOutStock(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}	
			if(obj.outStockState != "00"){
				$.messager.alert('提示', "当前选中的出库申请单状态不是【创建状态】,无法进行[删除]操作！", 'error');
				return;
			}
			$.messager.confirm("操作提示", "确定要进行删除操作么?", function(data){
				if(data){
					var url = "${basePath}/stock/OutStockServlet?method=delete";
					$.ajax( {
						url : url,
						type : 'post',
						data : {
							id : obj.id
						},
						dataType : 'json',
						success : function(data) {
							$.messager.alert('提示', data.msg);
							doSearch();
						},
						error : function(transport) {
							$.messager.alert('提示', "系统产生错误,请联系管理员!", "error");
						}
					});
				}
			});
		}
		
		function hangUpGoods(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}
			if(obj.outStockState != "00"){
				$.messager.alert('提示', "当前选中的出库申请单状态不是【创建状态】,无法进行[选择产品]操作！", 'error');
				return;
			}
			var path = "${basePath}/stock/OutStockServlet?method=hang&id=" + obj.id;
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function detailOutStock(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}
			var path = "${basePath}/stock/OutStockServlet?method=detail&id=" + obj.id;
			document.getElementById('frameDetailContent').src = path;
			$('#dlg-detailFrame').dialog('open');
		}
		function confirmOutStock(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}	
			if(obj.outStockState != "00"){
				$.messager.alert('提示', "当前选中的出库申请单状态不是【创建状态】,无法进行[记账]操作！", 'error');
				return;
			}
			var url = "${basePath}/stock/OutStockServlet?method=confirm";
			$.ajax( {
				url : url,
				type : 'post',
				data : {
					id : obj.id
				},
				dataType : 'json',
				success : function(data) {
					$.messager.alert('提示', data.msg);
					doSearch();
				},
				error : function(transport) {
					$.messager.alert('提示', "系统产生错误,请联系管理员!", "error");
				}
			});
		}
		
	</script>

</head>
<body>
	<div id="tb" title="查询条件区" class="easyui-panel"  style="padding:3px;width:85%" iconCls="icon-search">
		<span>出库单号:</span>
		<input id="search-outStockNo" name="outStockNo"/>
		<span>所在仓库:</span>
		<select id="search-stockId" name="stockId">
			<option value="">---请选择---</option>
		</select> 
		<span>供货供应商:</span>
		<select id="search-supplierId" name=supplierId>
			<option value="">---请选择---</option>
		</select> 
		<span>出库单状态:</span>
		<select id="search-outStockState" name="outStockState">
			<option value="">---请选择---</option>
		</select>
		<span>出库日期:</span>
		<input id="search-outStockDateStart" class="easyui-datebox"  name="outStockDateStart" data-options="editable:false"/>-
		<input id="search-outStockDateEnd" class="easyui-datebox"  name="outStockDateEnd" data-options="editable:false"/>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-clear" onclick="doClear()">清除</a>
	</div>
	<table id="data-table" style="height:510px" title="数据列表" width="85%"></table>
	<div id="dlg-frame">
		<iframe width="99%" height="98%" name="frameContent" id="frameContent"
			frameborder="0"></iframe>
	</div>
	<div id="dlg-detailFrame">
		<iframe width="99%" height="98%" name="frameDetailContent" id="frameDetailContent"
			frameborder="0"></iframe>
	</div>
	
</body>
</html>