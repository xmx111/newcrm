/**
 * @author ZhangHuihua@msn.com
 */
(function ($) {
	$.setRegional = function(key, value){
		if (!$.regional) $.regional = {};
		$.regional[key] = value;
	};
	//en
	$.setRegional("alertMsg", {
		title:{error:"Error", info:"Information", warn:"Warning", correct:"Successful", confirm:"Confirmation"},
		butMsg:{ok:"OK", yes:"Yes", no:"No", cancel:"Cancel"}
	});
	//zh_CN
	$.setRegional("alertMsg", {
		title:{error:"错误", info:"提示", warn:"警告", correct:"成功", confirm:"确认提示"},
		butMsg:{ok:"确定", yes:"是", no:"否", cancel:"取消"}
	});
})(jQuery);

var alertMsg = {
	options : {timeout:3500,center:false},
	_boxId: "#alertMsgBox",
	_bgId: "#alertBackground",
	_closeTimer: null,

	_types: {error:"error", info:"info", warn:"warn", correct:"correct", confirm:"confirm",prompt:"prompt"},

	_getTitle: function(key){
		return $.regional.alertMsg.title[key];
	},

	/**
	 * 
	 * @param {Object} type
	 * @param {Object} msg
	 * @param {Object} buttons [button1, button2]
	 */
	_open: function(type, msg, buttons,op){
		$(this._boxId).remove();
		op = $.extend({},alertMsg.options,op);
		var butsHtml = "";
		if (buttons) {
			for (var i = 0; i < buttons.length; i++) {
				var sRel = buttons[i].call ? "callback" : "";
				butsHtml += DWZ.frag["alertButFrag"].replace("#butMsg#", buttons[i].name).replace("#callback#", sRel);
			}
		}
		var title = op.title ? op.title : this._getTitle(type);
		var boxHtml = DWZ.frag["alertBoxFrag"].replace("#type#", type).replace("#title#", title).replace("#message#", msg).replace("#butFragment#", butsHtml);
		var dlg = $(boxHtml).appendTo("body");
		var top = !!!op.center ? 0 : ($(window).height() - dlg.height() * 2) / 2;
		dlg.css({top:- dlg.height()+"px"}).animate({top: top + "px"}, 500);
				
		if (this._closeTimer) {
			clearTimeout(this._closeTimer);
			this._closeTimer = null;
		}
		if (this._types.info == type || this._types.correct == type){
			this._closeTimer = setTimeout(function(){alertMsg.close();}, op.timeout);
		} else {
			$(this._bgId).show();
		}
		var jCallButs = $(this._boxId).find("[rel=callback]");
		for (var i = 0; i < buttons.length; i++) {
		    if (buttons[i].call) jCallButs.eq(i).click(buttons[i].call);
		}
		$(alertMsg._boxId).data('options',op);
		
	},
	close: function(){
		$(this._boxId).animate({top:-$(this._boxId).height()}, 500, function(){
			$(this).remove();
		});
		$(this._bgId).hide();
	},
	error: function(msg, options) {
		this._alert(this._types.error, msg, options);
	},
	info: function(msg, options) {
		this._alert(this._types.info, msg, options);
	},
	warn: function(msg, options) {
		this._alert(this._types.warn, msg, options);
	},
	correct: function(msg, options) {
		this._alert(this._types.correct, msg, options);
	},
	_alert: function(type, msg, options) {
		var op = {okName:$.regional.alertMsg.butMsg.ok, okCall:null};
		$.extend(op, options);
		var buttons = [
			{name:op.okName, call: op.okCall}
		];
		this._open(type, msg, buttons);
	},
	/**
	 * 
	 * @param {Object} msg
	 * @param {Object} options {okName, okCal, cancelName, cancelCall}
	 */
	confirm: function(msg, options) {
		var op = {okName:$.regional.alertMsg.butMsg.ok, okCall:null, cancelName:$.regional.alertMsg.butMsg.cancel, cancelCall:null};
		$.extend(op, options);
		var buttons = [
			{name:op.okName, call: op.okCall},
			{name:op.cancelName, call: op.cancelCall}
		];
		this._open(this._types.confirm, msg, buttons);
	},
	/**
	 * window.prompt
	 * @param msg  
	 * @param options {title:title, okName, okCal, cancelName, cancelCall}
	 */
	prompt: function(msg, options) {
		var op = {okName:$.regional.alertMsg.butMsg.ok, okCall:null, cancelName:$.regional.alertMsg.butMsg.cancel, cancelCall:null};
		$.extend(op, options);
		var buttons = [
			{name:op.okName, call: function(event){
			    var el = $('input', $(alertMsg._boxId)).first(); val = el.val();
			    if($.trim(val) == ''){
				el.addClass('error');
				return false;
			    }else{
				var op  = $(alertMsg._boxId).data('options');
				if($.isFunction(op.okCall))op.okCall(val);
				alertMsg.close();
			    }
			}},{name:op.cancelName, call:function(){
			    var op  = $(alertMsg._boxId).data('options');
			    if($.isFunction(op.cancelCall))op.cancelCall();
			    alertMsg.close();
			 }}
		];
		if(msg.indexOf('<input') == -1){
		    msg += '<input type="text" id="prompt"  class="required textInput" />';
		}
		this._open(this._types.prompt, msg, buttons,op);
		$('a[rel]',$(alertMsg._boxId)).removeAttr('onclick');
		$(alertMsg._boxId).initUI();
	},
	//通用原因.
	lookupCallback : function(data){
	    $('input',$(alertMsg._boxId)).val(data.value || '').focus();
	}
	
	
};

