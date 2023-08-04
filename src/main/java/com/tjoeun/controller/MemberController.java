package com.tjoeun.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dto.MemberFormDTO;
import com.tjoeun.entity.Member;
import com.tjoeun.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member")
@Log4j2
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/new")
	public String memberForm(MemberFormDTO memberFormDTO) {
		
		return "member/memberForm";
	}
	/*		memberForm1, 2, 3 메소드는 모두 동일한 기능
	@GetMapping("/new")
	public String memberForm2(Model model) {
		model.addAttribute("memberFormDTO", new MemberFormDTO());
		
		return "member/memberForm";
	}
	
	@GetMapping("/new")
	public String memberForm3(MemberFormDTO memberFormDTO, Model model) {
		model.addAttribute("memberFormDTO", memberFormDTO);
		return "member/memberForm";
	}
	*/
	
	@PostMapping("/new")
	public String memberForm(@Valid MemberFormDTO memberFormDTO, 
							 Model model, BindingResult result) {
		
		if(result.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDTO, passwordEncoder);
			memberService.saveMember(member);	
		} catch(IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		log.info("회원가입완료" + memberFormDTO);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "member/memberLoginForm";
	}
	
	@GetMapping("/login/error")
	public String loginError(Model model) {
		
		model.addAttribute("loginErrorMessage", "이메일 또는 비밀번호를 확인하세요.");
		
		return "member/memberloginForm";
	}
	
	
	
	
	
	
	
	
	
	
}