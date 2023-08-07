package com.tjoeun.dto;

import org.modelmapper.ModelMapper;

import com.tjoeun.entity.BaseEntity;
import com.tjoeun.entity.Item;
import com.tjoeun.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ItemImgDTO {

	private Long id;
	
	private String imgName;				//	이미지 파일명 : project 내에서 UUID로 저장되는 이름
	
	private String oriImgName;			//	원본 이미지 파일명
	
	private String imgUrl;				//	이미지 저장 경로
	
	private String repImgYn;			//	대표 이미지 여부
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//	ItemImgDTO 가 화면에서 받아온 data를
	//	Entity 클래스인 ItemImg에 전달하는 메소드
	public static ItemImgDTO of(ItemImg itemImg) {
		
		return modelMapper.map(itemImg, ItemImgDTO.class);
	}
	
}