package com.example.yongDiary.service;

import java.util.Optional;

import com.example.yongDiary.model.Member;

// 로그인 정보 확인
public interface MemberService {


	Optional<Member> userLogin(Member member);
	
}
