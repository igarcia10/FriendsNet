package com.everis.alicante.courses.beca.java.friendsnet.service.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO implements DTOEntity {
	
	private Long id;
	private String name;
	private String surname;
	private byte[] picture;
	private Set<FriendDTO> friends;

}
