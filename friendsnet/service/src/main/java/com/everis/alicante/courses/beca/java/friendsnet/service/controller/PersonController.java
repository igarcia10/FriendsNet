package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.PersonManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;

@RestController
@RequestMapping("/persons")
public class PersonController extends AbstractController<PersonDTO, Person, Long> {

	protected PersonController() {
		super(Person.class, PersonDTO.class);
	}
	
	@Autowired
	private PersonManagerImpl manager;

	@PostMapping("/{id}/relate")
	public PersonDTO relate(@PathVariable("id") Long id, @RequestBody List<PersonDTO> friendsDTO) {
			List<Person> friends = new ArrayList<>();
			for (PersonDTO friendDTO : friendsDTO) {
				if (null != friendDTO) {
						friends.add(mapper.map(friendDTO, Person.class));
				}
			}
		return mapper.map(manager.relatePersons(id, friends), PersonDTO.class);
	}

}
