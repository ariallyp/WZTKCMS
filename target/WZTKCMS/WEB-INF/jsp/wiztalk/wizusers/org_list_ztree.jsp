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
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	<link rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	
<body>
	


		
<div class="container-fluid" id="main-container">


<table style="width:100%;" border="0">
	<tr>
		<td style="width:15%;" valign="top" bgcolor="#F9F9F9">
			<div style="width:15%;">
				<ul id="leftTree" class="ztree"></ul>
			</div>
		</td>
		<td style="width:85%;" valign="top" >
			<iframe name="treeFrame" id="treeFrame" frameborder="0" src="<%=basePath%>/organization/list.do" style="margin:0 auto;width:100%;height:100%;"></iframe>
		</td>
	</tr>
</table>

	
</div><!--/.fluid-container#main-container-->
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<!-- 引入 -->
		<script type="text/javascript">
			$(top.hangge());
		</script>
		<SCRIPT type="text/javascript">
		var treeNodes="";
		var setting = {
			view: {
				showIcon: showIconForTree
			},
			
			data: {
				simpleData: {
					enable: true,
					idKey : "id", // id编号命名     
	                pIdKey : "pid" // 父id编号命名      
	               
	                
				}
			}
		     
	            ,callback:{  
	             	// beforeAsync: zTreeBeforeAsync,      // 异步加载事件之前得到相应信息    
	                asyncSuccess: zTreeOnAsyncSuccess,//异步加载成功的fun    
	                asyncError: zTreeOnAsyncError,   //加载错误的fun    
	                beforeClick:beforeClick, //捕获单击节点之前的事件回调函数  ,
	                onClick:zTreeOnClick
	            
	            }
		
		
		};
          
		function zTreeOnClick(event,treeId,treeNode){
			/* alert(treeNode.pid); */
			var pid=treeNode.id;
			$("#treeFrame").attr("src", 'organization/list.do?PARENT_ID='+pid);
			//动态加载treeFrame就可以不用ajax
		}
		
		
        function beforeClick(treeId,treeNode){  
            if(!treeNode.isParent){  
                return false;  
            }else{  
                return true;  
            }  
        }  
          
        function zTreeOnAsyncError(event, treeId, treeNode){    
        }    
          
        function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){    
              
        }  
        
        function showIconForTree(treeId, treeNode) {
			return !treeNode.isParent;
		};
		
		
		function treeFrameT(){
			var hmainT = document.getElementById("treeFrame");
			var bheightT = document.documentElement.clientHeight;
			hmainT .style.width = '100%';
			hmainT .style.height = (bheightT-7) + 'px';
		}
		treeFrameT();
		window.onresize=function(){  
			treeFrameT();
		};
		
		
		//tee values
		treeNodes= ${treeNodes};
		
		$(document).ready(function(){
			$.fn.zTree.init($("#leftTree"), setting, treeNodes);
		});
		
		
		
				
	</SCRIPT>
	</body>
</html>

