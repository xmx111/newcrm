<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-${full_entity_name_field}-inputBasic"
	action="${ctx}${model_path}/save.json" method="post" >
	<input type="hidden" name="id" value="${'$'}{clone eq true ? '' : dto.id}" />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-${full_entity_name_field}-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
        <#list entityFields as entityField>
        <#if entityField.edit>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">${entityField.title}</label>
					<div class="controls">
						<#if entityField.fieldType=='Boolean'>
						<label class="radio-inline">
							<input name="${entityField.fieldName}" id="typeFalse" checked="checked" value="false" type="radio">否
						</label>
						<label class="radio-inline">
							<input name="${entityField.fieldName}" id="typeTrue" value="true" type="radio">是
						</label>
		                <#elseif entityField.enumField>
		                <#elseif entityField.fieldType=='Date'>
		                <div data-date-viewmode="years" data-date-format="yyyy-mm-dd" class="input-group  date date-picker">
							<input name="${entityField.fieldName}" id="${entityField.fieldName}" class="form-control" value="${'$'}{dto.${entityField.fieldName}}" dateinputclass="" format="yyyy-MM-dd" type="text">
							<span class="input-group-btn">
								<button type="button" class="btn default date-set">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
		                <#elseif (entityField.fieldType=='String' && entityField.listWidth gt 255)>
		                <textarea class="form-control" name="${entityField.fieldName}" rows="3" id="${entityField.fieldName}"></textarea>                                             
		                <#else>
		                <input class="form-control" name="${entityField.fieldName}" value="${'$'}{dto.${entityField.fieldName}}" id="${entityField.fieldName}" type="text">
		                </#if>
					</div>
				</div>
            </div>
        </div>
        </#if>
        </#list>  
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-${full_entity_name_field}-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${ctx}/resources/script${model_path}/${entity_name_field}-inputBasic.js" />
<%@ include file="/WEB-INF/common/ajax-footer.jsp"%>