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

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.EventManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.EventDTO;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventManagerImpl eventManager;

	@Autowired
	private DozerBeanMapper mapper;

	@GetMapping
	public List<EventDTO> getAll() {
		List<Event> eventList = (List<Event>) eventManager.findAll();
		List<EventDTO> eventDTOList = new ArrayList<>();
		if (null != eventList) {
			for (Event event : eventList) {
				eventDTOList.add(mapper.map(event, EventDTO.class));
			}
		}
		return eventDTOList;
	}

	@GetMapping("/{id}")
	public EventDTO getById(@PathVariable("id") Long id) {
		final Event event = eventManager.findById(id);
		EventDTO eventDTO = null;
		if (null != event) {
			eventDTO = mapper.map(event, EventDTO.class);
		}
		return eventDTO;
	}

	// TODO
	@GetMapping("/{id}/person/{idperson}/add")
	public Event addPerson() {
		
		return null;
	}

	// TODO
	@GetMapping("/person/{id}")
	public List<Event> getByPersonId() {
		return null;
	}

	@PostMapping
	public EventDTO create(@RequestBody EventDTO event) {
		Event eventDB = eventManager.save(mapper.map(event, Event.class));
		EventDTO eventDTO = null;
		if (null != eventDB) {
			eventDTO = mapper.map(eventDB, EventDTO.class);
		}
		return eventDTO;
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable("id") Long id) {
		final Event event = eventManager.findById(id);
		if (null != event) {
			eventManager.remove(event);
		}
	}

}
