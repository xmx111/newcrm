function initEnv() {
	$("body").append(DWZ.frag["dwzFrag"]);
	if ( $.browser.msie && /6.0/.test(navigator.userAgent) ) {
		try {
			document.execCommand("BackgroundImageCache", false, true);
		}catch(e){}
	}
	//清理浏览器内存,只对IE起效
	if ($.browser.msie) {
		window.setInterval("CollectGarbage();", 10000);
	}
	$(window).resize(function(){
		initLayout();
		$(this).trigger("resizeGrid");
	});

	var ajaxbg = $("#background,#progressBar");
	ajaxbg.hide();
	$(document).ajaxStart(function(){
		ajaxbg.show();
	}).ajaxStop(function(){
		ajaxbg.hide();
	});
	
	if ($.fn.switchEnv) $("#switchEnvBox").switchEnv();
	setTimeout(function(){
		initLayout();
		initUI();
	}, 10);

}
function initLayout(){
	var iContentW = $(window).width(),headerEl = $("#header")[0] ? $("#header") : $("#shortheader");
	var iContentH = $(window).height() - headerEl.height() - $("#footer").height();
	$("#singleContainer").height(iContentH).find("[layoutH]").layoutH();
	$("#singleContainer").width(iContentW);
}

function initUI(_box){
	var $p = $(_box || document);
	//contextMenu 鼠标右键
	$p.find(".contextMenu").each(function(){
	    var id = $(this).attr("target");
		if(id.indexOf(',')!=-1){
			var arr = id.split(',');
			for(var i =0;i<arr.length;i++){
			    var _id = arr[i];
			    $('#'+_id).contextMenu(_id+"CM",{});
			}
		}else{
			$('#'+id).contextMenu(id+"CM",{});
	    }
	});
	// css tables
	$('table.list', $p).cssTable();
	//auto bind tabs
	if($.fn.tabs){
	    $("div.tabs", $p).each(function(){
		var $this = $(this);
		var options = {};
		options.currentIndex = $this.attr("currentIndex") || 0;
		options.eventType = $this.attr("eventType") || "click";
		$this.tabs(options);
	    });
	}
	
	$(":button.checkboxCtrl, :checkbox.checkboxCtrl", $p).checkboxCtrl($p);
	
	if ($.fn.combox) $("select.combox",$p).combox();
	
	 
	// init styles
	$("input[type=text], input[type=password], textarea", $p).addClass("textInput").focusClass("focus");

	$("input[readonly], textarea[readonly]", $p).addClass("readonly");
	$("input[disabled=true], textarea[disabled=true]", $p).addClass("disabled");

	$("input[type=text]", $p).not("div.tabs input[type=text]", $p).filter("[alt]").inputAlert();

	//Grid ToolBar
	$("div.panelBar li, div.panelBar", $p).hoverClass("hover");

	//Button
	$("div.button", $p).hoverClass("buttonHover");
	$("div.buttonActive", $p).hoverClass("buttonActiveHover");
	
	//tabsPageHeader
	$("div.tabsHeader li, div.tabsPageHeader li, div.accordionHeader, div.accordion", $p).hoverClass("hover");
	
	//$("div.panel", $p).jPanel();

	//validate form
	$("form.required-validate", $p).each(function(){
		$(this).validate({
			focusInvalid: false,
			focusCleanup: true,
			errorElement: "",
			ignore:".ignore",
			invalidHandler: function(form, validator) {
				var errors = validator.numberOfInvalids();
				if (errors) {
				    var firstEL =  $(validator.errorList[0].element);
				    var callback = function(){firstEL.focus();};
				    if(firstEL && firstEL.is('input')){
					firstEL.addClass('error');
				    }				    
				    var message = DWZ.msg("validateFormError",[errors]);
				    alertMsg.error(message,{okCall:callback});
				} 
			}
		});
	});

	 
	/**WdatePicker*/
	if($.fn.WdatePicker){
	    $('input.wdate', $p).each(function(){
		var $this = $(this);
		var opts = {};
		if ($this.attr("format")) opts.dateFmt = $this.attr("format");
		$this.WdatePicker(opts);
	});  	
	}
 
	//dialogs
	$("a[target=dialog]", $p).each(function(){
		$(this).click(function(event){
			var $this = $(this);
			/**添加前置检查*/
			var hander = $this.attr('prevCheck');
			if(!!hander &&eval(hander)){if(!eval(hander)($this)){return false;}}
			
			var title = $this.attr("title") || $this.text();
			var rel = $this.attr("rel") || "_blank";
			var options = {};
			var w = $this.attr("width");
			var h = $this.attr("height");
			if (w) options.width = w;
			if (h) options.height = h;
			options.max = eval($this.attr("max") || "false");
			options.mask = eval($this.attr("mask") || "true");
			options.maxable = eval($this.attr("maxable") || "true");
			options.minable = eval($this.attr("minable") || "true");
			options.fresh = eval($this.attr("fresh") || "true");
			options.resizable = eval($this.attr("resizable") || "false");
			options.drawable = eval($this.attr("drawable") || "true");
			options.close = eval($this.attr("close") || "");
			options.param = $this.attr("param") || "";

			var url = unescape($this.attr("href")).replaceTmById(DWZ.getUnitBox(event.target));
			DWZ.debug(url);
			if (!url.isFinishedTm()) {
				alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
				return false;
			}
			$.pdialog.open(url, rel, title, options);
			
			return false;
		});
	});
	$("a[target=ajax]", $p).each(function(){
		$(this).click(function(event){
			var $this = $(this);
			var rel = $this.attr("rel");
			if (rel) {
				var $rel = $("#"+rel);
				$rel.loadUrl($this.attr("href"), {}, function(){
					$rel.find("[layoutH]").layoutH();
				});
			}
			event.preventDefault();
		});
	});
	
	$("div.pagination", $p).each(function(){
		var $this = $(this);
		$this.pagination({
			targetType:$this.attr("targetType"),
			rel:$this.attr("rel"),
			totalCount:$this.attr("totalCount"),
			numPerPage:$this.attr("numPerPage"),
			pageNumShown:$this.attr("pageNumShown"),
			currentPage:$this.attr("currentPage")
		});
	});

	if ($.fn.sortDrag) $("div.sortDrag", $p).sortDrag();

	// dwz.ajax.js
	if ($.fn.ajaxTodo) $("a[target=ajaxTodo]", $p).ajaxTodo();
	if ($.fn.dwzExport) $("a[target=dwzExport]", $p).dwzExport();

	if ($.fn.lookup) $("a[lookupGroup]", $p).lookup();
	if ($.fn.multLookup) $("[multLookup]:button", $p).multLookup();
	if ($.fn.suggest) $("input[suggestFields]", $p).suggest();
	if ($.fn.selectedTodo) $("a[target=selectedTodo]", $p).selectedTodo();
	if ($.fn.pagerForm) $("form[rel=pagerForm]", $p).pagerForm({parentBox:$p});
	//scrollbar
	if($.fn.scrollbar){$p.scrollbar();$('.scrollbar',$p).scrollbar();}
	if($.fn.shortcut){$('[shortcut]',$p).shortcut();}
	if($.fn.shortcutquery){$('span.search',$p).shortcutquery();}
	//初始页面数据
	var initData = $p.children(':not(link,script,style):first').attr('init');
	if(initData!=null && initData!=''){var $fn = eval(initData);if($fn)$fn($p);}
	// 这里放其他第三方jQuery插件...
	return $p;
}


