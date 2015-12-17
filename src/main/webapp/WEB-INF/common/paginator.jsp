<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<c:if test="${not empty paginator and not empty paginator.totalPages and  paginator.totalPages ne 0}">
<div class="panelBar">
        <c:if test="${not empty  pagerUrl}">
			<form id="pagerForm" method="post" action="${pagerUrl}">
	        <jodd:form bean="paginator" scope="request">
			<input type="hidden" name="pageIndex"/>
			<input type="hidden" name="pageSize"/>
			<input type="hidden" name="sort"/>
			<input type="hidden" name="dir"/>
	        </jodd:form>
		    <input type="hidden" name="keyword" value="${param.keyword}" />
			</form>
        </c:if>
        <div class="configpagecontier paginator" data-index="${paginator.pageIndex}" data-last="${paginator.totalPages}" <c:if test="${not empty target}"> target="${target}"</c:if> <c:if test="${not empty type}"> type="${type}"</c:if>>
            <a  href="javascript:void(0);" class="choosePage prevPage ${paginator.pageIndex eq 1 ? 'pageNumDisable' : ''}">上一页</a>
            <c:set var="begin">${paginator.pageIndex le  1 ? 1 : paginator.pageIndex-1 }</c:set>
            <c:set var="end">${paginator.totalPages le begin + 4 ? paginator.totalPages : begin + 4}</c:set>
            <c:forEach begin="${begin }" end="${end}" var="idx">
              <c:if test="${idx le paginator.totalPages}"><a href="javascript:void(0);" class="pageNum ${idx  eq paginator.pageIndex ? 'pageNumActive' : '' }">${idx}</a></c:if>
            </c:forEach>
            <a href="javascript:void(0);" class="choosePage nextPage ${paginator.pageIndex eq paginator.totalPages ? 'pageNumDisable' : ''}">下一页</a>
        </div>
</div>
</c:if>