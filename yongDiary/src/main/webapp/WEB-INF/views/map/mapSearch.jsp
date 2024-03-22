<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="UTF-8">
<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=tgsnqirzkh&submodules=geocoder"></script>
<script language="javascript">
var sortValue = null;
$(document).ready(function() {
	$("#navBar").hide();
});

// document.getElementById("btn").addEventListener("click", function(event) {
//     event.preventDefault(); // 기본 동작인 링크 이동을 막음
    
//     var xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성
//     xhr.open("POST", "/map/mapDetail", true); // POST 요청 설정
//     xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); // 요청 헤더 설정
//     xhr.onreadystatechange = function() {
//         if (xhr.readyState === 4 && xhr.status === 200) {
//             // 서버로부터 응답을 받았을 때 수행할 작업
//             console.log("POST 요청 성공");
//         }
//     };
//     // POST 요청 본문에 데이터 추가 (필요에 따라 데이터 추가)
//     var postData = "data1=value1&data2=value2";
//     xhr.send(postData); // 요청 보내기
// });

function changeSort() {
    // 정렬 기준을 바꿨을 때, 선택된 값을 hidden input에 설정하고 검색 실행
    var sortValue = $("#sortValue").val();
    $("#firstSort").val(sortValue);
    getAddrLoc(); // 정렬 변경 시 데이터 가져오기
}
var totalCnt = null;
var totalPages = null;
function getAddrLoc(index){
	var keyword = $("#searchItem_" + index).val();
	// 적용예 (api 호출 전에 검색어 체크) 	
	var nowPage = $("#currentPage").val();
// 	alert("getAddrLoc 실행 nowPage : " + nowPage);
	$("#navBar").show();
// 	if(nowPage == 1) {
// 		$("#beButton").hide();
// 	} else
// 		$("#beButton").show();
// 	if (!checkSearchedWord(document.form.keyword)) {
// 		return ;
// 	}
	
	 if (!$("#keyword").val()) {
		 $("#keyword").val(keyword);
	}
	$.ajax({
		 url :"/map/searchMap"
		,type:"post"
		,data:$("#form").serialize()
		,dataType:"json"
		,success:function(jsonStr){
			$("#list").html("");
			$("#recentSearches").hide();
			var errCode = jsonStr.results.common.errorCode;
			var errDesc = jsonStr.results.common.errorMessage;
			totalCnt = jsonStr.results.common.totalCount;
			var countPerPage = jsonStr.results.common.countPerPage;
			totalPages = Math.ceil(totalCnt / countPerPage);
			firstSort = jsonStr.results.common.firstSort;
			// 페이징 생성 function 실행 
			generatePagination(totalPages)
// 			alert("getAddrLoc 실행 nowPage : " +totalPages);
// 			alert("getAddrLoc 실행 totalCnt : " + totalCnt);
			// 마지막 페이징 처리이면 다음 버튼 없애기
			if(nowPage == totalPages) {
				$("#nextButton").hide();
			}
			$("#nextButton").show();
			// 검색 api 실행 후 검색 창 누르면 최근검색어 나옴
		    $("#keyword").click(function(event) {
		        $("#recentSearches").show();
		        $("#keyword").val("");
		    });
		    $(document).click(function(event) {
		        if (!$(event.target).closest('#keyword').length) {
		            $("#recentSearches").hide();
		        }
		    });
		    $("#totalCnt").html("");
		    $("#totalCnt").append("총 건: " + totalCnt + "</br> 현재 페이지 : " + nowPage );	

			var firstSortValue = $("#firstSort").val();
// 			alert(firstSortValue);
		    // 가져온 값으로 select 요소의 옵션 선택하기
		    $("#sortValue").val(firstSortValue);
		    
			if(errCode != "0"){
// 				alert(errCode+"="+errDesc);
				$("#recentSearches").show();
			}else{
				if(jsonStr != null){
					makeListJson(jsonStr);
				}
			}
		}
	    ,error: function(xhr,status, error){
	    	alert("에러발생");
	    	$("#recentSearches").show();
	    }
	});	
}


function makeListJson(jsonStr){
	var htmlStr = "";
	var htmlThead = "";
// 	alert(jsonStr);
	 var firstSortValue = $("#firstSort").val(); // firstSort hidden input 요소의 값 가져오기

	    htmlThead += "<tr>";
	    htmlThead += "<td>";
	    htmlThead += "<select class='form-select' name='sortValue' style='margin-right: 10px;' id='sortValue' onchange='changeSort()'>";
	    htmlThead += "<option value='road' id='road'" + (firstSortValue === 'road' ? " selected" : "") + ">도로명</option>";
	    htmlThead += "<option value='location' id='location'" + (firstSortValue === 'location' ? " selected" : "") + ">지번</option>";
	    htmlThead += "</select>";
	    htmlThead += "</td>";
	    htmlThead += "<td>도로명/지번</td>";
	    htmlThead += "<td>우편번호 </td>";
	    htmlThead += "<td>지도보기</td>";
	    htmlThead += "</tr>";
	

	$(jsonStr.results.juso).each(function(){
		var roadAddr = this.roadAddr;
		var zipNo = this.zipNo;
		htmlStr += "<tr>";
		htmlStr += "<td></td>";
		htmlStr += "<td>"+ roadAddr +"/"+ this.jibunAddr+"</td>";
		htmlStr += "<td>"+this.zipNo+"</td>";
		htmlStr += "<td><input type='button' id='btn' class='btn btn-primary' value='지도보기' onclick='mapDetail(\""+ roadAddr +"\")'></td>";
		htmlStr += "</tr>";
	});
	
	$("#list").html(htmlStr);
	$("#thead1").html(htmlThead);

}

function generatePagination(totalPages) {
// 페이징 만들기 위해 필요한 값들 설정
    var paginationHtml = "";
    var currentPage = parseInt($("#currentPage").val());
    
    // 이전 다음 기준 10개씩 ( 1 -10, 11 - 20, 21 - 30)
    var startPage = Math.floor((currentPage - 1) / 10) * 10 + 1;
    var endPage = Math.min(startPage + 9, totalPages);
    
    // 10개 기준으로 페이징 changePage 함수 호출해서 currentPage 업데이트 후 getAddrLoc 실행
    for (var i = startPage; i <= endPage; i++) {
        paginationHtml += "<a class='page-link mx-1 text-body' href='#' onclick='changePage(" + i + ")'>" + i + "</a>";
    }
    if (startPage > 1) {
        paginationHtml = "<a class='page-link mx-1 text-body' href='#' onclick='changePage(" + (startPage - 1) + ")'>이전</a>" + paginationHtml;
    }
    if (endPage < totalPages) {
        paginationHtml += "<a class='page-link mx-1 text-body' href='#' onclick='changePage(" + (endPage + 1) + ")'>다음</a>";
    }
    $("#page").html(paginationHtml);
}

function changePage(pageNumber) {
    $("#currentPage").val(pageNumber); // 현재 페이지 번호 업데이트
    getAddrLoc(); // 해당 페이지의 데이터 가져오기
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
        alert(item) 	
	    if (item.roadAddress) {
	        htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
	    }
	    if (item.jibunAddress) {
	        htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
	    }
	    if (item.englishAddress) {
	        htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
	    }
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
<body>
	<div class="col-lg-11 wow fadeInUp" data-wow-delay="0.5s">
     	<div class="mb-9" style="text-align: center;">
           <!-- heading -->
           <h2 style="margin-bottom: 15px;">주소 찾기</h2>
           <p style="margin-bottom: 15px;">주소를 검색해보세요!${memNum }</p>
           <div id="totalCnt" style="margin-bottom: 35px;"></div>
        </div>
       
		<form name="form" id="form"method="post">
			<div class="input-group col-md-5 mb-3" style="width:70%; margin: 0 auto;"> 
				<input type="hidden" id="currentPage" name="currentPage" value="1"/> <!-- 요청 변수 설정 (현재 페이지. currentPage : n > 0) -->
				<input type="hidden" name="countPerPage" value="10"/><!-- 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100) -->
				<input type="hidden" name="resultType" value="json"/> <!-- 요청 변수 설정 (검색결과형식 설정, json) --> 
				<input type="hidden" name="confmKey" id="confmKey" style="width:250px;display:none" value="devU01TX0FVVEgyMDI0MDMwOTIxMjM0NDExNDU4MDk="/><!-- 요청 변수 설정 (승인키) -->
				<input type="hidden" name="firstSort" id="firstSort" value="">
				<input type="text" class="form-control rounded" name="keyword" id="keyword" onkeypress="enterSearch(event)"/>
				<div style="margin-left: 10px; width: 65px; margin-top: 6px;">
					<button type="button" onClick="getAddrLoc();" class="btn bi bi-search rounded" id="startLoc"></button>
				</div>
			</div>
		</form>
		 <div class="input-group col-md-5 mb-3" style="width:70%; margin: 0 auto;"> 
			<table class="table" style="text-align: center;" id="recentSearches">
				<thead>
					<tr>
						<td>최근 검색어</td>
						<td>날짜</td>
						<td>삭제</td>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${searchList }" var="searchList" varStatus="status" step="1" end="4">
						<tr>
							<td><a href="#" onclick="getAddrLoc('${status.index}'); return false;">${searchList.keyword }</a>
								<input type="hidden" value="${searchList.keyword }" name="searchItem" id="searchItem_${status.index}"></td>
							<td><fmt:formatDate pattern="YYYY년MM월dd일" value="${searchList.searchDate }"></fmt:formatDate></td>
							<td><a href="/map/deleteSearch?keyword=${searchList.keyword }"><i class="bi bi-trash"></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<table class="table" style="text-align: center;">
			<thead id="thead1">
			</thead>
			<tbody id="list">
			</tbody>
		</table>
		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
				<li class="page-item justify-content-center">
					<div id="page" class="pagination"></div>
				</li>
			</ul>
		
			
		</nav>
<!-- 		이전, 다음 버튼으로만 -->
<!-- 		<div id="navBar"> -->
<!-- 		    <nav aria-label="Page navigation example"> -->
<!-- 			  <ul class="pagination justify-content-center"> -->
<!-- 				<li class="page-item justify-content-center">					 -->
<!-- 					<a class="page-link mx-1 text-body" href="#" id="beButton" onclick="beButton()">이전</a> -->
<!-- 				</li> -->
<!-- 				 <li class="page-item justify-content-center">		  -->
<!-- 					<a class="page-link mx-1 text-body" href="#" id="nextButton"  onclick="nextButton()">다음</a> -->
<!-- 				</li> -->
<!-- 			  </ul> -->
<!-- 		   </nav> -->
<!-- 	   </div> -->
	</div>
</body>

