package com.tjoeun.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
class MemberTest {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	@DisplayName("auditing test")
	@WithMockUser(username = "더조은", roles = "ADMIN")
	void auditingTest() {
		Member member = new Member();
		memberRepository.save(member);
		
		//	persistence context(영속성 컨텍스트)의 변경 내용을 DB에 반영함
		entityManager.flush();
		//	persistence context를 초기화함
		entityManager.clear();
		
		Member foundMember = memberRepository.findById(member.getId())
											 .orElseThrow(EntityNotFoundException::new);
		
		log.info("등록일 : " + foundMember.getRegTime());
		log.info("수정일 : " + foundMember.getUpdateTime());
		log.info("작성자 : " + foundMember.getCreatedBy());
		log.info("수정자 : " + foundMember.getModifiedBy());
		
	}

}
