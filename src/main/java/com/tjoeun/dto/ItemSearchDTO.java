package com.tjoeun.dto;

import com.tjoeun.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDTO {
	
	private String searchDateType;
	
	private ItemSellStatus searchSellStatus;
	
	private String searchBy;
	
	private String searchQuery = "";
}
