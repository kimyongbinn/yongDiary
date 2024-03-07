package com.example.yongDiary.configuration.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.yongDiary.model.Member;
import com.example.yongDiary.service.MemberService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final MemberService ms;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		System.out.println("로그인 성공 핸들러");
		if(authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_STUDENT"))){
//			String mmId = authentication.getName();
			
			Optional<Member> memberOptional = ms.selectUserById();
			Member member = memberOptional.get();
			
//			System.out.println("유저 아이디 -> " + authentication.getName() );
		} else {
			response.sendRedirect("/");
			
		}
		
	}
	}
