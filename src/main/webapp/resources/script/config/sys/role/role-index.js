$(function() {
    
    $(".grid-config-sys-role-index").data("gridOptions", {
        url : WEB_ROOT + '/config/sys/role/findByPage.json',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true                          
        }, {
            label : '编码',
            name : 'code',
            width : 80,
            editable: true,
            align : 'left'
        }, {
            label : '名称 ',
            name : 'name',
            width : 80,
            editable: true,
            align : 'left'
        }, {
            label : '描述',
            name : 'description',
            width : 80,
            editable: true,
            align : 'left'
        }, {
            label : '操作',
            name : 'payType',
            formatter : function(cellValue, options, rowdata, action) {
                var url = WEB_ROOT + '/config/sys/role/roleAuthorize.htm?id='+rowdata.id;
                return '<a data-toggle="modal-ajaxify" href='+url+' title="设置权限"><button type="button" class="btn btn-warning">设置权限</button></a>';
            },
            editable: false,
            width : 45,
            align : 'center'
        } ],
        editurl : WEB_ROOT + '/config/sys/role/save.json',
        delurl : WEB_ROOT + '/config/sys/role/delete.json',
        fullediturl : WEB_ROOT + '/config/sys/role/edit.htm'
    });
    
});
