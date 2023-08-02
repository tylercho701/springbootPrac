package com.tjoeun.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tjoeun.dto.MemberFormDTO;
import com.tjoeun.entity.Member;

@SpringBootTest
@Transactional
//	@Rollback(value=false)
class MemberServiceTest {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		MemberFormDTO memberFormDTO = new MemberFormDTO();
		memberFormDTO.setName("더조은");
		memberFormDTO.setEmail("student1@naver.com");
		memberFormDTO.setAddress("주엽");
		memberFormDTO.setPassword("1234");
		
		Member member = Member.createMember(memberFormDTO, passwordEncoder);
		
		return member;
	}
	
	@Test
	@DisplayName("회원가입 테스트")
	public void regMemberTest() {
		Member member = createMember();
		
		Member savedMember = memberService.saveMember(member);
		
		assertEquals(member.getEmail(), savedMember.getEmail());
		assertEquals(member.getName(), savedMember.getName());
		assertEquals(member.getAddress(), savedMember.getAddress());
		assertEquals(member.getPassword(), savedMember.getPassword());
		assertEquals(member.getRole(), savedMember.getRole());
		
	}

}