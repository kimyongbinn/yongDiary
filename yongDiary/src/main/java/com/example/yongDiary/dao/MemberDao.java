package com.example.yongDiary.dao;

import java.util.Optional;

import com.example.yongDiary.model.Member;

public interface MemberDao {

//	Optional<Member> userLogin(Member member);

	Member findByMemId(String memId);


//	Optional<Member> selectUserById(String memId);

//	Member findByMemId(Member member1);

}
