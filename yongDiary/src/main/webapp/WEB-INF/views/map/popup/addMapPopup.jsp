<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<script type="text/javascript">
$(function(){
	var result = '${insertMap}';
	
	if(result == 1){
		alert("등록되었습니다.");
		window.close();
		opener.parent.location.reload();
	} 
});
</script>
<div class="contact-text py-5 wow fadeIn" data-wow-delay="0.5s" style="margin: auto;width: 50%; ">
    <div class="p-lg-5 ps-lg-0">
            <h3 class="display-6 mb-4" style="text-align: center;">장소 추가</h3>
        <form action="/map/addMyMap" method="post" id="sendForm">
            <div class="row g-3">
                <div class="col-12">
                	<div>장소</div>
                    <div class="">
                        <input type="text" style="width: 100%;" class="form-control" id="placeName" name="placeName" value="${roadAddr }" readonly="readonly">
                    </div>
                </div>
                <div class="col-12">
               		<div>메모</div>
                    <div class="">
          			   <textarea rows="3" name="pmContent" style="width: 100%;" id="pmContent" class="form-control" placeholder="장소 관련 메모하고 싶은 코멘트 적어주세요."></textarea>
                    </div>
                </div>
                <div class="col-12" style="text-align: center;">
                    <button class="btn btn-primary w-100 py-3" type="submit">추가하기</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
