<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门管理</title>
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		$(function(){
			$('#data-table').datagrid( {
				url : '${basePath}/admin/DeptServlet?method=search',
				rownumbers : true,
				autoRowHeight : true, 
				singleSelect : true,
				pagination : true,
				nowrap: false,
				toolbar: [{
					text:'添加',
					iconCls: 'icon-add',
					handler: function(){addDept();}
				},'-',{
					text:'修改',
					iconCls: 'icon-edit',
					handler: function(){updateDept();}
				},'-',{
					text:'删除',
					iconCls: 'icon-remove',
					handler: function(){deleteDept();}
				}],
				columns:[[
				    {field : 'id',align : 'center',halign:'center',checkbox : true}, 
				    {field : 'deptNo',title : '部门编号',halign:'center',width : 120},
				    {field : 'deptName',title : '部门名称',halign:'center',width : 150},
				    {field : 'deptLeader',title : '部门领导',halign:'center',width : 80},
				    {field : 'deptTel',title : '部门联系方式',halign:'center',width : 80}, 
				    {field : 'parentDeptNo',title : '上级部门',halign:'center',width : 150,formatter:function(value,rowData,rowIndex){
				    	if(value){
				    		return "未知"
				    	}
				    }},
				    {field : 'deptDesc',title : '部门描述',halign:'center',width : 200}
				]]
			});
			
			$('#dlg-frame').dialog( {
				title : '部门管理',
				width :  700,
				height : 400,
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
			var deptNo = $("#search-deptNo").val();
			var deptName = $("#search-deptName").val();
			var parentDeptNo = $("#search-parentDeptNo").val();
			$('#data-table').datagrid('reload',{
				deptNo:deptNo,deptName:deptName,parentDeptNo:parentDeptNo
			} );
		}
		function doClear(){
			$("#search-deptNo").val("");
			$("#search-deptName").val("");
			$("#search-parentDeptNo").val("");
		}
		function addDept(){
			var path = "${basePath}/dept/dept_add.jsp";
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function updateDept(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}		
			var path = "${basePath}/admin/DeptServlet?method=updateInit&id=" + obj.id;
			document.getElementById('frameContent').src = path;
			$('#dlg-frame').dialog('open');
		}
		
		function deleteDept(){
			var obj = $('#data-table').datagrid('getSelected');
			if (obj == null || obj.id == null) {
				$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
				return;
			}	
			var url = "${basePath}/admin/DeptServlet?method=delete&id=" + obj.id;
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
				<td><span>部门编号:</span></td>
				<td><input id="search-deptNo" name="deptNo"/></td>
				<td><span>部门名称:</span></td>
				<td><input id="search-deptName" name="deptName"/></td>
				<td><span>上级部门:</span></td>
				<td>
					<input id="search-deptLearder" name="deptLearder"/>
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