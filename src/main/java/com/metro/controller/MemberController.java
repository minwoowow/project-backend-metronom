package com.metro.controller;

import org.springframework.web.bind.annotation.RestController;

import com.metro.dto.MemberRequestDTO;
import com.metro.persistence.member.MemberRepository;
import com.metro.service.auth.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
public class MemberController {
	
	private final MemberRepository memberRepo;
	private final AuthService authService;
	
//	@GetMapping("/login")
//	public ResponseEntity<?> login(@AuthenticationPrincipal User user) {
//		
//		return ResponseEntity.ok(user.getUsername());
//	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody MemberRequestDTO requestDTO) {
		
		return ResponseEntity.ok(authService.signup(requestDTO));
	}
}