package com.example.yongDiary.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public ModelAndView main(ModelAndView mv) {
		System.out.println("YbController main start..");
		
		mv.setViewName("main/main");
		return mv;
	}
	
	// 로그인 페이지 이동
	@RequestMapping("/user/loginPage") 
	public ModelAndView loginPage(ModelAndView mv, Member member)throws Exception {
		System.out.println("YbController loginPage start..");
		
		mv.setViewName("page/loginPage");
		return mv;
	}
	
	// 회원가입
	@RequestMapping("/join")
	public ModelAndView joinForm(ModelAndView mv) {
		System.out.println("YbController loginPage start..");
		
		mv.setViewName("page/joinForm");
		return mv;
	}
	
	
	
	// 지도 페이지 이동
	@RequestMapping("/mapView") 
	public ModelAndView mapView(ModelAndView mv, Member member)throws Exception {
		System.out.println("YbController loginPage start..");
		
		mv.setViewName("page/mapView");
		return mv;
	}
	
	
	
}
