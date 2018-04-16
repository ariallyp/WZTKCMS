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
		
<script type="text/javascript">
	
	
	//保存
	function save(){
			if($("#UID").val()==""){
			$("#UID").tips({
				side:3,
	            msg:'请输入用户ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#UID").focus();
			return false;
		}
		if($("#UNAME").val()==""){
			$("#UNAME").tips({
				side:3,
	            msg:'请输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#UNAME").focus();
			return false;
		}
		if($("#DEVICE_NAME").val()==""){
			$("#DEVICE_NAME").tips({
				side:3,
	            msg:'请输入设备名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#DEVICE_NAME").focus();
			return false;
		}
		if($("#OS_VERSION").val()==""){
			$("#OS_VERSION").tips({
				side:3,
	            msg:'请输入系统版本',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#OS_VERSION").focus();
			return false;
		}
		if($("#APP_VERSION").val()==""){
			$("#APP_VERSION").tips({
				side:3,
	            msg:'请输入应用版本',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#APP_VERSION").focus();
			return false;
		}
		if($("#FID").val()==""){
			$("#FID").tips({
				side:3,
	            msg:'请输入日志文件',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FID").focus();
			return false;
		}
		if($("#CREATED").val()==""){
			$("#CREATED").tips({
				side:3,
	            msg:'请输入创建日期',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATED").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="clientlog/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="CLIENTLOG_ID" id="CLIENTLOG_ID" value="${pd.CLIENTLOG_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">用户ID:</td>
				<td><input type="text" name="UID" id="UID" value="${pd.UID}" maxlength="32" placeholder="这里输入用户ID" title="用户ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">用户名:</td>
				<td><input type="text" name="UNAME" id="UNAME" value="${pd.UNAME}" maxlength="32" placeholder="这里输入用户名" title="用户名"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">设备名称:</td>
				<td><input type="text" name="DEVICE_NAME" id="DEVICE_NAME" value="${pd.DEVICE_NAME}" maxlength="32" placeholder="这里输入设备名称" title="设备名称"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">系统版本:</td>
				<td><input type="text" name="OS_VERSION" id="OS_VERSION" value="${pd.OS_VERSION}" maxlength="32" placeholder="这里输入系统版本" title="系统版本"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">应用版本:</td>
				<td><input type="text" name="APP_VERSION" id="APP_VERSION" value="${pd.APP_VERSION}" maxlength="32" placeholder="这里输入应用版本" title="应用版本"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">日志文件:</td>
				<td><input type="text" name="FID" id="FID" value="${pd.FID}" maxlength="32" placeholder="这里输入日志文件" title="日志文件"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">创建日期:</td>
				<td><input class="span10 date-picker" name="CREATED" id="CREATED" value="${pd.CREATED}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="创建日期" title="创建日期"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
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