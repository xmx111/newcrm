<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
        <li><a class="tab-default" data-toggle="tab" href="authority!view?id=${parameters.id}">基本信息</a></li>
        <%-- 去掉注释添加功能Tab
        <li><a class="tab-default" data-toggle="tab" href="authority!forward?_to_=TODO&id=${parameters.id}">TODO关联</a></li>
        --%>
    </ul>
</div>