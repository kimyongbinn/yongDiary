<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>지도 검색</title>
</head>
<script type="text/javascript">
if (window.history.replaceState) {
    window.history.replaceState(null, null, window.location.href);
}



	function addMapPop() {
		var popupW = 600;
		var popupH = 500;
		var left = Math.ceil((window.screen.width - popupW)/2);
		var top = Math.ceil((window.screen.height - popupH)/2);
	
		var url = "/popup/addMapPopup?roadAddr=${roadAddr}";
        var name = "addMyMap";
        
        window.open(url, name, 'width='+popupW+',height='+popupH+',left='+left+',top='+top)
	}
	window.addEventListener('load', function() {
	    if (window.history.replaceState) {
	        window.history.replaceState(null, null, window.location.href);
	    }
	});
</script>
<body>
	<div class="col-lg-11 wow fadeInUp" data-wow-delay="0.5s">
     	<div class="mb-9" style="text-align: center;">
           <!-- heading -->
           <p style="margin-bottom: 15px;">${roadAddr }</p>
           <div id="map" style="height:500px; margin-bottom: 15px;"></div>
           <button type="button" class="btn btn-primary" style="margin-bottom: 35px;"><a href="javascript:history.back(${keyword })" style="color: white">목록</a></button>
           <button type="button" class="btn btn-primary" style="margin-bottom: 35px;" onclick="addMapPop()">내 지도 추가</button>
        </div>
		
	</div>
<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=tgsnqirzkh"></script>
<script>
var pointX = "${pointX}";
var pointY = "${pointY}";

if (!isNaN(pointX) && !isNaN(pointY)) {
    var centerCoordinate = new naver.maps.LatLng(pointY, pointX); 
    
    var map = new naver.maps.Map('map', {
        center: centerCoordinate,
        zoom: 15
    });
    
    var marker = new naver.maps.Marker({
        position: centerCoordinate,
        map: map
    });
    
    var address = "${roadAddr}";
    var contentString = [
        '<div class="form-floating">',
        ' ' + address + ' ',
        '</div>'
    ].join('');
    
    var infowindow = new naver.maps.InfoWindow({
        content: contentString,
        maxWidth: 250,
        backgroundColor: "white",
        borderColor: "#AB7442",
        margin: 15,
        borderWidth: 5,
        anchorSize: new naver.maps.Size(30, 30),
        anchorSkew: true,
        anchorColor: "white",
        pixelOffset: new naver.maps.Point(20, -20)
    });
    infowindow.open(map, marker);
   
} else {
    console.error('Invalid coordinates: pointX=', pointX, ', pointY=', pointY);
}

</script>
</body>
</html>