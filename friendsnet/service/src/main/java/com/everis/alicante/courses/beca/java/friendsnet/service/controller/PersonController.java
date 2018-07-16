package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.PersonManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonManager manager;

	@Autowired
	private DozerBeanMapper mapper;

	@GetMapping
	public List<PersonDTO> findAll() {
		List<Person> personList = (List<Person>) manager.findAll();
		List<PersonDTO> personDTOList = new ArrayList<>();
		if (null != personList) {
			for (Person person : personList) {
				personDTOList.add(mapper.map(person, PersonDTO.class));
			}
		}
		return personDTOList;
	}

	@GetMapping("/{id}")
	public PersonDTO findById(@PathVariable("id") Long id) {
		final Person person = manager.findById(id);
		PersonDTO personDTO = new PersonDTO();
		if (null != person) {
			personDTO = mapper.map(person, PersonDTO.class);
		}
		return personDTO;
	}
	
	@PostMapping
	public PersonDTO save(@RequestBody PersonDTO person) {
		Person personDB = manager.findById(person.getId());
		PersonDTO personDTO = new PersonDTO();
		if(null!=personDB) {
			personDTO = mapper.map(person, PersonDTO.class);
		}
		return personDTO;
	}
	
}
