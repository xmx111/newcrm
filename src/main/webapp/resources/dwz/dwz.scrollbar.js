/**
 * scrollbar
 */
(function($){    
    $.fn.extend({
	scrollbar: function(options){
	    var onScroll = function(pos){
		window._scrollbar = pos;
	    };
	    var opts =$.extend({scrollEasing:"easeOutQuint",autoDraggerLength:false,callbacks:{onScroll:onScroll}},options);
	    return this.each(function(){
		var $this = $(this);
		if(!$this.hasClass('scrollbar') || $this.data('scrollbar')){return;}
		if($.fn.mCustomScrollbar){$this.data('scrollbar',true);$this.mCustomScrollbar(opts);}	 
	    });
	}
    });
})(jQuery);