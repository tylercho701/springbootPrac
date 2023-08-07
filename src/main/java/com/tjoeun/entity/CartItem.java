package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity {

	@Id
	@Column(name = "cart_item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//	cartitem 테이블과 cart 테이블의	n	:	1	연관 관계 매핑
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	//	cartitem 테이블과 item 테이블의	n	:	1	연관 관계 매핑
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	private int count;
}
