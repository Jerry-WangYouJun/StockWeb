<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/js/common.js"></script>
	<script type="text/javascript">
		$(function(){
			$('#data-table').datagrid( {
				url : '${basePath}/admin/GoodsServlet?method=search',
				rownumbers : true,
				autoRowHeight : true, 
				singleSelect : true,
				pagination : true,
				nowrap: false,
				toolbar: [{
					text:'添加',
					iconCls: 'icon-add',
					handler: function(){addGoods();}
				},'-',{
					text:'修改',
					iconCls: 'icon-edit',
					handler: function(){updateGoods();}
				},'-',{
					text:'删除',
					iconCls: 'icon-remove',
					handler: function(){deleteGoods();}
				}],
				columns:[[
				    {field : 'id',align : 'center',halign:'center',checkbox : true}, 
				    {field : 'productNo',title : '产品编码',halign:'center',width : 120},
				    {field : 'productName',title : '产品名称',halign:'center',width : 150},
				    {field : 'productTypeName',title : '产品分类',halign:'center',width : 80},
				    {field : 'productStandard',title : '产品规格',halign:'center',width : 80}, 
				    {field : 'supplierNo',title : '供应商',halign:'center',width : 150,formatter:function(value,rowData,rowIndex){
				    	/* if(value){
				    		return "未知"
				    	} */
				    	return value;
				    }},
				    {field : 'productDate',title : '生产日期',halign:'center',width : 80},
				    {field : 'unit',title : '计量单位',halign:'center',width : 80}, 
				    {field : 'price',title : '产品单价',halign:'center',width : 80}, 
				    {field : 'remark',title : '备注',halign:'center',width : 200}
				]]
			});
			
			$('#dlg-frame').dialog( {
				title : '产品管理',
				width :  900,
				height : 400,
				top:50,
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
				}, {
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#dlg-frame').dialog("close");
					}
				} ]
			});
			
			initDictionarySelect("productType", "#search-productTypeCode");
			initSupplierSelect("#search-supplierNo");
		});
		
		function doSearch(){
			var productNo = $("#search-productNo").val();
			var productName = $("#search-productName").val();
			var productTypeCode = $("#search-productTypeCode").val();
			var productStandard = $("#search-productStandard").val();
			var supplierNo = $("#search-supplierNo").val();
			var productDateStart = $("#search-productDateStart").datebox("getValue");
			var productDateEnd = $("#search-productDateEnd").datebox("getValue");
			$('#data-table').datagrid('reload',{
				productNo:productNo,productName:productName,productTypeCode:productTypeCode,
				productStandard:productStandard,supplierNo:supplierNo,productDateStart:productDateStart,
				productDateEnd:productDateEnd
			} );
		}
		function doClear(){
			$("#search-productNo").val("");
			$("#search-productName").val("");
			$("#search-productTypeCode").val("");
			$("#search-productStandard").val("");
			$("#search-supplierNo").val("");
			$("#search-productDateStart").combo("setText","");
			$("#search-productDateStart").combo("setValue","");
			$("#search-productDateEnd").combo("setText","");
			$("#search-productDateEnd").combo("setValue","");
		}
		function addGoods(){
			var path = "${basePath}/goods/goods_add.jsp";
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function updateGoods(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}		
			var path = "${basePath}/admin/GoodsServlet?method=updateInit&id=" + obj.id;
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function deleteGoods(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}	
			var url = "${basePath}/admin/GoodsServlet?method=delete&id=" + obj.id;
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
		<table align="center">
			<tr>
				<td><span>产品编码:</span></td>
				<td><input id="search-productNo" name="productNo"/></td>
				<td><span>产品名称:</span></td>
				<td><input id="search-productName" name="productName"/></td>
				<td><span>供应商:</span></td>
				<td>
					<select id="search-supplierNo" name="supplierNo">
						<option value="">---请选择---</option>
					</select> 
				</td>
				<td>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
				</td>
			</tr>
			<tr>
				<td><span>产品规格:</span></td>
				<td><input id="search-productStandard" name="productStandard"/></td>
				<td><span>产品分类:</span></td>
				<td>
					<select id="search-productTypeCode" name="productTypeCode">
		  				<option value="">---请选择---</option>
		  			</select>
				</td>
				<td><span>生产日期:</span></td>
				<td>
					<input id="search-productDateStart" class="easyui-datebox"  name="productDateStart" size="14" data-options="editable:false"/>-
					<input id="search-productDateEnd" class="easyui-datebox"  name="productDateEnd" size="14" data-options="editable:false"/>
				</td>
				<td>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-clear" onclick="doClear()">清除</a>
				</td>
			</tr>
		</table>
	</div>
	<table id="data-table"  style="height:510px" title="数据列表" width="85%"></table>
	<div id="dlg-frame">
		<iframe width="99%" height="98%" name="frameContent" id="frameContent"
			frameborder="0"></iframe>
	</div>
	</body>
</html>