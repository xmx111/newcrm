<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-erp-business-business-inputBasic"
	action="${ctx}/erp/business/save.json" method="post" >
	<input type="hidden" name="id" value="${clone eq true ? '' : dto.id}" />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-erp-business-business-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否删除</label>
					<div class="controls">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务名字</label>
					<div class="controls">
		                <input class="form-control" name="name" value="${dto.name}" id="name" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务名字缩写</label>
					<div class="controls">
		                <input class="form-control" name="abbr" value="${dto.abbr}" id="abbr" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">贷款额度下限</label>
					<div class="controls">
		                <input class="form-control" name="creditLowerLimit" value="${dto.creditLowerLimit}" id="creditLowerLimit" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否需要征信</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="needCredit" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="needCredit" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否需要流水</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="needAccount" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="needAccount" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">贷款额度上限</label>
					<div class="controls">
		                <input class="form-control" name="creditUpperLimit" value="${dto.creditUpperLimit}" id="creditUpperLimit" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否需要房产证明</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="needHouse" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="needHouse" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否需要车子证明</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="needCar" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="needCar" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-erp-business-business-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${ctx}/resources/script/erp/business/business-inputBasic.js" />
<%@ include file="/WEB-INF/common/ajax-footer.jsp"%>