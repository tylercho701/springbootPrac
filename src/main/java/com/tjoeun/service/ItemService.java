package com.tjoeun.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.dto.ItemFormDTO;
import com.tjoeun.entity.Item;
import com.tjoeun.entity.ItemImg;
import com.tjoeun.repository.ItemImgRepository;
import com.tjoeun.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;
	private final ItemImgRepository itemImgRepository;
	private final ItemImgService itemImgService;
	
	/*
    item 을 등록하면 item id 가 넘어옴
    itemFormDto 와 MultipartFile 를 Generic 으로 하는 List 를 파라미터로 전달받음 
                   List<MultipartFile> itemImgFileList
    itemImgFileList : 등록한 이미지파일들을 list 로 가져옴
    itemFormDto : front 단에서 입력한 data 를 저장해서 server 단으로 가져옴
	*/
	
	public Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception {
		
		//	상품 등록
		//	itemFormDTO.createItem()에서 mapper를 사용해서
		//	DTO를 entity로 바꾼 Item(entity) 객체를 받아옴
		
		Item item = itemFormDTO.createItem();
		
		//	DB에 저장함 : 이미지 번호(id)가 생김
		//	Item item 변수에 할당하지 않아도
		//	consistence 영역에 저장됭서 값을 확인할 수 있음
		//	return item.getId() : 업로드 한 상품 번호(id) <-- 행의 Item item 과 같은 item
		
		itemRepository.save(item);
		
		
		//	이미지 등록 : 저장한 이미지 개수만큼 등록함
		for(int i = 0; i<itemImgFileList.size(); i++) {
			//	ItemImg는 Entity임
			//	item image 등록을 위해서 ItemImg 객체를 생성함
			ItemImg itemImg = new ItemImg();
			
			//	persistence 영역에 있는 item 객체를 setItem으로 설정함
			//	id 값을 사용하게 됨 <-- foreign key 가 설정됨
			//	몇 번째 등록인지를 image 에 설정함
			//	첫 번째이면 아래 if 문에서 대표 이미지로 설정
			itemImg.setItem(item);
			
			//	첫 번째 이미지를 대표 이미지로 설정
			if(i==0) {
				itemImg.setRepImgYn("Y");
			} else {
				itemImg.setRepImgYn("N");
			}
			
			//	이미지를 DB 에 저장
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
		}
		
		//	item의 id를 반환
		return item.getId();
		
	}
	
}
