package com.example.yongDiary.configuration.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//접근 권한 없는 경우 alert로 메세지 띄우고 이전 페이지로 되돌리기
				response.setContentType("text/html;charset=UTF-8");
		        String alertScript = "<script>alert('접근 권한이 없습니다.');history.go(-1);</script>";
		        response.getWriter().write(alertScript);
			}

}
