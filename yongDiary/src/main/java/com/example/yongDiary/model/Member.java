package com.example.yongDiary.model;

import lombok.Data;

@Data
public class Member {
	private int 	memNum;		// 회원번호
	private String	memId;		// 아이디
	private String  memPw;		// 비밀번호
	private String  memNick;	// 닉네임
	private String  memName;	// 이름
	private int		memTell;	// 전화번호
	private String  memEmail;	// 이메일
	private int		memBirth;   // 생년월일
}
