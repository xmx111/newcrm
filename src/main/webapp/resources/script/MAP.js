var MAP ={type:{BAIDU: 'baidu',GOOGLE:"google"},loaded:false,currentType:'google'};

function loadScript(url,success){
   console.log(url);
   return  $.ajax({
	  url: url,
	  dataType: "script",
	  async: true,
	  cache: "false",
	  timeout:60*1000,
	  success: success
   });
}
//加载地图
function loadMap(){
    if(!MAP.loaded){
	if(MAP.type.BAIDU == MAP.currentType){
	    $(document.head).append('<link rel="stylesheet" type="text/css" href="http://api.map.baidu.com/res/13/bmap.css"/>');
	    var url = 'http://api.map.baidu.com/getscript?v=1.3&key=&services=';
	    loadScript(url,function(){
		MAP.loaded=true;
	    });
        }else if(MAP.type.GOOGLE == MAP.currentType){
            //'http://maps.gstatic.com/intl/zh_cn/mapfiles/api-3/9/0/main.js'
            var url ='http://maps.google.com/maps/api/js?sensor=false';
            loadScript(url,function(){
        	loadScript('http://maps.gstatic.com/intl/zh_cn/mapfiles/api-3/9/0/main.js');
        	MAP.loaded=true;
            });
            //$(document).append('<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&language=zh-CN&t='+new Date().getTime()+'"></script>');
            //$(document).append('<script type="text/javascript" src="http://maps.gstatic.com/intl/zh_cn/mapfiles/api-3/9/0/main.js&t='+new Date().getTime()+'"></script>');
        }
    }
}
//地图
function initializeMap(id,lng,lat,name){
    if(!MAP.loaded){
	return null;
    }
    lng = lng==null ||lng==''? 112.969746 : lng;
    lat = lat==null ||lat==''? 28.201563 : lat;
    if(MAP.type.BAIDU == MAP.currentType){
        if(!!!BMap || !!!BMap.Map){return null;}
        var map = new BMap.Map(id);
        var point = new BMap.Point(lng, lat);
        map.centerAndZoom(point, 14);
        map.enableScrollWheelZoom(); 
        map.addControl(new BMap.NavigationControl());
        if(name!=''){
            var marker = new BMap.Marker(point); 
            map.addOverlay(marker);
            //marker.setLabel(new BMap.Label(name,{offset:new BMap.Size(10,-40)}));
            var infoWindow = new BMap.InfoWindow(name);  // 创建信息窗口对象
            marker.addEventListener("click", function(){          
               this.openInfoWindow(infoWindow);  
            });
        }
	map.addEventListener("click", function(e){
	    //type, target, point, pixel, overlay
	    var point = e.point;
	    $('#_lng').val(point.lng);
	    $('#_lat').val(point.lat);
	}); 
	$('#'+id).data('map',map);
        return map;
    }else if(MAP.type.GOOGLE == MAP.currentType ){
	if(!!!google || !!!google.maps || !!!google.maps.Map){return null;}
	var center = new google.maps.LatLng(lat, lng);
	var map = new google.maps.Map(document.getElementById(id), {
              zoom: 13, //放大比例
              center: center, //经纬度
              mapTypeId: google.maps.MapTypeId.ROADMAP, //地图类型ROADMAP、SATELLITE、HYBRID
              panControl: false, //方向盘
              scaleControl: false, //比例尺
              mapTypeControl: false, //可以选的地图类型，下面是配置
              mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
              streetViewControl:false,//街头小人
              zoomControl: true, //放大按钮，下面是配置
              zoomControlOptions: {
                  style: google.maps.ZoomControlStyle.LARGE,//LARGE
                  position: google.maps.ControlPosition.LEFT_TOP
              }
          });
	  google.maps.event.addListener(map, 'click', function(event) {
	     var point =  event.latLng;
	     $('#_lng').val(point.lng());
	     $('#_lat').val(point.lat());	     
	   });
	  if(name!=''){
	      var marker = new google.maps.Marker({
		    position: center,
		    'draggable': false,
		    'animation': google.maps.Animation.DROP,
		    map: map
	      	});
	      var infowindow = new google.maps.InfoWindow({
		        content: name
	      });
	      google.maps.event.addListener(marker, 'click', function(){
		        infowindow.open(map,marker);
	      });
	      $('#'+id).data('marker',marker);
	  }
	  $('#'+id).data('map',map);
	  return map;
    }else{
	return null;
    }
}