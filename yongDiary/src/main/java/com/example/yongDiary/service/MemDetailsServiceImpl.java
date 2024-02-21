package com.example.yongDiary.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.yongDiary.model.MemDetails;
import com.example.yongDiary.model.Member;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
@Service
public class MemDetailsServiceImpl implements UserDetailsService {

	
	private final MemberService memberService;
	
	public MemDetailsServiceImpl(MemberService ms) {
		this.memberService = ms;
	}
	
	@Override
	public UserDetails loadUserByUsername(String memId) {
		Member member = Member
				.builder()
				.memId(memId)
				.build();

		// 사용자 정보가 존재 경우
		if(memId == null || memId.equals("")) {
			return memberService.userLogin(member)
					.map(u -> new MemDetails(u, Collections.singleton(new SimpleGrantedAuthority(u.getMemId()))))
					.orElseThrow(() -> new AuthenticationServiceException(memId));
		}	
		// 비밀번호가 맞지 않을경우 	
		else {
			return memberService.userLogin(member)
					.map(u -> new MemDetails(u, Collections.singleton(new SimpleGrantedAuthority(memId))))
					.orElseThrow(() -> new BadCredentialsException(memId));
		}
		
	}

}
