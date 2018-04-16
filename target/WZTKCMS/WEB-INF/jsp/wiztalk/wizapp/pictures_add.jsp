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
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

<link rel="stylesheet" type="text/css"
	href="plugins/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css"
	href="plugins/webuploader/style.css" />

</head>
<body>
	<form action="wizapp/saveFile.do" name="Form" id="Form"
		method="post" enctype="multipart/form-data">
		<div id="zhongxin">
			<table style="width: 95%;">

				<tr>
					<td style="padding-left: 40px; padding-top: 20px; "><input type="file"
						id="uploadfile" name="file" style="width: 350px;" /></td>
				</tr>
				<tr>
					<td style="padding-left: 60px; padding-top: 120px;"><a
					style="width: 50px;"	class="btn btn-mini btn-primary" onclick="save();">上传</a></td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <img src="static/images/jzx.gif" /><br />
			<h4 class="lighter block green"></h4>
		</div>

	</form>


	<!-- 	<div id="zhongxin">
		
   <div id="wrapper">
        <div id="container">
        

            <div id="uploader">
                <form action="clientversion/saveFile.do" method="post" enctype="multipart/form-data">
                <input type="file" id="uploadfile" name="file" style="font-size: 36" />
                <br><br><br><br>
                <input type="submit" value="开始上传" style="font-size: 36">
                
                
                </form>
            </div>
        </div> 
        
        
    </div>

	</div>	
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/>
		<h4 class="lighter block green">提交中...</h4></div> -->

	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

	<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
	<script type="text/javascript" src="plugins/webuploader/upload.js"></script>

	<script type="text/javascript">
		$(top.hangge());

		//保存
		function save() {
			if ($("#uploadfile").val() == "") {

				$("#uploadfile").tips({
					side : 3,
					msg : '请选择文件',
					bg : '#AE81FF',
					time : 3
				});
				return false;
			}

			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		
			
		}
	</script>
</body>
</html>