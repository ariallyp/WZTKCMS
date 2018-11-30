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
<title>获取授权码</title>

<style type="text/css">
.creditly-wrapper {
  background-color: rgba(66, 139, 202, 0.8);
  border-color: #357ebd;
  border-radius: 4px;
  -webkit-transition: all 0.2s ease-in-out;
  transition: all 0.2s ease-in-out;

  width: 350px;
  min-height: 216px;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 14px;
  line-height: 1.428571429;
  color: white;

  background-image: -webkit-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -moz-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -ms-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -o-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -webkit-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -moz-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -ms-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -o-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
}

.creditly-wrapper .credit-card-wrapper {
  padding: 40px 30px;
}

.creditly-wrapper .credit-card-wrapper .controls {
  padding-left: 7px;
  padding-right: 7px;
}

.creditly-wrapper input.has-error {
  outline: none;
  border-color: #ff7076;
  border-top-color: #ff5c61;
  -webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,0.2),0 1px 0 rgba(255,255,255,0),0 0 4px 0 rgba(255,0,0,0.5);
  -moz-box-shadow: inset 0 1px 2px rgba(0,0,0,0.2),0 1px 0 rgba(255,255,255,0),0 0 4px 0 rgba(255,0,0,0.5);
  -ms-box-shadow: inset 0 1px 2px rgba(0,0,0,0.2),0 1px 0 rgba(255,255,255,0),0 0 4px 0 rgba(255,0,0,0.5);
  -o-box-shadow: inset 0 1px 2px rgba(0,0,0,0.2),0 1px 0 rgba(255,255,255,0),0 0 4px 0 rgba(255,0,0,0.5);
  box-shadow: inset 0 1px 2px rgba(0,0,0,0.2),0 1px 0 rgba(255,255,255,0),0 0 4px 0 rgba(255,0,0,0.5);
}

/*
 * Bootstrap stuff
 */
.creditly-wrapper .form-control {
  display: block;
  width: 100%;
  height: 34px;
  padding: 6px 12px;
  font-size: 14px;
  line-height: 1.428571429;
  color: #555;
  vertical-align: middle;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
  box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
  -webkit-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
  transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
}

.creditly-wrapper .form-control:focus {
  border-color: #66afe9;
  outline: 0;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(102,175,233,0.6);
  box-shadow: inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(102,175,233,0.6);
}

.creditly-wrapper label {
  display: inline-block;
  margin-bottom: 5px;
  font-weight: bold;
}

.creditly-wrapper input:focus::-webkit-input-placeholder {
    color: #ccc;
}

.creditly-wrapper input:focus:-moz-placeholder {
    color: #ccc;
}

.creditly-wrapper input:focus::-moz-placeholder {
    color: #ccc;
}

.creditly-wrapper input:focus:-ms-input-placeholder {
    color: #ccc;
}

.creditly-wrapper .col-sm-8 {
  width: 66.6666666666666%;
}

.creditly-wrapper .col-sm-4 {
  width: 33.3333333333333%;
}

.creditly-wrapper .col-sm-8,
.creditly-wrapper .col-sm-4 {
  float: left;
  position: relative;
  display: block;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

.creditly-wrapper .form-group {
  width: 100%;
  display: table;
}

.creditly-wrapper .first-row {
  margin-bottom: 20px;
}

.creditly-wrapper .card-type {
  margin-top: 10px;
  float: right;
}

/**
 * Button CSS
 */

.creditly-card-form .submit {
  overflow: hidden;
  display: inline-block;
  visibility: visible!important;
  background-image: -webkit-linear-gradient(#28a0e5,#015e94);
  background-image: -moz-linear-gradient(#28a0e5,#015e94);
  background-image: -ms-linear-gradient(#28a0e5,#015e94);
  background-image: -o-linear-gradient(#28a0e5,#015e94);
  background-image: -webkit-linear-gradient(#28a0e5,#015e94);
  background-image: -moz-linear-gradient(#28a0e5,#015e94);
  background-image: -ms-linear-gradient(#28a0e5,#015e94);
  background-image: -o-linear-gradient(#28a0e5,#015e94);
  background-image: linear-gradient(#28a0e5,#015e94);
  -webkit-font-smoothing: antialiased;
  border: 0;
  padding: 1px;
  text-decoration: none;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  -ms-border-radius: 5px;
  -o-border-radius: 5px;
  border-radius: 5px;
  -webkit-box-shadow: 0 1px 0 rgba(0,0,0,0.2);
  -moz-box-shadow: 0 1px 0 rgba(0,0,0,0.2);
  -ms-box-shadow: 0 1px 0 rgba(0,0,0,0.2);
  -o-box-shadow: 0 1px 0 rgba(0,0,0,0.2);
  box-shadow: 0 1px 0 rgba(0,0,0,0.2);
  -webkit-touch-callout: none;
  -webkit-tap-highlight-color: transparent;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  -o-user-select: none;
  user-select: none;
  cursor: pointer;
}

.creditly-card-form .submit span {
  display: block;
  position: relative;
  padding: 0 12px;
  height: 30px;
  line-height: 30px;
  background: #1275ff;
  background-image: -webkit-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -moz-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -ms-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -o-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -webkit-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -moz-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -ms-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: -o-linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  background-image: linear-gradient(#7dc5ee,#008cdd 85%,#30a2e4);
  font-size: 14px;
  color: #fff;
  font-weight: bold;
  font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
  text-shadow: 0 -1px 0 rgba(0,0,0,0.25);
  -webkit-box-shadow: inset 0 1px 0 rgba(255,255,255,0.25);
  -moz-box-shadow: inset 0 1px 0 rgba(255,255,255,0.25);
  -ms-box-shadow: inset 0 1px 0 rgba(255,255,255,0.25);
  -o-box-shadow: inset 0 1px 0 rgba(255,255,255,0.25);
  box-shadow: inset 0 1px 0 rgba(255,255,255,0.25);
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  -ms-border-radius: 4px;
  -o-border-radius: 4px;
  border-radius: 4px;
}

body {
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 14px;
  line-height: 1.428571429;
}

h1, h2 {
  color: #357ebd;
}

.banner .row {
  width: 350px;
  margin-left: auto;
  margin-right: auto;
}

.banner .creditly-card-form {
  margin-top: 60px;
}

.banner .row .submit {
  margin-top: 20px;
  float: right;
}

.banner .footer {
  bottom: 0;
  position: absolute;
  margin: 20px;
}




</style>
<body>
  
  <div class="banner">
    <div class="row">
      <div class="header">
      </div>
      <form class="creditly-card-form" action="appcode/getCode.do" name="Form" id="Form" method="post">
        <section class="creditly-wrapper">
          <div class="credit-card-wrapper">
           
            <div class="second-row form-group">
              <div class="col-sm-8 controls">
                <label class="control-label">WizTalk 授权码</label>
                <c:if test="${code==null}">
                <input class="billing-address-name form-control"
                  type="text" name="name"
                  placeholder="请输入用户名">
                  </c:if>
              </div>
            <br><br>
            </div>
            <c:if test="${code!=null}">
             
             <div class="second-row form-group">
								<div class="col-sm-8 controls">
									<label class="control-label">授权码</label> 
									
									<input
										class="billing-address-name form-control" type="text" id="code"
										name="code" value="${code}">
								</div>

							</div>
            </c:if>
            <div class="card-type"></div>
          </div>
        </section>
        <button class="submit"><span>Submit</span></button>
      </form>
    </div>
    
  </div>
<div style="text-align:center;clear:both"><br>

</div>
</body>
</html>