package com.tjoeun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.dto.TmpDTO;

@RestController
public class TestController {
	
	@GetMapping("/")
	public String home() {
		return "Spring Boot";
	}

	@GetMapping("/test1")
	public TmpDTO test1() {
		TmpDTO t1 = TmpDTO.builder()
						  .name("더조은")
						  .height(178)
						  .build();
		
		return t1;
	}
	
	@GetMapping("/test2")
	public TmpDTO test2() {
		TmpDTO t2 = TmpDTO.builder()
						  .height(178)
						  .build();
		
		return t2;
	}
}