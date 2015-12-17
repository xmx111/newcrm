<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/tags.jsp"%>
<div class="container-fluid data-view">
    <div class="well form-horizontal">
<#list entityFields as entityField><#if entityField.edit>
        <div class="row-fluid">
            <div class="span6">
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
							<input name="${entityField.fieldName}" id="${entityField.fieldName}" class="form-control" dateinputclass="" format="yyyy-MM-dd" type="text">
							<span class="input-group-btn">
								<button type="button" class="btn default date-set">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
		                <#elseif (entityField.fieldType=='String' && entityField.listWidth gt 255)>
		                <textarea class="form-control" name="${entityField.fieldName}" rows="3" id="${entityField.fieldName}"></textarea>                                             
		                <#else>
		                <input class="form-control" name="${entityField.fieldName}" value="" id="${entityField.fieldName}" type="text">
		                </#if>
	                </div>
		        </div>
            </div>
        </div>
        </#if>
        </#list>            
    </div>    
</div>