package com.tjoeun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tjoeun.dto.ItemSearchDTO;
import com.tjoeun.entity.Item;

public interface CustomItemRepository {
	
	Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
	
}