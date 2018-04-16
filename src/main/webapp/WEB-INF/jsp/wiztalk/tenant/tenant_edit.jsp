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
		if ($("#TENANT_ID").val() != "") {
			$("#NAME").attr("readonly", "readonly");
			$("#NAME").css("color", "gray");
		}
	});

	//保存
	function save() {
		if ($("#NAME").val() == "") {
			$("#NAME").tips({
				side : 3,
				msg : '请输入机构名称',
				bg : '#AE81FF',
				time : 2
			});
			$("#NAME").focus();
			return false;
		}
		if ($("#CODE").val() == "") {
			$("#CODE").tips({
				side : 3,
				msg : '请输入代码',
				bg : '#AE81FF',
				time : 2
			});
			$("#CODE").focus();
			return false;
		}
		if ($("#STATUS").val() == "") {
			$("#STATUS").tips({
				side : 3,
				msg : '请输入状态',
				bg : '#AE81FF',
				time : 2
			});
			$("#STATUS").focus();
			return false;
		}
		/* $("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show(); */
		if($("#TENANT_ID").val()==""){
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
			url: '<%=basePath%>tenant/hasU.do',
	    	data: {NAME:NAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					 $("#NAME").css("background-color","#FF0000");
						setTimeout("$('#NAME').val('此用户名已存在!')",200);
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
	<form action="tenant/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="TENANT_ID" id="TENANT_ID"
			value="${pd.TENANT_ID}" />
		<div id="zhongxin">
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">机构名称:</td>
					<td><input type="text" name="NAME" id="NAME"
						value="${pd.NAME}" maxlength="32" placeholder="这里输入机构名称"
						title="机构名称" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">代码:</td>
					<td><input type="text" name="CODE" id="CODE"
						value="${pd.CODE}" maxlength="32" placeholder="这里输入代码" title="代码" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">状态:</td>
					<td><input type="number" name="STATUS" id="STATUS"
						value="${pd.STATUS}" maxlength="32" placeholder="这里输入状态"
						title="状态" /></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="10"><a
						class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
						class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br />
			<br />
			<br />
			<br />
			<br />
			<img src="static/images/jiazai.gif" /><br />
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