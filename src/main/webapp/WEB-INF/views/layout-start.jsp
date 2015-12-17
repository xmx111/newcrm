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
<title>${systemTitle}</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">

<!-- Basic Javascripts -->
<script src="${ctx}/resources/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${ctx}/resources/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/select2/select2_metro.css" />

<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${ctx}/resources/assets/css/style-metronic.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/themes/light.css" rel="stylesheet" type="text/css" id="style_color" />

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/assets/plugins/bootstrap-switch/static/stylesheets/bootstrap-switch-metro.css" />
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/bootstrap-timepicker/compiled/timepicker.css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/assets/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/bootstrap-editable/inputs-ext/address/address.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/bootstrap-toastr/toastr.min.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/jquery-ui/redmond/jquery-ui-1.10.3.custom.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/jquery-jqgrid/plugins/ui.multiselect.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/jquery-jqgrid/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/app/bootstrap-jqgrid.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/kindeditor/themes/default/default.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/jquery-ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" />

<%--<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/tooltipster/css/tooltipster.css" />--%>
<%--<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/tooltipster/css/themes/tooltipster-light.css" />--%>
<%--<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/tooltipster/css/themes/tooltipster-noir.css" />--%>
<%--<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/tooltipster/css/themes/tooltipster-punk.css" />--%>
<%--<link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/extras/tooltipster/css/themes/tooltipster-shadow.css" />--%>

<link href="${ctx}/resources/assets/plugins/fullcalendar/fullcalendar.css" rel="stylesheet" />

<link href="${ctx}/resources/assets/css/pages/tasks.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/pages/search.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/assets/css/pages/lock.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/resources/assets/app/custom.css?_=${buildVersion}" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="${ctx}/pub/favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-body" style="min-height: 600px">
	<script>
        $("body").block({
            message : '<img src="${ctx}/resources/assets/img/ajax-modal-loading.gif" width="80px" align="">',
            centerY : true,
            css : {
                top : '10%',
                border : 'none',
                padding : '2px',
                backgroundColor : 'none'
            },
            overlayCSS : {
                backgroundColor : '#000',
                opacity : 0.6,
                cursor : 'wait'
            }
        });
    </script>
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">

		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			<!-- BEGIN LOGO -->
			<a class="navbar-brand" href="${ctx}/index.htm"
				style="padding-top: 5px; padding-bottom: 5px; margin-left: 2px; width: 400px">
				<h3 style="margin-top: 2px; margin-bottom: 0px; color: #dddddd;">
					<%-- <img src="resources/images/logo.jpg"/> --%>
					${systemTitle}
				</h3>
			</a>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <img
				src="${ctx}/resources/assets/img/menu-toggler.png" alt="" />
			</a>

			<!-- END RESPONSIVE MENU TOGGLER -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav navbar-nav pull-right" style="margin-right: 50px">
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown user" style="padding-top: 5px"><a href="javascript:;" class="dropdown-toggle"
					data-toggle="dropdown" data-close-others="true"> <span class="username"><i class="fa fa-user"></i> ${operation.name}</span> <i class="fa fa-angle-down"></i>
				</a>
					<ul class="dropdown-menu">
						<li class="hide"><a href="extra_profile.html"><i class="fa fa-user"></i> My Profile</a></li>
						<li class="hide"><a href="page_calendar.html"><i class="fa fa-calendar"></i> My Calendar</a></li>
						<li class="hide"><a href="inbox.html"><i class="fa fa-envelope"></i> My Inbox <span
								class="badge badge-danger">3</span></a></li>
						<li class="hide"><a href="javascript:;"><i class="fa fa-tasks"></i> My Tasks <span
								class="badge badge-success">7</span></a></li>
						<li class="divider hide"></li>
						<li><a href="javascript:;" id="trigger_fullscreen"><i class="fa fa-move"></i> 全屏显示</a></li>
						<li><a id="trigger_passwd" href="${ctx}/main/profile-pwd.htm" title="修改密码"><i class="fa fa-key"></i> 修改密码</a></li>
						<li><a href="javascript:;" id="a-lock-screen"><i class="fa fa-lock"></i> 锁定系统</a></li>
						<li><a href="javascript:;" id="a-logout"><i class="fa fa-sign-out"></i> 注销登录</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->

			</ul>
			<!-- END TOP NAVIGATION MENU -->

		</div>

		<!-- BEGIN STYLE CUSTOMIZER -->
		<div class="theme-panel hidden-xs hidden-sm pull-right" style="margin-top: -3px; position: absolute; right: 0px">
			<div class="toggler"></div>
			<div class="toggler-close"></div>
			<div class="theme-options">
				<div class="theme-option theme-colors clearfix">
					<span>颜色样式</span>
					<ul>
						<li class="color-black current color-default" data-style="default"></li>
						<li class="color-blue" data-style="blue"></li>
						<li class="color-brown" data-style="brown"></li>
						<li class="color-purple" data-style="purple"></li>
						<li class="color-grey" data-style="grey"></li>
						<li class="color-white color-light" data-style="light"></li>
					</ul>
				</div>
				<div class="theme-option">
					<span>页面布局</span> <select class="layout-option form-control input-small">
						<option value="fluid" selected="selected">扩展</option>
						<option value="boxed">收缩</option>
					</select>
				</div>
				<div class="theme-option">
					<span>页面头部</span> <select class="header-option form-control input-small">
						<option value="fixed" selected="selected">固定</option>
						<option value="default">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>侧边菜单</span> <select class="sidebar-option form-control input-small">
						<option value="fixed">浮动</option>
						<option value="default" selected="selected">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>页面底部</span> <select class="footer-option form-control input-small">
						<option value="fixed">固定</option>
						<option value="default" selected="selected">自动</option>
					</select>
				</div>
				<div class="theme-option">
					<span>右键菜单</span> <select class="context-menu-option form-control input-small">
						<option value="enable" selected="selected">启用</option>
						<option value="disable">禁用</option>
					</select>
				</div>
				<div class="theme-option">
					<span>表格布局</span> <select class="grid-shrink-option form-control input-small">
						<option value="auto" selected="selected">自动</option>
						<option value="true">收缩</option>
					</select>
				</div>
			</div>
		</div>
		<!-- END BEGIN STYLE CUSTOMIZER -->

		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
		<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">Modal title</h4>
					</div>
					<div class="modal-body">Widget settings form goes here</div>
					<div class="modal-footer">
						<button type="button" class="btn blue">Save changes</button>
						<button type="button" class="btn default" data-dismiss="modal">Close</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
		<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
		<!-- HIDDEN PARAM VALUE -->
		<input type="hidden" value="${indexMenuParams}" id="indexMenuParams">
		<input type="hidden" value="${indexMenuParams2}" id="indexMenuParams2">
		<!-- BEGIN SIDEBAR1 -->
		<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU1 -->
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-xs"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
				<li>
					<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
					<div class="sidebar-search">
						<div class="form-container">
							<div class="input-box">
								<a href="javascript:;" class="remove"></a> <input type="text" name="search" placeholder="菜单项快速查询过滤..." value=""
									title="试试输入菜单名称拼音首字母" /> <input type="button" class="submit" value=" " />
							</div>
						</div>
					</div> <!-- END RESPONSIVE QUICK SEARCH FORM -->
				</li>
				<c:forEach items="${menus}" varStatus="status" var="root">
					<li class="menu-root ${root.checked?'open':''}" ><a href="javascript:;"
						data-code="${root.id}"> <i class="fa fa-cogs"></i> <span class="title">${root.name}</span> <span class="arrow ${root.checked?'open':''}"></span>
					</a> <c:if test="${root.hasChildren}">
							<ul class="sub-menu" style="display: ${root.checked?'block':'none'};">
								<c:forEach items="${root.children}" var="childLevel1">
									<c:if test="${childLevel1.hasChildren}">
										<li><a href="javascript:;" data-code="${childLevel1.id}"> <i
												class="fa fa-ellipsis-v"></i> <span class="title">${childLevel1.name}</span> <span
												class="arrow ${childLevel1.checked?'open':''}"></span>
										</a>
											<ul class="sub-menu" style="display: ${childLevel1.checked?'block':'none'};">
												<c:forEach items="${childLevel1.children}" var="childLevel2">
													<c:if test="${childLevel2.hasChildren}">
														<li><a href="javascript:;" data-code="${childLevel2.id}"> <i
																class="fa fa-ellipsis-v"></i> <span class="title">${childLevel2.name}</span>
																<span class="arrow ${childLevel2.checked?'open':''}"></span>
														</a>
															<ul class="sub-menu" style="display: ${childLevel2.checked?'block':'none'};">
																<c:forEach items="${childLevel2.children}" var="childLevel3">
																	<li><a class="ajaxify" data-code="${childLevel3.id}"
																		href="${ctx}/${childLevel3.url}"
																		rel="address:/${childLevel3.id}"> <i class="fa fa-dot-circle-o"></i> ${childLevel3.name}</a></li>
																</c:forEach>
															</ul></li>
													</c:if>
													<c:if test="${not childLevel2.hasChildren}">
														<li><a class="ajaxify" data-code="${childLevel2.id}"
															href="${ctx}/${childLevel2.url}"
															rel="address:/${childLevel2.id}" target="_blank"> <i class="fa fa-dot-circle-o"></i>
																${childLevel2.name}</a></li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:if>
									<c:if test="${not childLevel1.hasChildren}">
										<li><a class="ajaxify" data-code="${childLevel1.id}"
											href="${ctx}/${childLevel1.url}" rel="address:/${childLevel1.id}">
												<i class="fa fa-dot-circle-o"></i> ${childLevel1.name}
										</a></li>
									</c:if>
								</c:forEach>
							</ul>
						</c:if></li>
				</c:forEach>
			</ul>
			<!-- END SIDEBAR MENU1 -->
		</div>
		<!-- END SIDEBAR1 -->
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<div class="row" style="margin-left: 0px; margin-right: 0px">
				<div class="col-md-12" style="padding-left: 0px; padding-right: 0px">
					<ul class="page-breadcrumb breadcrumb" id="layout-nav" style="margin-top: 0px; margin-bottom: 5px;">
						<li class="btn-group" style="right: 0px;">
							<button data-close-others="true" data-delay="1000" data-toggle="dropdown" class="btn blue dropdown-toggle"
								type="button">
								<span><i class="fa fa-reorder"></i> 访问列表</span> <i class="fa fa-angle-down"></i>
							</button>
							<ul role="menu" class="dropdown-menu">
							</ul>
							<button class="btn default btn-dashboard" type="button">
								<i class="fa fa-home"></i> 首页
							</button>
							<button class="btn default btn-close-active" type="button">
								<i class="fa fa-times"></i>
							</button>
						</li>
						<li><i class="fa fa-home"></i> <a href="javascript:;">首页</a></li>
					</ul>
					<div class="tab-content">
						<div id="tab_content_dashboard"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- BEGIN PAGE -->

	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner">
			2013 &copy;
			<%=request.getServerName()%><%@ include file="/WEB-INF/common/app-ver.jsp"%></div>
		<div class="footer-tools">
			<span class="go-top"> <i class="fa fa-angle-up"></i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->

	<!-- BEGIN FileUpload FORM -->
	<div class="modal fade" id="fileupload-dialog" tabindex="-1" role="basic" aria-hidden="true">
		<div class="modal-dialog modal-wide">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title">文件上传</h4>
				</div>
				<div class="modal-body">
					<form id="fileupload" enctype="multipart/form-data" method="POST">
						<input type="hidden" name="attachmentName" value="attachments" />
						<div class="row fileupload-buttonbar">
							<div class="col-lg-7">
								<!-- The fileinput-button span is used to style the file input field as button -->
								<span class="btn green fileinput-button"> <i class="fa fa-plus"></i> <span>添加文件...</span> <input
									type="file" multiple="" name="files">
								</span>
								<button class="btn blue start" type="submit">
									<i class="fa fa-upload"></i> <span>开始上传</span>
								</button>
								<button class="btn yellow cancel" type="reset">
									<i class="fa fa-ban"></i> <span>取消上传</span>
								</button>
								<!-- The loading indicator is shown during file processing -->
								<span class="fileupload-loading"></span>
							</div>
							<!-- The global progress information -->
							<div class="col-lg-5 fileupload-progress fade">
								<!-- The global progress bar -->
								<div aria-valuemax="100" aria-valuemin="0" role="progressbar" class="progress progress-striped active">
									<div style="width: 0%;" class="progress-bar progress-bar-success"></div>
								</div>
								<!-- The extended global progress information -->
								<div class="progress-extended">&nbsp;</div>
							</div>
						</div>
						<table class="table table-striped clearfix" role="presentation">
							<tbody class="files"></tbody>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn blue btn-add">添加</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- END FileUpload FORM -->

	<div class="page-lock" id="page-lock" style="text-align: left; color: #eeeeee; display: none">
		<div class="page-logo">
			<h3>
				${systemTitle}
			</h3>
		</div>
		<div class="page-body">
			<div class="tile bg-blue" style="margin-top: 10px; margin-left: 10px">
				<div class="tile-body">
					<i class="fa fa-lock"></i>
				</div>
			</div>
			<div class="page-lock-info">
				<h1>
					${operation.name}
				</h1>
				<form id="form-unlock" action="${ctx}/main/lock.htm" class="form-inline">
					<div class="input-group input-medium">
						<input type="password" name="password" placeholder="输入解锁码..." class="form-control" autocomplete="off"> <span
							class="input-group-btn">
							<button class="btn blue icn-only" type="submit">
								<i class="m-icon-swapright m-icon-white"></i>
							</button>
						</span>
					</div>
					<div class="relogin">请输入登录账号解锁</div>
				</form>
			</div>
		</div>
		<div class="page-footer">
			2013 &copy;
			<%=request.getServerName()%>
		</div>
	</div>

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<script id="template-upload" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
            <tr class="template-upload fade">
                <td>
                    <p class="name">{%=file.name%}</p>
                    {% if (file.error) { %}
                        <div><span class="label label-danger">Error</span> {%=file.error%}</div>
                    {% } %}
                </td>
                <td align="right">
                    <p class="size">{%=o.formatFileSize(file.size)%}</p>
                    {% if (!o.files.error) { %}
                        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
                        <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                        </div>
                    {% } %}
                </td>
                <td align="right">
                    {% if (!o.files.error && !i && !o.options.autoUpload) { %}
                        <button type="button" class="btn blue start">
                            <i class="fa fa-upload"></i>
                            <span>上传</span>
                        </button>
                    {% } %}
                    {% if (!i) { %}
                        <button type="button" class="btn red cancel">
                            <i class="fa fa-ban"></i>
                            <span>取消</span>
                        </button>
                    {% } %}
                </td>
            </tr>
        {% } %}
    </script>
	<!-- The template to display files available for download -->
	<script id="template-download" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
            <tr class="template-download">
                <td>
                    <p class="name">
                        <input type="hidden" name="{%=file.attachmentName%}" value="{%=file.id%}"/>
                        {% if (file.url) { %}
                            <a target="_blank" href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                        {% } else { %}
                            <span>{%=file.name%}</span>
                        {% } %}
                    </p>
                    {% if (file.error) { %}
                        <div><span class="label label-danger">Error</span> {%=file.error%}</div>
                    {% } %}
                </td>
                <td align="right" class="td-size">
                    <span class="size">{%=file.size%}</span>
                </td>
                <td align="right" class="td-op">
                        <button type="button" class="btn default" onclick="$(this).closest('tr').remove()">
                            <i class="fa fa-trash-o"></i>
                            <span>删除</span>
                        </button>
                </td>
            </tr>
        {% } %}
    </script>

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
	<script src="${ctx}/resources/assets/plugins/respond.min.js"></script>
	<script src="${ctx}/resources/assets/plugins/excanvas.min.js"></script> 
	<![endif]-->

	<script src="${ctx}/resources/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="${ctx}/resources/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>

	<script src="${ctx}/resources/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/select2/select2.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/jquery-validation/localization/messages_zh.js"></script>

	<script type="text/javascript" src="${ctx}/resources/assets/plugins/fuelux/js/spinner.min.js"></script>

	<script type="text/javascript" src="${ctx}/resources/assets/plugins/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-fileupload/bootstrap-fileupload.js"></script>

	<script type="text/javascript" src="${ctx}/resources/assets/plugins/select2/select2.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
	<%-- <script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script> --%>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-datetimepicker/js/moment.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-datetimepicker/js/moment-lang-zh-cn.js"></script>
	
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/clockface/js/clockface.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<%-- <script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-timepicker/js/bootstrap-timepicker.js"></script> --%>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/jquery.input-ip-address-control-1.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/jquery-multi-select/js/jquery.quicksearch.js"></script>
	<script src="${ctx}/resources/assets/plugins/jquery.pwstrength.bootstrap/src/pwstrength.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/bootstrap-switch/static/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/jquery-tags-input/jquery.tagsinput.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/bootstrap-markdown/js/bootstrap-markdown.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/bootstrap-markdown/lib/markdown.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/jquery.pulsate.min.js"></script>
	<script src="${ctx}/resources/assets/plugins/bootstrap-toastr/toastr.min.js"></script>
	<script src="${ctx}/resources/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/assets/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-editable/bootstrap-editable/js/bootstrap-editable.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/assets/plugins/bootstrap-editable/inputs-ext/address/address.js"></script>

    <!--
	<script src="${ctx}/resources/assets/plugins/flot/jquery.flot.js"></script>
	<script src="${ctx}/resources/assets/plugins/flot/jquery.flot.resize.js"></script>
	<script src="${ctx}/resources/assets/plugins/flot/jquery.flot.pie.js"></script>
	<script src="${ctx}/resources/assets/plugins/flot/jquery.flot.stack.js"></script>
	<script src="${ctx}/resources/assets/plugins/flot/jquery.flot.crosshair.js"></script>
	<script src="${ctx}/resources/assets/plugins/flot/jquery.flot.time.js"></script>
	-->

	<%--<script src="${ctx}/resources/assets/plugins/fancybox/source/jquery.fancybox.pack.js"></script>--%>

	<script src="${ctx}/resources/assets/plugins/fullcalendar/lib/moment.min.js"></script>
	<script src="${ctx}/resources/assets/plugins/fullcalendar/fullcalendar.min.js"></script>
	<script src="${ctx}/resources/assets/plugins/fullcalendar/lang/zh-cn.js"></script>

	<!-- BEGIN:File Upload Plugin JS files-->
	<!-- The Templates plugin is included to render the upload/download listings -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/vendor/tmpl.min.js"></script>
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/vendor/load-image.min.js"></script>
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
	<!-- The basic File Upload plugin -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
	<!-- The File Upload processing plugin -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
	<!-- The File Upload image preview & resize plugin -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/jquery.fileupload-image.js"></script>
	<!-- The File Upload audio preview plugin -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/jquery.fileupload-audio.js"></script>
	<!-- The File Upload video preview plugin -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/jquery.fileupload-video.js"></script>
	<!-- The File Upload validation plugin -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
	<!-- The File Upload user interface plugin -->
	<script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
	<!-- The main application script -->
	<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
	<!--[if (gte IE 8)&(lt IE 10)]>
    <script src="${ctx}/resources/assets/plugins/jquery-file-upload/js/cors/jquery.xdr-transport.js"></script>
    <![endif]-->
	<!-- END:File Upload Plugin JS files-->

	<script src="${ctx}/resources/assets/extras/pinyin.js?_=${buildVersion}"></script>
	<script src="${ctx}/resources/assets/extras/JSPinyin.js?_=${buildVersion}"></script>

	<script src="${ctx}/resources/assets/extras/jquery-jqgrid/plugins/ui.multiselect.js?_=${buildVersion}"></script>
	<script src="${ctx}/resources/assets/extras/jquery-jqgrid/js/i18n/grid.locale-cn.js?_=${buildVersion}"></script>
	<script src="${ctx}/resources/assets/extras/jquery-jqgrid/js/jquery.jqGrid.src.js?_=111"></script>

	<script src="${ctx}/resources/assets/extras/jquery-ztree/js/jquery.ztree.all-3.5.js?_=${buildVersion}"></script>

	<script src="${ctx}/resources/assets/extras/jquery.address/jquery.address-1.5.min.js?_=${buildVersion}"></script>

    <script src="${ctx}/resources/assets/extras/kindeditor/kindeditor-ext.js?_=${buildVersion}"></script>
	
	<script type="text/javascript">
        var WEB_ROOT = "${ctx}";
		var ctx = '${ctx}';
        var AUTH_USER = {
            uid : '${operation.id}',
            username : '${operation.name}" />'
        };
        KindEditor.options.uploadJson = '${ctx}/upload.json;JSESSIONID=<%=session.getId()%>';
		KindEditor.options.postParams = {"JSESSIONID":"<%=session.getId()%>"};
		KindEditor.options.fillDescAfterUploadImage = false;

    </script>

	<%--<script type="text/javascript" src="${ctx}/resources/assets/extras/tooltipster/js/jquery.tooltipster.min.js"></script>--%>

	<script src="${ctx}/resources/assets/scripts/app.js"></script>
	<script src="${ctx}/resources/assets/extras/jquery.form.js"></script>
	<script src="${ctx}/resources/assets/extras/bootstrap-contextmenu.js"></script>
	<script src="${ctx}/resources/assets/extras/taffydb/taffy-min.js"></script>
	<script src="${ctx}/resources/assets/app/dynamic-table.js"></script>
	<script src="${ctx}/resources/assets/app/util.js"></script>
	<script src="${ctx}/resources/assets/app/global.js"></script>
	<script src="${ctx}/resources/assets/app/grid.js"></script>
	<script src="${ctx}/resources/assets/app/form-validation.js"></script>
	<script src="${ctx}/resources/assets/app/page.js"></script>
	<script src="${ctx}/resources/script/common/utils/tools.js"></script>
	<script src="${ctx}/resources/script/Array.js"></script>
	
	<script>
        $(function() {

            // console.profile('Profile Sttart');
            App.init();
            Util.init();
            Global.init();
            FormValidation.init();
            
            App.unblockUI($("body"));
            console.profileEnd();

            // 初始跳转
            if ('${addreeChangePath}' != ''){
                $.address.value(WEB_ROOT+'${addreeChangePath}');
            }
        });
        
    </script>
</body>
<!-- END BODY -->
</html>