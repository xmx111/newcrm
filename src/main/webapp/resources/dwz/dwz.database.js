/**
\ * @author ZhangHuihua@msn.com
 */
(function($){
	var _lookup = {currentGroup:"", suffix:"", $target:null, pk:"id"};
	var _util = {
		_lookupPrefix: function(key){
			var strDot = _lookup.currentGroup ? "." : "";
			return _lookup.currentGroup + strDot + key + _lookup.suffix;
		},
		lookupPk: function(key){
			return this._lookupPrefix(key);
		},
		lookupField: function(key){
			return this.lookupPk(key);
		}
	};
	
	$.extend({
	    	bringDefaultSuggest:function(data,target){
        	    	var $box = _lookup['$target'].parents(".unitBox:first");
        	    	$box.find(":input").each(function(){
        			var $input = $(this); 
        			var inputName = $input.attr("name");
        			if(!!!inputName) return false;
        			var namelist =  inputName.split(".");
        			if(inputName && _lookup.currentGroup == namelist[0] && namelist.length > 1){
        				var inputValue ='';
        				for(var k = 1;k<namelist.length;k++){
        					inputValue = !!!inputValue ? data[namelist[k]] : inputValue[namelist[k]]==undefined ? "":inputValue[namelist[k]];
        				}
        				$input.val(inputValue);
        			}
        		});
	    	},
		bringBackSuggest: function(args){
		    var $box = _lookup['$target'].parents(".unitBox:first");
			$box.find(":input").each(function(){
				var $input = $(this), inputName = $input.attr("name");
				for (var key in args) {
					var name = (_lookup.pk == key) ? _util.lookupPk(key) : _util.lookupField(key);
					if (name == inputName) {
						$input.val(args[key]);
						break;
					}
				}
			});
		},
		bringBack: function(args){
			$.bringBackSuggest(args);
			$.pdialog.closeCurrent();
		}
	});
	
	$.fn.extend({
		lookup: function(){
			return this.each(function(){
				var $this = $(this), options = {mask:true, 
					width:$this.attr('width')||820, height:$this.attr('height')||400,
					maxable:eval($this.attr("maxable") || "true"),
					resizable:eval($this.attr("resizable") || "true")
				};
				$this.click(function(event){
					_lookup = $.extend(_lookup, {
						currentGroup: $this.attr("lookupGroup") || "",
						suffix: $this.attr("suffix") || "",
						$target: $this,
						pk: $this.attr("lookupPk") || "id"
					});
					
					var url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
					if (!url.isFinishedTm()) {
						alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
						return false;
					}
					
					$.pdialog.open(url, "_blank", $this.attr("title") || $this.text(), options);
					return false;
				});
			});
		},
		multLookup: function(){
			return this.each(function(){
				var $this = $(this), args={};
				$this.click(function(event){
					var $unitBox = $this.parents(".unitBox:first");
					$unitBox.find("[name='"+$this.attr("multLookup")+"']").filter(":checked").each(function(){
						var _args = DWZ.jsonEval($(this).val());
						for (var key in _args) {
							var value = args[key] ? args[key]+"," : "";
							args[key] = value + _args[key];
						}
					});

					if ($.isEmptyObject(args)) {
						alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
						return false;
					}
					$.bringBack(args);
				});
			});
		},
		suggest: function(){
			var op = {suggest$:"#suggest", suggestShadow$: "#suggestShadow"};
			var selectedIndex = -1;
			return this.each(function(){			    	
				var $input = $(this).attr('autocomplete', 'off').keydown(function(event){
					if (event.keyCode == DWZ.keyCode.ENTER) return false; //屏蔽回车提交
				}); 
				function _show(event){
					if($input.data("search")==1){return;}
					var suggestFields=$input.attr('suggestFields').split(",");
					var offset = $input.offset(),val = $input.val()||'',prev=$input.data("prev"),min = $input.attr('min') || 1;
					var iTop = offset.top+($input[0].offsetHeight||0);
					var $suggest = $(op.suggest$);					
					if ($suggest.size() == 0) $suggest = $('<div id="suggest"></div>').appendTo($('body'));
					$suggest.css({
						left:offset.left+'px',
						top:iTop+'px'
					}).hide();
					if(val.length < min){return;}					
					/*if(val === prev || (!$.isUndefined(prev) && prev!='' && $input.data('empty') && val.startsWith($input.data("prev"))) ){
					    if($suggest.find('li').length != 0){$suggest.show();}
					    return;
					}*/				
					_lookup = $.extend(_lookup, {
						currentGroup: $input.attr("lookupGroup") || "",
						suffix: $input.attr("suffix") || "",
						$target: $input,
						pk: $input.attr("lookupPk") || "id",
						auto: eval($input.attr('auto') || "true")
					});
					var url = unescape($input.attr("suggestUrl")).replaceTmById($(event.target).parents(".unitBox:first"));
					if (!url.isFinishedTm()) {
						alertMsg.error($input.attr("warn") || DWZ.msg("alertSelectMsg"));
						return false;
					}
					
					var postData = {};  //请求参数名称  postField  inputValue
					postData[$input.attr("postField")||"inputValue"] = val;
					//$input.data("prev",val);
					$.ajax({   
						type:'POST', dataType:"json", url:url, cache: false, global: $input.attr('mask') || false,
						data: postData,
						beforeSend:function(){
							$input.data("search",1);
						},
						success: function(response){
							$input.data("search",0);
							if (!response) return;
							var html = '';
							$.each(response, function(i){
								var liAttr = '', liLabel = '';
									for (var i=0; i<suggestFields.length; i++){
										var args = suggestFields[i];
										var str = "";
										if(args.indexOf(".")>0){
											var list = args.split(".");
											for(var j=0;j<list.length;j++){
												str = str?str[list[j]]:this[list[j]];
											}
										}else{
											argstr = args;
											str = this[argstr];
										}
										if (null != str && undefined != str) {
											if (liLabel) liLabel += '-';
											liLabel += str;
											if (liAttr) liAttr += ',';
											liAttr += suggestFields[i]+":'"+str+"'";
										}
									}
								html += '<li lookupId="'+this[_lookup.pk]+'" lookupAttrs="'+liAttr+'">' + liLabel + '</li>';
							});
							
							var $lis = $suggest.html('<ul>'+html+'</ul>').find("li");
							/**添加数据绑定*/
							$lis.each(function(i,el){
							    var obj = response[i];
							    $(el).data('data',obj);
							});
							$lis.hoverClass("selected").click(function(){
							    _select($(this));
							});
							if ($lis.size() == 1 && event.keyCode != DWZ.keyCode.BACKSPACE && _lookup.auto) {
								$lis.eq(0).addClass("selected");
								if(event.keyCode == DWZ.keyCode.ENTER){
									_select($lis.eq(0));
								}
							} 
							if ($lis.size() == 0){
							    _select(null);
							    $input.data("empty",true);
							}else{
							    $suggest.show();
							    $input.removeData("empty");
							}
						},
						error: function(){
							$input.data("search",0);
						    $suggest.html('').hide();
						}
					});

					$(document).bind("click", _close);
					return false;
				}
				function _select($item){
					if(!!!$item) return false;
       				     	var jsonStr =  !!!$item ? "{"+_lookup.pk+":''}" :  "{"+_lookup.pk+":'"+$item.attr('lookupId')+"'," + $item.attr('lookupAttrs') +"}";
       				     	var obj =  DWZ.jsonEval(jsonStr);
       				     	var target = _lookup.$target;
					var $callback = target.attr('callback') ||  $.bringBackSuggest;
					if (!$.isFunction($callback)) $callback = eval('(' + $callback + ')');
       				     	if($item && target && target.attr('callback')){
       				     	    var $callback = target.attr('callback') || function(){};
       				     	    if (!$.isFunction($callback)) $callback = eval('(' + $callback + ')');
       				     	    var data  = $item.data('data')|| obj;
       				     	    $callback(data,target);
       				     	}else{
       				     	    var data  = $item && $item.data('data') ? $item.data('data') : obj ;
       				     	    $callback(data,target); 
       				     	}
       				     _close();	
				}
				function _close(){
					$(op.suggest$).hide();
					selectedIndex = -1;
					$input.removeData("prev").removeData('selectedIndex');
					$(document).unbind("click", _close);
				}
				
				$input.focus(function(){
					$input.select();
				}).click(false).keydown(function(event){
					var $items = $(op.suggest$).find("li");
					switch(event.keyCode){
						case DWZ.keyCode.ESC:
							_close();
							break;
						case DWZ.keyCode.TAB:
						case DWZ.keyCode.SHIFT:
						case DWZ.keyCode.HOME:
						case DWZ.keyCode.END:
						case DWZ.keyCode.LEFT:
						case DWZ.keyCode.RIGHT:
							break;
						case DWZ.keyCode.ENTER:
							if (selectedIndex>=0) {
								var $item = $items.eq(selectedIndex).addClass("selected");
								_select($item);
							}
							_close();
							break;
						case DWZ.keyCode.DOWN:
							if (selectedIndex >= $items.size()-1) selectedIndex = -1;
							else selectedIndex++;
							break;
						case DWZ.keyCode.UP:
							if (selectedIndex < 0) selectedIndex = $items.size()-1;
							else selectedIndex--;
							break;
						default:
							setTimeout(function(){
								_show(event);
							},500);	
					}
					$items.removeClass("selected");
					if (selectedIndex>=0) {
						var $item = $items.eq(selectedIndex).addClass("selected");
						//_select($item);
					}
				});
				var fn = {show:_show,select:_select,close:_close};
				$input.data('fn',fn);
			});
		},
		
		itemDetail: function(){
			return this.each(function(){
				var $table = $(this).css("clear","both"), $tbody = $table.find("tbody");
				var fields= [];

				$table.find("tr:first th[type]").each(function(i){
					var $th = $(this);
					var field = {
						type: $th.attr("type") || "text",
						patternDate: $th.attr("format") || "yyyy-MM-dd",
						name: $th.attr("name") || "",
						defaultVal: $th.attr("defaultVal") || "",
						size: $th.attr("size") || "12",
						enumUrl: $th.attr("enumUrl") || "",
						lookupGroup: $th.attr("lookupGroup") || "",
						lookupUrl: $th.attr("lookupUrl") || "",
						lookupPk: $th.attr("lookupPk") || "id",
						suggestUrl: $th.attr("suggestUrl"),
						suggestFields: $th.attr("suggestFields"),
						postField: $th.attr("postField") || "",
						fieldClass: $th.attr("fieldClass") || ""
					};
					fields.push(field);
				});
				
				$tbody.find("a.btnDel").click(function(){
					var $btnDel = $(this);
					function delDbData(){
						$.ajax({
							type:'POST', dataType:"json", url:$btnDel.attr('href'), cache: false,
							success: function(){
								$btnDel.parents("tr:first").remove();
								initSuffix($tbody);
							},
							error: DWZ.ajaxError
						});
					}
					
					if ($btnDel.attr("title")){
						alertMsg.confirm($btnDel.attr("title"), {okCall: delDbData});
					} else {
						delDbData();
					}
					
					return false;
				});

				var addButTxt = $table.attr('addButton') || "Add New";
				if (addButTxt) {
					var $addBut = $('<div class="button"><div class="buttonContent"><button type="button">'+addButTxt+'</button></div></div>').insertBefore($table).find("button");
					var $rowNum = $('<input type="text" name="dwz_rowNum" class="textInput" style="margin:2px;" value="1" size="2"/>').insertBefore($table);
					
					var trTm = "";
					$addBut.click(function(){
						if (! trTm) trTm = trHtml(fields);
						var rowNum = 1;
						try{rowNum = parseInt($rowNum.val());} catch(e){}

						for (var i=0; i<rowNum; i++){
							var $tr = $(trTm);
							$tr.appendTo($tbody).initUI().find("a.btnDel").click(function(){
								$(this).parents("tr:first").remove();
								initSuffix($tbody);
								return false;
							});
						}
						initSuffix($tbody);
					});
				}
			});
			
			/**
			 * 删除时重新初始化下标
			 */
			function initSuffix($tbody) {
				$tbody.find('>tr').each(function(i){
					$(':input, a.btnLook', this).each(function(){
						var $this = $(this), name = $this.attr('name'), val = $this.val();

						if (name) $this.attr('name', name.replaceSuffix(i));
						
						var lookupGroup = $this.attr('lookupGroup');
						if (lookupGroup) {$this.attr('lookupGroup', lookupGroup.replaceSuffix(i));}
						
						var suffix = $this.attr("suffix");
						if (suffix) {$this.attr('suffix', suffix.replaceSuffix(i));}
						
						if (val && val.indexOf("#index#") >= 0) $this.val(val.replace('#index#',i+1));
					});
				});
			}
			
			function tdHtml(field){
				var html = '', suffix = '';
				
				if (field.name.endsWith("[#index#]")) suffix = "[#index#]";
				else if (field.name.endsWith("[]")) suffix = "[]";
				
				var suffixFrag = suffix ? ' suffix="' + suffix + '" ' : '';
				
				switch(field.type){
					case 'del':
						html = '<a href="javascript:void(0)" class="btnDel '+ field.fieldClass + '">删除</a>';
						break;
					case 'lookup':
						var suggestFrag = '';
						if (field.suggestFields) {
							suggestFrag = 'autocomplete="off" lookupGroup="'+field.lookupGroup+'"'+suffixFrag+' suggestUrl="'+field.suggestUrl+'" suggestFields="'+field.suggestFields+'"' + ' postField="'+field.postField+'"';
						}

						html = '<input type="hidden" name="'+field.lookupGroup+'.'+field.lookupPk+suffix+'"/>'
							+ '<input type="text" name="'+field.name+'"'+suggestFrag+' lookupPk="'+field.lookupPk+'" size="'+field.size+'" class="'+field.fieldClass+'"/>'
							+ '<a class="btnLook" href="'+field.lookupUrl+'" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' lookupPk="'+field.lookupPk+'" title="查找带回">查找带回</a>';
						break;
					case 'attach':
						html = '<input type="hidden" name="'+field.lookupGroup+'.'+field.lookupPk+suffix+'"/>'
							+ '<input type="text" name="'+field.name+'" size="'+field.size+'" readonly="readonly" class="'+field.fieldClass+'"/>'
							+ '<a class="btnAttach" href="'+field.lookupUrl+'" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' lookupPk="'+field.lookupPk+'" width="560" height="300" title="查找带回">查找带回</a>';
						break;
					case 'enum':
						$.ajax({
							type:"POST", dataType:"html", async: false,
							url:field.enumUrl, 
							data:{inputName:field.name}, 
							success:function(response){
								html = response;
							}
						});
						break;
					case 'date':
						html = '<input type="text" name="'+field.name+'" value="'+field.defaultVal+'" class="date '+field.fieldClass+'" format="'+field.patternDate+'" size="'+field.size+'"/>'
							+'<a class="inputDateButton" href="javascript:void(0)">选择</a>';
						break;
					default:
						html = '<input type="text" name="'+field.name+'" value="'+field.defaultVal+'" size="'+field.size+'" class="'+field.fieldClass+'"/>';
						break;
				}
				return '<td>'+html+'</td>';
			}
			function trHtml(fields){
				var html = '';
				$(fields).each(function(){
					html += tdHtml(this);
				});
				return '<tr class="unitBox">'+html+'</tr>';
			}
		},
		
		selectedTodo: function(){
			
			function _getIds(selectedIds, targetType){
				var ids = "";
				var $box = targetType == "dialog" ? $.pdialog.getCurrent() : navTab.getCurrentPanel();
				$box.find("input:checked").filter("[name='"+selectedIds+"']").each(function(i){
					var val = $(this).val();
					ids += i==0 ? val : ","+val;
				});
				return ids;
			}
			return this.each(function(){
				var $this = $(this);
				var selectedIds = $this.attr("rel") || "ids";
				var postType = $this.attr("postType") || "map";

				$this.click(function(){
					var ids = _getIds(selectedIds, $this.attr("targetType"));
					if (!ids) {
						alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
						return false;
					}
					function _doPost(){
						$.ajax({
							type:'POST', url:$this.attr('href'), dataType:'json', cache: false,
							data: function(){
								if (postType == 'map'){
									return $.map(ids.split(','), function(val, i) {
										return {name: selectedIds, value: val};
									});
								} else {
									var _data = {};
									_data[selectedIds] = ids;
									return _data;
								}
							}(),
							success: navTabAjaxDone,
							error: DWZ.ajaxError
						});
					}
					var title = $this.attr("title");
					if (title) {
						alertMsg.confirm(title, {okCall: _doPost});
					} else {
						_doPost();
					}
					return false;
				});
				
			});
		}
	});
})(jQuery);

