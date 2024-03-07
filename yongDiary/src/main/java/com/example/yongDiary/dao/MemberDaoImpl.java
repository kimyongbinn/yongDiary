package com.example.yongDiary.dao;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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
	        // Log any exceptions that occur during query execution
	        System.out.println("Error executing query: " + e.getMessage());
	        e.printStackTrace(); // Print the stack trace for detailed error information
	    }
	    
	    System.out.println("memberDaoImpl findByMemId End@@@@@@@@");
	    return member;
	}

//	@Override
//	public Member findByMemId(String memId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
