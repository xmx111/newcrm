$(function() {
    $(".grid-erp-customer-customer-index").data("gridOptions", {
        url : WEB_ROOT + '/erp/customer/findByPage.json',
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
            label : '客户名字',
            name : 'name',
            width : 32,
            editable: true,
            align : 'left'
        }, {
            label : '手机号',
            name : 'mobile',
            width : 16,
            editable: true,
            align : 'left'
        }, {
            label : '身份证',
            name : 'cardNo',
            width : 20,
            editable: true,
            align : 'left'
        }, {
            label : '年龄',
            name : 'age',
            width : 60,
            editable: true,
            align : 'right'
        }, {
            label : '地址',
            name : 'address',
            width : 128,
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
            label : '婚否',
            name : 'maritalStatus',
            formatter : 'select',
            searchoptions : {
                value : Util.getCacheEnumsByType('MaritalStatusEnum')
            },
            width : 80,
            editable: true,
            align : 'center'
        }, {
            label : '是否有房',
            name : 'hasHouse',
            width : 200,
            formatter : 'checkbox',
            editable: true,
            align : 'center'
        }, {
            label : '房子是否贷款',
            name : 'isHouseCredit',
            width : 200,
            formatter : 'checkbox',
            editable: true,
            align : 'center'
        }, {
            label : '是否有车',
            name : 'hasCar',
            width : 200,
            formatter : 'checkbox',
            editable: true,
            align : 'center'
        }, {
            label : '车是否贷款',
            name : 'isCarCredit',
            width : 200,
            formatter : 'checkbox',
            editable: true,
            align : 'center'
        }, {
            label : '是否有流水',
            name : 'hasAccount',
            width : 200,
            formatter : 'checkbox',
            editable: true,
            align : 'center'
        }, {
            label : '是否信誉良好',
            name : 'isGoodCredit',
            width : 200,
            formatter : 'checkbox',
            editable: true,
            align : 'center'
        } ],
        editurl : WEB_ROOT + '/erp/customer/save.json',
        delurl : WEB_ROOT + '/erp/customer/delete.json',
        fullediturl : WEB_ROOT + '/erp/customer/inputTabs.htm'
    });
});
