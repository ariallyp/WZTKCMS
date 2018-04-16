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
	$(top.hangge());

	$(document).ready(function() {
		if ($("#WIZUSERS_ID").val() != "") {
			$("#NAME").attr("readonly", "readonly");
			$("#NAME").css("color", "gray");
			
			if ($("#PASSWORD").val() == "") {
				$("#PASSWORD").val("${pd.PASSWORD}");
			}
			
			
		}
	});

	//保存
	function save() {
		if ($("#NAME").val() == "") {
			$("#NAME").tips({
				side : 3,
				msg : '请输入用户名',
				bg : '#AE81FF',
				time : 2
			});
			$("#NAME").focus();
			return false;
		}

		if ($("#TENANT_ID").val() == "") {
			$("#TENANT_ID").tips({
				side : 3,
				msg : '请选择租户',
				bg : '#AE81FF',
				time : 2
			});
			$("#TENANT_ID").focus();
			return false;
		}
		if ($("#org_id").val() == "") {
			$("#org_id").tips({
				side : 3,
				msg : '请选择机构',
				bg : '#AE81FF',
				time : 2
			});
			$("#org_id").focus();
			return false;
		}
		
		
		if ($("#EMAIL").val() == "") {
			$("#EMAIL").tips({
				side : 3,
				msg : '请输入邮箱',
				bg : '#AE81FF',
				time : 2
			});
			$("#EMAIL").focus();
			return false;
		}
		if ($("#MOBILE").val() == "") {
			$("#MOBILE").tips({
				side : 3,
				msg : '请输入手机号码',
				bg : '#AE81FF',
				time : 2
			});
			$("#MOBILE").focus();
			return false;
		}

		/* $("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		 */
		if($("#WIZUSERS_ID").val()==""){
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
			url: '<%=basePath%>wizusers/hasU.do',
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
	<form action="wizusers/${msg }.do?ORG_ID=${ORG_ID }" name="Form"
		id="Form" method="post">
		<input type="hidden" name="WIZUSERS_ID" id="WIZUSERS_ID"
			value="${pd.WIZUSERS_ID}" />
		<div id="zhongxin">
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<%-- <tr>
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
				--%>
				
				<input type="hidden" name="AVATAR" id="AVATAR" value="${pd.AVATAR}" />
				<input type="hidden" name="NAME_PY" id="NAME_PY" value="${pd.NAME_PY}" />
				<input type="hidden" name="NAME_QUANPIN" id="NAME_QUANPIN" value="${pd.NAME_QUANPIN}" />
				<input type="hidden" name="STATUS" id="STATUS" value="${pd.STATUS}" />
				<input type="hidden" name="RAND" id="RAND" value="${pd.RAND}" />
				<input type="hidden" name="LEVEL" id="LEVEL" value="${pd.LEVEL}" />
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">租户:</td>
					<td><input type="text" name="TENANT_NAME" id="TENANT_NAME"
						value="${pd.TENANT_NAME}" maxlength="32" readonly="readonly" title="租户" />
						<input type="hidden" name="TENANT_ID" id="TENANT_ID" value="${pd.TENANT_ID}" />
					</td>
				</tr>
				
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">机构:</td>
					<td><select class="chzn-select" name="ORG_ID" id="org_id"
						data-placeholder="请选择机构" style="vertical-align: top;" title="机构">
							<option value=""></option>
							<c:forEach items="${orgList}" var="org">

								<option value="${org.ORGANIZATION_ID }"
									<c:if test="${pd.ORG_ID==org.ORGANIZATION_ID}">selected</c:if>>
									${org.ORG_NAME }</option>
							</c:forEach>
					</select></td>
				</tr> 
				
				
				
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">账号:</td>
					<td><input type="text" name="NAME" id="NAME"
						value="${pd.NAME}" maxlength="32" placeholder="这里输入用户名" title="账号" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">中文名:</td>
					<td><input type="text" name="NICKNAME" id="NICKNAME"
						value="${pd.NICKNAME}" maxlength="32" placeholder="这里输入中文名称"
						title="nickname" /></td>
				</tr>
			 <tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">密码:</td>
					<td><input type="text" name="PASSWORD" id="PASSWORD" value=""
						maxlength="32" placeholder="这里输入密码" title="PASSWORD" /></td>
				</tr>


				<%-- <tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">图标:</td>
					<td><input type="text" name="AVATAR" id="AVATAR"
						value="${pd.AVATAR}" maxlength="32" placeholder="这里输入图标"
						title="avatar" /></td>
				</tr> --%>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">邮箱:</td>
					<td><input type="email" name="EMAIL" id="EMAIL"
						value="${pd.EMAIL}" maxlength="32" placeholder="这里输入邮箱"
						title="email" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">手机:</td>
					<td><input type="tel" name="MOBILE" id="MOBILE"
						value="${pd.MOBILE}" maxlength="32" placeholder="这里输入手机号"
						title="mobile" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">电话:</td>
					<td><input type="tel" name="TEL" id="TEL" value="${pd.TEL}"
						maxlength="32" placeholder="这里输入座机号" title="tel" /></td>
				</tr>
				<tr>
					<td style="width: 70px; text-align: right; padding-top: 13px;">区域:</td>
					<td><input type="text" name="AREA" id="AREA"
						value="${pd.AREA}" maxlength="32" placeholder="这里输入区域"
						title="AREA" /></td>
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
			<br /> <br /> <br /> <br /> <br /> <img
				src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">提交中...</h4>
		</div>

	</form>


	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
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
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
	
		
		
		</script>
</body>
</html>