package com.tjoeun.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.tjoeun.constant.ItemSellStatus;
import com.tjoeun.entity.Item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ItemFormDTO {

	private Long id;
	
	@NotBlank(message = "상품 이름을 입력하세요.")
	private String itemNm;
	
	@NotNull(message = "가격을 입력하세요.")
	private Integer price;						//	상품 가격
	
	@NotNull(message = "수량을 입력하세요.")
	private Integer stockNumber;				//	재고 수량
	
	@NotBlank(message = "상세 내용을 입력하세요.")
	private String itemDetail	;			//	상품 상세 설명
	
	private ItemSellStatus itemSellStatus;	//	상품 판매 상태
	
	//	이미지 리스트	:	이미지 받아오기
	private List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
	
	//	이미지 번호 받아오기
	private List<Long> itemImgIds = new ArrayList<>();
	
	//	Item Entity와 ItemFormDTO Mapping하기
	private static ModelMapper modelMapper = new ModelMapper();
	
	//	ItemFormDTO가 받은 값을 Item Entity에 저장하기
	//	화면에 입력한 값을 DB에 저장함 - 등록
	public Item createItem() {
		return modelMapper.map(this, Item.class);
	}
	
	//	Item Entity에 있는 값을 ItemFormDTO에 저장해서 return함
	//	DB에 있는 값을 화면에 출력함 - 수정
	public static ItemFormDTO of(Item item) {
		return modelMapper.map(item, ItemFormDTO.class);
	}
	
}
