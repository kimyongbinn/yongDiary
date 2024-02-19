package com.example.yongDiary.dao;

import java.util.Optional;

import com.example.yongDiary.model.Member;

public interface memberDao {

	Optional<Member> userLogin(Member member);

}
