package com.example.yongDiary.handler;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.yongDiary.model.MemDetails;
import com.example.yongDiary.service.MemDetailsServiceImpl;
import com.example.yongDiary.service.MemberService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 전달받은 사용자의 아이디와 비밀번호 기반으로 비즈니스 로직을 처리
 * 사용자의 '인증'에 대해 검증 수행하는 클래스
 * CustomAuthenticationFilter로부터 생성한 토큰을 통해 MemberService로 데이터베이스에서 정보 조회
 */

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Resource
	private UserDetailsService userDetailsService;
	
	@NonNull
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("2.CustomAuthenticationProvider start@@@@@@");
		
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		
		// AuthenticationFilter 에서 생성된 토큰으로부터 아이디와 비밀번호 조회
		String memId = token.getName();
		String memPw = (String) token.getCredentials();
		
		// Spring Security - MemberService를 통해 DB에서 아이디로 사용자 조회
		MemDetails memDetails = (MemDetails) userDetailsService.loadUserByUsername(memId);
		
		if(!(memDetails.getMemPw().equalsIgnoreCase(memPw) && memDetails.getMemPw().equalsIgnoreCase(memPw))) {
			throw new BadCredentialsException(memDetails.getMemNum() + "Invaild password");		
		}
		return new UsernamePasswordAuthenticationToken(memDetails, memPw, memDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
