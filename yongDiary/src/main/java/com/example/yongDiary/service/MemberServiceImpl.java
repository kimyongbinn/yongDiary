package com.example.yongDiary.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.yongDiary.dao.memberDao;
import com.example.yongDiary.dao.memberDaoImpl;
import com.example.yongDiary.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private static memberDao md;
	
	// 로그인 관련
	@Override
	public Optional<Member> userLogin(Member member) {
		System.out.println("MemberServiceImpl userLogin start@@@@@@@@@");
		
		Optional<Member> userLogin = md.userLogin(member);
		return userLogin;
	}

}
