package com.metro.dto;

import com.metro.domain.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
	
	private String email;
	private String name;
	
	public static MemberResponseDTO of(Member member) {
		return MemberResponseDTO.builder()
				.email(member.getEmail())
				.name(member.getName())
				.build();
	}
}
