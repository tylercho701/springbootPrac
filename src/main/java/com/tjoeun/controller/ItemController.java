package com.tjoeun.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.dto.ItemFormDTO;
import com.tjoeun.dto.ItemSearchDTO;
import com.tjoeun.entity.Item;
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
	
	//	상품 등록
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
	
	// 상품 수정
	@GetMapping("/admin/item/{itemId}")
	public String getItemDetail(@PathVariable("itemId") Long itemId, Model model) {
		
		try {
  		ItemFormDTO itemFormDTO = itemService.getItemDetail(itemId);
  		model.addAttribute("itemFormDTO", itemFormDTO);
  		
		}catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "등록되지 않은 상품입니다");
			model.addAttribute("itemFormDTO", new ItemFormDTO());
			return "item/itemForm";
			
		}
		
		return "item/itemForm";
	}
	
	//	상품 수정
	@PostMapping("/admin/item/{itemId}")
	public String itemUpdate(@Valid ItemFormDTO itemFormDTO, BindingResult result, Model model,
							 @RequestParam("itemImgFileList") List<MultipartFile> itemImgFileList) {
		
		if(result.hasErrors()) {
			return "item/itemForm";
			
		}
		
		// 상품 이미지를 선택하지 않고 상품저장을 누른 경우
		// 상품 이미지는 최소한 하나는 올려야 되도록 함
		if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null) {
			model.addAttribute("errorMessage", "첫 번째 상품 이미지는 반드시 업로드하셔야 합니다");
			return "item/itemForm";
		}
		
		try {
			itemService.updateItem(itemFormDTO, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 오류가 발생했습니다");
			return "item/itemForm";
		}
		
		return "redirect:/";	
	}
	
	@GetMapping({"/admin/items", "/admin/items/{page}"})
	public String itemList(ItemSearchDTO itemSearchDTO, Model model,
						   @PathVariable("page") Optional<Integer> page) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		Page<Item> items = itemService.getAdminItemPage(itemSearchDTO, pageable);
		
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDTO", itemSearchDTO);
		
		//	페이지 최대 사이즈
		model.addAttribute("maxPage", 5);
		
		return "item/itemList";
	}
	
	
}