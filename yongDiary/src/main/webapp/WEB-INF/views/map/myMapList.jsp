<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var placeName = $("#placeName_" + index).val();
	
	$.ajax({
		 url :"/map/myMapDetail"
		,type: "post"
		,data:placeName
		,dataType:"json"
		,success:function(jsonStr) {
			
		}
	})
</script>
</head>
<body>
   	<div class="mb-9" style="text-align: center;">
        <h2 style="margin-bottom: 35px;">MyMap</h2>
    </div>
	<div id="map" style="width:60%; height:350px; margin-bottom: 15px;"></div>
	<div class="col-lg-11 wow fadeInUp" data-wow-delay="0.5s">

		<form name="form" id="form"method="post">
			<div class="input-group col-md-5 mb-3" style="width:70%; margin: 0 auto;"> 
				<input type="hidden" name="currentPage" value="1"/> <!-- 요청 변수 설정 (현재 페이지. currentPage : n > 0) -->
				<input type="hidden" name="countPerPage" value="10"/><!-- 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100) -->
				<input type="hidden" name="resultType" value="json"/> <!-- 요청 변수 설정 (검색결과형식 설정, json) --> 
				<input type="hidden" name="confmKey" id="confmKey" style="width:250px;display:none" value="devU01TX0FVVEgyMDI0MDMwOTIxMjM0NDExNDU4MDk="/><!-- 요청 변수 설정 (승인키) -->
				<input type="text" class="form-control rounded" name="keyword" id="keyword" onkeypress="enterSearch(event)"/>
				<div style="margin-left: 10px; width: 65px; margin-top: 6px;">
					<button type="button" onClick="getAddrLoc();" class="btn bi bi-search rounded"></button>
				</div>
			</div>
		</form>
			<div class="input-group col-md-5 mb-3" style="width:70%; margin: 0 auto;"> 
				<table class="table" style="text-align: center;" id="recentSearches">
					<thead>
						<tr>
							<td>장소</td>
							<td>추가 날짜</td>
							<td>지도보기</td>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${myMapList }" var="myMapList" varStatus="status" step="1" end="4">
							<tr>
								<td><a href="#" onclick="getAddrLoc('${status.index}'); return false;">${myMapList.placeName }</a>
								<td><fmt:formatDate pattern="YYYY년MM월dd일" value="${myMapList.addDate }"></fmt:formatDate></td>
								<td><a href="/map/deleteSearch?keyword=${searchList.keyword }"><i class="bi bi-map"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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
        content: contentString
    });
    infowindow.open(map, marker);
   
} else {
    console.error('Invalid coordinates: pointX=', pointX, ', pointY=', pointY);
}
</script>
</body>
</html>