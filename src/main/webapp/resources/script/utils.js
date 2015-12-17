/**
 * date utils
 */
var DateUtils = {
	YEAR:'yyyy',
	MONTH:'MM',
	DAY:'dd',
	WEEK:'W',
	HOUR:'HH',
	MINUTE:'mm',
	SECOND:'ss',
	MILLISECOND:'ms',
	defaultFormat : "yyyy-MM-dd",
	DateTimeFormat : "yyyy-MM-dd HH:mm",
	parseDate : function(date, format){
		if(this.isDate(date)){return date;}
		var dObj = new Date();
		var begin=0;
		var last = "";
		format = (!format ? this.defaultFormat : format) + " " ;
		for(var i=0;i<format.length;i++){
			var c = format.substr(i,1);
			if(c != last){
				last = c;
				var key = format.substring(begin,i);
				if("" != key){
					switch(key){
						case this.YEAR:dObj.setYear(parseInt(date.substring(begin,i),10)); break;
						case this.MONTH:dObj.setMonth(parseInt(date.substring(begin,i),10) - 1); break;
						case this.DAY:dObj.setDate(parseInt(date.substring(begin,i),10)); break;
						case this.HOUR:dObj.setHours(parseInt(date.substring(begin,i),10));break;
						case this.MINUTE:dObj.setMinutes(parseInt(date.substring(begin,i),10));break;
						case this.SECOND:dObj.setSeconds(parseInt(date.substring(begin,i),10));break;
					}					
				}
				begin = i;
			}
		}
		return dObj;
	},
	formatDate : function(date, format){
		if(!this.isDate(date)){return date;}
		var dateString = [];
		var begin=0;
		var last = "";
		format = (!format ? this.defaultFormat : format) + " " ;
		for(var i=0;i<format.length;i++){
			var c = format.substr(i,1);
			if(c != last){
				last = c;
				var key = format.substring(begin,i);
				if("" != key){
					switch(key){
					case this.YEAR:dateString.push(date.getFullYear());break; 
					case this.MONTH:var m = date.getMonth() + 1;dateString.push((m < 10 ? "0" : "") + m);break;
					case this.DAY:dateString.push((date.getDate() < 10 ? "0" : "") + date.getDate()); break;
					case this.HOUR:dateString.push((date.getHours() < 10 ? "0" : "") + date.getHours());break;
					case this.MINUTE:dateString.push((date.getMinutes() < 10 ? "0" : "") + date.getMinutes());break;
					case this.SECOND:dateString.push((date.getSeconds() < 10 ? "0" : "") + date.getSeconds());break;
					default:dateString.push(key);
					}					
				}
				begin = i;
			}
		}
		return dateString.join("");
	},
	toDate:function(datetimeString){
		if(!datetimeString) return null;
		var dateTime = datetimeString.split(" ");
		var hourMinutes = dateTime[1].split(":");
		var dObj = this.parseDate(dateTime[0]);
		dObj.setHours(parseInt(hourMinutes[0],10));
		dObj.setMinutes(parseInt(hourMinutes[1],10));
		dObj.setSeconds(hourMinutes.length > 2 ? parseInt(hourMinutes[2],10) : 0);
		return dObj;
	},
	addDays : function(date, days){
		return this.add(date, this.DAY, days);
	},
	addMinute : function(time,minute){
		return this.add(time, this.MINUTE, minute, "HH:mm");
	},
	// 增加时间
	add:function(val,field,number,fmt){
		var date = new Date(this.isDate(val) ? val : this.parseDate(val,fmt));
		var val =parseInt(number,10);
	 	switch(field){
	 		case this.YEAR: date.setFullYear(date.getFullYear()+val); break; 
			case this.MONTH: date.setMonth(date.getMonth()+val); break;
			case this.DAY: date.setDate(date.getDate()+val); break;
			case this.WEEK: date.setDate(date.getDate()+(7*val)); break;
			case this.HOUR: date.setHours(date.getHours()+val); break;
			case this.MINUTE: date.setMinutes(date.getMinutes()+val); break;
			case this.SECOND: date.setSeconds(date.getSeconds()+val); break;
			case this.MILLISECOND: date.setMilliseconds(date.getMilliseconds()+val); break;
	 	}
	 	return this.isDate(date) ? date : this.formatDate(date,fmt);
	},
	// 计算时间差
	diff:function(field,date1,date2,fmt){
		date1 = this.isDate(date1) ? date1 : this.parseDate(date1,fmt); 
		date2 = this.isDate(date2) ? date2 : this.parseDate(date2,fmt); 
        var long = date2.getTime() - date1.getTime(); // 相差毫秒
        switch(field){
	        case this.YEAR: return parseInt(date2.getFullYear() - date1.getFullYear(),10);
			case this.MONTH: return parseInt((date2.getFullYear() - date1.getFullYear(),10)*12 + (date2.getMonth()-date1.getMonth()));
			case this.DAY: return parseInt(long/1000/60/60/24,10);
			case this.WEEK: return parseInt(long/1000/60/60/24/7,10);
			case this.HOUR: return parseInt(long/1000/60/60,10);
			case this.MINUTE: return parseInt(long/1000/60,10);
			case this.SECOND: return parseInt(long/1000,10);
			case this.MILLISECOND: return parseInt(long,10);
        }
        return long;
	},
	isDate:function(obj){
		return (typeof obj == 'date') || (obj.constructor == Date); 
	},
	/**
	 * 比较两个日期 '2013-8-15','2013-6-28' compareDate(param1, param2),如果param1 大于 param2 返回true,反之false
	 */
	compareDate:function(param1, param2) {
	    var arr1 = param1.split("-");
	    var data1 = new Date(arr1[0], arr1[1], arr1[2]);
	    var arr2 = param2.split("-");
	    var data2 = new Date(arr2[0], arr2[1], arr2[2]);
	    if (data1.getTime() > data2.getTime()) {
	        return true;
	    } else {
	    	return false;
	    } 
	}
};

/**
 * json utils
 */
var JsonUtils = {
	// object to string
	serialize: function(obj) {
	    if ($.isFunction(obj)) {
	        return '';
	    } else if ($.isArray(obj)) {
	        return this._serialize(obj);
	    } else if ($.isPlainObject(obj)) {
	        return '[' + this._serialize(obj) + ']';
	    }
	},
	_serialize: function(obj) {
	    if ($.isFunction(obj)) {
	        return '';
	    } else if ($.isArray(obj)) {
	        var json = [];
	        for (var i = 0; i < obj.length; i++) {
	            json.push(this._serialize(obj[i]));
	        }
	        return "[" + json.join(",") + "]";
	    } else if ($.isPlainObject(obj)) {
	        var json = [];
	        for (var key in obj) {
	            var val = obj[key];
	            if ($.isFunction(val))continue;
	            json.push("\"" + key + "\":" + this._serialize(val));
	        }
	        return "{" + json.join(',') + "}";
	    } else {
	        return "\"" + obj + "\"";
	    }
	}  
};
//读取Cookie的函数
function readCookie(name){
	var cookieValue = "";
	var search = name + "=";
	if(document.cookie.length > 0){ 
		offset = document.cookie.indexOf(search);
		if (offset != -1){
			offset += search.length;
			end = document.cookie.indexOf(";", offset);
			if (end == -1) end = document.cookie.length;
				cookieValue = unescape(document.cookie.substring(offset, end));
		}
	}
	return cookieValue;
}
//写入Cookie的函数
function writeCookie(name, value, hours){
	var expire = "";
	if(hours != null){
		expire = new Date((new Date()).getTime() + hours * 3600000);
		expire = "; expires=" + expire.toGMTString();
	}
	document.cookie = name + "=" + escape(value) + expire;
}