package com.example.yongDiary.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.yongDiary.dao.MemberDao;
import com.example.yongDiary.model.SearchList;
import com.example.yongDiary.model.AddMyMap;
import com.example.yongDiary.model.Member;
import com.example.yongDiary.service.DiaryService;
import com.example.yongDiary.service.MemberService;
import com.example.yongDiary.service.MemoService;
import com.example.yongDiary.service.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ybController {
	private final MemberService ms;
	private final MemoService	 mes;
	private final DiaryService  ds;
	private final MemberDao md;
	
	public Member aboutMem() {
		Optional<Member> memberOptional = ms.selectUserById();
		Member member = null;
		if(memberOptional.isPresent()) {
			member = memberOptional.get();
			System.out.println("YbController about LoginMember : " + member);
		}
		return member;
	}
	
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
	//로그아웃
	@GetMapping("logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("JhController logout Start...");
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		//SecurityConfig에서 .logoutSuccessUrl로 처리하므로 return 불필요
//		return "redirect:/info/loginForm";
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
	
	// naver 지도 검색 api
	@RequestMapping(value="/map/searchMap")
    public void getAddrApi(HttpServletRequest req, ModelMap model, HttpServletResponse response, 
    		SearchList map, Model modell) throws Exception {
		
		// 로그인한 정보
		Member member = aboutMem();
		int memNum = member.getMemNum();
		
		System.out.println("aboutMem : " + aboutMem());
		// 요청변수 설정
        String currentPage = req.getParameter("currentPage");    //요청 변수 설정 (현재 페이지. currentPage : n > 0)
		String countPerPage = req.getParameter("countPerPage");  //요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100)
		String resultType = req.getParameter("resultType");      //요청 변수 설정 (검색결과형식 설정, json)
		String confmKey = req.getParameter("confmKey");          //요청 변수 설정 (승인키)
		String keyword = req.getParameter("keyword");            //요청 변수 설정 (키워드)
		System.out.println("keyword : " + keyword);
		int totalcnt = req.getContentLength();
		System.out.println("totalcnt -> " + totalcnt);
		// 최근검색어 추가
		
		Paging page = new Paging(totalcnt, currentPage);
		
		if(keyword == null || !keyword.equals("")) { 
			System.out.println("YbController searchInsert start..");
			int searchInsert = ms.searchInsert(memNum, keyword);
		}
		// OPEN API 호출 URL 정보 설정
		String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+URLEncoder.encode(keyword,"UTF-8")+"&confmKey="+confmKey+"&resultType="+resultType;
		URL url = new URL(apiUrl);
    	BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
    	StringBuffer sb = new StringBuffer();
    	String tempStr = null;
    	
    	while(true){
    		tempStr = br.readLine();
    		if(tempStr == null) break;
    		sb.append(tempStr);								// 응답결과 JSON 저장
    	}
    	br.close();
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		response.getWriter().write(sb.toString());			// 응답결과 반환
		modell.addAttribute("memNum", memNum);
		modell.addAttribute("keyword", keyword);
		modell.addAttribute("page", page);
    }
	// 최근검색어 리스트
	@RequestMapping(value = "/map/mapSearch")
	public String mapSearch(Model model, SearchList map, Member member) {	
		System.out.println("ybController mapSearch start...");
		
		
		List<SearchList> searchList = ms.searchList(map);
		System.out.println("ybController mapSearch searchList : " + searchList);
		model.addAttribute("searchList", searchList);
		return "map/mapSearch";
	}
	// 지도 검색 상세페이지
	@RequestMapping(value = "/map/mapDetail")
	public String mapResult(Model model, String pointX, String pointY, String roadAddr, String keyword) {	
		System.out.println("ybController mapResult start...");
		System.out.println("pointX -> " + pointX);
		System.out.println("pointy -> " + pointY);
		System.out.println("roadAddr -> " + roadAddr);
		System.out.println("keyword -> " + keyword);
		
		model.addAttribute("pointX", pointX);
		model.addAttribute("pointY", pointY);
		model.addAttribute("roadAddr", roadAddr);
		model.addAttribute("keyword", keyword);
		
		return "map/mapDetail";
	}
	// 지도 최근검색어 삭제
	@RequestMapping(value = "/map/deleteSearch")
	public String deleteSearch(Model model, String keyword) {	
		System.out.println("ybController deleteSearch start...");
		System.out.println("ybController deleteSearch keyword : " + keyword);	
		int deleteSearch = ms.deleteSearch(keyword);
		
		return "redirect:/map/mapSearch";
	}
	
	// 내 지도 추가하기 팝업화면
	@RequestMapping(value = "/popup/addMapPopup")
	public String addMapPopup(Model model, String keyword, String roadAddr) {	
		System.out.println("ybController addMyMap start...");
		System.out.println("ybController addMyMap roadAddr : " + roadAddr);	
		
		
		model.addAttribute("roadAddr", roadAddr);
		return "map/popup/addMapPopup";
	}
	// 내 지도 추가하기
	@RequestMapping(value = "/map/addMyMap")
	public String addMyMap(Model model, String roadAddr, AddMyMap addMyMap) {	
		System.out.println("ybController addMyMap start...");
		System.out.println("ybController addMyMap roadAddr : " + roadAddr);	
		Member member = aboutMem();
		int memNum = member.getMemNum();
		
		addMyMap.setMemNum(memNum);
		
		int insertMap = ms.insertMap(addMyMap);
		
		model.addAttribute("roadAddr", roadAddr);
		model.addAttribute("insertMap", insertMap);
		return "map/popup/addMapPopup";
	}
	
	
	// 내 지도 
	@RequestMapping(value = "/map/myMapList")
	public String myMapList(Model model, String keyword, AddMyMap addMyMap) {	
		System.out.println("ybController myMapList start...");
		
		List<AddMyMap> myMapList = ms.myMapList(addMyMap);
		
		model.addAttribute("myMapList", myMapList);
		return "map/myMapList";
	}
}
