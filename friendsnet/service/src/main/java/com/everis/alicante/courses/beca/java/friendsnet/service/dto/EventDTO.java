package com.everis.alicante.courses.beca.java.friendsnet.service.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.EventType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO implements DTOEntity {
	
	private Long id;
	private String name;
	private Date startingDate;
	private Date endingDate;
	private EventType type;
	private byte[] picture;
	private Set<Person> persons = new HashSet<>();

}
