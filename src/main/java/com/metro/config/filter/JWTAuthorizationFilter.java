package com.metro.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.metro.domain.member.Member;
import com.metro.persistence.member.MemberRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {	// OncePerRequestFilter를 Extends하면 하나의 요청에 대해서 단 한번만 필터를 거치게 된다
																	// 예를 들어 forwarding 되어 다른 페이지로 이동하게 되더라도 다시 이 필터를 거치지 않게 한다	
	private final MemberRepository memberRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String srcToken = request.getHeader("Authorization");	// 요청 Header에서 Authorization을 가져온다
		
		if(srcToken == null || !srcToken.startsWith("Bearer ")) {		// 내용이 없거나 "Bearer "로 시작하지 않는다면
			filterChain.doFilter(request, response);					// 필터를 그냥 통과
			return;
		}
		
		String JwtToken = srcToken.replace("Bearer ", "");	// 토큰에서 "Bearer "를 제거
		
		// 토큰에서 username 추출
		String username = JWT.require(Algorithm.HMAC256("busan.metro")).build().verify(JwtToken).getClaim("username").asString();
		
		Optional<Member> opt = memberRepo.findById(username);	// 토큰에서 얻은 username으로 DB에서 사용자 검색
		
		if(!opt.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Member findMember = opt.get();
		// DB에서 읽은 사용자 정보를 이용해서 UserDetails 타입의 객체를 생성
		User user = new User(findMember.getEmail(), findMember.getPassword(), AuthorityUtils.createAuthorityList(findMember.getRole().toString()));
		// Authentication 객체를 생성 : 사용자명과 권한 관리를 위한 정보를 입력(암호 필요 X)
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		// Security Session에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		filterChain.doFilter(request, response);
	}

}
