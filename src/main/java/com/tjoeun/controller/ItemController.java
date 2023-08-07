package com.tjoeun.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.dto.ItemFormDTO;
import com.tjoeun.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;

	@GetMapping("/admin/item/new")
	public String itemFrom(ItemFormDTO itemFormDTO) {
		return "item/itemForm";
	}
	
	@PostMapping("/admin/item/new")
	public String itemNew(@Valid ItemFormDTO itemFormDTO, 
						  BindingResult result, Model model,
						  @RequestParam("itemImgFileList") List<MultipartFile> itemImgFileList) {
		
		if(result.hasErrors()) {
			return "item/itemForm";
		}
		
		//	상품 이미지를 선택하지 않고 submit 한 경우
		//	상품 이미지는 최소 하나는 등록하도록 함
		if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null) {
			model.addAttribute("errorMessage", "첫 번째 상품 이미지는 반드시 등록해야합니다.");
			return "item/itemForm";
		}
		
		try {
			itemService.saveItem(itemFormDTO, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 문제가 발생했습니다.");
			return "item/itemForm";
		}
		
		return "redirect:/";
	}
	
	
}