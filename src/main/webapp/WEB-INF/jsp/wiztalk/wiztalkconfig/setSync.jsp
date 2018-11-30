<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<style type="text/css">
		  input{
		    width: 500px;
		    color: #FFF
		  }
		  td {
			width: 120px;
			}
		  
		</style>
<script type="text/javascript">
	
	
	//保存
	function save(){

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="wiztalkconfig/saveSynconfig.do" name="Form" id="Form" method="post">
		
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			
			<tr>
				<td style="width:150px;text-align: right;padding-top: 13px;">Easc tenantId:</td>
				<td><input type="text" name="tenantId"  value="${eascParam.tenantId}" maxlength="200"  title="租户ID"/></td>
			</tr>
			<tr>
				<td style="width:150px;text-align: right;padding-top: 13px;">Easc appID:</td>
				<td style="color:#fff;"><input type="text" name="appId" value="${eascParam.appId}" maxlength="200"  title="应用名"/></td>
			</tr>
			
		 	<tr>
				<td style="width:150px;text-align: right;padding-top: 13px;">Easc domain:</td>
				<td><input type="text" size="400" name="domain" value="${eascParam.domain}" maxlength="200"  title="接口地址"/></td>
			</tr>
			<tr>
				<td style="width:150px;text-align: right;padding-top: 13px;">Easc Port:</td>
				<td><input type="text" name="port"  value="${eascParam.port}" maxlength="200"  title="端口号"/></td>
			</tr>
			<tr>
				<td style="width:150px;text-align: right;padding-top: 13px;">Easc user:</td>
				<td><input type="text" name="userName"  value="${eascParam.userName}" maxlength="200"  title="用户名"/></td>
			</tr>
			<tr>
				<td style="width:150px;text-align: right;padding-top: 13px;">Easc Pwd:</td>
				<td><input type="text" name="eascPwd"  value="${eascParam.pwd}" maxlength="200"  title="密码"/></td>
			</tr> 
			<tr>
				<td style="width:150px;text-align: right;padding-top: 13px;">Easc Uid:</td>
				<td><input type="text" name="userId"  value="${eascParam.userId}" maxlength="200"  title="用户ID"/></td>
			</tr>
			
		
			
			
			
			
			
			
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>