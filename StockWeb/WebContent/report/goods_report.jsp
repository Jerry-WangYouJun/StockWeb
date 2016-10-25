<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>台账查询</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/js/common.js"></script>
	<script type="text/javascript" src="${basePath}/js/datagrid-detailview.js"></script>
	<script type="text/javascript">
	$(function(){
		var curDate = getCurDate();
		$("#search-startDate").combo("setText",curDate);
		$("#search-startDate").combo("setValue",curDate);
		$("#search-endDate").combo("setText",curDate);
		$("#search-endDate").combo("setValue",curDate);
		$('#data-table').datagrid( {
			url : '${basePath}/report/ReportServlet?method=searchByGoods',
			queryParams:{
				startDate:curDate,
				endDate:curDate
			},
			rownumbers : true,
			autoRowHeight : true, 
			singleSelect : true,
			nowrap: false,
			toolbar: [{
				text:'导出到excel',
				iconCls: 'icon-page-excel',
				handler: function(){exportGoods();}
			}],
			columns:[[
			    {field : 'operateDate',title : '结算日期',halign:'center',width : 100},
			    {field : 'productNo',title : '物料编码',halign:'center',width : 120},
			    {field : 'productName',title : '物料名称',halign:'center',width : 130},
			    {field : 'productStandard',title : '物料规格',halign:'center',width : 100},
			    {field : 'startNum',title : '期初结存<br/>库存量',halign:'center',width :100,formatter:function(value,rowData,index){
			    	if(isNaN(value)){
			    		return value;
			    	}else{
			    		return formatNum(value,2);
			    	}
			    }},
			    {field : 'startTotal',title : '期初结存<br/>库存金额',halign:'center',width : 120,formatter:function(value,rowData,index){
			    	if(isNaN(value)){
			    		return value;
			    	}else{
			    		return formatNum(value,2);
			    	}
			    }},
			    {field : 'endNum',title : '期末结存<br/>库存量',halign:'center',width : 100,formatter:function(value,rowData,index){
			    	if(isNaN(value)){
			    		return value;
			    	}else{
			    		return formatNum(value,2);
			    	}
			    }},
			    {field : 'endTotal',title : '期末结存<br/>库存金额',halign:'center',width : 120,formatter:function(value,rowData,index){
			    	if(isNaN(value)){
			    		return value;
			    	}else{
			    		return formatNum(value,2);
			    	}
			    }}
			]],
			view: detailview,
			detailFormatter: function(rowIndex, rowData){
				return '<div style="padding:2px"><table id="goodsDetail-' + rowIndex + '"></table></div>';  
			},
			onExpandRow:function(rowIndex, rowData){
				$('#goodsDetail-' + rowIndex).datagrid( {
					url : '${basePath}/report/ReportServlet?method=searchByGoodsDetail',
					queryParams:{
						productNo:rowData.productNo,
						startDate:$("#search-startDate").datebox("getValue"),
						endDate:$("#search-endDate").datebox("getValue")
					},
					autoRowHeight : true, 
					singleSelect : true,
					nowrap: false,
					checkOnSelect:false,
					columns:[[
							    {field : 'operateDate',title : '操作日期',halign:'center',width : 100},
							    {field : 'productNo',title : '物料编码',halign:'center',width : 120},
							    {field : 'productName',title : '物料名称',halign:'center',width : 130},
							    {field : 'productStandard',title : '物料规格',halign:'center',width : 100},
							    {field : 'inNum',title : '产品<br/>入库量',halign:'center',width : 100,formatter:function(value,rowData,index){
							    	if(isNaN(value)){
							    		return value;
							    	}else{
							    		return formatNum(value,2);
							    	}
							    }},	
							    {field : 'inTotal',title : '产品<br/>入库金额',halign:'center',width :110,formatter:function(value,rowData,index){
							    	if(isNaN(value)){
							    		return value;
							    	}else{
							    		return formatNum(value,2);
							    	}
							    }},
							    {field : 'outNum',title : '产品<br/>出库量',halign:'center',width : 100,formatter:function(value,rowData,index){
							    	if(isNaN(value)){
							    		return value;
							    	}else{
							    		return formatNum(value,2);
							    	}
							    }},
							    {field : 'outTotal',title : '产品<br/>出库金额',halign:'center',width : 110,formatter:function(value,rowData,index){
							    	if(isNaN(value)){
							    		return value;
							    	}else{
							    		return formatNum(value,2);
							    	}
							    }}
							]],
					onLoadSuccess:function(){
						$('#goodsDetail-' + rowIndex).datagrid("appendRow",{
							operateDate:'<span class="subtotal">合计：</span>',
							inNum:'<span class="subtotal">' + getTotal("goodsDetail-" + rowIndex,"inNum") + '</span>',
							inTotal:'<span class="subtotal">' + getTotal("goodsDetail-" + rowIndex,"inTotal")  + '</span>',
							outNum:'<span class="subtotal">' + getTotal("goodsDetail-" + rowIndex,"outNum") + '</span>',
							outTotal:'<span class="subtotal">' + getTotal("goodsDetail-" + rowIndex,"outTotal")  + '</span>',
						});
					},
					onSelect:function(rowIndex, rowData){
						$('#data-table').datagrid("unselectAll");
					}
				});
			}
		});
	});
	
	function doSearch(){
		var startDate = $("#search-startDate").datebox("getValue");
		var endDate = $("#search-endDate").datebox("getValue");
		if(startDate == "" || endDate == "") {
			$.messager.alert("提示","结算开始时间和结束时间不能为空!","error");
			return;
		} 
		var productNo = $("#search-productNo").val();
		$('#data-table').datagrid('reload',{
			startDate:startDate,endDate:endDate,productNo:productNo
		} );
	}
	function doClear(){
		$("#search-startDate").combo("setText","");
		$("#search-startDate").combo("setValue","");
		$("#search-endDate").combo("setText","");
		$("#search-endDate").combo("setValue","");
		$("#search-productNo").val("");
	}
	
	function exportGoods(){
		alert("导出到EXCEL，此功能暂时未开放！");
	}
	
	</script>
</head>
<body>
	<div id="tb" title="查询条件区" class="easyui-panel"  style="padding:3px;width:85%" iconCls="icon-search">
		<span> 台账开始日期:</span>
		<input class="easyui-datebox" type="text" id="search-startDate" name="startDate"  size="14"
	  				data-options="currentText:'今日',closeText:'关闭',editable:false"></input>
		
		<span>台账结束日期:</span>
		<input class="easyui-datebox" type="text" id="search-endDate" name="endDate"  size="14"
	  				data-options="currentText:'今日',closeText:'关闭',editable:false"></input>
		
		<span>物料编码:</span>
		<input id="search-productNo" name="productNo"/>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-clear" onclick="doClear()">清除</a>
	</div>
	<table id="data-table"   style="height:510px" title="数据列表" width="85%"></table>
</body>
</html>