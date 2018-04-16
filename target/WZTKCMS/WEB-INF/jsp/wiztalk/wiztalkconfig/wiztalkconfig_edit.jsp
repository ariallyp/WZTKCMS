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
			if($("#APPNAME").val()==""){
			
			$("#APPNAME").tips({
				side:3,
	            msg:'请输入应用名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#APPNAME").focus();
			return false;
		}
		if($("#XMPP_HOST_NAME").val()==""){
			$("#XMPP_HOST_NAME").tips({
				side:3,
	            msg:'请输入应用服务器',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#XMPP_HOST_NAME").focus();
			return false;
		}
		if($("#XMPP_HOST_IP").val()==""){
			$("#XMPP_HOST_IP").tips({
				side:3,
	            msg:'请输入聊天服务器',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#XMPP_HOST_IP").focus();
			return false;
		}
		if($("#XMPP_FILE_SERVER_IP").val()==""){
			$("#XMPP_FILE_SERVER_IP").tips({
				side:3,
	            msg:'请输入文件服务器',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#XMPP_FILE_SERVER_IP").focus();
			return false;
		}
		if($("#XMPP_HOST_PORTIN").val()==""){
			$("#XMPP_HOST_PORTIN").tips({
				side:3,
	            msg:'请输入HOST_PORTIN',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#XMPP_HOST_PORTIN").focus();
			return false;
		}
		if($("#XMPP_SIGN_PORTIN").val()==""){
			$("#XMPP_SIGN_PORTIN").tips({
				side:3,
	            msg:'请输入SIGN_PORTIN',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#XMPP_SIGN_PORTIN").focus();
			return false;
		}
		if($("#XMPP_REQUST_PORTIN").val()==""){
			$("#XMPP_REQUST_PORTIN").tips({
				side:3,
	            msg:'请输入REQUST_PORTIN',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#XMPP_REQUST_PORTIN").focus();
			return false;
		}
		if($("#TYPE").val()==""){
			$("#TYPE").tips({
				side:3,
	            msg:'请输入内外网',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TYPE").focus();
			return false;
		}
		if($("#TARGET").val()==""){
			$("#TARGET").tips({
				side:3,
	            msg:'请输入目标代码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TARGET").focus();
			return false;
		}else{
			if($("#TARGET").val()<=0){
				$("#TARGET").tips({
					side:3,
		            msg:'目标代码需大于0',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TARGET").focus();
				return false;
			
		}
		}
		if($("#POINTINSPECTION_URL").val()==""){
			$("#POINTINSPECTION_URL").tips({
				side:3,
	            msg:'请输入点巡检URL',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#POINTINSPECTION_URL").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="wiztalkconfig/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="WIZTALKCONFIG_ID" id="WIZTALKCONFIG_ID" value="${pd.WIZTALKCONFIG_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">应用名:</td>
				<td><input type="text" name="APPNAME" id="APPNAME" value="${pd.APPNAME}" maxlength="32" placeholder="这里输入应用名" title="应用名"/></td>
			
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">应用服务器:</td>
				<td><input type="text" name="XMPP_HOST_NAME" id="XMPP_HOST_NAME" value="${pd.XMPP_HOST_NAME}" maxlength="50" placeholder="这里输入应用服务器" title="应用服务器"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">聊天服务器:</td>
				<td><input type="text" name="XMPP_HOST_IP" id="XMPP_HOST_IP" value="${pd.XMPP_HOST_IP}" maxlength="32" placeholder="这里输入聊天服务器" title="聊天服务器"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">文件服务器:</td>
				<td><input type="text" name="XMPP_FILE_SERVER_IP" id="XMPP_FILE_SERVER_IP" value="${pd.XMPP_FILE_SERVER_IP}" maxlength="32" placeholder="这里输入文件服务器" title="文件服务器"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">HOST端口:</td>
				<td><input type="text" name="XMPP_HOST_PORTIN" id="XMPP_HOST_PORTIN" value="${pd.XMPP_HOST_PORTIN}" maxlength="32" placeholder="这里输入HOST_PORTIN" title="HOST_PORTIN"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">SIGN端口:</td>
				<td><input type="text" name="XMPP_SIGN_PORTIN" id="XMPP_SIGN_PORTIN" value="${pd.XMPP_SIGN_PORTIN}" maxlength="32" placeholder="这里输入SIGN_PORTIN" title="SIGN_PORTIN"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">REQUST端口:</td>
				<td><input type="text" name="XMPP_REQUST_PORTIN" id="XMPP_REQUST_PORTIN" value="${pd.XMPP_REQUST_PORTIN}" maxlength="32" placeholder="这里输入REQUST_PORTIN" title="REQUST_PORTIN"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">内外网:</td>
				<td><input type="text" name="TYPE" id="TYPE" value="${pd.TYPE}" maxlength="32" placeholder="这里输入内外网" title="内外网"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">目标代码:</td>
				<td><input type="number" name="TARGET" id="TARGET" min="0" value="${pd.TARGET}" maxlength="32" placeholder="这里输入目标代码" title="目标代码"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">点巡检URL:</td>
				<td><input type="text" name="POINTINSPECTION_URL" id="POINTINSPECTION_URL" value="${pd.POINTINSPECTION_URL}" maxlength="32" placeholder="这里输入点巡检URL" title="点巡检URL"/></td>
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