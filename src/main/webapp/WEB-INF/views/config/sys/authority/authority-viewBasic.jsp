<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<div class="container-fluid data-view">
    <div class="well form-horizontal">
        <div class="row-fluid">
            <div class="span6">
	            <div class="form-group">
					<label class="control-label">编码</label>
					<div class="controls">
		                <input class="form-control" name="code" value="" id="code" type="text">
	                </div>
		        </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
	            <div class="form-group">
					<label class="control-label">名称</label>
					<div class="controls">
		                <input class="form-control" name="name" value="" id="name" type="text">
	                </div>
		        </div>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span6">
	            <div class="form-group">
					<label class="control-label">排序</label>
					<div class="controls">
		                <input class="form-control" name="sequence" value="" id="sequence" type="text">
	                </div>
		        </div>
            </div>
        </div>
    </div>    
</div>