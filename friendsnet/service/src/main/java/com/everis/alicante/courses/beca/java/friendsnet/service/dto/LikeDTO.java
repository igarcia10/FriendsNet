package com.everis.alicante.courses.beca.java.friendsnet.service.dto;

import java.util.Date;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.LikeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO implements DTOEntity {
	
	private Long id;
	private Date creationDate;
	private LikeType type;
	private Person person;
	private Post post;

}
