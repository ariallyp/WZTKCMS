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
<link rel="stylesheet" href="static/css/global.css" />

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
input {
	width: 300px;
}
</style>
<style type="text/css">
.hidebox {
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}
</style>
</head>
<body>


	<div class=""
		style="padding-top: 17px; height: 33px; width: 99%; background-color: #dcdcdc; margin-left: 8px; margin-top: 15px">
		<span id="span_tips"
			style="font-size: 20px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-left: 12px;">当前接口配置</span>
	</div>
	<div
		style="width: 99%; font-size: 14px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-top: 15px; margin-left: 8px">




	</div>
	<div
		style="padding-top: 17px; height: 33px; width: 99%; background-color: #dcdcdc; margin-left: 8px; margin-top: 15px">
		<form action="wiztalkconfig/saveConfig.do" name="Form" id="configForm"
			method="post">
			<input type="hidden" name="edit" value=""> <input
				type="hidden" name="encrypt" value=""> <input type="hidden"
				name="del" value=""> <input type="hidden" name="propName"
				value="">



			<div class="jive-table">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<th nowrap="">属性名</th>
							<th nowrap="">属性值</th>
							<th style="text-align: center;">编辑</th>
							<th style="text-align: center;">加密</th>
							<th style="text-align: center;">删除</th>
						</tr>
					</thead>
					<tbody>

						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="adminConsole.port"> adminConsole.port </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span title="9090">9090</span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('adminConsole.port');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="doencrypt('adminConsole.port');"> <img
									src="static/images/add-16x16.gif" width="16" height="16"
									alt="点击加密该配置" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="return dodelete('adminConsole.port');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>


						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="adminConsole.securePort">
										adminConsole.securePort </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span title="9091">9091</span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('adminConsole.securePort');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="doencrypt('adminConsole.securePort');"> <img
									src="static/images/add-16x16.gif" width="16" height="16"
									alt="点击加密该配置" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="return dodelete('adminConsole.securePort');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>


						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="connectionProvider.className">
										connectionProvider.className </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span
										title="org.jivesoftware.database.DefaultConnectionProvider">org.jivesoftware.database.DefaultConnectionProvider</span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('connectionProvider.className');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="doencrypt('connectionProvider.className');"> <img
									src="static/images/add-16x16.gif" width="16" height="16"
									alt="点击加密该配置" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="return dodelete('connectionProvider.className');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>


						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="database.defaultProvider.connectionTimeout">
										database.defaultProvider.connectionTimeout </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span title="1.0">1.0</span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('database.defaultProvider.connectionTimeout');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="doencrypt('database.defaultProvider.connectionTimeout');">
									<img src="static/images/add-16x16.gif" width="16" height="16"
									alt="点击加密该配置" border="0">
							</a></td>
							<td align="center"><a href="#"
								onclick="return dodelete('database.defaultProvider.connectionTimeout');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>


						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="database.defaultProvider.driver">
										database.defaultProvider.driver </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span title="com.mysql.jdbc.Driver">com.mysql.jdbc.Driver</span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('database.defaultProvider.driver');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="doencrypt('database.defaultProvider.driver');"> <img
									src="static/images/add-16x16.gif" width="16" height="16"
									alt="点击加密该配置" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="return dodelete('database.defaultProvider.driver');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>


						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="database.defaultProvider.maxConnections">
										database.defaultProvider.maxConnections </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span title="25">25</span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('database.defaultProvider.maxConnections');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="doencrypt('database.defaultProvider.maxConnections');">
									<img src="static/images/add-16x16.gif" width="16" height="16"
									alt="点击加密该配置" border="0">
							</a></td>
							<td align="center"><a href="#"
								onclick="return dodelete('database.defaultProvider.maxConnections');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>


						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="database.defaultProvider.minConnections">
										database.defaultProvider.minConnections </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span title="5">5</span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('database.defaultProvider.minConnections');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="doencrypt('database.defaultProvider.minConnections');">
									<img src="static/images/add-16x16.gif" width="16" height="16"
									alt="点击加密该配置" border="0">
							</a></td>
							<td align="center"><a href="#"
								onclick="return dodelete('database.defaultProvider.minConnections');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>


						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="database.defaultProvider.password">
										database.defaultProvider.password </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span style="color: #999;"><i>hidden</i></span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('database.defaultProvider.password');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><img src="static/images/lock.gif"
								width="16" height="16" alt="配置已经加密" border="0"></td>
							<td align="center"><a href="#"
								onclick="return dodelete('database.defaultProvider.password');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>


						<tr class="">

							<td>
								<div class="hidebox" style="width: 200px;">
									<span title="database.defaultProvider.serverURL">
										database.defaultProvider.serverURL </span>
								</div>
							</td>
							<td>
								<div class="hidebox" style="width: 300px;">

									<span
										title="jdbc:mysql://192.168.1.22:3306/openfire?useUnicode=true&amp;characterEncoding=utf8">jdbc:mysql://192.168.1.22:3306/openfire?useUnicode=true&amp;characterEncoding=utf8</span>

								</div>
							</td>
							<td align="center"><a href="#"
								onclick="doedit('database.defaultProvider.serverURL');"><img
									src="static/images/edit-16x16.gif" width="16" height="16"
									alt="单击可编辑此属性" border="0"></a></td>
							<td align="center"><a href="#"
								onclick="doencrypt('database.defaultProvider.serverURL');">
									<img src="static/images/add-16x16.gif" width="16" height="16"
									alt="点击加密该配置" border="0">
							</a></td>
							<td align="center"><a href="#"
								onclick="return dodelete('database.defaultProvider.serverURL');"><img
									src="static/images/delete-16x16.gif" width="16" height="16"
									alt="单击可删除此属性" border="0"></a></td>
						</tr>








					</tbody>
				</table>
			</div>
		</form>
	</div>
	<div
		style="padding-top: 250px; height: 33px; width: 99%; margin-left: 8px; margin-top: 15px">
		<br>
		<br> <a name="edit"></a>
		<form action="server-properties.jsp" method="post" name="editform">

			<div class="jive-table">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<th colspan="2">添加新属性</th>
						</tr>
					</thead>
					<tbody>
						<tr valign="top">
							<td>属性名:</td>
							<td><input type="text" name="propName" size="40"
								maxlength="100" value=""></td>
						</tr>
						<tr valign="top">
							<td>属性值:</td>
							<td><textarea cols="45" rows="5" name="propValue"
									wrap="virtual"></textarea></td>
						</tr>
						<tr valign="top">
							<td>配置加密:</td>
							<td><input type="radio" name="encrypt" value="true" />加密此配置值<br />
								<input type="radio" name="encrypt" value="false" checked />不加密此配置值
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2"><input type="submit" name="save"
								value="保存属性"> <input type="submit" name="cancel"
								value="取消"></td>
						</tr>
					</tfoot>
				</table>
			</div>

		</form>


	</div>

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
				side : 3,
				msg : msg,
				bg : '#AE81FF',
				time : 5
			});
		}
	</script>

</body>
</html>