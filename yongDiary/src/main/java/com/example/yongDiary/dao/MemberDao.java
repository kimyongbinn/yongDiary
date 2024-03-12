package com.example.yongDiary.dao;

import java.util.List;
import java.util.Optional;

import com.example.yongDiary.model.SearchList;
import com.example.yongDiary.model.Member;

public interface MemberDao {

//	Optional<Member> userLogin(Member member);

	Member findByMemId(String memId);

	int searchInsert(int memNum, String keyword);

	List<SearchList> searchList(SearchList map);

	int deleteSearch(String keyword);

//	Optional<Member> selectUserById(String memId);

//	Member findByMemId(Member member1);

}
