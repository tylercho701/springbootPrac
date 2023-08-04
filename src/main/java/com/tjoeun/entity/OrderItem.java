package com.tjoeun.entity;

import java.time.LocalDateTime;

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
public class OrderItem {

	@Id
	@Column(name = "order_item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orders_id")
	private Orders orders;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	private int orderPrice;
	
	private int count;
	
	private LocalDateTime regTime;
	
	private LocalDateTime updateTime;
}
