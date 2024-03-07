<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="contact-text py-5 wow fadeIn" data-wow-delay="0.5s" style="margin: auto;width: 50%; ">
    <div class="p-lg-5 ps-lg-0">
            <h4 class="display-6 mb-4">회원가입</h4>
        <form action="/login" method="post">
            <div class="row g-1">
           		<div class="col-12 mb-3">
                    <div class="form-floating">
                        <input type="password" class="form-control" id="memName" name="memName" placeholder="비밀번호를 입력하세요.">
                        <label for="subject">이름</label>
                    </div>
                </div>
                <div class="col-12 mb-3">
                    <div class="form-floating">
                        <input type="password" class="form-control" id="memNick" name="memNick" placeholder="닉네임을 입력하세요.">
                        <label for="subject">닉네임</label>
                    </div>
                </div>
                <div class="col-12 mb-3">
                    <div class="form-floating" style="display: flex;">
                        <input type="text" class="form-control" id="memId" name="memId" placeholder="아이디를 입력하세요." style="width: 85%; margin-right: 10px;">
                        <button type="button" class="btn btn-primary py-2" style="padding: 0px;">중복확인</button>
                        <label for="subject">아이디</label>
                    </div>
                </div>
                <div class="col-12">
                    <div class="form-floating">
                        <input type="password" class="form-control" id="memPw" name="memPw" placeholder="비밀번호를 입력하세요.">
                        <label for="subject">비밀번호</label>
                    </div>
                </div>
                <div class="col-12 mb-3">
                    <div class="form-floating" style="display: flex;">
                        <input type="text" class="form-control" id="memPw1" name="memPw1" placeholder="비밀번호를 확인하세요." style="width: 85%; margin-right: 10px;">
                        <button type="button" class="btn btn-primary py-3" style="padding: 0px; width: 70px;">확인</button>
                        <label for="subject">비밀번호 확인</label>
                    </div>
                </div>
             
                <div class="col-12 mb-3">
                    <div class="form-floating">
                        <input type="password" class="form-control" id="memPh" name="memPh" placeholder="전화번호를 입력하세요.">
                        <label for="subject">전화번호</label>
                    </div>
                </div>
                <div class="col-12 mb-3">
                    <div class="form-floating" style="display: flex;">
                        <input type="text" class="form-control" id="memAddr" name="memAddr" placeholder="주소를 입력하세요." style="width: 85%; margin-right: 10px;">
                        <button type="button" class="btn btn-primary py-3" style="padding: 0px; width: 70px;">검색</button>
                        <label for="subject">주소 확인</label>
                    </div>
                </div>
                <div class="col-12 mb-3">
                    <button class="btn btn-primary w-100 py-3" type="submit">회원가입</button>
                </div>
               
            </div>
        </form>
    </div>
</div>
</body>
</html>