package com.tjoeun.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Repository
@Getter @Setter @ToString
public class ItemDTO {
	
	private  Long id;					//	상품 코드
	
	private String itemNm;				//	상품 이름

	private int price;					//	상품 가격

	private int stockNumber;			//	재고 수량

	private String itemDetail;			//	상품 상세 설명
	
	private String itemSellStatus;
	
	private LocalDateTime regTime;		//	등록 시간
	
	private LocalDateTime updateTime;	//수정 시간
}
