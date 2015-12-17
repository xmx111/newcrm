/*
 * SWFUpload jQuery Plugin 
 *
 * Copyright (c) 2009 Adam Royle
 * Licensed under the MIT license.
 *
 */

(function($) {
    var defaultHandlers = [ 'swfupload_loaded_handler', 'file_queued_handler', 'file_queue_error_handler',
	    'file_dialog_start_handler', 'file_dialog_complete_handler', 'upload_start_handler',
	    'upload_progress_handler', 'upload_error_handler', 'upload_success_handler', 'upload_complete_handler',
	    'queue_complete_handler' ];
    var additionalHandlers = [];
    
    $.fn.swfupload = function() {
	var args = $.makeArray(arguments);
	return this.each(function() {
	    var swfu;
	    if (args.length == 1 && typeof (args[0]) == 'object') {
		var $this = $(this);
		swfu = $this.data('__swfu');
		if (!swfu) {
		    var settings = $.extend({
			// Backend Settings
        	        upload_url: $this.attr('upload_url'),  
        	        // File Upload Settings
        	        file_size_limit : "100 MB", // 100MB
        	        file_types : "*.*",
        	        file_types_description : "All Files",
        	        file_upload_limit : "10",
        	        file_queue_limit : "0",
        	        custom_settings : {begin:$this.attr("begin")},
        	        // Button Settings
        	        button_text :  $this.attr('button_text'),
        	        button_placeholder_id :$this.attr("target"),
        	        button_width: 61,
        	        button_height: 22,
        	        successCallBack : $this.attr("callback") ,
        	        // Flash Settings
        	        flash_url : $this.attr('flash_url')
		    }, args[0]);
		    var $magicUploadControl = $(this);
		    var handlers = [];
		    $.merge(handlers, defaultHandlers);
		    $.merge(handlers, additionalHandlers);
		    $.each(handlers, function(i,v) {
			var eventName = v.replace(/_handler$/, '').replace(/_([a-z])/g, function() {
			    return arguments[1].toUpperCase();
			});
			settings[v] = function() {
			    var event = $.Event(eventName);
			    $magicUploadControl.trigger(event, $.makeArray(arguments));
			    return !event.isDefaultPrevented();
			};
		    });
		    settings["upload_success_handler"]= function(file, serverData, responseReceived){
    			//upload success
    			var obj = $.parseJSON(serverData);
    			//var $this = $(this);
    			var el = $('#fsUploadProgress');
    			if(el.length!=0){
    			    var html = [];
    			    var idx = parseInt(file.index) + parseInt(this.customSettings.begin);
    			    html.push("<span id='attachments_{idx}'>".replace("{idx}",idx));
    			    html.push('<input type="hidden" name="attachments[{idx}].name" value="{val}"/>'.replace("{idx}",idx).replace("{val}",obj.name));
    			    html.push('<input type="hidden" name="attachments[{idx}].path" value="{val}"/>'.replace("{idx}",idx).replace("{val}",obj.url));
    			    html.push('<input type="hidden" name="attachments[{idx}].size" value="{val}"/>'.replace("{idx}",idx).replace("{val}",obj.size));
    			    html.push('<input type="hidden" name="attachments[{idx}].suffix" value="{val}"/>'.replace("{idx}",idx).replace("{val}",obj.type));
    			    html.push(obj.name);
    			    html.push("</span>");
    			    el.append(html.join(""));
    			}
    			if(this.settings['successCallBack'] != ''){
    			    eval(this.settings['successCallBack']+"(obj)");    			       
    			}
    		    };
		    $this.bind('fileQueued', function(event, file){
			// start the upload once a file is queued
			$(this).swfupload('startUpload');
		    }).bind('uploadComplete', function(event, file){
			// start the upload (if more queued) once an upload is complete
			$(this).swfupload('startUpload');
		    });
		    $this.data('__swfu', new SWFUpload(settings));
		}
	    } else if (args.length > 0 && typeof (args[0]) == 'string') {
		var methodName = args.shift();
		swfu = $(this).data('__swfu');
		if (swfu && swfu[methodName]) {
		    swfu[methodName].apply(swfu, args);
		}
	    }
	});
    };
    $.swfupload = { additionalHandlers : function() {
	if (arguments.length === 0) {
	    return additionalHandlers.slice();
	} else {
	    $(arguments).each(function(i,v) {
		$.merge(additionalHandlers, $.makeArray(v));
	    });
	}
    }, defaultHandlers : function() {
	return defaultHandlers.slice();
    }, getInstance : function(el) {
	return $(el).data('__swfu');
    } };

})(jQuery);