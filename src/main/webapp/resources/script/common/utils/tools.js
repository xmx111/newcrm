Tools = function (){
	var a = false;
	return {
		/*
		 * 
		 * 枚举类型转下拉 公用方法
		 * enumType:定义的枚举类型,id:为下拉菜单的ID (如：id='periodType')
		 * 注意：请在下拉列表前增加隐藏域加载默认下拉值，id:下拉菜单的ID+"Key" (如：id='periodTypeKey')
		 * 目的：方便修改带回下拉默认值
		 * xin.wu
		 */
		EnumSelectValue : function(enumType,select,keyValue){
			var period = "";
			var periodUnitEnum = Tools.getCacheEnumsByType(enumType);
		    for(var key in periodUnitEnum){  
		        	period += "<option value =\"" + key + "\">"
		        	+ periodUnitEnum[key] + "</option>";
		    }
			$(select).empty();
		    $(select).html(period);
		    if(!!keyValue){
		    	$(select +" option[value='"+keyValue+"']").attr("selected", "selected"); 
		    }
		},
        EnumTextValue : function(enumType,keyValue){
            var period = "";
            var periodUnitEnum = Tools.getCacheEnumsByType(enumType);
            for(var key in periodUnitEnum){
                if(keyValue == key){
                    period = periodUnitEnum[key];
                    break;
                }
            }
            return period;
        },
		EnumSelectList : function(enumType,select,keyId){
			Tools.EnumSelectValue(enumType, select, $.trim($(keyId).val()));
		},
		EnumUnitList : function (enumType,id){
			Tools.EnumSelectList(enumType, "#"+id, "#"+id+"Key");
		},

        /*
         * AJAX请求数据库取值类型转下拉 公用方法
         * url:AJAX 直接调用所需数据对应Control层findByPage请求,id:为下拉菜单的ID (如：id='periodType')
         * 注意：请在下拉列表前增加隐藏域加载默认下拉值，id:下拉菜单的ID+"Key" (如：id='periodTypeKey')
         * 目的：方便修改带回下拉默认值
         * xin.wu
         */
        AjaxSelectValueNoPage : function(url,select,keyValue){
            var options = "";
            $.ajax({
                async : false,
                type : 'POST',// 使用get方法访问后台
                dataType : 'json',// 返回json格式的数据
                url : WEB_ROOT + url,
                data : '',
                success : function(data) {// data为返回的数据
                    options = "<option value=''></option>";
                    if (!!data) {
                        for (var i = 0; i < data.length; i++) {
                            options += "<option value =\"" + data[i].id + "\">"
                                + data[i].name + "</option>";
                        }
                    }
                    $(select).empty();
                    $(select).html(options);
                    if(!!keyValue){
                        //$(select +" option[value !='"+keyValue+"']").remove();
                        $(select +" option[value ='"+keyValue+"']").attr("selected", "selected");
                    }
                }
            });
        },
		
		/*
		 * AJAX请求数据库取值类型转下拉 公用方法
		 * url:AJAX 直接调用所需数据对应Control层findByPage请求,id:为下拉菜单的ID (如：id='periodType')
		 * 注意：请在下拉列表前增加隐藏域加载默认下拉值，id:下拉菜单的ID+"Key" (如：id='periodTypeKey')
		 * 目的：方便修改带回下拉默认值
		 * xin.wu
		 */
		AjaxSelectValue : function(url,select,keyValue){
			var options = "";
			$.ajax({
				async : false,
				type : 'POST',// 使用get方法访问后台
				dataType : 'json',// 返回json格式的数据
				url : WEB_ROOT + url,
				data : '',
				success : function(data) {// data为返回的数据
					var contents = data.content;
					options = "<option value=''></option>";
					if (!!contents) {
						for (var i = 0; i < contents.length; i++) {						
							options += "<option value =\"" + contents[i].id + "\">"
							+ contents[i].name + "</option>";													
						}
					}
					$(select).empty();
					$(select).html(options);
					if(!!keyValue){
						//$(select +" option[value !='"+keyValue+"']").remove();
						$(select +" option[value ='"+keyValue+"']").attr("selected", "selected");
					}
				}
			});
		},
		
		//去掉remove的ajax下拉提交
		AjaxSelectValue1 : function(url,select,keyValue){
			var options = "";
			$.ajax({
				type : 'POST',// 使用get方法访问后台
				dataType : 'json',// 返回json格式的数据
				url : WEB_ROOT + url,
				data : '',
				success : function(data) {// data为返回的数据
					var contents = data.content;
					options = "<option value=''></option>";
					if (!!contents) {
						for (var i = 0; i < contents.length; i++) {						
							options += "<option value =\"" + contents[i].id + "\">"
							+ contents[i].name + "</option>";													
						}
					}
					$(select).empty();
					$(select).html(options);
					if(!!keyValue){
						$(select +" option[value ='"+keyValue+"']").attr("selected", "selected");
					}
				}
			});
		},
		AjaxSelectList1 : function(url,select,keyId){
			Tools.AjaxSelectValue1(url, select, $.trim($(keyId).val()));
		},
		
		AjaxUnitList1 : function(url,id){
			Tools.AjaxSelectList1(url, "#"+id, "#"+id+"Key");
		},
		
		AjaxSelectList : function(url,select,keyId){
			Tools.AjaxSelectValue(url, select, $.trim($(keyId).val()));
		},
		
		AjaxUnitList : function(url,id){
			Tools.AjaxSelectList(url, "#"+id, "#"+id+"Key");
		},
		getCacheEnumsByType: function (c, e) {
			if (e == undefined) {
				e = $('body');
			}
			if (e.data('CacheEnumDatas') == undefined) {
				$.ajax({
					async : false,
					type : 'GET',
					url : WEB_ROOT + '/pub/data/enums.json?name=' + c,
					dataType : 'json',
					success : function(k) {
						for ( var j in k) {
							var g = k[j];
							var f = {
								'' : ''
							};
							for ( var h in g) {
								f[h] = g[h];
							}
							k[j] = f;
						}
						e.data('CacheEnumDatas', k);
					}
				});
			}
			var d = e.data('CacheEnumDatas')[c];
			if (d == undefined) {
				alert('错误的枚举数据类型：' + c);
				d = {};
			}
			return d;
		}
	};
}();