package com.example.yongDiary.dao;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.yongDiary.model.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class memberDaoImpl implements memberDao {
	private static SqlSession session;

	public Optional<Member> userLogin(Member member) {
		System.out.println("memberDaoImpl userLogin Start@@@@@@@@");
		Optional<Member> userLogin = null;
		try {
			userLogin = session.selectOne("userLogin", member);
		} catch (Exception e) {
			System.out.println("memberDaoImpl userLogin Exception -> " + e.getMessage());
		}
		
		return userLogin;
	}
	
}
