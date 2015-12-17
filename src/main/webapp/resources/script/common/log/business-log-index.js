$(function() {
    $(".grid-common-business-log-index").data("gridOptions", {
        url : WEB_ROOT + '/common/log/findByPage.json',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true                          
        }, {
            label : '访问IP',
            name : 'clientIP',
            width : 255,
            editable: true,
            align : 'left'
        }, {
            label : '访问地址',
            name : 'requestUrl',
            width : 255,
            editable: true,
            align : 'left'
        }, {
            label : '业务类型',
            name : 'type',
            width : 60,
            editable: true,
            align : 'right'
        }, {
            label : '操作类型',
            name : 'operType',
            width : 60,
            editable: true,
            align : 'right'
        }, {
            label : '操作结果',
            name : 'result',
            width : 200,
            formatter : 'checkbox',
            editable: true,
            align : 'center'
        } ],
        editurl : WEB_ROOT + '/common/log/save.json',
        delurl : WEB_ROOT + '/common/log/delete.json',
        fullediturl : WEB_ROOT + '/common/log/inputTabs.htm'
    });
});
