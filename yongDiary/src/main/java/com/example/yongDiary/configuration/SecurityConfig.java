package com.example.yongDiary.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.yongDiary.configuration.filter.CustomAccessDeniedHandler;
import com.example.yongDiary.configuration.filter.CustomAuthenticationSuccessHandler;
import com.example.yongDiary.service.MemberService;
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	//접근 권한 없는 경우 예외 처리 할 클래스 
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
    	//아래는 AccessDeniedHandler 상속받아서 직접 생성
        return new CustomAccessDeniedHandler();
    }
	
	// 해쉬 암호화 방식을 사용하겠다. -> password 암호화
	//@Bean 사용하면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	//로그인 성공 후 학습자인 경우 그룹에 가입 여부에 따라 그룹가입신청 페이지로 이동하도록 핸들러 만들고
	//학습 그룹 가입여부 확인하기 위해 memberService를 파라미터로 주입해줌 그래야 @RequiredArgsConstructor로 생성자 생성가능 안그러면 에러남 
	@Autowired
    private MemberService memberService;
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler(memberService);
    }
    
	//세션 관리 및 인증 실패 핸들링
	@Bean
	public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
		//SimpleUrlAuthenticationFailureHandler 객체를 생성하여 반환
		
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
	    failureHandler.setUseForward(true);
	    failureHandler.setDefaultFailureUrl("/user/loginPage?error=true");
	    
	    return failureHandler;
	}
	 
	// 인증요청으로 들어온 토큰이 올바른 유저인지 인증 수행하므로 빈 등록 필수!
	@Bean
	AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	
//	WebSecurityConfigurerAdapter는  Deprecated 되었으므로 사용하지 않고 아래와 같이 SecurityFilterChain을 Bean으로 등록하여 사용 
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		//접근 권한 없는 경우 예외처리할 핸들러 필터체인에 등록
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		
		http.authorizeHttpRequests((requests) -> requests
				//구독서비스
//				.antMatchers("/mapView").hasAnyRole("ADMIN", "USER")
				.anyRequest().permitAll()
			);		
		
		http.formLogin((form) -> form
				.loginPage("/user/loginPage")
				.permitAll()
				.usernameParameter("memId")		// login에 필요한 id값 설정 (default는 username)
                .passwordParameter("memPw")	// login에 필요한 password 값  (default password)
                .loginProcessingUrl("/login")	// login주소가 호출 되면 시큐리티가 낚아채서 대신 로그인 진행해줌
                .failureUrl("/user/loginPage?error=true")
                .successHandler(successHandler())
//				.defaultSuccessUrl("/")			// 로그인 성공시 이동할 URL (메이페이지로 이동) 이거 때문에 successHandler실행 안됨 
			);
		
		// Logout 설정.
		http.logout((logout) -> logout
				.permitAll()
				.logoutSuccessUrl("/user/loginPage")
				.invalidateHttpSession(true) //세션 무효화 -현재 세션을 끝내고 새로운 세션을 시작
			);	
		//세션을 날렸는데도 헤더에서 계속 로그아웃만 표시 되어서 바꿈 
		//근데도 문제 지속되어서 헤더에서 <%= SecurityContextHolder.getContext().getAuthentication() %>
		//에서 지금으로 바꿈
//				.logoutSuccessHandler((request, response, authentication) -> {
//			        SecurityContextHolder.clearContext(); // 세션을 무효화하고 SecurityContextHolder를 비웁니다.
//			        response.sendRedirect("/info/loginForm");
//				})
		
		// Authentication Provider 등록. -> CustomAuthenticationProvider에서 실제 로그인 처리
//		http.authenticationProvider(authProvider);
		
		 // 세션 설정 추가
        http.sessionManagement()							//세션 관리 설정을 시작
            .maximumSessions(2)								//동시에 허용되는 최대 세션 수
            .expiredUrl("/info/loginForm?expired=true")		//세션이 만료된 경우 리디렉션할 URL(로그인 페이지로 이동)
            .and()
            .sessionAuthenticationFailureHandler(authenticationFailureHandler()); //세션에서의 인증 실패 시 처리를 담당하는 핸들러를

		return http.build();
	}
}