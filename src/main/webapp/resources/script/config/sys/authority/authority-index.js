$(function() {
    $(".grid-config-sys-authority-index").data("gridOptions", {
        url : WEB_ROOT + '/config/sys/authority/findByPage.json',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true                          
        }, {
            label : '编码',
            name : 'code',
            width : 255,
            editable: true,
            align : 'left'
        }, {
            label : '名称',
            name : 'name',
            width : 255,
            editable: true,
            align : 'left'
        }, {
            label : '排序',
            name : 'sequence',
            width : 60,
            editable: true,
            align : 'right'
        } ],
        multiselect : false // 定义是否可以多选
        //editurl : WEB_ROOT + '/config/sys/authority/save.json',
        //delurl : WEB_ROOT + '/config/sys/authority/delete.json',
        //fullediturl : WEB_ROOT + '/config/sys/authority/inputTabs.htm'
    });
});
