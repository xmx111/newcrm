<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<div class="row">
	<div class="col-md-12">
		<form class="form-horizontal form-bordered form-label-stripped form-validation" method="post" formNotRule="true" action="${ctx}/main/editPwd.json">
			<div class="form-body">
				<div class="form-group">
					<label class="control-label">输入原密码</label>
					<div class="controls">
						<input type="password" name="oldPwd" class="form-control" required="true">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">输入新密码</label>
					<div class="controls">
						<input type="password" name="newPwd" class="form-control" required="true">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">确认新密码</label>
					<div class="controls">
						<input type="password" name="cfmPwd" class="form-control" required="true" data-rule-equaltobyname="newPwd">
					</div>
				</div>
			</div>
			<div class="form-actions right">
				<button class="btn blue" type="submit">
					<i class="fa fa-check"></i> 保存
				</button>
			</div>
		</form>
	</div>
</div>