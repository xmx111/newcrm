/**
 * Theme Plugins
 * @author ZhangHuihua@msn.com
 */
(function($){
	$.fn.extend({
		cssTable: function(options){

			return this.each(function(){
				var $this = $(this);
				if($this.attr('noDef')){return;}
				var $trs = $this.find('tbody>tr');
				var $grid = $this.parent(); // table
				var nowrap = $this.hasClass("nowrap");
				
				$trs.hoverClass("hover").each(function(index){				    
					var $tr = $(this);
					if (!nowrap && index % 2 == 1) $tr.addClass("trbg");					
					$tr.click(function(){
						$trs.filter(".selected").removeClass("selected");
						$tr.addClass("selected");
						var sTarget = $tr.attr("target");
						if (sTarget) {
							if ($("#"+sTarget, $grid).size() == 0) {
								$grid.prepend('<input id="'+sTarget+'" type="hidden" />');
							}
							$("#"+sTarget, $grid).val($tr.attr("rel"));
						}
						/**添加单击事件*/
						var hander = $this.attr('trClickEvent');						
						if(!!hander &&eval(hander)){eval(hander)($this);}
					});
					$tr.dblclick(function(){
						var hander = $this.attr('trDBClickEvent');						
						if(!!hander &&eval(hander)){eval(hander)($this);}
					});
					
				});
				//添加排序 
				$this.find("th[orderField]").click(function(){
				    var asc =$(this).hasClass('asc');
				    $(this).removeClass( !asc ? 'desc' : 'asc').addClass(!asc ? 'asc' : 'desc');
				    $this.attr('orderField',$(this).attr('orderField'));
				    $this.removeClass( !asc ? 'asc' : 'desc').addClass(!asc ? 'desc' : 'asc');
				    $this.orderBy({targetType: $this.attr("targetType"),rel:$this.attr("rel")});
				});
			});
		}
	});
})(jQuery);
