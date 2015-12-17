<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-common-business-log-inputBasic"
	action="${ctx}/common/log/save.json" method="post" >
	<input type="hidden" name="id" value="${clone eq true ? '' : dto.id}" />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-common-business-log-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">操作内容</label>
					<div class="controls">
		                <input class="form-control" name="content" value="${dto.content}" id="content" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">访问IP</label>
					<div class="controls">
		                <input class="form-control" name="clientIP" value="${dto.clientIP}" id="clientIP" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">访问地址</label>
					<div class="controls">
		                <input class="form-control" name="requestUrl" value="${dto.requestUrl}" id="requestUrl" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务类型</label>
					<div class="controls">
		                <input class="form-control" name="type" value="${dto.type}" id="type" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">操作类型</label>
					<div class="controls">
		                <input class="form-control" name="operType" value="${dto.operType}" id="operType" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">操作结果</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="result" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="result" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-common-business-log-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${ctx}/resources/script/common/log/business-log-inputBasic.js" />
<%@ include file="/WEB-INF/common/ajax-footer.jsp"%>