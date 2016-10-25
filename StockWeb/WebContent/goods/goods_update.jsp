<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品修改页面</title>
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
	function doServlet(){
		$.ajax({
	    	url : "${basePath}/admin/GoodsServlet?method=update",
       		type:'post',  
       		data: $("#goodsForm").serialize(),
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
	
	function chooseOption(obj){
		var optionValue = $(obj).find("option:selected").val();
		var optionText = $(obj).find("option:selected").text();
		if(optionValue == ""){
			$("#productTypeName").val("");
		}else{
			$("#productTypeName").val(optionText);
		}
		
	}
	
	$(function(){
		initDictionarySelect("productType", "#productTypeCode",$("#productTypeCodeDB").val());
		initSupplierSelect("#supplierNo",$("#supplierNoDB").val());
	});
</script>
</head>
<body>
	<form id="goodsForm" action="${basePath}/admin/GoodsServlet?method=update" method="post">
	  <table width="100%">
	  	<tr>
	  		<td>产品编码：</td>
	  		<td>
	  			<input type="hidden" id="id" name="id" value="${requestScope.goods.id }" />
	  			<input type="text" name="productNo" size="14" value="${requestScope.goods.productNo }"/>
	  		</td>
	  		<td>产品分类：</td>
	  		<td>
	  			<select id="productTypeCode" name="productTypeCode">
	  				<option value="">---请选择---</option>
	  			</select>
	  			<input type="hidden" id="productTypeCodeDB" value="${requestScope.goods.productTypeCode }"/>
	  		</td>
	  		<td>产品供货商：</td>
	  		<td>
	  			<select id="supplierNo" name="supplierNo" >
	  				<option value="">---请选择---</option>
	  			</select>
	  			<input type="hidden" id="supplierNoDB" value="${requestScope.goods.supplierNo }"/>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>产品名称：</td>
	  		<td>
	  			<input type="text" name="productName" size="14"  value="${requestScope.goods.productName }"/>
	  		</td>
	  		<td>产品规格：</td>
	  		<td>
	  			<input type="text" id="productStandard" name="productStandard" size="14" value="${requestScope.goods.productStandard }" />
	  		</td>
	  		<td>生产日期：</td>
	  		<td>
	  			<input class="easyui-datebox" type="text" name="productDate"  size="14" value="${requestScope.goods.productDate }"
	  				data-options="currentText:'今日',closeText:'关闭',editable:false"></input>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td>产品数量：</td>
	  		<td>
	  			<input type="text" name="productNum" size="14" value="${requestScope.goods.productNum }"></input>
	  		</td>
	  		<td>计量单位：</td>
	  		<td>
	  			<input type="text" name="unit" size="14" value="${requestScope.goods.unit }"></input>
	  		</td>
	  		<td>产品单价：</td>
	  		<td>
	  			<input type="text" name="price" size="14" value="${requestScope.goods.price }"></input>
	  		</td>
	  	</tr>
	  	<tr>
	  		<td valign="top" colspan="">备注：</td>
			<td colspan="5">
				<textarea rows="2" cols="60" name="remark">${requestScope.goods.remark }</textarea>
			</td>
	  	</tr>
	  </table>
  </form>
</body>
</html>