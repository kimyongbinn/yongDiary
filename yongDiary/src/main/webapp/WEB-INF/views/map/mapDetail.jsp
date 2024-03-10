<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>간단한 지도 표시하기</title>
</head>
<body>
<div id="map" style="width:100%;height:6

00px;"></div>
<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=tgsnqirzkh"></script>
<script>
var pointX = "${pointX}";
var pointY = "${pointY}";

pointX = parseFloat(pointX);
pointY = parseFloat(pointY);

alert(pointX);
alert(pointY);

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
        '<div class="iw_inner">',
        '   <h3>' + address + '</h3>',
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