<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典管理</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		$(function(){
			$('#data-table').datagrid( {
				url : '${basePath}/admin/DictionaryServlet?method=search',
				rownumbers : true,
				autoRowHeight : true, 
				singleSelect : true,
				pagination : true,
				nowrap: false,
				toolbar: [{
					text:'添加',
					iconCls: 'icon-add',
					handler: function(){addDictionary();}
				},'-',{
					text:'修改',
					iconCls: 'icon-edit',
					handler: function(){updateDictionary();}
				},'-',{
					text:'删除',
					iconCls: 'icon-remove',
					handler: function(){deleteDictionary();}
				}],
				columns:[[
				    {field : 'id',align : 'center',halign:'center',checkbox : true}, 
				    {field : 'dicId',title : '字典类型ID',halign:'center',width : 120},
				    {field : 'dicType',title : '字典类型名称',halign:'center',width : 150},
				    {field : 'dicCode',title : '字典项编码',halign:'center',width : 80},
				    {field : 'dicValue',title : '字典项值',halign:'center',width : 80}, 
				    {field : 'dicSort',title : '排序',halign:'center',width : 80}, 
				    {field : 'validateFlag',title : '是否生效',halign:'center',width : 150,formatter:function(value,rowData,rowIndex){
				    	if(value == "0"){
				    		return "生效"
				    	}else if(value == "1"){
				    		return "失效"
				    	}
				    }},
				    {field : 'remark',title : '备注',halign:'center',width : 200}
				]]
			});
			
			$('#dlg-frame').dialog( {
				title : '字典管理',
				width :  600,
				height : 250,
				top:50,
				left:200,
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
			
		});
		
		function doSearch(){
			var dicType = $("#search-dicType").val();
			var dicCode = $("#search-dicCode").val();
			var dicValue = $("#search-dicValue").val();
			$('#data-table').datagrid('reload',{
				dicType:dicType,dicCode:dicCode,dicValue:dicValue
			} );
		}
		function doClear(){
			$("#search-dicType").val("");
			$("#search-dicCode").val("");
			$("#search-dicValue").val("");
		}
		function addDictionary(){
			var path = "${basePath}/dic/dictionary_add.jsp";
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function updateDictionary(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}		
			var path = "${basePath}/admin/DictionaryServlet?method=updateInit&id=" + obj.id;
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function deleteDictionary(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}	
			var url = "${basePath}/admin/DictionaryServlet?method=delete&id=" + obj.id;
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
				<td><span>字典类型名称:</span></td>
				<td><input id="search-dicType" name="dicType"/></td>
				<td><span>字典项编码:</span></td>
				<td><input id="search-dicCode" name="dicCode"/></td>
				<td><span>字典项值:</span></td>
				<td>
					<input id="search-dicValue" name="dicValue"/>
				</td>
				<td>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-clear" onclick="doClear()">清除</a>
				</td>
			</tr>
		</table>
	</div>
	<table id="data-table" style="height:510px" title="数据列表" width="85%"></table>
	<div id="dlg-frame">
		<iframe width="99%" height="98%" name="frameContent" id="frameContent"
			frameborder="0"></iframe>
	</div>
	</body>
</html>