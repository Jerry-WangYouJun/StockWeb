<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息展示页面</title>
<!-- 导入使用EASY UI所依赖的CSS文件和JS文件 -->
<link rel="stylesheet" type="text/css"
	href="${basePath}/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${basePath}/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript"
	src="${basePath}/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${basePath}/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${basePath}/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${basePath}/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		//easyUI 数据列表组件  
		$('#data').datagrid({
			//数据列表信息的来源地址 
			url : '${basePath}/admin/UserServlet?method=search',
			rownumbers : true,
			autoRowHeight : true,
			singleSelect : true,
			pagination : true,//分页组件
			nowrap : false,
			toolbar : [ { //工具栏
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					addUser();
				} //当点击当前的菜单时发生的事件响应
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					updateUser();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					deleteUser();
				}
			} ],
			columns : [ [ //控制当前数据列表所显示的列
			{
				field : 'id',
				align : 'center',
				halign : 'center',
				checkbox : true
			}, {
				field : 'userNo',
				title : '用户名',
				halign : 'center',
				width : 80
			}, // userNo 取自后台的key值  json
			{
				field : 'userName',
				title : '用户姓名',
				halign : 'center',
				width : 80
			}, {
				field : 'deptNo',
				title : '所在部门',
				halign : 'center',
				width : 80
			},
			//formatter进行格式化操作
			{
				field : 'sex',
				title : '性别',
				halign : 'center',
				width : 50,
				formatter : function(value, rowData, rowIndex) {
					if (value == "1") {
						return "女";
					} else if (value == "0") {
						return "男";
					} else {
						return "";
					}
				}
			}, {
				field : 'age',
				title : '年龄',
				halign : 'center',
				width : 50
			}, {
				field : 'position',
				title : '职位',
				halign : 'center',
				width : 80
			}, {
				field : 'telphone',
				title : '联系方式',
				halign : 'center',
				width : 100
			}, {
				field : 'email',
				title : '邮箱',
				halign : 'center',
				width : 120
			}, {
				field : 'roleNo',
				title : '所属角色',
				halign : 'center',
				width : 80
			}, {
				field : 'remark',
				title : '备注',
				halign : 'center',
				width : 80
			} ] ]
		});
		
		
		//初始化dialog对话框组件
		$('#dlg-frame').dialog( {
			title : '用户管理',
			width :  700,
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

		initDeptSelect("#search-deptNo");
	});
	//查询JS
	function doSearch() {
		var userNo = $("#search-userNo").val();
		var userName = $("#search-userName").val();
		var position = $("#search-position").val();
		var deptNo = $("#search-deptNo").val();
		//[{key:value,key1:value1}]
		$('#data').datagrid('reload', {
			userNo : userNo,
			userName : userName,
			position : position,
			deptNo : deptNo
		});
	}
	function doClear() {
		$("#search-userNo").val("");
		$("#search-userName").val("");
		$("#search-position").val("");
		$("#search-deptNo").val("");
	}
	
	
	function addUser(){
		var url = "${basePath}/user/user_add.jsp";
		document.getElementById('frameContent').src = url;
		$('#dlg-frame').dialog("open");
	}
	
	function updateUser(){
		var obj = $('#data').datagrid('getSelected');
		//将obj这个对象打印到浏览器控制台  F12
		//console.info(obj);
		if (obj == null || obj.id == null) {
			$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
			return;
		}		
		var path = "${basePath}/admin/UserServlet?method=updateInit&id=" + obj.id;
		document.getElementById('frameContent').src = path;
		$('#dlg-frame').dialog('open');
	}
	
	function deleteUser(){
		var del= confirm("确认删除？");
		if(!del){
			return false;
		}
		//获取datagrid中的某一行 （当前选中的）
		var obj = $('#data').datagrid('getSelected');
		if (obj == null || obj.id == null) {
			$.messager.alert('提示', "请先选中一行(只允许单行操作)", 'error');
			return;
		}	
		var url = "${basePath}/admin/UserServlet?method=delete&id=" + obj.id;
		$.ajax( {
			url : url,
			type : 'post',
			data : {
				id : obj.id
			},
			dataType : 'json',
			success : function(data) {
				$.messager.alert('提示',data.msg);
	       		if(data.success == true) {
	       			doSearch();
		       	}
			},
			error : function(transport) {
				$.messager.alert('提示', "系统产生错误,请联系管理员!", "error");
			}
		});
	}
</script>
</head>
<body class="easyui-layout">

	<div region="north" title="查询条件区" iconCls="icon-search"
		style="height: 80px; padding: 3px;">
		<span>用户名:</span> <input id="search-userNo" name="userNo" /> <span>姓名:</span>
		<input id="search-userName" name="userName" /> <span>所属部门:</span> <select
			id="search-deptNo" name="deptNo">
			<option value="">---请选择---</option>
		</select> <br /> <span>岗位:</span> <input id="search-position" name="position" />
		<a href="#" class="easyui-linkbutton" plain="true"
			iconCls="icon-search" onclick="doSearch()">查询</a> 
			<a href="#"
			class="easyui-linkbutton" plain="true" iconCls="icon-clear"
			onclick="doClear()">清除</a>

	</div>
	<div region="center" title="数据查询区">
		<table id="data" border="false"></table>
	</div>
	<!-- 用于展示dialog对话框 -->
	<div id="dlg-frame">
		<iframe width="99%" height="98%" name="frameContent" id="frameContent"
			frameborder="0"></iframe>
	</div>

</body>
</html>