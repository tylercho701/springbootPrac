package com.tjoeun.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	
	
}
