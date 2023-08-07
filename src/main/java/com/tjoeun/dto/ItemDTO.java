package com.tjoeun.dto;

import com.tjoeun.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ItemDTO extends BaseEntity {
	
	private  Long id;					//	상품 코드
	
	private String itemNm;				//	상품 이름

	private int price;					//	상품 가격

	private int stockNumber;			//	재고 수량

	private String itemDetail;			//	상품 상세 설명
	
	private String itemSellStatus;		//	판매 상태
	/*
	BaseEntity에 있는 regTime과 updateTime을 사용함
	private LocalDateTime regTime;		//	등록 시간
	
	private LocalDateTime updateTime;	//수정 시간
	*/
}
