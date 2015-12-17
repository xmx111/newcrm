<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-config-sys-manager-inputBasic"
	action="${ctx}/config/sys/manager/save.json" method="post" >
	<input type="hidden" name="id" value="${clone eq true ? '' : dto.id}" />
    <input type="hidden" name="code" value="${dto.code}" id="code" />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-config-sys-manager-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">账号名</label>
					<div class="controls">
		                <input class="form-control" name="loginName" required="required" value="${dto.loginName}" id="loginName" type="text">
						<input name="name" value="${dto.name}" id="name" type="hidden">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">账号密码</label>
					<div class="controls">
		                <input class="form-control" name="password" value="" id="password" ${dto.id eq null ? 'required' : ''} type="password">
					</div>
				</div>
            </div>
        </div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">账号类型</label>
					<div class="controls">
						<input type="hidden" id="typeKey" value="${dto.type}">
						<select name="type" id="type"></select>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">选择关联信息</label>
					<div class="controls">
						<div class="input-icon right supplier-input-div">
							<i class="fa fa-ellipsis-h supplier-select"></i>
							<input class="form-control supplier-select" name="supplier.name" value="${dto.supplier.name}" id="supplier_name" readonly="readonly" type="text"  placeholder="选择供货商" />
							<input name="supplier.id" id="supplier_id" value="${dto.supplier.id}" type="hidden">
						</div>

						<div class="input-icon right employee-input-div">
							<i class="fa fa-ellipsis-h employee-select"></i>
							<input class="form-control employee-select" name="employee.name" value="${dto.employee.name}" id="employee_name" readonly="readonly" type="text"  placeholder="选择员工" />
							<input name="employee.id" id="employee_id" value="${dto.employee.id}" type="hidden">
						</div>

						<div class="input-icon right custom-input-div">
							<i class="fa fa-ellipsis-h custom-select"></i>
							<input class="form-control custom-select" name="custom.name" value="${dto.custom.name}" id="custom_name" readonly="readonly" type="text"  placeholder="选择负责人" />
							<input name="custom.id" id="custom_id" value="${dto.custom.id}" type="hidden">
						</div>
					</div>
				</div>
			</div>
		</div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">权限</label>
					<div class="controls">
		                <table class="table table-striped table-advance table-bordered table-hover table-authority">
							<thead>
							</thead>
							<tbody>
                            <c:forEach items="${roles}" var="role" varStatus="vs">
                                <c:if test="${vs.count % 2 == 1}">
                                    <tr align="left">
                                </c:if>
                                <td><input type="checkbox" name="roles[${vs.count}].id" value="${role.id}" ${roleMap[role.id] != null ? "checked='true'" : ""}>${role.name}</td>
                                <c:if test="${vs.count % 2 == 0}">
                                    </tr>
                                </c:if>
                            </c:forEach>
							</tbody>
						</table>
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-config-sys-manager-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${ctx}/resources/script/config/sys/manager/manager-inputBasic.js" />
<%@ include file="/WEB-INF/common/ajax-footer.jsp"%>