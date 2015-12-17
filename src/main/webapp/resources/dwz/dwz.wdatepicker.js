/**
 * reference My97 DatePicker
 */
(function($){
	$.fn.WdatePicker = function(opts){
	    	var defaults = {dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true};
	    	opts =  $.extend({},defaults,opts);
		return this.each(function(){
		    	$(this).focus(function(){
		    	    	WdatePicker(opts); 
		    	});
		    });
		};
			
})(jQuery);
 