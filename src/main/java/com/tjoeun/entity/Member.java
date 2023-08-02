package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.tjoeun.constant.Role;
import com.tjoeun.dto.MemberFormDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_id")
	private Long id;
	
	private String name;
	
	@Column(unique = true)
	private String email;
	private String password;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member createMember(MemberFormDTO memberFormDTO, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setName(memberFormDTO.getName());
		member.setEmail(memberFormDTO.getEmail());
		member.setAddress(memberFormDTO.getAddress());
		
		//	비밀번호 암호화 처리
		String password = passwordEncoder.encode(memberFormDTO.getPassword());
		
		//	암호화 된 비밀번호를 Member Entity의 Password에 저장
		member.setPassword(password);
		member.setRole(Role.USER);
		
		
		return member;
	}

}
