package com.example.yongDiary.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.example.yongDiary.model.MemDetails;
import com.example.yongDiary.model.Member;

import ch.qos.logback.core.pattern.ConverterUtil;
import lombok.extern.slf4j.Slf4j;

// 사용자의 인증에 대해 성공했을 경우 수행되는 Handler로 성공에 대한 사용자에게 반환값 구성
@Configuration
@Slf4j
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException {
		
		log.debug("3. CustomLoginSuccessHandler");
		
		// [STEP 1] 사용자와 관련된 정보를 모두 조회
		Member member = ((MemDetails) authentication.getPrincipal()).getMember();
		
		// [STEP 2] 조회한 데이터를 JSONObject 형태로 파싱
		JSONObject memVoObj = (JSONObject) ConvertUtils.convert(member, JSONObject.class);
		
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		
		JSONObject jsonObject;
		
		// [STEP3 - 1] 사용자의 상태가 탈퇴 상태 인 경우 응답 값으로 전달할 데이터
		if(member.getMemWd().equals("Y")) {
			responseMap.put("memInfo", memVoObj);
			responseMap.put("resultCode", 9001);
			responseMap.put("token", null);
			responseMap.put("failMsg", "휴면 계정입니다.");
			jsonObject = new JSONObject(responseMap);
		}
		// [STEP3-2] 사용자의 상태가 탈퇴 상태 아닐 때 응답 값
		else {
			// 1. 일반 계정일 경우
			responseMap.put("memInfo", memVoObj);
			responseMap.put("resultCode", 200);
			responseMap.put("failMsg", null);
			jsonObject = new JSONObject(responseMap);
			
			// TODO: 추후 JWT 발급에 사용 할 예정
            // String token = TokenUtils.generateJwtToken(userVo);
            // jsonObject.put("token", token);
            // response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
		}
		
		// [STEP4] 응답 값 전달
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter printWriter = response.getWriter();
		printWriter.print(jsonObject); // 최종 저장된 사용자 정보, 사이트 정보 front 전달
		printWriter.flush();
		printWriter.close();
		
		
	}
	
}
