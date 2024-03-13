package com.example.yongDiary.dao;

import java.util.List;
import java.util.Optional;

import com.example.yongDiary.model.SearchList;
import com.example.yongDiary.model.AddMyMap;
import com.example.yongDiary.model.Member;

public interface MemberDao {
	// 로그인 시 회원 찾기
	Member findByMemId(String memId);
	// 최근 검색어
	int searchInsert(int memNum, String keyword);
	// 최근 검색어 리스트
	List<SearchList> searchList(SearchList map);
	// 최근검색어 삭제
	int deleteSearch(String keyword);
	// myMap 추가
	int insertMap(AddMyMap addMyMap);
	// myMap 리스트
	List<AddMyMap> myMapList(AddMyMap addMyMap);


}
