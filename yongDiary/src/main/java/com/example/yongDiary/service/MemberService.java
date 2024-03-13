package com.example.yongDiary.service;

import java.util.List;
import java.util.Optional;

import com.example.yongDiary.model.SearchList;
import com.example.yongDiary.model.AddMyMap;
import com.example.yongDiary.model.Member;

// 로그인 정보 확인
public interface MemberService {

	//로그인된 아이디 가져오기
	public String getLoggedInId();
	
//	Optional<Member> userLogin(Member member);

	public Optional<Member> selectUserById();

	public int searchInsert(int memNum, String keyword);

	public List<SearchList> searchList(SearchList map);

	public int deleteSearch(String keyword);

	public int insertMap(AddMyMap addMyMap);

	public List<AddMyMap> myMapList(AddMyMap addMyMap);


	
}
