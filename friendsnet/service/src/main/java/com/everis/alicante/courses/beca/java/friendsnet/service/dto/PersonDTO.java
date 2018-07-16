package com.everis.alicante.courses.beca.java.friendsnet.service.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO implements DTOEntity {
	
	private Long id;
	private String name;
	private String surname;
	private byte[] picture;
	private Set<Group> groups = new HashSet<>();
	private Set<Event> events = new HashSet<>();
	private Set<Person> friends;
	private List<Post> posts = new ArrayList<>();
	private List<Like> likes = new ArrayList<>();

}
