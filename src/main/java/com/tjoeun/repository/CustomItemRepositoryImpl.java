package com.tjoeun.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tjoeun.constant.ItemSellStatus;
import com.tjoeun.dto.ItemSearchDTO;
import com.tjoeun.entity.Item;
import static com.tjoeun.entity.QItem.item;

public class CustomItemRepositoryImpl implements CustomItemRepository{
	
	//	querydsl 사용할때는, JPAQueryFactory가 필요
	private JPAQueryFactory jpaQueryFactory;
	
	public CustomItemRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
		
		List<Item> contentList = jpaQueryFactory.selectFrom(item)
					   					 .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
					   							searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
											    searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
					   					 .orderBy(item.id.desc())
					   					 .offset(pageable.getOffset())
					   					 .limit(pageable.getPageSize())
					   					 .fetch();
		
		Long total = jpaQueryFactory.select(Wildcard.count)
									.from(item)
									.where(regDtsAfter(itemSearchDTO.getSearchDateType()),
										   searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
										   searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
									.fetchOne();
		
		return new PageImpl<Item>(contentList, pageable, total);
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		
		//	searchBy : 어떤 방법으로 조회할 것인지
		//			     아래 코드에서는 두 가지 방법 (itemNm, CreatedBy)
		if(StringUtils.equals("itemNm", searchBy)) {
			return item.itemNm.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return item.createdBy.like("%" + searchQuery + "%");
		}
		
		return null;
	}

	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		return searchSellStatus == null ? null : item.itemSellStatus.eq(searchSellStatus);
	}

	private BooleanExpression regDtsAfter(String searchDateType) {
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
			return null;
		} else if(StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);
		} else if(StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1);
		} else if(StringUtils.equals("6m", searchDateType)) {
			dateTime = dateTime.minusMonths(6);
		}
		
		return item.regTime.after(dateTime);
	}

}
