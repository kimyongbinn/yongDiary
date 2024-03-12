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
<body>
	<div class="col-lg-11 wow fadeInUp" data-wow-delay="0.5s">
     	<div class="mb-9" style="text-align: center;">
           <!-- heading -->
           <p style="margin-bottom: 15px;">${roadAddr }</p>
           <button type="button" class="btn btn-primary" style="margin-bottom: 35px;"><a href="javascript:history.back()">목록</a></button>
        </div>
		<div id="map" style="height:500px;"></div>
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
        content: contentString
    });
    infowindow.open(map, marker);
   
} else {
    console.error('Invalid coordinates: pointX=', pointX, ', pointY=', pointY);
}

</script>
</body>
</html>