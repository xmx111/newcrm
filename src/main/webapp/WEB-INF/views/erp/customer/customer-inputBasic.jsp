<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-erp-customer-customer-inputBasic"
	action="${ctx}/erp/customer/save.json" method="post" >
	<input type="hidden" name="id" value="${clone eq true ? '' : dto.id}" />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-erp-customer-customer-index">
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
					<label class="control-label">客户名字</label>
					<div class="controls">
		                <input class="form-control" name="name" value="${dto.name}" id="name" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">客户名字缩写</label>
					<div class="controls">
		                <input class="form-control" name="abbr" value="${dto.abbr}" id="abbr" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">手机号</label>
					<div class="controls">
		                <input class="form-control" name="mobile" value="${dto.mobile}" id="mobile" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">身份证</label>
					<div class="controls">
		                <input class="form-control" name="cardNo" value="${dto.cardNo}" id="cardNo" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">年龄</label>
					<div class="controls">
		                <input class="form-control" name="age" value="${dto.age}" id="age" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">地址</label>
					<div class="controls">
		                <input class="form-control" name="address" value="${dto.address}" id="address" type="text">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">性别</label>
					<div class="controls">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">婚否</label>
					<div class="controls">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否有房</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="hasHouse" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="hasHouse" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">房子是否贷款</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="isHouseCredit" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="isHouseCredit" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否有车</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="hasCar" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="hasCar" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">车是否贷款</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="isCarCredit" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="isCarCredit" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否有流水</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="hasAccount" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="hasAccount" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否信誉良好</label>
					<div class="controls">
						<label class="radio-inline">
							<input name="isGoodCredit" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="isGoodCredit" id="typeTrue" value="true" type="radio">是
						</label>
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-erp-customer-customer-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${ctx}/resources/script/erp/customer/customer-inputBasic.js" />
<%@ include file="/WEB-INF/common/ajax-footer.jsp"%>