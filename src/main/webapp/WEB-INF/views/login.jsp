<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>系统登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${ctx}/resources/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${ctx}/resources/assets/css/style-metronic.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/pages/login.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->

<link rel="shortcut icon" href="${ctx}/pub/favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
	<!-- BEGIN LOGIN -->
	<div class="clearfix" style="padding: 50px">
		<div class="content" style="width: 100%; max-width: 800px">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-4">
							<%-- <img alt="" src="${ctx}/resources/images/logo.png"> --%>
						</div>
						<div class="col-md-8">
							<h2 style="color: #555555">
								<s:property value="%{systemTitle}" />
							</h2>
						</div>
					</div>
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<!-- BEGIN LOGIN FORM -->
					<form id="login-form" class="login-form" action="${ctx}/j_spring_security_check?redirect=${redirect}" method="post">
						<h3 class="form-title" style="color: #666666">系统登录</h3>
						
						<div class="form-group">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label visible-ie8 visible-ie9">登录账号</label>
							<div class="input-icon">
								<i class="fa fa-user"></i> <input class="form-control placeholder-no-fix" type="text" autocomplete="off"
									placeholder="登录账号" name="j_username" value="" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label visible-ie8 visible-ie9">登录密码</label>
							<div class="input-icon">
								<i class="fa fa-lock"></i> <input class="form-control placeholder-no-fix" type="password" autocomplete="off"
									placeholder="登录密码" name="j_password" />
							</div>
						</div>
						<div class="form-actions">
							<%--<label> <input type="checkbox" name="_spring_security_remember_me" checked="true" value="true" />--%>
								<%--记住我(两周内自动登录)--%>
							<%--</label>--%>
							<button type="submit" class="btn blue pull-right">
								登录 <i class="m-icon-swapright m-icon-white"></i>
							</button>
						</div>
					</form>
					<!-- END LOGIN FORM -->
				</div>
			</div>

			<!-- BEGIN COPYRIGHT -->
			<div class="row">
				<div class="col-md-12">
					<div class="copyright">
						<span>版权所有&nbsp;&nbsp;&nbsp;&nbsp; 融云管理服务器 Copyright © 2015</span>
					</div>
				</div>
			</div>
			<!-- END COPYRIGHT -->

		</div>
	</div>
	<!-- END LOGIN -->

</body>
<!-- END BODY -->

<script type="text/javascript" src="${ctx}/resources/jquery/jquery.min.js"></script>
<script src="${ctx}/resources/assets/plugins/backstretch/jquery.backstretch.min.js?_=${buildVersion}" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
    $('#_username').focus();
	$(this).keydown(function(event){
		if(event.keyCode == 13) {
		   if($('#_username').val()==''){
		       $('#_username').focus();
		   }else if($('#_pwd').val()==''){
		       $('#_pwd').focus();	       
		   } else{
				$('#loginForm').submit();
		   }
		}
	});

    var imageHV = 'h';
    if ($(window).height() > $(window).width()) {
        imageHV = 'v';
        //$(".content").removeClass('pull-left');
    }

    $.backstretch([ "${ctx}/resources/images/bg01_" + imageHV + ".jpg", "${ctx}/resources/images/bg02_" + imageHV + ".jpg" ], {
        fade : 1000,
        duration : 8000
    });
});
</script>

</html>