package com.tjoeun.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberFormDTO {
	//회원 가입할 때 입력해야 되는 내용을
	//	MemberFormDTO 클래스에 멤버 변수로 ㅣ지정함
	//	name, email, password, address
	
	@NotBlank(message="이름을 입력하세요.")
	private String name;
	
	@NotEmpty(message="이메일 주소를 입력하세요.")
	@Email(message="이메일 주소를 확인하세요.")
	private String email;
	
	@NotEmpty(message="비밀번호를 입력하세요.")
	@Length(min=4, max=20, message="4글자 이상, 20글자 이하로 입력해 주세요")
	private String password;
	
	@NotEmpty(message="주소를 입력하세요.")
	private String address;
}
