package com.example.yongDiary.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.yongDiary.configuration.filter.CustomAuthenticationFilter;
import com.example.yongDiary.handler.CustomAuthFailureHandler;
import com.example.yongDiary.handler.CustomAuthSuccessHandler;
import com.example.yongDiary.handler.CustomAuthenticationProvider;

@Configuration
public class SecurityConfig {
	
	/* 1. 정적 자원(Resource)에 대해서 인증된 사용자가 정적 자원의 접근에 대해 
	'인가'에 대한 설정을 담당하는 메소드
	*/
	@Bean
	public WebSecurityCustomizer websecurityCustomizer() {
		// 정적 자원에 대해서 Security를 적용하지 않음으로 설정
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	/*
	 2. HTTP에 대해서 '인증'과 '인가'를 담당하는 메소드이며 필터를 통해 
	 인증 방식과 인증절차에 대해서 등록하며 설정을 담당하는 메소드
	*/
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// [STEP 1] 서버에 인증정보를 저장하지 않기에 csrf 사용 X 
		//csrf() : 웹 사이트 취약점 공격의 하나/.disable
		http.csrf().disable();
		
		// [STEP 2] form 기반의 로그인에 대해 비 활성화하며 커스텀으로 구성한 필터를 사용
		http.formLogin().disable();
		
		// [STEP 3] 토큰을 활용하는 경우 모든 요청에 대해 '인가'에 대해서 사용
		http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll());
		
		// [STEP 4] Spring Security Custom FIlter LOAD - Form '인증'에 대해서 사용
		http.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		// [STEP 5] Session 기반의 인증을 사용하지 않고 추후 JWT를 이용해 인증
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// [STEP 6] Spring Security JWT Filter Load
		//http.addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class);
		
		// [STEP 7] 최종 구성한 값을 사용함
		return http.build();
	}
	
	// 3. authenticate의 인증 메소드를 제공하는 매니저로 'Provider'의 인터페이스를 의미
	// 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
    @Bean
    public AuthenticationManager authenticationManager() {
    	return new ProviderManager(customAuthenticationProvider());
    }
	
    // 4. 인증 제공자로 사용자의 이름과 비밀번호 요구
    // - 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
    	return new CustomAuthenticationProvider(bCryptPasswordEncoder());
    }
    
    // 5. 비밀번호를 암호화하기 위한 BCrypt 인코딩을 통하여 비밀번호에 대한 암호화 수행
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    // 6. 커스텀을 수행한 '인증' 필터로 접근 URL, 데이터 전달방식(form) 등 인증과정 및 인증 후 처리에 대한 설정 구성하는 메소드
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
    	CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
    	customAuthenticationFilter.setFilterProcessesUrl("/page/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());    // '인증' 성공 시 해당 핸들러로 처리를 전가한다.
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthFailureHandler());    // '인증' 실패 시 해당 핸들러로 처리를 전가한다.
    	return customAuthenticationFilter;
    }
    
    
	//암호화 제외 security 사용 X
	
	//패스워드를 암호화하는 모듈
	@Bean	//수동 설정일 때만 Bean 사용
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public CustomAuthSuccessHandler customLoginSuccessHandler() {
		return new CustomAuthSuccessHandler();	
	}
	
	@Bean 
	public CustomAuthFailureHandler customAuthFailureHandler() {
		return new CustomAuthFailureHandler();
	}
	
	 /**
     * 9. JWT 토큰을 통하여서 사용자를 인증합니다.
     *
     * @return JwtAuthorizationFilter
     */
//    @Bean
//    public JwtAuthorizationFilter jwtAuthorizationFilter() {
//        return new JwtAuthorizationFilter();
//    }
	
	
}
