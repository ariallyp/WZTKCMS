<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<!-- 确认窗口 -->


</head>
<body>




	<div class=""
		style="padding-top: 17px; height: 33px; width: 99%; background-color: #dcdcdc; margin-left: 8px; margin-top: 15px">
		<span
			style="font-size: 20px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-left: 12px;">同步设置</span>
	</div>
	<div
		style="width: 99%; font-size: 14px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-top: 15px; margin-left: 8px">

		<div style="padding-left: 68px; margin-top: 30px">
			<span>同步模式:</span> ${pd.WZTK_SYNC_MODEL}
		</div>

		<div class="synt" style="padding-left: 68px; margin-top: 15px">
			<span>同步时间:</span> ${pd.WZTK_SYNC_TIME}
		</div>
		<div style="padding-left: 140px; margin-top: 15px">
         <input type="button" style="background-color: #3f71d4; color: #ffffff; 
         height: 22px; width: 80px; border-radius: 5px; border: none" name="edit"
         value="更新设置" onclick="edit();" />
        </div>


	</div>





	<div id="zhongxin2" class="center" style="display: none">
		<br /> <br /> <br /> <br /> <br /> <img
			src="static/images/jiazai.gif" /><br />
		<h4 class="lighter block green">提交中...</h4>
	</div>




	<div
		style="margin-top: 30px; height: 152px; width: 99%; border: 1px solid; border-color: darkgrey; margin-left: 8px">
		<div class=""
			style="padding-top: 17px; height: 33px; width: 100%; background-color: #dcdcdc">
			<span
				style="font-size: 16px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-left: 12px;">同步设置</span>
		</div>
		<div class="" style="padding-left: 531px">
			<input
				style="font-size: 16px; background-color: #3f71d4; color: #ffffff; font-family: MicrosoftYaHei; margin-top: 34px; height: 40px; width: 320px; border-radius: 5px; border: none"
				type="button" name="commit" value="即时同步用户数据" onclick="sync();" />
		</div>
	</div>

		<div id="zhongxin3" class="center" style="display:none"><br/><img src="static/images/jzx.gif" /><br/><h4 class="lighter block green"></h4></div>





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
	
	 function sync(){
		 bootbox.confirm("确定要立即同步数据吗?", function(result) {
				
			 if(result) {
					$("#zhongxin").hide();
					$("#zhongxin3").show();
					top.jzts();
					var url = "<%=basePath%>client/sync.do";
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
					
				}
			});
		 	
		} 
	
	
	

	
	 function edit(){
		 bootbox.confirm("确定要更新同步时间吗?", function(result) {
				
			 if(result) {
					var url = "<%=basePath%>client/syncEdit.do";
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
					
				}
			});
		 	
		} 
	 
	 
</script>

</body>
</html>