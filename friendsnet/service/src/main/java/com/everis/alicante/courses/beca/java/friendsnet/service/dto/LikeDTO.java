package com.everis.alicante.courses.beca.java.friendsnet.service.dto;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.LikeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO implements DTOEntity {
	
	private Long id;
	private LikeType type;
	private PersonDTO person;
	
}
