package com.metro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.metro.config.filter.JWTAuthenticationFilter;
import com.metro.config.filter.JWTAuthorizationFilter;
import com.metro.persistence.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	private final MemberRepository memberRepo;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.cors(cors -> cors.configurationSource(corsSource()));
		http.csrf(csrf -> csrf.disable());	// CSRF 보호 비활성화
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());
		http.formLogin(frmLogin -> frmLogin.disable());	// Form을 이용한 로그인을 사용하지 않겠다는 설정
		http.httpBasic(basic -> basic.disable());	// HTTP Basic 인증 방식을 사용하지 않겠다는 설정
		
		// 세션을 유지하지 않겠다고 설정 -> URL 호출 뒤 응답할 때 까지는 유지되지만 응답 후 삭제된다는 의미
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// Spring Security가 등록한 필터체인의 뒤에 작선한 필터를 추가한다
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), memberRepo));
		// Spring Security가 등록한 필터들 중에서 AuthorizatinoFilter 앞부분에 작성한 필터를 삽입한다
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepo), AuthorizationFilter.class);
		
		return http.build();
	}
	
	private CorsConfigurationSource corsSource() {
		CorsConfiguration config = new CorsConfiguration();
		
		config.addAllowedOriginPattern(CorsConfiguration.ALL);	// 요청을 허용할 Server
		config.addAllowedMethod(CorsConfiguration.ALL);			// 요청을 허용할 Method
		config.addAllowedHeader(CorsConfiguration.ALL);			// 요청을 허용할 Header
		config.setAllowCredentials(true);						// 요청/응답에 자격증명정보 포함을 허용
		config.addExposedHeader("Authorization");				// Header에 Authorization 추가 가능
		
		// true인 경우 addAllowedOrigin("*")는 사용 불가 -> Pattern으로 변경
		config.addExposedHeader(CorsConfiguration.ALL);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);	// 위 설정을 적용할 Rest 서버의 URL 패턴 설정
		return source;
	}

}
