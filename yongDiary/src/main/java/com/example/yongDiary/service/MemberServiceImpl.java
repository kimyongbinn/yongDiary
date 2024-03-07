package com.example.yongDiary.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.yongDiary.dao.MemberDao;
import com.example.yongDiary.dao.MemberDaoImpl;
import com.example.yongDiary.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private static MemberDao md;

	@Override
	public Optional<Member> selectUserById() {
		//SecurityContextHolder에서 로그인된 아이디 가져오기
		String memId = getLoggedInId();
		Member member = md.findByMemId(memId);
		return Optional.ofNullable(member);
	}

	@Override
	public String getLoggedInId() {
		System.out.println("MemberServiceImpl getLoggedInId Start...");
		//Authentication (인증) : 증명, 유저가 누구인지 확인하는 과정
		//로그인 성공시 인증된 사용자 정보 Authentication 호출가능
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null && !authentication.getName().equals("anonymousUser") ){
			return authentication.getName();
		}
		//Security에선 로그인 안하면 리디렉션하는데 null값이 반환될 경우 무한 리디렉션 발생할 수 있음
		//따라서 임시로 사용자가 로그인하지 않은 경우 null 대신 빈 문자열("") 반환
		//컨트롤러에서 getLoggedInId호출했는데 로그인 안한 경우 발생할 수 있는 문제
		return "";
	}

}
