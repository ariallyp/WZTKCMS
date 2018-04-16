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
	$(document).ready(function() {
	if ($("#CLIENTVERSION_ID").val() != "") {
		
		$("#LAN_URL_1").val($("#LAN_URL").val());
		
		$("#DOWNLOAD_URL_1").val($("#DOWNLOAD_URL").val());
		
	}
	});
	
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
		if($("#VER_CODE").val()==""){
			$("#VER_CODE").tips({
				side:3,
	            msg:'请输入版本',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#VER_CODE").focus();
			return false;
		}else{
			if($("#VER_CODE").val()<=0){
				$("#VER_CODE").tips({
					side:3,
		            msg:'版本号不能小于0',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VER_CODE").focus();
				return false;
			}
		}
		
		
		if($("#VER_NAME").val()==""){
			$("#VER_NAME").tips({
				side:3,
	            msg:'请输入版本名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#VER_NAME").focus();
			return false;
		}
		if($("#VER_DESCRIPTION").val()==""){
			$("#VER_DESCRIPTION").tips({
				side:3,
	            msg:'请输入描述',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#VER_DESCRIPTION").focus();
			return false;
		}
		if($("#DOWNLOAD_URL_1").val()==""){
			$("#DOWNLOAD_URL_1").tips({
				side:3,
	            msg:'请输入外网地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#DOWNLOAD_URL_1").focus();
			return false;
		}
		if($("#LAN_URL_1").val()==""){
			$("#LAN_URL_1").tips({
				side:3,
	            msg:'请输入内网地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LAN_URL_1").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="clientversion/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="CLIENTVERSION_ID" id="CLIENTVERSION_ID" value="${pd.CLIENTVERSION_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">名称:</td>
					<td><select class="chzn-select" name="APP_ID" id="rent_id"
						data-placeholder="请选择 APP" style="vertical-align: top;" title="APP">
							<option value=""></option>
							<c:forEach items="${rentList}" var="rent">
								<option value="${rent.APP_ID }"
									<c:if test="${pd.APP_ID==rent.APP_ID}">selected</c:if>>${rent.APP_NAME }</option>

							</c:forEach>
					</select></td>
					
			</tr>
			
			
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">类型:</td>
				<td style="vertical-align:top;"> 
				<select class="chzn-select" name="TYPE" id="类型" data-placeholder="请选择" style="vertical-align:top;">
					 		<option value="">请选择</option>
					 		<option value="android" <c:if test="${pd.TYPE == 'android' }">selected</c:if> >android</option>
					 		<option value="ios" <c:if test="${pd.TYPE == 'ios' }">selected</c:if> >ios</option>
							<option value="h5" <c:if test="${pd.TYPE == 'h5' }">selected</c:if> >h5</option>
				</select>
				</td>			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">版本:</td>
				<td><input type="number" name="VER_CODE" id="VER_CODE" value="${pd.VER_CODE}" maxlength="32" placeholder="这里输入版本号" title="ver_code"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">版本名称:</td>
				<td><input type="text" name="VER_NAME" id="VER_NAME" value="${pd.VER_NAME}" maxlength="32" placeholder="这里输入版本名称" title="ver_name"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">包名:</td>
				<td><input type="text" name="PACKAGE_NAME" id="PACKAGE_NAME" value="${pd.PACKAGE_NAME}" maxlength="32" placeholder="这里输入包名" title="PACKAGE_NAME"/></td>
			</tr>
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">文件名:</td>
				<td><input type="text" name="FILE_NAME" id="FILE_NAME" value="${pd.FILE_NAME}" maxlength="32" placeholder="这里输入文件名" title="FILE_NAME"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">描述:</td>
				<td><input type="text" name="VER_DESCRIPTION" id="VER_DESCRIPTION" value="${pd.VER_DESCRIPTION}" maxlength="150" placeholder="这里输入描述" title="description"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">外网地址:</td>
				<td>
				<input type="hidden"  id="DOWNLOAD_URL" value="${pd.DOWNLOAD_URL}" maxlength="150" placeholder="这里输入内网地址" title="内网地址"/>
				
				<textarea name="DOWNLOAD_URL" id="DOWNLOAD_URL_1"  placeholder="这里输入内网地址" title="内网地址"></textarea></td>
				
			</tr>
			
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">内网地址:</td>
				<td>
				<input type="hidden"  id="LAN_URL" value="${pd.LAN_URL}" maxlength="150" placeholder="这里输入内网地址" title="内网地址"/>
				
				<textarea name="LAN_URL" id="LAN_URL_1"  placeholder="这里输入内网地址" title="内网地址"></textarea></td>
			
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