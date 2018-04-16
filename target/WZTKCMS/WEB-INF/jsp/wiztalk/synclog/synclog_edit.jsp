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
			if($("#TYPE").val()==""){
			$("#TYPE").tips({
				side:3,
	            msg:'请输入类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TYPE").focus();
			return false;
		}
		if($("#APPID").val()==""){
			$("#APPID").tips({
				side:3,
	            msg:'请输入APPID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#APPID").focus();
			return false;
		}
		if($("#WSDLSITE").val()==""){
			$("#WSDLSITE").tips({
				side:3,
	            msg:'请输入同步服务器IP',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#WSDLSITE").focus();
			return false;
		}
		if($("#TENANTIDORGID").val()==""){
			$("#TENANTIDORGID").tips({
				side:3,
	            msg:'请输入同步主ID',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TENANTIDORGID").focus();
			return false;
		}
		if($("#RESULT").val()==""){
			$("#RESULT").tips({
				side:3,
	            msg:'请输入结果',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RESULT").focus();
			return false;
		}
		if($("#CREATED").val()==""){
			$("#CREATED").tips({
				side:3,
	            msg:'请输入同步时间',
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
	<form action="synclog/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="SYNCLOG_ID" id="SYNCLOG_ID" value="${pd.SYNCLOG_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">类型:</td>
				<td><input type="text" name="TYPE" id="TYPE" value="${pd.TYPE}" maxlength="32" placeholder="这里输入类型" title="类型"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">APPID:</td>
				<td><input type="text" name="APPID" id="APPID" value="${pd.APPID}" maxlength="32" placeholder="这里输入APPID" title="APPID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">同步服务器IP:</td>
				<td><input type="text" name="WSDLSITE" id="WSDLSITE" value="${pd.WSDLSITE}" maxlength="32" placeholder="这里输入同步服务器IP" title="同步服务器IP"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">同步主ID:</td>
				<td><input type="text" name="TENANTIDORGID" id="TENANTIDORGID" value="${pd.TENANTIDORGID}" maxlength="32" placeholder="这里输入同步主ID" title="同步主ID"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">结果:</td>
				<td><input type="text" name="RESULT" id="RESULT" value="${pd.RESULT}" maxlength="32" placeholder="这里输入结果" title="结果"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">同步时间:</td>
				<td><input class="span10 date-picker" name="CREATED" id="CREATED" value="${pd.CREATED}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="同步时间" title="同步时间"/></td>
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