package com.everis.alicante.courses.beca.java.friendsnet.service.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.PostType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO implements DTOEntity {
	
	private Long id;
	private String text;
	private Date creationDate;
	private PostType type;
	private byte[] picture;
	private List<LikeDTO> likes = new ArrayList<>();
	private Person person;

}
