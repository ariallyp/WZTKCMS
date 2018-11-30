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
		if ($("#WIZAPP_ID").val() != "") {
			$("#NAME").attr("readonly", "readonly");
			$("#NAME").css("color", "gray");
			
			$("#DESCRIPTION").val($("#DESCRIPTION_ID").val());

			
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
		if ($("#TOKEN").val() == "") {
			$("#TOKEN").tips({
				side : 3,
				msg : '请输入令牌',
				bg : '#AE81FF',
				time : 2
			});
			$("#TOKEN").focus();
			return false;
		}
		if ($("#TYPE").val() == "") {
			$("#TYPE").tips({
				side : 3,
				msg : '请输入类型',
				bg : '#AE81FF',
				time : 2
			});
			$("#TYPE").focus();
			return false;
		}
		if ($("#AVATAR").val() == "") {
			$("#AVATAR").tips({
				side : 3,
				msg : '请输入图片信息',
				bg : '#AE81FF',
				time : 2
			});
			$("#AVATAR").focus();
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
		if ($("#NAME_PY").val() == "") {
			$("#NAME_PY").tips({
				side : 3,
				msg : '请输入全名',
				bg : '#AE81FF',
				time : 2
			});
			$("#NAME_PY").focus();
			return false;
		}
		if ($("#NAME_QUANPIN").val() == "") {
			$("#NAME_QUANPIN").tips({
				side : 3,
				msg : '请输入命名',
				bg : '#AE81FF',
				time : 2
			});
			$("#NAME_QUANPIN").focus();
			return false;
		}
		if ($("#DESCRIPTION").val() == "") {
			$("#DESCRIPTION").tips({
				side : 3,
				msg : '请输入描述',
				bg : '#AE81FF',
				time : 2
			});
			$("#DESCRIPTION").focus();
			return false;
		}
		/* $("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show(); */
		if($("#WIZAPP_ID").val()==""){
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
				url: '<%=basePath%>wizapp/hasU.do',
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
						setTimeout("$('#NAME').val('此应用已存在!')",500);
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
	<form action="wizapp/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="WIZAPP_ID" id="WIZAPP_ID"
			value="${pd.WIZAPP_ID}" />
		<div id="zhongxin">
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">租户:</td>
					<td><select class="chzn-select" name="TENANT_ID" id="rent_id"
						data-placeholder="请选择租户" style="vertical-align: top;" title="租户">
							<option value=""></option>
							<c:forEach items="${rentList}" var="rent">
								<option value="${rent.TENANT_ID }"
									<c:if test="${pd.TENANT_ID==rent.TENANT_ID}">selected</c:if>>${rent.TENANT_NAME }</option>

							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">名称:</td>
					<td><input type="text" name="NAME" id="NAME"
						value="${pd.NAME}" maxlength="32" placeholder="这里输入名称" title="名称" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">令牌:</td>
					<td><input type="text" name="TOKEN" id="TOKEN"
						value="${pd.TOKEN}" maxlength="32" placeholder="这里输入令牌" title="令牌" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">类型:</td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="TYPE" id="TYPE" data-placeholder="请选择"
						style="vertical-align: top; ">
							<option value="">请选择</option>
							<option value="app"
								<c:if test="${pd.TYPE == 'app' }">selected</c:if>>app</option>
							<option value="h5"
								<c:if test="${pd.TYPE == 'h5' }">selected</c:if>>h5</option>
					</select></td>
				</tr>
				
				
				
				
			<%-- 	<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">图标:</td>
					<td><input type="text" name="AVATAR" id="AVATAR"
						value="${pd.AVATAR}" maxlength="32" title="avatar" /></td>
				</tr> --%>

				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">命名:</td>
					<td><input type="text" name="NAME_PY" id="NAME_PY"
						value="${pd.NAME_PY}" maxlength="32" placeholder="命名" title="命名" /></td>
				</tr>
				
					<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">状态:</td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="STATUS" id="STATUS" data-placeholder="请选择"
						style="vertical-align: top; ">
							
							<option value="0"
								<c:if test="${pd.STATUS == 0 }">selected</c:if>>上架</option>
							<option value="1"
								<c:if test="${pd.STATUS == 1 }">selected</c:if>>下架</option>
								<option value="2"
								<c:if test="${pd.STATUS == 2 }">selected</c:if>>审核</option>
					</select></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">全名:</td>
					<td><input type="text" name="NAME_QUANPIN" id="NAME_QUANPIN"
						value="${pd.NAME_QUANPIN}" maxlength="32" placeholder="全名"
						title="全名" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">描述:</td>
					<td>
					
					<input type="hidden" id="DESCRIPTION_ID"
						value="${pd.DESCRIPTION}" />
					<textarea rows=""  cols="" name="DESCRIPTION" id="DESCRIPTION"
						 placeholder="这里输入描述"
						title="描述">
					</textarea>
					
						
					</td>
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