package com.example.yongDiary.common;

import com.example.yongDiary.model.Member;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.JWindow;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// JWT 관련 토큰
@Slf4j
public class TokenUtil {
	 //    @Value(value = "${custom.jwt-secret-key}")
    private static final String jwtSecretKey = "exampleSecretKey";
    
    public static String generateJwtToken(Member member) {
        // 사용자 시퀀스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())                              // Header 구성
                .setClaims(createClaims(member))                       // Payload - Claims 구성
                .setSubject(String.valueOf(member.getMemNum()))        // Payload - Subject 구성
                .signWith(SignatureAlgorithm.HS256, createSignature())  // Signature 구성
                .setExpiration(createExpiredDate());                    // Expired Date 구성
        return builder.compact();
    }
    
    // 토큰을 기반으로 사용자 정보를 반환해주는 메소드
    public static String parseTokenToUserInfo(String token) {
    	return Jwts.parser()
    			.setSigningKey(jwtSecretKey)
    			.parseClaimsJws(token)
    			.getBody()
    			.getSubject();
    }
    
    // 유효한 토큰인지 확인해주는 메소드
    public static boolean isValidToken(String token) {
    	try {
    		Claims claims = getClaimsFormToken(token);
    		
    		log.info("expireTime : " + claims.getExpiration());
			log.info("memId : " + claims.get("memId"));
			log.info("memNum : " + claims.get("memNum"));
			
			return true;
		} catch (ExpiredJwtException exception) {
			log.error("Token Expired");
			return false;
		} catch (JwtException exception) {
			log.error("Token Tampered");
			return false;
		} catch (NullPointerException exception) {
			log.error("Token is null");
			return false;
		}
    }
    
    // Header 내에 토큰 추출
    public static String getTokenFromHeader(String header) {
    	return header.split(" ")[1];
    }
    
    
    // 토큰의 만료기간을 지정해주는 함수
    private static Date createExpiredDate() {
    	// 토큰 만료시간 30일 설정	
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.HOUR, 8); // 8시간
    	// c.add(Calendar.DATE, 1); //f
    	
		return c.getTime();
    }
    
    // JWT의 헤더 값을 생성해주는 메소드
    private static Map<String, Object> createHeader() {
    	Map<String, Object> header = new HashMap<>();
    
    	header.put("typ", "JWT");
    	header.put("alg", "JWT");
    	header.put("regDate", System.currentTimeMillis());
    	
    	return header;
    }
    
    // 사용자 정보 기반으로 클래임을 생성해주는 메소드 
    private static Map<String, Object> createClaims(Member member) {
    	// 공개 클레임에 사용자 이름과 이메일을 설정하여 정보 조회 가능
    	Map<String, Object> claims = new HashMap<>();
    	
    	log.info("memId : " + member.getMemId());
    	log.info("memNum : " + member.getMemNum());


    	claims.put("memId", member.getMemId());
    	claims.put("memNum", member.getMemNum());
    	return claims;
    }
    
    // JWT 서명(signature) 발급해주는 메소드
    private static Key createSignature() {
    	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
    	return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
    
    
    
}
