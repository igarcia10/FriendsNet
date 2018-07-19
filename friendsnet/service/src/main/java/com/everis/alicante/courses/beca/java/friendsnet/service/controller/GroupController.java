package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.GroupManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.GroupDTO;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;

@RestController
@RequestMapping("/groups")
public class GroupController extends AbstractController<GroupDTO, Group, Long> {

	protected GroupController() {
		super(Group.class, GroupDTO.class);
	}

	@Autowired
	private GroupManagerImpl manager;

	@GetMapping("/person/{id}")
	public List<GroupDTO> getByPersonId(@PathVariable("id") Long id) {
		List<Group> resultList = manager.findByPersonsId(id);
		List<GroupDTO> listDTO = new ArrayList<>();
		if (null != resultList) {
			for (Group group : resultList) {
				listDTO.add(mapper.map(group, GroupDTO.class));
			}
		}
		return listDTO;
	}

	@PostMapping("/{id}/relate")
	public GroupDTO addPerson(@PathVariable("id") Long id, @RequestBody List<PersonDTO> personsDTO) {
		final List<Person> persons = new ArrayList<>();
		for (PersonDTO personDTO : personsDTO) {
			if (null != personDTO) {
				persons.add(mapper.map(personDTO, Person.class));
			}
		}
		return mapper.map(manager.addPersons(id, persons), GroupDTO.class);
	}
	
	@PostMapping("/new")
	public GroupDTO createGroup(@RequestBody String name, @RequestBody byte[] picture) {
		final Group group = manager.createGroup(name, picture);
		return mapper.map(group, GroupDTO.class);
	}
	

}
