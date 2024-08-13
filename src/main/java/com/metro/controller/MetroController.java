package com.metro.controller;

import org.springframework.web.bind.annotation.RestController;

import com.metro.service.MetroService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@RestController
public class MetroController {
	
	private final MetroService metroService;
	
	@GetMapping("/getLocation")
	public ResponseEntity<?> getLocation(Integer id) {
		return ResponseEntity.ok(metroService.getLocation(id));
	}
	
	@GetMapping("/getArrivalTime")
	public ResponseEntity<?> getArrivalTime(Integer id) {
		return ResponseEntity.ok(metroService.getArrivalTime(id));
	}
	
	@GetMapping("/getArrivalInfo")
	public ResponseEntity<?> getArrivalInfo(Integer sid, Integer eid) {
		return ResponseEntity.ok(metroService.getArrivalInfo(sid, eid));
	}	
	
}