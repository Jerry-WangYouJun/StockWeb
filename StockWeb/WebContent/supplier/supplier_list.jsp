<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商管理</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${basePath}/js/common.js"></script>
	<script type="text/javascript">
		$(function(){
			$('#data-table').datagrid( {
				url : '${basePath}/admin/SupplierServlet?method=search',
				rownumbers : true,
				autoRowHeight : true, 
				singleSelect : true,
				pagination : true,
				nowrap: false,
				toolbar: [{
					text:'添加',
					iconCls: 'icon-add',
					handler: function(){addSupplier();}
				},'-',{
					text:'修改',
					iconCls: 'icon-edit',
					handler: function(){updateSupplier();}
				},'-',{
					text:'删除',
					iconCls: 'icon-remove',
					handler: function(){deleteSupplier();}
				}],
				columns:[[
				    {field : 'id',align : 'center',halign:'center',checkbox : true}, 
				    {field : 'supplierNo',title : '供应商编号',halign:'center',width : 80},
				    {field : 'supplierName',title : '供应商名称',halign:'center',width : 80},
				    {field : 'provinceName',title : '所在地',halign:'center',width : 80},
				    {field : 'supplierAddress',title : '详细地址',halign:'center',width : 180}, 
				    {field : 'supplierEmail',title : '邮箱',halign:'center',width : 120},
				    {field : 'supplierTel',title : '供应商联系电话',halign:'center',width : 80},
				    {field : 'supplierTax',title : '供应商传真',halign:'center',width : 100},
				    {field : 'userName',title : '联系人',halign:'center',width : 120}, 
				    {field : 'userTel',title : '联系人电话',halign:'center',width : 80}, 
				    {field : 'remark',title : '备注',halign:'center',width : 200}
				]]
			});
			
			$('#dlg-frame').dialog( {
				title : '供应商管理',
				width :  900,
				height : 320,
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
			
			initDictionarySelect("province", "#search-provinceCode");
		});
		
		function doSearch(){
			var supplierNo = $("#search-supplierNo").val();
			var supplierName = $("#search-supplierName").val();
			var provinceCode = $("#search-provinceCode").val();
			var userName = $("#search-userName").val();
			$('#data-table').datagrid('reload',{
				supplierNo:supplierNo,supplierName:supplierName,provinceCode:provinceCode,userName:userName
			} );
		}
		function doClear(){
			$("#search-supplierNo").val("");
			$("#search-supplierName").val("");
			$("#search-provinceCode").val("");
			$("#search-userName").val("");
		}
		function addSupplier(){
			var path = "${basePath}/supplier/supplier_add.jsp";
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function updateSupplier(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}		
			var path = "${basePath}/admin/SupplierServlet?method=updateInit&id=" + obj.id;
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function deleteSupplier(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}	
			var url = "${basePath}/admin/SupplierServlet?method=delete&id=" + obj.id;
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
	<div id="tb" title="查询条件区" class="easyui-panel" style="padding:3px;width:85%" iconCls="icon-search">
		<span>供应商编号:</span>
		<input id="search-supplierNo" name="supplierNo"/>
		<span>供应商名称:</span>
		<input id="search-supplierName" name="supplierName"/>
		<span>所在地:</span>
		<select id="search-provinceCode" name="provinceCode">
			<option value="">---请选择---</option>
		</select> 
		<span>联系人:</span>
		<input id="search-userName" name="userName"/>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-clear" onclick="doClear()">清除</a>
	</div>
	<table id="data-table" style="height:510px" title="数据列表" width="85%"></table>
	<div id="dlg-frame">
		<iframe width="99%" height="98%" name="frameContent" id="frameContent"
			frameborder="0"></iframe>
	</div>
	
</body>
</html>