package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.GroupDTO;

@RestController
@RequestMapping("/groups")
public class GroupController extends AbstractController<GroupDTO, Group, Long> {

	protected GroupController() {
		super(Group.class, GroupDTO.class);
	}

	// TODO
	@GetMapping("/person/{id}")
	public Group getByPersonId() {
		return null;
	}

}
