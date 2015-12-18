$(function() {
    $(".grid-erp-business-business-index").data("gridOptions", {
        url : WEB_ROOT + '/erp/business/findByPage.json',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true                          
        }, {
            label : '是否删除',
            name : 'deleted',
            formatter : 'select',
            searchoptions : {
                value : Util.getCacheEnumsByType('DeleteTypeEnum')
            },
            width : 80,
            editable: true,
            align : 'center'
        }, {
            label : '业务名字',
            name : 'name',
            width : 200,
            editable: true,
            align : 'left'
        } ],
        editurl : WEB_ROOT + '/erp/business/save.json',
        delurl : WEB_ROOT + '/erp/business/delete.json',
        fullediturl : WEB_ROOT + '/erp/business/inputTabs.htm'
    });
});
