$(function() {
    $(".grid-erp-salesman-salesman-index").data("gridOptions", {
        url : WEB_ROOT + '/erp/salesman/findByPage.json',
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
            label : '业务员名字',
            name : 'name',
            width : 32,
            editable: true,
            align : 'left'
        }, {
            label : '手机号',
            name : 'mobile',
            width : 20,
            editable: true,
            align : 'left'
        }, {
            label : '性别',
            name : 'sex',
            formatter : 'select',
            searchoptions : {
                value : Util.getCacheEnumsByType('SexEnum')
            },
            width : 80,
            editable: true,
            align : 'center'
        }, {
            label : '微信号',
            name : 'weixin',
            width : 32,
            editable: true,
            align : 'left'
        }, {
            label : 'QQ号',
            name : 'qq',
            width : 32,
            editable: true,
            align : 'left'
        } ],
        editurl : WEB_ROOT + '/erp/salesman/save.json',
        delurl : WEB_ROOT + '/erp/salesman/delete.json',
        fullediturl : WEB_ROOT + '/erp/salesman/inputTabs.htm'
    });
});
