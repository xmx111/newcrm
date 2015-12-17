var calendar;
(function($){
	$.fn.calendar = function(config){
		var cfg = $.extend({
			tdClick: function(tdEl,calendar){},
			tdDblClick: function(tdEl,calendar){},
			tdMouseOver:function(tdEl,calendar){},
			tdMouseOut:function(tdEl, calendar){},
			monthChgAfter : function(year, month, calendar) {},
			defaultDate:new Date(),
			startYear:2005,
			maxYearNum: 10,
			content:'节假日'
		},config);
		return this.each(function(){
			var _this = this;
			var $this = $(this);
			this.jC = new calendar(this, cfg);
			$(window).unload(function(){
				$this.unbind();
				_this.jC = null;
			});
		});
	};
	
	var calendarUtil = {
		lunarInfo : [0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,0x06ca0,0x0b550,0x15355,0x04da0,0x0a5b0,0x14573,0x052b0,0x0a9a8,0x0e950,0x06aa0,0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0,0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b6a0,0x195a6,0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x055c0,0x0ab60,0x096d5,0x092e0,0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0,0x14b63],
		solarMonth : [31,28,31,30,31,30,31,31,30,31,30,31],
		gan : ["甲","乙","丙","丁","戊","己","庚","辛","壬","癸"],
		zhi : ["子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"],
		animals : ["鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"],
		solarTerm : ["小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至"],
		sTermInfo : [0,21208,42467,63836,85337,107014,128867,150921,173149,195551,218072,240693,263343,285989,308563,331033,353350,375494,397447,419210,440795,462224,483532,504758],
		nStr1 : ['日','一','二','三','四','五','六','七','八','九','十'],
		nStr2 : ['初','十','廿','卅','□'],
		monthName : ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"],
		sFtv : ["0101*元旦节", "0214 情人节", "0308 妇女节","0312 植树节",
			"0401 愚人节", "0501*劳动节","0504 青年节", "0531 无烟日", "0601 儿童节",			
			"0605 环保日", "0801 建军节","0808 父亲节", "0910 教师节",
			"1001*国庆节", "1002*国庆节","1003*国庆节",
			"1201 艾滋病日", "1224 平安夜","1225 圣诞节"],
		lFtv : ["0101*春节","0102*春节","0103*春节","0115 元宵节","0505* 端午节","0624 火把节","0625 火把节","0626 火把节","0707 七夕","0715 中元节","0815*中秋节","0909 重阳节","1208 腊八节","1223 小年","0100 除夕"],
		wFtv : ["0150 世界麻风日","0520 国际母亲节","0530 全国助残日","0630 父亲节","0730 被奴役国家周","0932 国际和平日","0940 国际聋人节 世界儿童日","0950 世界海事日","1011 国际住房日","1013 国际减轻自然灾害日(减灾日)","1144 感恩节"]
	};
	window.calendarUtil=calendarUtil;
	// 日历对象
	calendar = function(el, cfg){
		this.container = $(el);
		this.cfg = cfg;
		this.date = this.getDate(this.cfg.defaultDate);
		this.currTd = null;
		this.init();
	};
	
	// 初始化 
	calendar.prototype.init = function(){
		this.container.empty();
		this.headerView();
		this.bodyView();
		this.drawHeader();
		this.drawCell();
		this.drawCellsCalendar(this.date.getFullYear(), this.date.getMonth() + 1);
	};
	
	calendar.prototype.getFirstHeader = function() {
		return this.container.find('.thHeader').eq(0);
	};
	
	calendar.prototype.drawHeader = function(year, month, lYear, lMonth, animals) {
		/** 设置 万年历的头 */
		var divLeft = this.container.find('').eq(0);
		var divCenter = this.container.find('.calendar-header-center').eq(0);
		if (this.getFirstHeader().width() - 120 > 120)
			divCenter.css('width', (this.getFirstHeader().width() - 120) + 'px');
		else
			divCenter.css('width', '500px');
	};
	
	/**中文生肖*/
	calendar.prototype.getAnimals = function(year) {
		return calendarUtil.animals[(year-4)%12];
	};
	
	calendar.prototype.getDate = function(date) {
		if (date == null) {
			return new Date();
		}
		if (typeof(date) == 'string') {
			var strs = date.split('-');
			return new Date(parseInt(strs[0],10), parseInt(strs[1],10), parseInt(strs[2],10));
		}
		return date;
	};
	
	// 头
	calendar.prototype.headerView = function() {
		var thead = $('<thead><tr><th class="thHeader" colspan="7"></th></tr><tr><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr></thead>');
		var leftDiv =$('<div class="calendar-header-left"></div>');
		var centerDiv = $('<div class="calendar-header-center"></div>');
		var rightDiv = $('<div class="calendar-header-right"></div>');
		var rightTodayDiv = $('<div class="calendar-header-right-today"></div>');
		
		var $this = this;
		
		var yearHtml = '<select id="calendarYear" class="calendarYear calendar-select" name="calendarYear">';
		var date = new Date();
		for(var i=this.cfg.startYear; i<=date.getFullYear() + $this.cfg.maxYearNum; i++) {
			yearHtml += '<option value="'+i+ '"' + (date.getFullYear() == i ? "selected=\"selected\"" : "") +' >'+i+'</option>';
		}
		yearHtml += '</select>';
		var $year = $(yearHtml);
		$year.change(function(){
			var year = parseInt($year.val(), 10);
			var month = parseInt($month.val(), 10);
			$this.drawCellsCalendar(year, month);
		});
		
		var monthHtml = '<select id="calendarMonth" class="calendarMonth calendar-select" name="calendarMonth">';
		for(var i=1; i<=12; i++) {
			monthHtml += '<option value="'+i+ '"' +((date.getMonth() +1) == i ? "selected=\"selected\"" : "") +'>'+i+'</option>';
		}
		monthHtml += '</select>';
		var $month = $(monthHtml);
		$month.change(function(){
			var month = parseInt($month.val(), 10);
			var year = parseInt($year.val(), 10);
			$this.drawCellsCalendar(year, month);
		});
		
		leftDiv.click(function() {
			var month = parseInt($month.val(), 10);
			var year = parseInt($year.val(), 10);
			if (month > 1) {
				month = month -1;
			}	
			else {
				month = 12;
				year = year - 1;
			}
			$month.val(month);
			$year.val(year);
			if (!!_this.currTd) {
				_this.currTd.find('div').eq(0).removeClass('calendar-div-today').removeClass('calendar-div-selected');
			}
			if (year >= $this.cfg.startYear) {
				$this.drawCellsCalendar(year, month);
			}
		});
		rightDiv.click(function() {
			var month = parseInt($month.val(), 10);
			var year = parseInt($year.val(), 10);
			if (month < 12) {
				month = month + 1;
			}	
			else {
				month = 1;
				year = year + 1;
			}
			$month.val(month);
			$year.val(year);
			if (!!_this.currTd) {
				_this.currTd.find('div').eq(0).removeClass('calendar-div-today').removeClass('calendar-div-selected');
			}
			if (year <= ($this.cfg.maxYearNum + date.getFullYear())) {
				$this.drawCellsCalendar(year, month);
			}
		});
		var _this = this;
		rightTodayDiv.click(function(){
			if (!!_this.currTd) {
				_this.currTd.find('div').eq(0).removeClass('calendar-div-today').removeClass('calendar-div-selected');
			}
			_this.currTd = null;
			$month.val(date.getMonth() + 1);
			$year.val(date.getFullYear());
			$this.drawCellsCalendar(date.getFullYear(), date.getMonth() + 1);	
		});

		var centerLeftDiv = $('<div class="calendar-header-center-left"></div>');
		var centerRightDiv = $('<div id="calendarYearContent" class="calendar-header-center-right">'+this.getYearContent(date.getFullYear(), date.getMonth() + 1)+'</div>');
		
		centerLeftDiv.empty().append($year).append('&nbsp;年&nbsp;').append($month).append('&nbsp;月&nbsp;');
		
		
		centerDiv.empty().append(centerLeftDiv).append(centerRightDiv);
		thead.find('tr:first').find('th').eq(0).append(leftDiv).append(centerDiv).append(rightTodayDiv).append(rightDiv);
		this.container.append(thead);
	};
	
	calendar.prototype.getYearContent = function(year, month) {
		return '&nbsp;&nbsp;农历' + this.getCyclical(year-1900+36) + '年  【'+calendarUtil.animals[(year-4)%12]+'年】';
	};
	
	calendar.prototype.bodyView = function() {
		this.container.append('<tbody></tbody>');
	};
	
	calendar.prototype.drawCellsCalendar = function(year, month) {
		$('#calendarYearContent').empty().html(this.getYearContent(year, month));
		
		var params = this.getCellsCalendar(year, month);
		if (this.cfg.monthChgAfter) {
			this.cfg.monthChgAfter(params, this);
		}
		var cells = params['cells'];
		/** 如果 cell 大于35就显示最后一行，否则就隐藏 */
		if (params.showLastRowCell) {
			this.getBody().find("tr:last").show();
		}
		else {
			this.getBody().find("tr:last").hide();
		}
		var $this = this;
		$.each(cells, function(i,n){
			$this.drawCellCalendar(i+1, n);
		});
	};
	
	calendar.prototype.drawCellCalendar = function(countIndex, cellData) {
		var cell = $('#calendarCell' + countIndex);
		cell.attr('date',[cellData.year,cellData.month,cellData.day].join('-'));
		cell.removeData('cell');
		cell.data('cell',cellData);
		var dayHtml = $('<font>'+cellData.day+'</font>');
		var ldayHtml = $('<font>'+cellData.lZhCnDay+'</font>');
		var holidayHtml = $('<font>'+cellData.holiday+'</font>');
		if  ($.trim(cellData.lunarFestival) != '') {
			ldayHtml.html(cellData.lunarFestival);
		}
		else if ($.trim(cellData.solarFestival) != '') {
			ldayHtml.html(cellData.solarFestival);
		}
		else if ($.trim(cellData.solarTerms) != '') {
			ldayHtml.html(cellData.solarTerms);
		}
		
		cell.find('div').eq(0).removeClass('calendar-div-nocurrmonth');
		if (!cellData.isCurrMonth) {
			cell.find('div').eq(0).addClass('calendar-div-nocurrmonth');
			dayHtml.addClass('calendar-div-day-font-nocurrmonth');
			ldayHtml.addClass('calendar-div-lday-font-nocurrmonth');
		}
		else {
			dayHtml.addClass('calendar-div-day-font-currmonth');
			ldayHtml.addClass('calendar-div-lday-font-currmonth');
		}

		if (cellData.isHoliday) {
			if (cellData.isCurrMonth) {
				dayHtml.addClass('calendar-div-day-font-holiday');
			}
			holidayHtml.addClass('calendar-div-bottom-font-holiday');
			cell.find('div:last').eq(0).empty().html(holidayHtml);
//			cell.find('div').eq(0).addClass('calendar-div-holiday');
		}
		else {
			holidayHtml.addClass('calendar-div-bottom-font');
			cell.find('div').eq(0).removeClass('calendar-div-holiday');
			cell.find('div:last').eq(0).empty();
		}
		var currCell = this.currTd == null ? null : this.currTd.data('cell');
		cell.find('div').eq(0).removeClass('calendar-div-today').removeClass('calendar-div-selected');
		/* 如果是日期是当天时间 */
		if (cellData.isToday) {
			cell.find('div').eq(0).addClass('calendar-div-today calendar-div-selected');
			if (currCell == null) {
				this.currTd = cell;
			}
			else {
				cell.find('div').eq(0).removeClass('calendar-div-selected');
			}
		}
		/* 如果选择时间不为空 , 当前时间不一定就是用户选中的日期 */
		if (currCell != null) {
			if ((currCell != null && (currCell.day == cellData.day && currCell.month == cellData.month && currCell.year == cellData.year))) {
				cell.find('div').eq(0).addClass('calendar-div-selected');
				this.currTd = cell;
			}
		}
        
		cell.find('div').eq(0).find('.calendar-div-topdiv-right').eq(0).html(dayHtml);
		cell.find('div').eq(0).find('.calendar-div-topdiv-left').eq(0).html(ldayHtml);
	};
	
	/** 画出所有日期格 */
	calendar.prototype.drawCell = function() {
		var body = this.getBody();
		body.empty();
		for(var i=0,count=1; i<6;i++) {
			var tr = $('<tr></tr>');
			for(var j=1;j<=7;j++, count++) {
				var td = this.getCell(count);
				tr.append(td);
			}
			body.append(tr);
		}
	};
	
	/** 获取一个单元格 */
	calendar.prototype.getCell = function(id) {
		var html = '<td id="calendarCell'+id+'"><div class="calendar-div">';
		html += '<div class="calendar-div-topdiv">';
		html += '<div class="calendar-div-topdiv-left">';
		html += '</div>';
		html += '<div class="calendar-div-topdiv-right">';
		html += '</div>';
		html += '</div>';
		html += '<div class="calendar-div-bottomdiv">';
		html += '</div>';
		html += '</div></td>';
		var td = $(html);
		var _this = this;
		td.click(function(){
			if (!!_this.currTd){
				_this.currTd.find('div').eq(0).removeClass("calendar-div-selected");
			}
			_this.currTd = td;
			td.find('div').eq(0).addClass("calendar-div-selected");
			
			 $("[name='checkbox']").each(function() {
				if ($(this).attr('checked') == 'checked') {				
					$(this).attr('checked', false);
				}				
			});			
			
			var cell = td.data('cell');
			if (cell != null && (!cell.isCurrMonth)) {
				td.find('div').eq(0).removeClass("calendar-div-selected");
				$('#calendarYear').val(cell.year);
				$('#calendarMonth').val(cell.month);
				_this.drawCellsCalendar(cell.year, cell.month);
			}
			_this.cfg.tdClick(td, _this, td.find('.calendar-div-bottomdiv').eq(0));
		}).dblclick(function(){
			_this.cfg.tdDblClick(td, _this, td.find('.calendar-div-bottomdiv').eq(0));
		}).hover(function(){
			td.find('div').eq(0).addClass("calendar-div-over");
			_this.cfg.tdMouseOver(td, _this, td.find('.calendar-div-bottomdiv').eq(0));
		},function(){
			td.find('div').eq(0).removeClass("calendar-div-over");
			_this.cfg.tdMouseOut(td, _this, td.find('.calendar-div-bottomdiv').eq(0));
		});
		return td;
	};
	
	/** 获取 Body */
	calendar.prototype.getBody = function() {
		return this.container.find('tbody').eq(0);
	};
	
	/** 获取公历年的天数 */
	calendar.prototype.getSolarDays = function(year,month) {
	   	if((month-1)==1) {
	   		return(((year%4 == 0) && (year%100 != 0) || (year%400 == 0))? 29: 28);
	   	}
	   	else {
	   		return(calendarUtil.solarMonth[month-1]);
	   	}
	};
	
	/** 根据农历日期数字返回中文 */
	calendar.prototype.getZhCnDay = function(day){
		var s;
		switch (day) {
			case 10:
				s = '初十'; break;
			case 20:
				s = '二十'; break;
			case 30:
				s = '三十'; break;
			default :
			s = calendarUtil.nStr2[Math.floor(day/10)];
			s += calendarUtil.nStr1[day%10];
		}
		return(s);
	};


	
	/** 返回农历 year 年的总天数 */
	calendar.prototype.getLYearDays = function(year) {
		var i , sum = 348;
		for(i=0x8000; i>0x8; i>>=1) {
			sum += (calendarUtil.lunarInfo[year-1900] & i) ? 1 : 0;
		}
		return (sum + this.getLeapDays(year));
	};
	
	/** 返回农历 year 年闰月的天数 */
	calendar.prototype.getLeapDays = function(year) {
		if(this.getLeapMonth(year)) {
			return ((calendarUtil.lunarInfo[year-1900]&0x10000) ? 30 : 29);
		}
		return (0);
	};
	
	/** 返回农历 Year年闰哪个月1-12, 没闰返回 0 */
	calendar.prototype.getLeapMonth = function(year) {
		return (calendarUtil.lunarInfo[year-1900] &0xf);
	};
	
	/** 返回农历 year年month月的总天数 */
	calendar.prototype.getMonthDays = function(year, month) {
		return ((calendarUtil.lunarInfo[year-1900] & (0x10000 >> month)) ? 30 : 29);
	};
	
	/** 根据日期获取闰年日期 */
	calendar.prototype.getLunar = function(objDate) {
		var i, leap=0, temp=0;
		var baseDate = new Date(1900,0,31);
		var offset   = (objDate - baseDate)/86400000;
		var dayCyl = offset + 40;
		var monCyl = 14;
		 
		for(i=1900; i<2050 && offset>0; i++) {
			temp = this.getLYearDays(i);
			offset -= temp;
			monCyl += 12;
		}
		 
		if(offset<0) {
			offset += temp;
			i--;
			monCyl -= 12;
		}
		 
		var year = i;
		var yearCyl = i-1864;
		 
		leap = this.getLeapMonth(i); //闰哪个月
		var isLeap = false;
		 
		for(i=1; i<13 && offset>0; i++) {
			//闰月
			if(leap>0 && i==(leap+1) && isLeap==false) { 
				--i; isLeap = true; temp = this.getLeapDays(year); 
			}
			else { 
				temp = this.getMonthDays(year, i); 
			}
		 
			//解除闰月
			if(isLeap==true && i==(leap+1)) isLeap = false;
			 
			offset -= temp;
			if(isLeap == false) monCyl ++;
		}
		 
		if(offset==0 && leap>0 && i==leap+1) {
			if(isLeap) { 
				isLeap = false; 
			} else { 
				isLeap = true; --i; --monCyl;
			}
		}
			 
		if(offset<0){ 
			offset += temp; --i; --monCyl; 
		}
			 
		var month = i;
		var day = offset + 1;
		return {'year':year, 'month':month, 'day':day, 'yearCyl':yearCyl, 'monthCyl':monCyl, 'dayCyl':dayCyl,'isLeap':isLeap};
	};
	
	/** 根据年月，获取，一个日历表 */
	calendar.prototype.getCellsCalendar=function(year, month) {		
		var params = {};
		params['year'] = year; // 国历年
		params['month'] = month; //国历月
		var lDate = this.getLunar(year, month, 1);
		params['lYear'] = lDate.year;  //农历年
		params['lMonth'] = lDate.month; //农历月
		params['lIsLeap'] = lDate.isLeap; //农历月是否为闰月
		var oneDate = new Date(year, month-1, 1);
		var monthOneDayWeek = oneDate.getDay();
		params['monthOneDayWeek'] = monthOneDayWeek; // 公历当月1日星期几
		var monthCount = this.getSolarDays(year, month);
		params['monthCount'] = monthCount; // 公历年当月天数
		
		var preYear = month == 1 ? (year -1) : year;
		var preMonth = month == 1 ? 12 : (month -1);
		params['preYear'] = preYear; // 国历年
		params['preMonth'] = preMonth; //国历月
		var preLDate = this.getLunar(preYear, preMonth, 1);
		params['preLYear'] = preLDate.year;  //农历年
		params['preLMonth'] = preLDate.month; //农历月
		params['preLIsLeap'] = preLDate.isLeap; //农历月是否为闰月
		var preMonthCount = this.getSolarDays(preYear, preMonth);
		params['preMonthCount'] = preMonthCount; // 公历年当月天数
		
		var nextYear = month == 12 ? (year + 1) : year;
		var nextMonth = month == 12 ? 1 : (month +1);
		params['nextYear'] = nextYear; // 国历年
		params['nextMonth'] = nextMonth; //国历月
		var nextLDate = this.getLunar(nextYear, nextMonth, 1);
		params['nextLYear'] = nextLDate.year;  //农历年
		params['nextLMonth'] = nextLDate.month; //农历月
		params['nextLIsLeap'] = nextLDate.isLeap; //农历月是否为闰月
		params['nextMonthCount'] = this.getSolarDays(nextYear, nextMonth); // 公历年当月天数
		
		//
		var cells = [];
		var count = 0;
		//节气 一个月都会有两个节气 下面是每个月的天数
		var preTermDay1 = this.getSTerm(preYear, preMonth * 2);
		var preTermDay2 = this.getSTerm(preYear, preMonth * 2 + 1);
		for (var i = 1; i<= monthOneDayWeek; i++) {
			var _preDay = (preMonthCount-monthOneDayWeek + i);
			var cell = this.getCellCalendar(preYear, preMonth, _preDay);
			count++;
			cell.isCurrMonth = false;
			//cell.solarTerms = (preTermDay1 == _preDay) ? (calendarUtil.solarTerm[preMonth*2]) : ((preTermDay2 == _preDay) ? (calendarUtil.solarTerm[preMonth*2 + 1]) : '');
			this.fillCellCalendar(cells, preMonth, _preDay, calendarUtil.sFtv,1);
			this.fillCellCalendar(cells, cell.lMonth, cell.lDay, calendarUtil.lFtv,2);
			cells.push(cell);
		}
		
		var termDay1 = this.getSTerm(year, month * 2);
		var termDay2 = this.getSTerm(year, month * 2 + 1);		
		for (var i =1; i <= monthCount; i++) {
			var cell = this.getCellCalendar(year, month, i);
			count++;
			cell.isCurrMonth = true;			
			//cell.solarTerms = (termDay1 == i) ? (calendarUtil.solarTerm[month*2]) : ((termDay2 == i) ? (calendarUtil.solarTerm[month*2 + 1]) : '');
			this.fillCellCalendar(cell, month, i, calendarUtil.sFtv,1);
			this.fillCellCalendar(cells, cell.lMonth, cell.lDay, calendarUtil.lFtv,2);
			cells.push(cell);
		}
		
		var nextTermDay1 = this.getSTerm(nextYear, nextMonth * 2);
		var nextTermDay2 = this.getSTerm(nextYear, nextMonth * 2 + 1);
		params['showLastRowCell'] = count <= 35 ?  false : true;
		for (var j = count+1, i=1; j<=42;i++, j++) {
			var cell = this.getCellCalendar(nextYear, nextMonth, i);
			count++;
			cell.isCurrMonth = false;
			//cell.solarTerms = (nextTermDay1 == i) ? (calendarUtil.solarTerm[nextMonth*2]) : ((nextTermDay2 == i) ? (calendarUtil.solarTerm[nextMonth*2 + 1]) : '');
			this.fillCellCalendar(cell, nextMonth, i, calendarUtil.sFtv, 1);
			this.fillCellCalendar(cells, cell.lMonth, cell.lDay, calendarUtil.lFtv, 2);
			cells.push(cell);
		}
		params['cells'] = cells;		
		return params;
	};
	
	calendar.prototype.fillCellCalendar = function(cell, month, day, objs, type) {
		var regx = (month >9 ? '' : '0') + month + "" + (day > 9 ? '' : '0') + ""+ day;
		for(i in objs) {
			if (typeof(objs[i]) == 'string')
			if(objs[i].indexOf(regx) == 0) {
				var content = objs[i].substring(5);
				if (type == '1') {
					cell.solarFestival = content;
				}
				else {
					cell.lunarFestival = content;
				}
				if (objs[i].indexOf(regx+'*') == 0) {
					cell.isHoliday = true;
					if (this.cfg.content == null || $.trim(this.cfg.content) == '')	{
						cell.holiday= content;
					}
					else {
						cell.holiday = this.cfg.content;
					}
				}
				break;
			}
		}
	};
	
	calendar.prototype.getCellCalendar = function(year, month, day) {
		var lunar = this.getLunar(new Date(year, month, day));
		var isToday = this.isToday(year, month, day);
		var lZhCnDay = this.getZhCnDay(lunar.day);
		var week = this.getDay(year, month, day);
		var isHoliday = (week == 0 || week == 6);
		var holiday = isHoliday ? '假日' : '';
		var data = {'year':year, 'month': month, 'day' : day, 'lYear':lunar.year, 'lMonth':lunar.month, 'lDay':lunar.day,'lZhCnDay':lZhCnDay, 'isToday' : isToday, 'lunarFestival':'', 'solarFestival' : '', 'solarTerms':'', 'holiday':holiday, 'isHoliday': isHoliday};
		return data;
	};
	
	calendar.prototype.getDay = function(year, month, day) {
		return (new Date(year, month-1, day)).getDay();
	};
	
	/** 返回干支 0=甲子 */
	calendar.prototype.getCyclical = function(num) {
		return (calendarUtil.gan[num%10] + calendarUtil.zhi[num%12]);
	};
	
	/** 某年的第n个节气为几日(从0小寒起算) */
	calendar.prototype.getSTerm = function(y, n) {
		var offDate = new Date((31556925974.7*(y-1900) + calendarUtil.sTermInfo[n]*60000  ) + Date.UTC(1900,0,6,2,5));
		return(offDate.getUTCDate());
	};
	
	/** 判断是否是当前日 */
	calendar.prototype.isToday = function(year, month, day) {
		var date = new Date();
		var currYear =  date.getFullYear();
		var currMonth = date.getMonth() + 1;
		var currDay = date.getDate();
		if ((year == currYear) && (month == currMonth) && (day == currDay)) {
			return true;
		}
		return false;
	};
})(jQuery);