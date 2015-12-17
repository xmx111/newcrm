<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<div>
	您需要添加${message}权限才可以进行访问! <a href="${ctx}/login.htm?redirect=${redirect}">重新登陆</a>
</div>