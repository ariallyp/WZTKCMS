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
		<title>添加应用</title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
	
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	
<script type="text/javascript">
	
	
	//保存
	function save(){
	
	 	if($("#rent_id").val()==""){
			$("#rent_id").tips({
				side:3,
	            msg:'请选择应用！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#rent_id").focus();
			return false;
		} 
	
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="wizusers/saveAppAlow.do" name="Form" id="Form" method="post">
		
		<br>&nbsp;&nbsp;&nbsp;&nbsp; 请选择权限<br><br>
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="type" value="1">内网&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="type" value="2">外网&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="type" value="3" checked="checked">全网
		
		<input type="hidden" name="DATA_IDS" id="DATA_IDS" value="${pd.DATA_IDS}"/>
		<br><br>
		<div id="zhongxin">
			
		
		<table id="table_report" class="table table-striped table-bordered table-hover">
		
			<tr>
					
					<td style="width: 70px; text-align: left; padding-top: 13px;"><select class="chzn-select" name="APP_ID" id="rent_id"
						data-placeholder="请选择 APP" style="vertical-align: top;" title="APP">
							<option value=""></option>
							<c:forEach items="${rentList}" var="rent">
								<option value="${rent.APP_ID }"
									<c:if test="${pd.APP_ID==rent.APP_ID}">selected</c:if>>${rent.APP_NAME }</option>

							</c:forEach>
					</select>
					</td>
				
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