package com.example.yongDiary.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	private int		memNum;		// 회원번호
	private String	memId;		// 아이디
	private String	memName; 	// 이름
	private String	memPw;		// 비밀번호
	private String	memPh;		// 전화번호
	private String	memAddr;	// 주소
	private String	memEmail;	// 이메일	
	private String	memBirth;	// 생년월일
	private String	memRegDate;	// 가입일
	private int		memAdmin;	// 관리자 여부
	private String	memWd;		// 탈퇴여부
	private String	memImage;	// 이미지
	private String	memNick;	// 닉네임
	
	
	
	@Builder
	Member(int memNum,String memId,String memName, String memPw,String memPh,String memAddr, 
			String	memEmail, String memBirth, String memRegDate, int memAdmin, String memWd, String memImage, String memNick) {
		this.memNum = memNum;
		this.memName = memName;
		this.memPw = memPw;
		this.memPh = memPh;
		this.memAddr = memAddr;
		this.memEmail = memEmail;
		this.memBirth = memBirth;
		this.memRegDate = memRegDate;
		this.memWd = memWd;
		this.memImage = memImage;
		this.memNick = memNick;
	}
}
