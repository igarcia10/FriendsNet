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

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.FNEntity;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.DTOEntity;

public abstract class AbstractController<DTO extends DTOEntity, E extends FNEntity, ID> {
	
	@Autowired
	private AbstractManager<E, ID> manager;

	@Autowired
	protected DozerBeanMapper mapper;
	
	Class<E> entityClass;
	Class<DTO> dtoClass;

	protected AbstractController(Class<E> entityClass, Class<DTO> dtoClass) {
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
	}

	@GetMapping("/")
	public List<DTO> getAll() {
		List<E> eventList = (List<E>) manager.findAll();
		List<DTO> eventDTOList = new ArrayList<>();
		if (null != eventList) {
			for (E event : eventList) {
				eventDTOList.add(mapper.map(event, dtoClass));
			}
		}
		return eventDTOList;
	}

	@GetMapping("/{id}")
	public DTO getById(@PathVariable("id") ID id) {
		final E event = manager.findById(id);
		DTO eventDTO = null;
		if (null != event) {
			eventDTO = mapper.map(event, dtoClass);
		}
		return eventDTO;
	}

	@PostMapping("/")
	public DTO create(@RequestBody DTO event) {
		E eventDB = manager.save(mapper.map(event, entityClass));
		DTO eventDTO = null;
		if (null != eventDB) {
			eventDTO = mapper.map(eventDB, dtoClass);
		}
		return eventDTO;
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable("id") ID id) {
		final E event = manager.findById(id);
		if (null != event) {
			manager.remove(event);
		}
	}

}
