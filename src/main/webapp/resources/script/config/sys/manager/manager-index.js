$(function() {
    $(".grid-config-sys-manager-index").data("gridOptions", {
        url : WEB_ROOT + '/config/sys/manager/findByPage.json',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true
        }, {
            label : '账号编码',
            name : 'loginName',
            width : 64,
            editable: true,
            align : 'left'
        }, {
            label : '账号名称',
            name : 'name',
            width : 64,
            editable: true,
            align : 'left'
        }, {
            label : '账号类型',
            name : 'type',
            width : 40,
            formatter : 'select',
            searchoptions : {
                value : Util.getCacheEnumsByType('ManagerEnum')
            },
            align : 'center'
        }, {
            label : '账号状态',
            name : 'status',
            width : 40,
            formatter : 'select',
            searchoptions : {
                value : Util.getCacheEnumsByType('ManagerStatusEnum')
            },
            align : 'center'
        } ],
        editurl : WEB_ROOT + '/config/sys/manager/save.json',
        delurl : WEB_ROOT + '/config/sys/manager/delete.json',
        fullediturl : WEB_ROOT + '/config/sys/manager/edit.htm',
        operations : function(itemArray) {
            var lock = $('<li data-position="multi" data-toolbar="show"><a href="javascript:;"><i class="fa fa-lock"></i> 锁定</a></li>');
            lock.children("a").bind("click", function(e) {
                e.preventDefault();
                var rowDatas = $(".grid-config-sys-manager-index").jqGrid("getSelectedRowdatas");
                if (rowDatas.length == 0){
                    return;
                }
                var url = WEB_ROOT + '/config/sys/manager/lock.json?id=0';
                for (i = 0; i < rowDatas.length; i++) {
                    var rowData = rowDatas[i];
                    url += '&ids=' + rowData['id'];
                }
                $.ajax({
                    type: 'POST',//使用get方法访问后台
                    dataType: 'json',//返回json格式的数据
                    url: url,
                    success: function(msg){//msg为返回的数据
                        Global.notify(msg.type, msg.smessage);
                        $(".grid-config-sys-manager-index").refresh();
                    }
                });
            });
            itemArray.push(lock);
            var logout = $('<li data-position="multi" data-toolbar="show"><a href="javascript:;"><i class="fa fa-ban"></i> 注销</a></li>');
            logout.children("a").bind("click", function(e) {
                e.preventDefault();
                var rowDatas = $(".grid-config-sys-manager-index").jqGrid("getSelectedRowdatas");
                if (rowDatas.length == 0){
                    return;
                }
                var url = WEB_ROOT + '/config/sys/manager/forbid.json?id=0';
                for (i = 0; i < rowDatas.length; i++) {
                    var rowData = rowDatas[i];
                    url += '&ids=' + rowData['id'];
                }
                $.ajax({
                    type: 'POST',//使用get方法访问后台
                    dataType: 'json',//返回json格式的数据
                    url: url,
                    success: function(msg){//msg为返回的数据
                        Global.notify(msg.type, msg.smessage);
                        $(".grid-config-sys-manager-index").refresh();
                    }
                });
            });
            itemArray.push(logout);
            var normal = $('<li data-position="multi" data-toolbar="show"><a href="javascript:;"><i class="fa fa-circle-o"></i> 启用</a></li>');
            normal.children("a").bind("click", function(e) {
                e.preventDefault();
                var rowDatas = $(".grid-config-sys-manager-index").jqGrid("getSelectedRowdatas");
                if (rowDatas.length == 0){
                    return;
                }
                var url = WEB_ROOT + '/config/sys/manager/active.json?id=0';
                for (i = 0; i < rowDatas.length; i++) {
                    var rowData = rowDatas[i];
                    url += '&ids=' + rowData['id'];
                }
                $.ajax({
                    type: 'POST',//使用get方法访问后台
                    dataType: 'json',//返回json格式的数据
                    url: url,
                    success: function(msg){//msg为返回的数据
                        Global.notify(msg.type, msg.smessage);
                        $(".grid-config-sys-manager-index").refresh();
                    }
                });
            });
            itemArray.push(normal);
        }
    });
});
