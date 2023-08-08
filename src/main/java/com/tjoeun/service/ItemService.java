package com.tjoeun.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.dto.ItemFormDTO;
import com.tjoeun.dto.ItemImgDTO;
import com.tjoeun.dto.ItemSearchDTO;
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
	
	// 상품 수정하기
	public ItemFormDTO getItemDetail(Long itemId) {
		
		// DB 에서 이미지 가져오기 : DB 에서 가져오므로 Entity 인 ItemImg list 로 함
		List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
		
		// Entity 를 DTO 로 옮기고 이를 mapping 하는 image list 를 만듬
		// DB 에서 이미지를 가져와서 View 에 보여줄려고 함 
		//   ㄴ 수정해야 하므로 원래의 내용을 화면에 보여줌
		List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
		
		// ItemImg(Entity)  -->  itemImgDto
		for(ItemImg itemImg: itemImgList) {
			// ItemImgDto.of() :  Entity(itemImg) 를 받아서 DTO(ItemImgDto) 로 변환하는 mapper 			
			ItemImgDTO itemImgDTO = ItemImgDTO.of(itemImg);
			// itemImgDto 를 itemImgDtoList 에 추가함
			itemImgDTOList.add(itemImgDTO);
		}
		
		// DB 에서 item 테이블에 있는 값들 꺼내옴
		Item item = itemRepository.findById(itemId)
			                      .orElseThrow(EntityNotFoundException::new);
		
		// Item(Entity) --> ItemFormDto
		ItemFormDTO itemFormDTO = ItemFormDTO.of(item); 
		
		itemFormDTO.setItemImgDTOList(itemImgDTOList);
		
		return itemFormDTO;
	}
	
	//	실제로 item 수정하기
	public Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception {
		
		//	상품 업데이트(수정)
		//	수정 대상 상품(item)을 DB에서 가져오기 : item entity
		Item item = itemRepository.findById(itemFormDTO.getId())
								  .orElseThrow(EntityNotFoundException::new);
		
		//	파라미터로 전달되어 들어온
		//	ItemFormDTO 객체를 argument로 넣음
		item.updateItem(itemFormDTO);
		
		//	상품 이미지 업데이트(수정)
		//	상품 이미지 번호(id)들을 가져옴
		List<Long> itemImgIds = itemFormDTO.getItemImgIds();
		
		//	상품 이미지 업데이트 : itemImgId, itemImgFileList
		for (int i = 0; i < itemImgFileList.size(); i++) {
			itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
		}
		
		
		// 정보를 수정한 상품이 몇 번째 상품인지 반환함
	    // 이후에는 Controller 로 가서 수정 작업함
		return item.getId();
	}
	
	// 상품 가져오기
	public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDto, Pageable pageable){
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}
	
}
