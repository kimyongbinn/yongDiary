package com.example.yongDiary.model;

import java.util.Date;

//import com.example.yongDiary.configuration.filter.Role;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Member {

	private int		memNum;		// 회원번호
	private String	memId;		// 아이디
	private String	memName; 	// 이름
	private String	memPw;		// 비밀번호
	private String	memPh;		// 전화번호
	private String	memAddr;	// 주소
	private String	memEmail;	// 이메일	
	private Date	memBirth;	// 생년월일
	private Date	memRegDate;	// 가입일
	private int		memAdmin;	// 관리자 여부
	private int		memWd;		// 탈퇴여부
	private String	memImage;	// 이미지
	private String	memNick;	// 닉네임
	
//	private Role role;
	
}
