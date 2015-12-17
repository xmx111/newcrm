var Index = function ()
{
    return {
        init : function ()
        {
            App.addResponsiveHandler(function ()
            {
                jQuery(".vmaps").each(function ()
                {
                    var a = jQuery(this);
                    a.width(a.parent().width())
                })
            })
        },
        initJQVMAP : function ()
        {
            var b = function (c)
            {
                jQuery(".vmaps").hide();
                jQuery("#vmap_" + c).show()
            };
            var a = function (c)
            {
                var d = 
                {
                    map : "world_en", backgroundColor : null, borderColor : "#333333", borderOpacity : 0.5, 
                    borderWidth : 1, color : "#c6c6c6", enableZoom : true, hoverColor : "#c9dfaf", hoverOpacity : null, 
                    values : sample_data, normalizeFunction : "linear", scaleColors : ["#b6da93", "#909cae"], 
                    selectedColor : "#c9dfaf", selectedRegion : null, showTooltip : true, onLabelShow : function (h, 
                    f, g) {},
                    onRegionOver : function (g, f)
                    {
                        if (f == "ca") {
                            g.preventDefault()
                        }
                    },
                    onRegionClick : function (f, h, i)
                    {
                        var g = 'You clicked "' + i + '" which has the code: ' + h.toUpperCase();
                        alert(g)
                    }
                };
                d.map = c + "_en";
                var e = jQuery("#vmap_" + c);
                if (!e) {
                    return
                }
                e.width(e.parent().parent().width());
                e.show();
                e.vectorMap(d);
                e.hide()
            };
            a("world");
            a("usa");
            a("europe");
            a("russia");
            a("germany");
            b("world");
            jQuery("#regional_stat_world").click(function ()
            {
                b("world")
            });
            jQuery("#regional_stat_usa").click(function ()
            {
                b("usa")
            });
            jQuery("#regional_stat_europe").click(function ()
            {
                b("europe")
            });
            jQuery("#regional_stat_russia").click(function ()
            {
                b("russia")
            });
            jQuery("#regional_stat_germany").click(function ()
            {
                b("germany")
            });
            $("#region_statistics_loading").hide();
            $("#region_statistics_content").show()
        },
        initCalendar : function ()
        {
            if (!jQuery().fullCalendar) {
                return
            }
            var b = new Date();
            var e = b.getDate();
            var a = b.getMonth();
            var f = b.getFullYear();
            var c = {};
            if ($("#calendar").width() <= 400)
            {
                $("#calendar").addClass("mobile");
                c = {
                    left : "title, prev, next", center : "", right : "today,month,agendaWeek,agendaDay"
                }
            }
            else
            {
                $("#calendar").removeClass("mobile");
                if (App.isRTL()) {
                    c = {
                        right : "title", center : "", left : "prev,next,today,month,agendaWeek,agendaDay"
                    }
                }
                else {
                    c = {
                        left : "title", center : "", right : "prev,next,today,month,agendaWeek,agendaDay"
                    }
                }
            }
            $("#calendar").fullCalendar("destroy");
            $("#calendar").fullCalendar(
            {
                disableDragging : false, header : c, editable : true, events : [
                {
                    title : "All Day Event", start : new Date(f, a, 1), backgroundColor : App.getLayoutColorCode("yellow")
                },

                {
                    title : "Long Event", start : new Date(f, a, e - 5), end : new Date(f, a, e - 2), 
                    backgroundColor : App.getLayoutColorCode("green")
                },

                {
                    title : "Repeating Event", start : new Date(f, a, e - 3, 16, 0), allDay : false, backgroundColor : App.getLayoutColorCode("red")
                },

                {
                    title : "Repeating Event", start : new Date(f, a, e + 4, 16, 0), allDay : false, backgroundColor : App.getLayoutColorCode("green")
                },
                {
                    title : "Meeting", start : new Date(f, a, e, 10, 30), allDay : false, 
                },

                {
                    title : "Lunch", start : new Date(f, a, e, 12, 0), end : new Date(f, a, e, 14, 0), 
                    backgroundColor : App.getLayoutColorCode("grey"), allDay : false, 
                },

                {
                    title : "Birthday Party", start : new Date(f, a, e + 1, 19, 0), end : new Date(f, 
                    a, e + 1, 22, 30), backgroundColor : App.getLayoutColorCode("purple"), allDay : false, 
                },

                {
                    title : "Click for Google", start : new Date(f, a, 28), end : new Date(f, a, 29), 
                    backgroundColor : App.getLayoutColorCode("yellow"), url : "http://google.com/", 
                }]
            })
        },
        initCharts : function ()
        {
            if (!jQuery.plot) {
                return
            }
            var i = [];
            var j = 250;
            function b()
            {
                if (i.length > 0) {
                    i = i.slice(1)
                }
                while (i.length < j)
                {
                    var q = i.length > 0 ? i[i.length - 1] : 50;
                    var r = q + Math.random() * 10 - 5;
                    if (r < 0) {
                        r = 0
                    }
                    if (r > 100) {
                        r = 100
                    }
                    i.push(r)
                }
                var p = [];
                for (var o = 0; o < i.length; ++o) {
                    p.push([o, i[o]])
                }
                return p
            }
            function n(q, o, r, p)
            {
                $('<div id="tooltip" class="chart-tooltip"><div class="date">' + q + '</div><div class="label label-success">CTR: ' + o / 10 + '%</div><div class="label label-danger">Imp: ' + o * 12 + "</div></div>").css(
                {
                    position : "absolute", display : "none", top : r - 100, width : 80, left : o - 40, 
                    border : "0px solid #ccc", padding : "2px 6px", "background-color" : "#fff", 
                }).appendTo("body").fadeIn(200)
            }
            function m()
            {
                return (Math.floor(Math.random() * (1 + 50 - 20))) + 10
            }
            var l = [[1, m()], [2, m()], [3, 2 + m()], [4, 3 + m()], [5, 5 + m()], [6, 10 + m()], [7, 
            15 + m()], [8, 20 + m()], [9, 25 + m()], [10, 30 + m()], [11, 35 + m()], [12, 25 + m()], [13, 
            15 + m()], [14, 20 + m()], [15, 45 + m()], [16, 50 + m()], [17, 65 + m()], [18, 70 + m()], 
            [19, 85 + m()], [20, 80 + m()], [21, 75 + m()], [22, 80 + m()], [23, 75 + m()], [24, 70 + m()], 
            [25, 65 + m()], [26, 75 + m()], [27, 80 + m()], [28, 85 + m()], [29, 90 + m()], [30, 95 + m()]];
            var c = [[1, m() - 5], [2, m() - 5], [3, m() - 5], [4, 6 + m()], [5, 5 + m()], [6, 20 + m()], 
            [7, 25 + m()], [8, 36 + m()], [9, 26 + m()], [10, 38 + m()], [11, 39 + m()], [12, 50 + m()], 
            [13, 51 + m()], [14, 12 + m()], [15, 13 + m()], [16, 14 + m()], [17, 15 + m()], [18, 15 + m()], 
            [19, 16 + m()], [20, 17 + m()], [21, 18 + m()], [22, 19 + m()], [23, 20 + m()], [24, 21 + m()], 
            [25, 14 + m()], [26, 24 + m()], [27, 25 + m()], [28, 26 + m()], [29, 27 + m()], [30, 31 + m()]];
            if ($("#site_statistics").size() != 0)
            {
                $("#site_statistics_loading").hide();
                $("#site_statistics_content").show();
                var d = $.plot($("#site_statistics"), [ {
                    data : l, label : "Unique Visits"
                },
                {
                    data : c, label : "Page Views"
                }], 
                {
                    series : 
                    {
                        lines : 
                        {
                            show : true, lineWidth : 2, fill : true, fillColor : {
                                colors : [ {
                                    opacity : 0.05
                                },
                                {
                                    opacity : 0.01
                                }]
                            }
                        },
                        points : {
                            show : true
                        },
                        shadowSize : 2
                    },
                    grid : {
                        hoverable : true, clickable : true, tickColor : "#eee", borderWidth : 0
                    },
                    colors : ["#d12610", "#37b7f3", "#52e136"], xaxis : {
                        ticks : 11, tickDecimals : 0
                    },
                    yaxis : {
                        ticks : 11, tickDecimals : 0
                    }
                });
                var h = null;
                $("#site_statistics").bind("plothover", function (q, s, p)
                {
                    $("#x").text(s.x.toFixed(2));
                    $("#y").text(s.y.toFixed(2));
                    if (p)
                    {
                        if (h != p.dataIndex)
                        {
                            h = p.dataIndex;
                            $("#tooltip").remove();
                            var o = p.datapoint[0].toFixed(2), r = p.datapoint[1].toFixed(2);
                            n("24 Jan 2013", p.pageX, p.pageY, p.series.label + " of " + o + " = " + r)
                        }
                    }
                    else {
                        $("#tooltip").remove();
                        h = null;
                    }
                })
            }
            if ($("#load_statistics").size() != 0)
            {
                $("#load_statistics_loading").hide();
                $("#load_statistics_content").show();
                var g = 30;
                var d = $.plot($("#load_statistics"), [b()], 
                {
                    series : {
                        shadowSize : 1
                    },
                    lines : {
                        show : true, lineWidth : 0.2, fill : true, fillColor : {
                            colors : [ {
                                opacity : 0.1
                            },
                            {
                                opacity : 1
                            }]
                        }
                    },
                    yaxis : {
                        min : 0, max : 100,
                        tickFormatter : function (o)
                        {
                            return o + "%";
                        }
                    },
                    xaxis : {
                        show : false
                    },
                    colors : ["#e14e3d"], grid : {
                        tickColor : "#a8a3a3", borderWidth : 0
                    }
                });
                function k()
                {
                    d.setData([b()]);
                    d.draw();
                    setTimeout(k, g)
                }
                k();
                $("#load_statistics").bind("mouseleave", function ()
                {
                    $("#tooltip").remove()
                })
            }
            if ($("#site_activities").size() != 0)
            {
                var a = null;
                $("#site_activities_loading").hide();
                $("#site_activities_content").show();
                var f = [[1, 10], [2, 9], [3, 8], [4, 6], [5, 5], [6, 3], [7, 9], [8, 10], [9, 12], [10, 
                14], [11, 15], [12, 13], [13, 11], [14, 10], [15, 9], [16, 8], [17, 12], [18, 14], [19, 
                16], [20, 19], [21, 20], [22, 20], [23, 19], [24, 17], [25, 15], [25, 14], [26, 12], [27, 
                10], [28, 8], [29, 10], [30, 12], [31, 10], [32, 9], [33, 8], [34, 6], [35, 5], [36, 3], 
                [37, 9], [38, 10], [39, 12], [40, 14], [41, 15], [42, 13], [43, 11], [44, 10], [45, 9], 
                [46, 8], [47, 12], [48, 14], [49, 16], [50, 12], [51, 10]];
                var e = $.plot($("#site_activities"), [
                {
                    data : f, color : "rgba(107,207,123, 0.9)", shadowSize : 0, bars : {
                        show : true, lineWidth : 0, fill : true, fillColor : {
                            colors : [ {
                                opacity : 1
                            },
                            {
                                opacity : 1
                            }]
                        }
                    }
                }], 
                {
                    series : {
                        bars : {
                            show : true, barWidth : 0.9
                        }
                    },
                    grid : {
                        show : false, hoverable : true, clickable : false, autoHighlight : true, borderWidth : 0
                    },
                    yaxis : {
                        min : 0, max : 20
                    }
                });
                $("#site_activities").bind("plothover", function (q, s, p)
                {
                    $("#x").text(s.x.toFixed(2));
                    $("#y").text(s.y.toFixed(2));
                    if (p)
                    {
                        if (a != p.dataIndex)
                        {
                            a = p.dataIndex;
                            $("#tooltip").remove();
                            var o = p.datapoint[0].toFixed(2), r = p.datapoint[1].toFixed(2);
                            n("24 Feb 2013", p.pageX, p.pageY, o)
                        }
                    }
                });
                $("#site_activities").bind("mouseleave", function ()
                {
                    $("#tooltip").remove()
                })
            }
        },
        initMiniCharts : function ()
        {
            $(".easy-pie-chart .number.transactions").easyPieChart({
                animate : 1000, size : 75, lineWidth : 3, barColor : App.getLayoutColorCode("yellow")
            });
            $(".easy-pie-chart .number.visits").easyPieChart({
                animate : 1000, size : 75, lineWidth : 3, barColor : App.getLayoutColorCode("green")
            });
            $(".easy-pie-chart .number.bounce").easyPieChart({
                animate : 1000, size : 75, lineWidth : 3, barColor : App.getLayoutColorCode("red")
            });
            $(".easy-pie-chart-reload").click(function ()
            {
                $(".easy-pie-chart .number").each(function ()
                {
                    var a = Math.floor(100 * Math.random());
                    $(this).data("easyPieChart").update(a);
                    $("span", this).text(a)
                })
            });
            $("#sparkline_bar").sparkline([8, 9, 10, 11, 10, 10, 12, 10, 10, 11, 9, 12, 11, 10, 9, 11, 
            13, 13, 12], 
            {
                type : "bar", width : "100", barWidth : 5, height : "55", barColor : "#35aa47", negBarColor : "#e02222"
            });
            $("#sparkline_bar2").sparkline([9, 11, 12, 13, 12, 13, 10, 14, 13, 11, 11, 12, 11, 11, 10, 
            12, 11, 10], 
            {
                type : "bar", width : "100", barWidth : 5, height : "55", barColor : "#ffb848", negBarColor : "#e02222"
            });
            $("#sparkline_line").sparkline([9, 10, 9, 10, 10, 11, 12, 10, 10, 11, 11, 12, 11, 10, 12, 
            11, 10, 12], {
                type : "line", width : "100", height : "55", lineColor : "#ffb848"
            })
        },
        initChat : function ()
        {
            var a = $("#chats");
            var f = $(".chats", a);
            var e = $(".chat-form", a);
            var c = $("input", e);
            var d = $(".btn", e);
            var b = function (j)
            {
                j.preventDefault();
                var l = c.val();
                if (l.length == 0) {
                    return
                }
                var i = new Date();
                var h = i.toString("MMM dd, yyyy hh:mm");
                var g = "";
                g += '<li class="out">';
                g += '<img class="avatar" alt="" src=' + WEB_ROOT + '"/resources/assets/img/avatar1.jpg"/>';
                g += '<div class="message">';
                g += '<span class="arrow"></span>';
                g += '<a href="#" class="name">Bob Nilson</a>&nbsp;';
                g += '<span class="datetime">at ' + h + "</span>";
                g += '<span class="body">';
                g += l;
                g += "</span>";
                g += "</div>";
                g += "</li>";
                var k = f.append(g);
                c.val("");
                $(".scroller", a).slimScroll({
                    scrollTo : f.height()
                })
            };
            $("body").on("click", ".message .name", function (h)
            {
                h.preventDefault();
                var g = $(this).text();
                c.val("@" + g + ":");
                App.scrollTo(c)
            });
            d.click(b);
            c.keypress(function (g)
            {
                if (g.which == 13) {
                    b();
                    return false;
                }
            })
        },
        initDashboardDaterange : function ()
        {
            $("#dashboard-report-range").daterangepicker(
            {
                opens : (App.isRTL() ? "right" : "left"), startDate : moment().subtract("days", 29), endDate : moment(), 
                minDate : "01/01/2012", maxDate : "12/31/2014", dateLimit : {
                    days : 60
                },
                showDropdowns : false, showWeekNumbers : true, timePicker : false, timePickerIncrement : 1, 
                timePicker12Hour : true, ranges : 
                {
                    Today : [moment(), moment()], Yesterday : [moment().subtract("days", 1), moment().subtract("days", 
                    1)], "Last 7 Days" : [moment().subtract("days", 6), moment()], "Last 30 Days" : [moment().subtract("days", 
                    29), moment()], "This Month" : [moment().startOf("month"), moment().endOf("month")], 
                    "Last Month" : [moment().subtract("month", 1).startOf("month"), moment().subtract("month", 
                    1).endOf("month")]
                },
                buttonClasses : ["btn"], applyClass : "blue", cancelClass : "default", format : "MM/DD/YYYY", 
                separator : " to ", locale : 
                {
                    applyLabel : "Apply", fromLabel : "From", toLabel : "To", customRangeLabel : "Custom Range", 
                    daysOfWeek : ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"], monthNames : ["January", 
                    "February", "March", "April", "May", "June", "July", "August", "September", "October", 
                    "November", "December"], firstDay : 1
                }
            },
            function (b, a)
            {
                console.log("Callback has been called!");
                $("#dashboard-report-range span").html(b.format("MMMM D, YYYY") + " - " + a.format("MMMM D, YYYY"))
            });
            $("#dashboard-report-range span").html(moment().subtract("days", 29).format("MMMM D, YYYY") + " - " + moment().format("MMMM D, YYYY"));
            $("#dashboard-report-range").show()
        },
        initIntro : function ()
        {
            if ($.cookie("intro_show")) {
                return
            }
            $.cookie("intro_show", 1);
            setTimeout(function ()
            {
                var a = $.gritter.add(
                {
                    title : "Meet Metronic!", text : "Metronic is a brand new Responsive Admin Dashboard Template you have always been looking for!", 
                    image : WEB_ROOT + "/resources/assets/img/avatar1.jpg", sticky : true, time : "", class_name : "my-sticky-class"
                });
                setTimeout(function ()
                {
                    $.gritter.remove(a, {
                        fade : true, speed : "slow"
                    })
                }, 12000)
            }, 2000);
            setTimeout(function ()
            {
                var a = $.gritter.add(
                {
                    title : "Buy Metronic!", text : "Metronic comes with a huge collection of reusable and easy customizable UI components and plugins. Buy Metronic today!", 
                    image : WEB_ROOT + "/resources/img/avatar1.jpg", sticky : true, time : "", class_name : "my-sticky-class"
                });
                setTimeout(function ()
                {
                    $.gritter.remove(a, {
                        fade : true, speed : "slow"
                    })
                }, 13000)
            }, 8000);
            setTimeout(function ()
            {
                $("#styler").pulsate({
                    color : "#bb3319", repeat : 10
                });
                $.extend($.gritter.options, {
                    position : "top-left"
                });
                var a = $.gritter.add(
                {
                    position : "top-left", title : "Customize Metronic!", text : "Metronic allows you to easily customize the theme colors and layout settings.", 
                    image1 : WEB_ROOT + "/resources/img/avatar1.png", sticky : true, time : "", class_name : "my-sticky-class"
                });
                $.extend($.gritter.options, {
                    position : "top-right"
                });
                setTimeout(function ()
                {
                    $.gritter.remove(a, {
                        fade : true, speed : "slow"
                    })
                }, 15000)
            }, 23000);
            setTimeout(function ()
            {
                $.extend($.gritter.options, {
                    position : "top-left"
                });
                var a = $.gritter.add(
                {
                    title : "Notification", text : "You have 3 new notifications.", image1 : WEB_ROOT + "/resources/assets/img/image1.jpg", 
                    sticky : true, time : "", class_name : "my-sticky-class"
                });
                setTimeout(function ()
                {
                    $.gritter.remove(a, {
                        fade : true, speed : "slow"
                    })
                }, 4000);
                $.extend($.gritter.options, {
                    position : "top-right"
                });
                var b = $("#header_notification_bar .badge").text();
                b = parseInt(b);
                b = b + 3;
                $("#header_notification_bar .badge").text(b);
                $("#header_notification_bar").pulsate({
                    color : "#66bce6", repeat : 5
                })
            }, 40000);
            setTimeout(function ()
            {
                $.extend($.gritter.options, {
                    position : "top-left"
                });
                var a = $.gritter.add(
                {
                    title : "Inbox", text : "You have 2 new messages in your inbox.", image1 : "./assets/img/avatar1.jpg", 
                    sticky : true, time : "", class_name : "my-sticky-class"
                });
                $.extend($.gritter.options, {
                    position : "top-right"
                });
                setTimeout(function ()
                {
                    $.gritter.remove(a, {
                        fade : true, speed : "slow"
                    })
                }, 4000);
                var b = $("#header_inbox_bar .badge").text();
                b = parseInt(b);
                b = b + 2;
                $("#header_inbox_bar .badge").text(b);
                $("#header_inbox_bar").pulsate({
                    color : "#dd5131", repeat : 5
                })
            }, 60000)
        }
    }
}();
