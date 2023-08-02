package com.tjoeun.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dto.ItemDTO;
import com.tjoeun.test.Person;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/test")
@Log4j2
public class TestController2 {

	@GetMapping("/t1")
	public void t1(Person person, Model model) {
		
		person.setName("더조은");
		person.setHeight(188);
		
		model.addAttribute("text", "Spring Boot 2.7.14");

	}
	
	@GetMapping("/t2")
	public void t2(ItemDTO itemDTO, Model model) {
		
		//	받는 곳에서는 변수명이 아닌 DTO명을 앞 첫 글자를 소문자로 입력하면
		//	model을 사용하지 않아도 받을 수 던져짐
		
		itemDTO.setItemDetail("상세설명");
		itemDTO.setItemNm("상품1");
		itemDTO.setPrice(10000);
		itemDTO.setRegTime(LocalDateTime.now());
		
		//	model.addAttribute("item", item);

	}
	
	@GetMapping({"/t3", "/t4"})
	public void t3(Model model) {
		
		List<ItemDTO> itemList = new ArrayList<ItemDTO>();
		
		for(int i = 1; i < 11; i++) {
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setItemDetail("상세설명-" + i);
			itemDTO.setItemNm("상품-" + i);
			itemDTO.setPrice(10000 * i);
			itemDTO.setRegTime(LocalDateTime.now());
			
			itemList.add(itemDTO);
			
			System.out.println(itemList.toString());
		}
		
		model.addAttribute("itemList", itemList);
	}
	
	@GetMapping("/t5")
	public void t5(String name, String height, Model model) {
		log.info(">>>>>>>>>>" + name + ", " + height);
		
		model.addAttribute("name", name);
		model.addAttribute("height", height);
	}
	
	@GetMapping({"/content1", "/content2"})
	public void content() {
		
	}
}
