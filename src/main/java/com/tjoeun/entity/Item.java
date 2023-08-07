package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.tjoeun.constant.ItemSellStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_id")
	private  Long id;					//	상품 코드
	
	@Column(nullable=false, length=50)
	private String itemNm;				//	상품 이름
	
	@Column(nullable=false)
	private int price;					//	상품 가격
	
	@Column(nullable=false, name="number")
	private int stockNumber;			//	재고 수량
	
	//	문자의 숫자가 255 이상인 대용량 텍스트에 사용
	@Lob
	@Column(nullable=false)
	private String itemDetail;			//	상품 상세 설명
	
	@Enumerated(EnumType.ORDINAL)
	private ItemSellStatus itemSellStatus;
	
	
	

}
