package com.example.yongDiary.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.yongDiary.model.Member;
import com.example.yongDiary.service.DiaryService;
import com.example.yongDiary.service.MemberService;
import com.example.yongDiary.service.MemoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ybController {
	private static MemberService ms;
	private static MemoService	 mes;
	private static DiaryService  ds;
	
	
	@RequestMapping("/")
	public ModelAndView main(ModelAndView mv) throws Exception {
		System.out.println("YbController main start..");
		
		mv.setViewName("main/main");
		return mv;
	}
	
	@RequestMapping("/user/login") 
	public ModelAndView login(ModelAndView mv, Member member)throws Exception {
		System.out.println("YbController login start..");
		
		Optional<Member> userLogin = ms.userLogin(member);
		
		mv.setViewName("page/login");
		return mv;
	}
	
	
}
