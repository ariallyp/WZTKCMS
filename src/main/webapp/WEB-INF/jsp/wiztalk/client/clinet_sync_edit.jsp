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
<link rel="stylesheet" href="static/css/build.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/bootstrap-timepicker.css" />
<link rel="stylesheet" href="static/css/bootstrap-datetimepicker.min.css" />
<!-- /*（重要，这就是日期控件所需的样式表）*/ -->
<link rel="stylesheet" href="static/css/datepicker.css" />




</head>
<body>


	<div class=""
		style="padding-top: 17px; height: 33px; width: 99%; background-color: #dcdcdc; margin-left: 8px; margin-top: 15px">
		<span
			style="font-size: 20px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-left: 12px;">当前同步设置</span>
	</div>
	<div
		style="width: 99%; font-size: 14px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-top: 15px; margin-left: 8px">

		<div style="padding-left: 68px; margin-top: 30px">
			<span>同步模式:</span> ${pd.WZTK_SYNC_MODEL}
		</div>

		<div style="padding-left: 68px; margin-top: 15px">
			<span>同步时间:</span> ${pd.WZTK_SYNC_TIME}
		</div>

	</div>




	<form action="client/hb.do" name="Form" id="Form" method="post">
		<input type="hidden" name="CLIENT_ID" id="CLIENT_ID"
			value="${pd.CLIENT_ID}" />
		<div id="zhongxin">

			<div class=""
				style="padding-top: 17px; height: 33px; width: 99%; background-color: #dcdcdc; margin-left: 8px; margin-top: 15px">
				<span
					style="font-size: 20px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-left: 12px;">同步设置</span>
			</div>
			<div
				style="width: 99%; font-size: 14px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-top: 15px; margin-left: 8px">

				
			
				
				<div style="padding-left: 68px; margin-top: 30px">
					<span>同步模式:</span> <input id="sync" type="radio" style="height: 22px; width: 70px;"
						name="synp" value="1" /><span id="zqtb-name">周期同步</span>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; <input
						id="insyn" type="radio" name="synp" value="2"  style="height: 22px; width: 70px;"/> <span id="jgtb-name">间隔性同步</span>
					<input type="hidden" id="syncChoose" value="insyn"/>	
				</div>
				
				
				
				<div class="synt" style="padding-left: 68px; margin-top: 15px">
					<span>同步时间:</span> <input id="radio1" type="radio" name="synt"
						value="d" /><span id="day-name">每天</span>&nbsp;
						<div id="datetimepicker3" class="input-append">
							<input data-format="hh:mm:ss" type="text" name="everyDay" id="everyDay" class="text4"  style="width: 66px"/> <span class="add-on">
								<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
							</span>
						</div>
					
					&nbsp; <span
						style="position: absolute; margin-left: 6px;" id="timer1">
					</span>
				</div>
				<div class="synt" style="padding-left: 138px; margin-top: 15px">
					<input id="radio2" type="radio" name="synt" value="w" /><span id="week-name">每周</span>&nbsp; <select
						id="select1" name="week" style="height: 30px; width: 80px">
						<option value="MON">周一</option>
						<option value="TUE">周二</option>
						<option value="WED">周三</option>
						<option value="THU">周四</option>
						<option value="FRI">周五</option>
						<option value="SAT">周六</option>
						<option value="SUN">周日</option>
					</select>&nbsp; 
					<div id="datetimepicker4" class="input-append">
							<input data-format="hh:mm:ss" type="text" name="weekDay" id="weekDay" class="text5"  style="width: 66px"/> <span class="add-on">
								<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
							</span>
						</div>
						 <span
						style="position: absolute; margin-left: 6px;" id="timer2"></span>
				</div>
				<div class="synt" id="monthId"
					style="padding-left: 138px; margin-top: 15px">
					<input id="radio3" type="radio" name="synt" value="y" /><span id="month-name">每月</span>&nbsp; <input
					type="number" min="1" max="28" value="1"	id="select2" name="month"  style="width: 66px" ></input>&nbsp; 
					<div id="datetimepicker5" class="input-append">
							<input data-format="hh:mm:ss" type="text" name="monthDay" id="monthDay" class="text6" style="width: 66px"/> <span class="add-on">
								<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
							</span>
						</div>
					 <span
						style="position: absolute; margin-left: 6px;" id="timer3"></span>
				</div>

				
				<div id="time2" style="padding-left: 68px; margin-top: 15px">
					<span>间隔时间（分钟）：</span> <input id="text2"
						style=" width: 80px" type="number" min="1" max="99999" name="jg"
						placeholder="间隔时间" />
				</div>
				<div style="padding-left: 68px; margin-top: 30px">
				<span>只同步用户密码:</span> <select class="chzn-select"
					name="SYNC_WZTK_PWD" id="SYNC_WZTK_PWD"
					data-placeholder="请选择" style="vertical-align: top; width: 92px;">
					<option value="YES"
						<c:if test="${pd.SYNC_WZTK_PWD == 'YES' }">selected</c:if>>YES</option>
					<option value="NO"
						<c:if test="${pd.SYNC_WZTK_PWD == 'NO' }">selected</c:if>>NO</option>
				</select>
			</div>

				<div style="padding-left: 140px; margin-top: 15px">
					 <input type="button"
						style="background-color: #3f71d4; color: #ffffff; height: 35px; width: 80px; border-radius: 5px; border: none"
						name="commit" value="设置" onclick="save();"/>
						
				</div>
			</div>


	

		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <br /> <img
				src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green">同步中...</h4>
		</div>

	</form>

	<div id="zhongxin3">
		<div
			style="margin-top: 30px; height: 152px; width: 99%; border: 1px solid; border-color: darkgrey; margin-left: 8px">
			<div class=""
				style="padding-top: 17px; height: 33px; width: 100%; background-color: #dcdcdc">
				<span
					style="font-size: 16px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-left: 12px;">即时同步</span>
			</div>
			<div class="" style="padding-left: 531px">
				<input
					style="font-size: 16px; background-color: #3f71d4; color: #ffffff; font-family: MicrosoftYaHei; margin-top: 34px; height: 40px; width: 320px; border-radius: 5px; border: none"
					type="button" name="commit" value="即时同步用户数据" onclick="sync();" />
			</div>
		</div>
	</div>

	<div id="zhongxin4" class="center" style="display: none">
		<br />
		<img src="static/images/jzx.gif" /><br />
		<h4 class="lighter block green"></h4>
	</div>

	<div id="zhongxin5" style="display: none">
		<div
			style="margin-top: 30px; height: 152px; width: 99%; border: 1px solid; border-color: darkgrey; margin-left: 8px">
			<div class=""
				style="padding-top: 17px; height: 33px; width: 100%; background-color: #dcdcdc">
				<span
					style="font-size: 16px; color: #3f71d4; font-weight: bold; font-family: MicrosoftYaHei; margin-left: 12px;">同步结果</span>
			</div>
			<div class="" style="padding-left: 531px">
				<div style="padding-left: 68px; margin-top: 30px">
					<span id="successSpan" style="font-size: 16px; color: #00CD66;"></span>
				</div>


			</div>
		</div>
	</div>



	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<!-- 日期框 -->
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
    $(".form_datetime").datetimepicker({
        format: "dd MM yyyy - hh:ii",
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });
</script>

	<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			$('#datetimepicker3').datetimepicker({
				autoclose: true,
				pickDate:false
			}); 
			$('#datetimepicker4').datetimepicker({
				autoclose: true,
				pickDate:false
			});
			$('#datetimepicker5').datetimepicker({
				autoclose: true,
				pickDate:false
			});
			$('#datetimepicker6').datetimepicker({
				autoclose: true,
				pickDate:false
			});
			
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			
			$('.date-picker').datepicker();
			
			
			$("#jgtb-name").css({
				"color" : "red"
			});
			$("#time1").css({
				"color" : "#3f71d4"
			});
			$("#time2").css({
				"color" : "#3f71d4"
			});
			$(".synt").css({
				"color" : "#dcdcdc"
			});
			$("#select1").attr("disabled", true);
			$("#select2").attr("disabled", true);
			
			$(".text1").attr("disabled", false);
			$("#text2").attr("disabled", false);
			$(".text4").attr("disabled", true);
			$(".text5").attr("disabled", true);
			$(".text6").attr("disabled", true);
			
			$("#text2").attr("disabled", false);
			$("#radio1").attr("disabled", true);
			$("#radio2").attr("disabled", true);
			$("#radio3").attr("disabled", true);
			$("#time1").attr("disabled", false);
			$("#time2").attr("disabled", false);
			
			
			
		});
	</script>
	<script type="text/javascript">
	$(function() {
		
		$("#insyn").attr("checked","checked");
		$("#sync").click(function() {
			$("#radio1").attr("checked","checked");
			
			
			$("#syncChoose").val("sync");
			
			$("#sync").css({
				"color" : "#dcdcdc"
			});

			$(".synt").css({
				"color" : "#3f71d4"
			});
			$("#time1").css({
				"color" : "#dcdcdc"
			});
			$("#time2").css({
				"color" : "#dcdcdc"
			});
			$("#jgtb-name").css({
				"color" : "#3f71d4"
			});
			
			$("#zqtb-name").css({
				"color" : "red"
			});
			
			$("#day-name").css({
				"color" : "red"
			});
			$("#week-name").css({
				"color" : "#3f71d4"
			});
			$("#month-name").css({
				"color" : "#3f71d4"
			});
			$("#select1").attr("disabled", false);
			$("#select2").attr("disabled", false);
		
			$("#select1").attr("disabled", true);
			$("#select2").attr("disabled", true);
			$(".text5").attr("disabled", true);
			$(".text6").attr("disabled", true);
			
			$(".text1").attr("disabled", true);
			$(".text4").attr("disabled", false);
			
			
			$("#text2").attr("disabled", true);
			$("#radio1").attr("disabled", false);
			$("#radio2").attr("disabled", false);
			$("#radio3").attr("disabled", false);
			$("#time1").attr("disabled", true);
			$("#time2").attr("disabled", true);
			$("#timer").Enabled = false;
		});
		$("#insyn").click(function() {
			$("#syncChoose").val("insyn");
			$("#jgtb-name").css({
				"color" : "red"
			});
			$("#zqtb-name").css({
				"color" : "#dcdcdc"
			});
			$("#zqtb-name").css({
				"color" : "#3f71d4"
			});
			$("#time1").css({
				"color" : "#3f71d4"
			});
			$("#time2").css({
				"color" : "#3f71d4"
			});
			$(".synt").css({
				"color" : "#dcdcdc"
			});
			
			$("#week-name").css({
				"color" : "dcdcdc"
			});
			$("#day-name").css({
				"color" : "#dcdcdc"
			});
			$("#month-name").css({
				"color" : "#dcdcdc"
			});
			$("#select1").attr("disabled", true);
			$("#select2").attr("disabled", true);
			
			$(".text1").attr("disabled", false);
			$("#text2").attr("disabled", false);
			$(".text4").attr("disabled", true);
			$(".text5").attr("disabled", true);
			$(".text6").attr("disabled", true);
			
			$("#text2").attr("disabled", false);
			$("#radio1").attr("disabled", true);
			$("#radio2").attr("disabled", true);
			$("#radio3").attr("disabled", true);
			$("#time1").attr("disabled", false);
			$("#time2").attr("disabled", false);

		});
		
		$("#radio1").click(function() {
			$("#select1").attr("disabled", true);
			$("#select2").attr("disabled", true);
			$(".text4").attr("disabled", false);
			$(".text5").attr("disabled", true);
			$(".text6").attr("disabled", true);
			$("#day-name").css({
				"color" : "red"
			});
			$("#week-name").css({
				"color" : "#3f71d4"
			});
			
			$("#month-name").css({
				"color" : "#3f71d4"
			});
			
		});
		
		$("#radio2").click(function() {
			$("#select1").attr("disabled", false);
			$("#select2").attr("disabled", true);
			$(".text4").attr("disabled", true);
			$(".text5").attr("disabled", false);
			$(".text6").attr("disabled", true);
			$("#week-name").css({
				"color" : "red"
			});
			$("#day-name").css({
				"color" : "#3f71d4"
			});
			$("#month-name").css({
				"color" : "#3f71d4"
			});
			
		});
		
		$("#radio3").click(function() {
			$("#select2").attr("disabled", false);
			$("#select1").attr("disabled", true);
			$(".text4").attr("disabled", true);
			$(".text5").attr("disabled", true);
			$(".text6").attr("disabled", false);
			$("#month-name").css({
				"color" : "red"
			});
			$("#day-name").css({
				"color" : "#3f71d4"
			});
			$("#week-name").css({
				"color" : "#3f71d4"
			});
		});
	

	});

	
	function save() {
		
		if($("#syncChoose").val()=="insyn"){	
			
		if($("#jgDay").val()==""){
			$("#jgDay").tips({
				side:3,
	            msg:'请选中同步时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#jgDay").focus();
			return false;
		}
		if($("#text2").val()==""){
			$("#text2").tips({
				side:3,
	            msg:'请选中间隔时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#text2").focus();
			return false;
		}else{
			
			if($("#text2").val() >99999){
				$("#text2").tips({
					side:3,
		            msg:'值必须小于或等于99999',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#text2").focus();
				return false;
			}
		}
		
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		
		}
		
		var radio_val = $("input[name='synt']:checked").val();
		if(radio_val=="d"){
			if($("#everyDay").val()==""){
				$("#everyDay").tips({
					side:3,
		            msg:'请选中时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#everyDay").focus();
				return false;
			}
		}else if(radio_val=="w"){
			if($("#weekDay").val()==""){
				$("#weekDay").tips({
					side:3,
		            msg:'请选中时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#weekDay").focus();
				return false;
			}
		}else if(radio_val=="y"){
			if($("#monthDay").val()==""){
				$("#monthDay").tips({
					side:3,
		            msg:'请选中时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#monthDay").focus();
				return false;
			}
		}
		
		
		
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	

	
	 function sync(){
		 bootbox.confirm("确定要立即同步数据吗?", function(result) {
				
			 if(result) {
				 $("#zhongxin").hide();
				 $("#zhongxin3").hide();
				 $("#zhongxin2").show();
					
					
					var url = "<%=basePath%>client/start_sync.do";
					$.get(url,function(data){
						$("#zhongxin2").hide();
						
						$("#zhongxin5").show();
						$("#successSpan").text(data.message)
					});
					
				}
			});
		 	
		} 
	 
	 
</script>

</body>
</html>