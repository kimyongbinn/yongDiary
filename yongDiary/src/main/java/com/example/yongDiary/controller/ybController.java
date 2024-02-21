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
	public ModelAndView main(ModelAndView mv, Member member) throws Exception {
		System.out.println("YbController main start..");
//		System.out.println("login after member id -> " + member.getMemId());
		if(member != null) {
			System.out.println("login after member id -> " + member.getMemId());
		}
//		System.out.println("login after member id -> " + member.getMemId());
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
	// 로그인
	@RequestMapping("/user/login.do")
	public String login(ModelAndView mv, Member member, Model model) throws Exception {
		System.out.println("YbController login.do Start...");
		
//		Optional<Member> userLogin = ms.userLogin(member);
		
		model.addAttribute(member);
		return "redirect:/";
	}
	
	
}
