package com.metro.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.metro.domain.member.Member;
import com.metro.domain.member.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
	
	private String email;
	private String password;
	private String name;
	
	public Member toMember(PasswordEncoder encoder) {
		return Member.builder()
				.email(email)
				.password(encoder.encode(password))
				.name(name)
				.role(Role.ROLE_MEMBER)
				.build();
	}
	
	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
