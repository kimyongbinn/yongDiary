package com.example.yongDiary.configuration.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.yongDiary.model.Member;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/*
 * 해당 메소드 내에서 AuthenticationManager를 호출하여 전달
 * 인증 성공 시 CustomAuthSuccessHandler를 호출, 실패 시 CustomAuthFailureHandler를 호출
 * 커스텀을 수행한 '인증' 필터로 접근 URL, 데이터 전달방식(form) 등 인증 과정 및 인증 후 처리에 대한 설정 구성하는 메소드
 * 최종 인증이 완료되면 사용자 아이디와 비밀번호 기반으로 토큰 발급
 * */

// 아이디와 비밀번호 기반의 데이터를 form 데이터로 전송 받아 '인증'을 담당하는 필터
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	// 지정된 URL로 form 전송 했을 경우 파라미터 정보 가져옴
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authRequest;
		try {
			authRequest = getAuthReqeust(request);
			setDetails(request, authRequest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	
	// Request로 받은 ID와 비밀번호 기반으로 토큰 발급
	private UsernamePasswordAuthenticationToken getAuthReqeust(HttpServletRequest request) throws Exception {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
			Member member = objectMapper.readValue(request.getInputStream(), Member.class);
			log.debug("1.CustomAutenticationFIlter :: userId :" + member.getMemId() + "userPw" + member.getMemPw());
			
			// ID와 패스워드 기반으로 토큰 발급
			return new UsernamePasswordAuthenticationToken(member.getMemId(), member.getMemPw());
			
		
		} catch (UsernameNotFoundException ae) {
			throw new UsernameNotFoundException(ae.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e.getCause());
		}
		

	}
	
}
