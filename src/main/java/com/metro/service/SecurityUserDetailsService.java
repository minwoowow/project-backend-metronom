package com.metro.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.metro.domain.member.Member;
import com.metro.persistence.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class SecurityUserDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {	// AuthenticationManager의 authenticate 메서드가 호출되면 실행
		
		log.info("#################### UserDetailsService ####################");
		log.info("#################### loadUserByUsername ####################");
		log.info("username = {}", email);
		
		Member member = memberRepo.findById(email)
								.orElseThrow(() -> new UsernameNotFoundException("Not Found!"));
		
		log.info("member = {}", member);
		log.info("member's role = {}", member.getRole());

		return new User(member.getEmail(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}
}
