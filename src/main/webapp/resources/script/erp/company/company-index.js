$(function() {
    $(".grid-erp-company-company-index").data("gridOptions", {
        url : WEB_ROOT + '/erp/company/findByPage.json',
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
            label : '小贷公司名字',
            name : 'name',
            width : 128,
            editable: true,
            align : 'left'
        }, {
            label : '电话',
            name : 'tel',
            width : 20,
            editable: true,
            align : 'left'
        } ],
        editurl : WEB_ROOT + '/erp/company/save.json',
        delurl : WEB_ROOT + '/erp/company/delete.json',
        fullediturl : WEB_ROOT + '/erp/company/inputTabs.htm'
    });
});
