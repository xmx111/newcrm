var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};
var zTreeObj;

$(function() {
	zTreeObj = $.fn.zTree.init($("#authorityTree"), setting, treeNodes);
	/**全选*/
	$('#checkAll').off('click').on('click', function() {
    	zTreeObj.checkAllNodes(true);
    });
	/**反选*/
	$('#reverseCheck').off('click').on('click', function() {
    	zTreeObj.checkAllNodes(false);
    });
	/**保存权限*/
	$('#btnRoleSave').off('click').on('click', function() {
    	var nodes = zTreeObj.getCheckedNodes(true);
    	var authIds = '';
    	$.each(nodes, function(i, node){
    		authIds += node.id + ',';
    	});
    	var url = ctx + '/config/sys/role/setRoleAuthorize.json';
    	var data = {
    		roleId: $('#roleId').val(),
    		authIds: authIds
    	};
    	$.post(url, data,
	    	function(data) {
    			Global.notify('success','权限设置成功');
	    	}
    	);
    });
});
