package com.example.yongDiary.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.yongDiary.model.SearchList;
import com.example.yongDiary.model.AddMyMap;
import com.example.yongDiary.model.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {
	private final SqlSession session;


	@Override
	public Member findByMemId(String memId) {
	    System.out.println("memberDaoImpl findByMemId Start@@@@@@@@");
	    System.out.println("memId: " + memId);

	    Member member = null;
	    try {
	        // Execute the query with the provided member1 object
	        member = session.selectOne("findByMemId", memId);
	        
	        // Check if the member is null after query execution
	        if (member == null) {
	            System.out.println("No member found with memId: " + memId);
	        } else {
	            System.out.println("Member found: " + member);
	        }
	    } catch (Exception e) {
	        System.out.println("Error executing query: " + e.getMessage());
	        e.printStackTrace(); // Print the stack trace for detailed error information
	    }
	    System.out.println("memberDaoImpl findByMemId member : " + member);
	    System.out.println("memberDaoImpl findByMemId End@@@@@@@@");
	    
	    return member;
	}

	@Override
	public int searchInsert(int memNum, String keyword) {
		 System.out.println("memberDaoImpl findByMemId Start@@@@@@@@");
		 
		 int searchInsert = 0;
		 System.out.println("memberDaoImpl searchInsert searchKeyword Start@@@@@@@@");
		 int searchKeyword = session.selectOne("keywordTotal", keyword);
		 System.out.println("memberDaoImpl searchInsert searchKeyword : " + searchKeyword);
		 HashMap<String, Object> map = new HashMap<>();
		 map.put("memNum", memNum);
		 map.put("keyword", keyword);
		 try {
			 if(searchKeyword > 0) {
				 searchInsert = session.update("searchUpdate", keyword);
				 System.out.println("memberDaoImpl searchInsert searchUpdate : " + searchInsert);
			 } else {
				 searchInsert = session.insert("searchInsert", map);
				 System.out.println("memberDaoImpl searchInsert searchInsert : " + searchInsert);
			 }
			 
		} catch (Exception e) {
			 System.out.println("memberDaoImpl findByMemId Error : " + e.getMessage());
		}
		return searchInsert;
	}

	@Override
	public List<SearchList> searchList(SearchList map) {
		System.out.println("memberDaoImpl searchList Start@@@@@@@@");
		List<SearchList> searchList = null;
		try {
			searchList = session.selectList("searchList", map);
		} catch (Exception e) {
			System.out.println("memberDaoImpl searchList Error : " + e.getMessage());
		}
		return searchList;
	}

	@Override
	public int deleteSearch(String keyword) {
		System.out.println("memberDaoImpl deleteSearch Start@@@@@@@@");
		int deleteSearch = 0;
		try {
			deleteSearch = session.delete("deleteSearch", keyword);
		} catch (Exception e) {
			System.out.println("memberDaoImpl deleteSearch Error : " + e.getMessage());
		}
		return deleteSearch;
	}

	@Override
	public int insertMap(AddMyMap addMyMap) {
		System.out.println("memberDaoImpl insertMap Start@@@@@@@@");
		int insertMap = 0;
		try {
			insertMap = session.insert("insertMap", addMyMap);
		} catch (Exception e) {
			System.out.println("memberDaoImpl insertMap Error : " + e.getMessage());
		}
		return insertMap;
	}

	@Override
	public List<AddMyMap> myMapList(AddMyMap addMyMap) {
		System.out.println("memberDaoImpl myMapList Start@@@@@@@@");
		List<AddMyMap> myMapList = null;
		try {
			myMapList = session.selectList("myMapList", addMyMap);
		} catch (Exception e) {
			System.out.println("memberDaoImpl myMapList Error : " + e.getMessage());
		}
		return myMapList;
	}


}

