<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<body>
<div class="contact-text py-5 wow fadeIn" data-wow-delay="0.5s" style="margin: auto;width: 50%; ">
    <div class="p-lg-5 ps-lg-0">
            <h3 class="display-6 mb-4">로그인</h3>
        <form action="/login" method="post">
            <div class="row g-3">
                <div class="col-12">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="memId" name="memId" placeholder="아이디를 입력하세요.">
                        <label for="subject">아이디</label>
                    </div>
                </div>
                <div class="col-12">
                    <div class="form-floating">
                        <input type="password" class="form-control" id="memPw" name="memPw" placeholder="비밀번호를 입력하세요.">
                        <label for="subject">비밀번호</label>
                    </div>
                </div>
                <div class="col-12">
                    <button class="btn btn-primary w-100 py-3" type="submit">로그인</button>
                </div>
                <div style="text-align: center;">
                	<span style="margin-right: 30px;"><a href="/join">회원가입</a></span>
                	<span><a href="">계정찾기</a></span>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>