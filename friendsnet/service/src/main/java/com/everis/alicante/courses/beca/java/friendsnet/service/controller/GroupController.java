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

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.GroupManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.GroupDTO;

@RestController
@RequestMapping("/groups")
public class GroupController {

	@Autowired
	private GroupManager manager;

	@Autowired
	private DozerBeanMapper mapper;

	@GetMapping
	public List<GroupDTO> getAll() {
		List<Group> groupList = (List<Group>) manager.findAll();
		List<GroupDTO> groupDTOList = new ArrayList<>();
		if (null != groupList) {
			for (Group group : groupList) {
				groupDTOList.add(mapper.map(group, GroupDTO.class));
			}
		}
		return groupDTOList;
	}

	@GetMapping("/{id}")
	public GroupDTO getById(@PathVariable("id") Long id) {
		final Group group = manager.findById(id);
		GroupDTO groupDTO = null;
		if (null != group) {
			groupDTO = mapper.map(group, GroupDTO.class);
		}
		return groupDTO;
	}

	@PostMapping
	public GroupDTO create(@RequestBody GroupDTO group) {
		Group groupDB = manager.save(mapper.map(group, Group.class));
		GroupDTO groupDTO = null;
		if (null != groupDB) {
			groupDTO = mapper.map(groupDB, GroupDTO.class);
		}
		return groupDTO;
	}

	// TODO
	@GetMapping("/person/{id}")
	public Group getByPersonId() {
		return null;
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable("id") Long id) {
		final Group group = manager.findById(id);
		if (null != group) {
			manager.remove(group);
		}
	}

}
