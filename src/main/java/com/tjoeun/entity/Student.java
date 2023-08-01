package com.tjoeun.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//	@Table(name="colleague")
@Entity
public class Student {
	
	//primary key
	@Id
	//	Auto-increment
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//	Entity class의 멤버 변수 이름과 table 내 column 이름을 다르게 지정
	@Column(name="name", nullable=false, length=30)
	private String myName;
	
	//	javaCamelCase	-->	db_snake_case
	private int myHeight;
}
