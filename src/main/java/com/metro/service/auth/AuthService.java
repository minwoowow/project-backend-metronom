package com.metro.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metro.domain.member.Member;
import com.metro.dto.MemberRequestDTO;
import com.metro.dto.MemberResponseDTO;
import com.metro.persistence.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
	
	private final MemberRepository memberRepo;
	private final PasswordEncoder encoder;
	
	public MemberResponseDTO signup(MemberRequestDTO requestDTO) {
		if(memberRepo.existsById(requestDTO.getEmail())) {
			throw new RuntimeException("존재하는 이메일 입니다");
		}
		
		Member member = requestDTO.toMember(encoder);
		
		return MemberResponseDTO.of(memberRepo.save(member));
	}

}
