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
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="static/js/timer.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<!-- 确认窗口 -->
<style type="text/css">
		  input{
		    width: 300px;
		   
		  }
		</style>

</head>
<body>


	<div class=""
		style="padding-top: 17px; height: 33px; width: 99%; background-color: #dcdcdc; margin-left: 8px; margin-top: 15px">
		<span id="span_tips"
			style="font-size: 20px;  color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-left: 12px;">当前接口配置</span>
	</div>
	<div
		style="width: 99%; font-size: 14px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-top: 15px; margin-left: 8px">




	</div>

	<form action="wiztalkconfig/saveConfig.do" name="Form" id="configForm"
		method="post">
		
		<div id="zhongxin">

			<div style="padding-left: 68px; margin-top: 30px">
				<span>皮肤接口:</span> <select class="chzn-select"
					name="OPEN_CLOSE_SKIN_KEY" id="OPEN_CLOSE_SKIN_KEY"
					data-placeholder="请选择" style="vertical-align: top; width: 120px;">
					<option value="open"
						<c:if test="${pd.OPEN_CLOSE_SKIN_KEY == 'open' }">selected</c:if>>开放</option>
					<option value="close"
						<c:if test="${pd.OPEN_CLOSE_SKIN_KEY == 'close' }">selected</c:if>>关闭</option>
				</select>
			</div>

			<div style="padding-left: 68px; margin-top: 30px">
				<span>密聊接口:</span> <select class="chzn-select"
					name="OPEN_CLOSE_CHAT_KEY" id="OPEN_CLOSE_CHAT_KEY"
					data-placeholder="请选择" style="vertical-align: top; width: 120px;">
					<option value="open"
						<c:if test="${pd.OPEN_CLOSE_CHAT_KEY == 'open' }">selected</c:if>>开放</option>
					<option value="close"
						<c:if test="${pd.OPEN_CLOSE_CHAT_KEY == 'close' }">selected</c:if>>关闭</option>
				</select>
			</div>
			
			<div style="padding-left: 68px; margin-top: 30px">
				<span>插件上传路径:</span> 
				<input type="text" name="WZTK_UPLOAD_PLUG_PATH" id="WZTK_UPLOAD_PLUG_PATH" value="${pd.WZTK_UPLOAD_PLUG_PATH}" width="500"   title="插件上传路径"/>
			</div>
			<div style="padding-left: 68px; margin-top: 30px">
				<span>文件下载上传路径:</span> 
				<input type="text" name="WZTK_UPLOAD_FILE_PATH" id="WZTK_UPLOAD_FILE_PATH" value="${pd.WZTK_UPLOAD_FILE_PATH}" width="500"   title="插件上传路径"/>
			</div>
			<div style="padding-left: 68px; margin-top: 30px">
				<span>限制次数:</span> 
				<input type="number" min="0" name="WZTK_LOGIN_TIMES_TIMESLIMTS" id="WZTK_LOGIN_TIMES_TIMESLIMTS" value="${pd.WZTK_LOGIN_TIMES_TIMESLIMTS}" width="500"   title="限制次数"/>
			</div>
			<div style="padding-left: 68px; margin-top: 30px">
				<span>限制时常（秒）:</span> 
				<input type="number" min="0" name="WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE" id="WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE" value="${pd.WZTK_LOGIN_TIMES_TIMESLIMTS_SIZE}" width="500"   title="限制次数"/>
			</div>

			<div style="padding-left: 140px; margin-top: 15px">
				<input type="submit"
					style="background-color: #3f71d4; color: #ffffff; height: 35px; width: 80px; border-radius: 5px; border: none"
					name="submit" value="保存" />
			</div>
		
		</div>


		<!-- <tr>
					<td style="text-align: center;" colspan="10"><a
						class="btn btn-mini btn-primary" onclick="save();">保存</a> 
				</tr> -->

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
	<script type="text/javascript">
		//保存
		function save() {
			
			$("#configForm").submit();
			/* $("#zhongxin").hide();
			$("#zhongxin2").show(); */
			var msg = "${msg}";
			$("#span_tips").tips({
				side:3,
	            msg:msg,
	            bg:'#AE81FF',
	            time:5
	        });
		}
	</script>

</body>
</html>