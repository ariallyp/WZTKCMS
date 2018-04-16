<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title></title>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!-- 下拉框 -->
<link rel="stylesheet" href="static/css/chosen.css" />

<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />

<link rel="stylesheet" href="static/css/datepicker.css" />
<!-- 日期框 -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		if ($("#ORGANIZATION_ID").val() != "") {
			$("#NAME").attr("readonly", "readonly");
			$("#NAME").css("color", "gray");
		}
	
		
		
	});

	//保存
	function save() {
		if ($("#NAME").val() == "") {
			$("#NAME").tips({
				side : 3,
				msg : '请输入名称',
				bg : '#AE81FF',
				time : 2
			});
			$("#NAME").focus();
			return false;
		}
		if ($("#SHORT_NAME").val() == "") {
			$("#SHORT_NAME").tips({
				side : 3,
				msg : '请输入简称',
				bg : '#AE81FF',
				time : 2
			});
			$("#SHORT_NAME").focus();
			return false;
		}
		/* if($("#PARENT_ID").val()==""){
			$("#PARENT_ID").tips({
				side:3,
		        msg:'请输入父类',
		        bg:'#AE81FF',
		        time:2
		    });
			$("#PARENT_ID").focus();
			return false;
		} */
		if ($("#LOCATION").val() == "") {
			$("#LOCATION").tips({
				side : 3,
				msg : '请输入位置',
				bg : '#AE81FF',
				time : 2
			});
			$("#LOCATION").focus();
			return false;
		}
		if ($("#TENANT_ID").val() == "") {
			$("#TENANT_ID").tips({
				side : 3,
				msg : '请输入租户',
				bg : '#AE81FF',
				time : 2
			});
			$("#TENANT_ID").focus();
			return false;
		}

		if ($("#SORT").val() == "") {
			$("#SORT").tips({
				side : 3,
				msg : '请输入排序',
				bg : '#AE81FF',
				time : 2
			});
			$("#SORT").focus();
			return false;
		}
/* 
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show(); */ 
		
		if($("#ORGANIZATION_ID").val()==""){
			hasU();
		}else{
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
		//判断用户名是否存在
		
		
	function hasU(){
			var NAME = $("#NAME").val();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>organization/hasU.do',
		    	data: {NAME:NAME,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
						$("#Form").submit();
						$("#zhongxin").hide();
						$("#zhongxin2").show();
					 }else{
						$("#NAME").css("background-color","#D16E6C");
						setTimeout("$('#NAME').val('机构名已存在!')",500);
						$("#NAME").tips({
							side : 3,
							msg : '此用户名已存在',
							bg : '#AE81FF',
							time : 5
						});
					 }
				}
			});
		}
</script>
</head>
<body>
	<form action="organization/${msg }.do" name="Form" id="Form"
		method="post">
		<input type="hidden" name="ORGANIZATION_ID" id="ORGANIZATION_ID"
			value="${pd.ORGANIZATION_ID}" />
		<div id="zhongxin">
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">租户:</td>
					<td><select class="chzn-select" name="TENANT_ID" id="TENANT_ID"
						data-placeholder="请选择租户" style="vertical-align: top;" title="租户">
							<option value=""></option>
							<c:forEach items="${rentList}" var="rent">
								<option value="${rent.TENANT_ID }"
									<c:if test="${pd.TENANT_ID==rent.TENANT_ID}">selected</c:if>>${rent.TENANT_NAME }</option>

							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">父类:</td>
					<td><select class="chzn-select" name="PARENT_ID"
						id="PARENT_ID" data-placeholder="请选择父类"
						style="vertical-align: top;" title="父类">
							<option value=""></option>
							<option value="">无</option>
							<c:forEach items="${varList}" var="org">
								<option value="${org.ORGANIZATION_ID }"
									<c:if test="${pd.PARENT_ID==org.ORGANIZATION_ID}">selected</c:if>>${org.ORG_NAME }</option>

							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">名称:</td>
					<td><input type="text" name="ORG_NAME" id="NAME"
						value="${pd.ORG_NAME}" maxlength="32" placeholder="这里输入名称"
						title="名称" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">简称:</td>
					<td><input type="text" name="SHORT_NAME" id="SHORT_NAME"
						value="${pd.SHORT_NAME}" maxlength="32" placeholder="这里输入简称"
						title="简称" /></td>
				</tr>

				<%-- <tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">位置:</td>
				<td><input type="text" name="LOCATION" id="LOCATION" value="${pd.LOCATION}" maxlength="32" placeholder="这里输入位置" title="位置"/></td>
			</tr>
			<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">排序:</td>
					<td><input type="number" name="SORT" id="SORT"
						value="${pd.SORT}" maxlength="32" placeholder="这里输入排序" title="排序" /></td>
			</tr> --%>

				<tr>
					<td style="text-align: center;" colspan="10"><a
						class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
						class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <br /> <img
				src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>

	</form>


	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript">
		$(top.hangge());
		$(function() {

			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();

		});
	</script>
</body>
</html>