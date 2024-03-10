<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=tgsnqirzkh&submodules=geocoder"></script>
<script language="javascript">



function getAddrLoc(){
	// 적용예 (api 호출 전에 검색어 체크) 	
	if (!checkSearchedWord(document.form.keyword)) {
		return ;
	}
	alert(keyword);
	$.ajax({
		 url :"/sample/getAddrApi.do"
		,type:"post"
		,data:$("#form").serialize()
		,dataType:"json"
		,success:function(jsonStr){
			$("#list").html("");
			var errCode = jsonStr.results.common.errorCode;
			var errDesc = jsonStr.results.common.errorMessage;
			if(errCode != "0"){
				alert(errCode+"="+errDesc);
			}else{
				if(jsonStr != null){
					makeListJson(jsonStr);
				}
			}
		}
	    ,error: function(xhr,status, error){
	    	alert("에러발생");
	    }
	});
}
function makeListJson(jsonStr){
	var htmlStr = "";
	
	$(jsonStr.results.juso).each(function(){
		var roadAddr = this.roadAddr;
		htmlStr += "<tr>";
		htmlStr += "<td>"+ roadAddr +"/"+ this.jibunAddr+"</td>";
		htmlStr += "<td>"+this.zipNo+"</td>";
		htmlStr += "<td><input type='button' value='지도보기' onclick='mapDetail(\""+ roadAddr +"\")'></td>";
		htmlStr += "</tr>";
	});

	$("#list").html(htmlStr);
}

function mapDetail(roadAddr) {
    // Assuming you want to navigate to another page using the roadAddr parameter
    // Replace 'target_page.html' with your target page URL
        naver.maps.Service.geocode({
        query: roadAddr
        
    }, function(status, response) {
        var htmlAddresses = [],
        item = response.v2.addresses[0],
        point = new naver.maps.Point(item.x, item.y);

	    if (item.roadAddress) {
	        htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
	    }
	
	    if (item.jibunAddress) {
	        htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
	    }
	
	    if (item.englishAddress) {
	        htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
	    }
	
		alert(point);
		alert(point.x);
		window.location.href = '/map/mapDetail?roadAddr=' + roadAddr +'&pointX=' + point.x +"&pointY=" + point.y;
    });
   
}


//특수문자, 특정문자열(sql예약어의 앞뒤공백포함) 제거
function checkSearchedWord(obj){
	if(obj.value.length >0){
		//특수문자 제거
		var expText = /[%=><]/ ;
		if(expText.test(obj.value) == true){
			alert("특수문자를 입력 할수 없습니다.") ;
			obj.value = obj.value.split(expText).join(""); 
			return false;
		}
		
		//특정문자열(sql예약어의 앞뒤공백포함) 제거
		var sqlArray = new Array(
			//sql 예약어
			"OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
             		 "UNION",  "FETCH", "DECLARE", "TRUNCATE" 
		);
		
		var regex;
		for(var i=0; i<sqlArray.length; i++){
			regex = new RegExp( sqlArray[i] ,"gi") ;
			
			if (regex.test(obj.value) ) {
			    alert("\"" + sqlArray[i]+"\"와(과) 같은 특정문자로 검색할 수 없습니다.");
				obj.value =obj.value.replace(regex, "");
				return false;
			}
		}
	}
	return true ;
}

function enterSearch(event) {
    var evt_code = (window.netscape) ? event.which : event.keyCode;
    if (evt_code == 13) {    
        event.preventDefault(); // Prevent the default form submission
        getAddrLoc(); 
    } 
}


</script>
<title>Insert title here</title>
</head>
<body>
<div class="row g-0 justify-content-center">
	<div class="col-lg-11 wow fadeInUp" data-wow-delay="0.5s">
     	<div class="mb-9" style="text-align: center;">
           <!-- heading -->
           <h2 style="margin-bottom: 15px;">주소 찾기</h2>
           <p style="margin-bottom: 35px;">주소를 검색해보세요!</p>
       </div>
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
				<!-- <input type="button" onClick="getAddrLoc();" value="주소검색하기"/>
 -->
			</div>
		</form>
			<table class="table" style="text-align: center;">
				<thead>
					<tr>
						<th>도로명/지번</th>
						<th>우편번호</th>
						<th>지도보기</th>
					</tr>
				</thead>
				
				<tbody id="list">
				</tbody>
				
			</table>
			<!-- <div  class="listTable" id="list" ></div>검색 결과 리스트 출력 영역 -->

	</div>
</div>
</body>
</html>