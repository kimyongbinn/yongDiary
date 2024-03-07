package com.example.yongDiary.service;

import java.util.Optional;

import com.example.yongDiary.model.Member;

// 로그인 정보 확인
public interface MemberService {

	//로그인된 아이디 가져오기
	public String getLoggedInId();
	
//	Optional<Member> userLogin(Member member);

	public Optional<Member> selectUserById();


	
}
