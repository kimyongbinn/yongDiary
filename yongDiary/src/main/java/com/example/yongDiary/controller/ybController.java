package com.example.yongDiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	
	@GetMapping("/")
	public String main() {
		System.out.println("YbController main start..");
		return "main";
	}
	
}
