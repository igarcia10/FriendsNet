package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class PersonController {

	@Autowired
	private PersonManagerImpl manager;

	@Autowired
	private DozerBeanMapper mapper;

	@GetMapping
	public List<PersonDTO> getAll() {
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
	public PersonDTO getById(@PathVariable("id") Long id) {
		final Person person = manager.findById(id);
		PersonDTO personDTO = null;
		if (null != person) {
			personDTO = mapper.map(person, PersonDTO.class);
		}
		return personDTO;
	}

	@PostMapping
	public PersonDTO create(@RequestBody PersonDTO person) {
		Person personDB = manager.save(mapper.map(person, Person.class));
		PersonDTO personDTO = null;
		if (null != personDB) {
			personDTO = mapper.map(personDB, PersonDTO.class);
		}
		return personDTO;
	}

	@PostMapping("/{id}/relate")
	public PersonDTO relate(@PathVariable("id") Long id, @RequestBody List<PersonDTO> friends) {
		PersonDTO personDTO = new PersonDTO();
		Person personDB = manager.findById(id);
		if (null != personDB) {
			Person friendDB = new Person();
			List<Person> friendsDB = new ArrayList<>();
			for (PersonDTO friend : friends) {
				if (null != friend) {
					friendDB = manager.findById(friend.getId());
					if (null != friendDB) {
						friendsDB.add(friendDB);
					}
				}
			}
			personDB = manager.relatePersons(id, friendsDB);
			personDTO = mapper.map(personDB, PersonDTO.class);
		}
		return personDTO;
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable("id") Long id) {
		final Person person = manager.findById(id);
		if (null != person) {
			manager.remove(person);
		}
	}

}
