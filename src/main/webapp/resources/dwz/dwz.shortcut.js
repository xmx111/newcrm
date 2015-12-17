/** DWZ shortcut 插件 */
(function($){
 $.fn.extend({
  shortcut: function(options) {
    return this.each(function() {
      var $this = $(this),key = $this.attr('shortcut') || '',arr = key.split(','),callback = $this.attr('shortcut-callback')  ||'', type=$this.attr('shortcut-type')||'keydown', propagate = $this.attr('shortcut-propagate')||false,disable=$this.attr('shortcut-disable')||false;
      var op = $.extend(options||{},{'type':type,'propagate':propagate,'disable_in_input':disable});
      var $fn = ''!=callback ? eval(callback):function(evt){$this.trigger('click');};
      if ('' != key && window.shortcut) {
	  for(var i=0;i<arr.length;i++){
	      window.shortcut.remove(arr[i]);
	      window.shortcut.add(arr[i],$fn,op);
	  }
	}});
      }
    });
})(jQuery);
