package com.everis.alicante.courses.beca.java.friendsnet.service.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO implements DTOEntity {
	
	private Long id;
	private String name;
	private byte[] picture;
	private Set<PersonDTO> persons = new HashSet<>();

}
