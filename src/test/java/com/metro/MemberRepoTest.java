package com.metro;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.metro.domain.member.Member;
import com.metro.domain.member.Role;
import com.metro.persistence.member.MemberRepository;

@SpringBootTest
public class MemberRepoTest {
	
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private PasswordEncoder encoder;
	
	@Test
	public void memberRepoTest() {
		memberRepo.save(Member.builder()
				.email("minwoo@metro.com")
				.password(encoder.encode("qwer"))
				.name("minwoo")
				.role(Role.ROLE_ADMIN)
				.build());
		
		memberRepo.save(Member.builder()
				.email("jeongwon@metro.com")
				.password(encoder.encode("qwer"))
				.name("jeongwon")
				.role(Role.ROLE_ADMIN)
				.build());
	}
	
	@Test
	public void findByIdTest() {
		String id = "minwoo@metro.com";
		Optional<Member> opt = memberRepo.findById(id);
		
		System.out.println("Member 정보 : " + opt.toString());
	}
}
