package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.EventDTO;

@RestController
@RequestMapping("/events")
public class EventController extends AbstractController<EventDTO, Event, Long> {

	protected EventController() {
		super(Event.class, EventDTO.class);
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

}
