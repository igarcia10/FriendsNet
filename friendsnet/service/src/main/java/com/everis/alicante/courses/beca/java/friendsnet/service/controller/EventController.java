package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.EventManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.EventType;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.EventDTO;

@RestController
@RequestMapping("/events")
public class EventController extends AbstractController<EventDTO, Event, Long> {

	protected EventController() {
		super(Event.class, EventDTO.class);
	}

	@Autowired
	private EventManagerImpl manager;

	@PostMapping("/{id}/person/{idperson}/add")
	public EventDTO addPerson(@PathVariable("id") Long id, @PathVariable("idperson") Long idperson) {
		return mapper.map(manager.addPerson(id, idperson), EventDTO.class);
	}

	@GetMapping("/person/{id}")
	public List<EventDTO> getByPersonId(@PathVariable("id") Long id) {
		List<Event> resultList = manager.findByPersonsId(id);
		List<EventDTO> listDTO = new ArrayList<>();
		if (null != resultList) {
			for (Event group : resultList) {
				listDTO.add(mapper.map(group, EventDTO.class));
			}
		}
		return listDTO;
	}

	@PostMapping(value = "/new/{eventtype}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public EventDTO createEvent(@PathVariable("eventtype") EventType type, @RequestBody EventDTO eventDTO,
			@RequestPart(required=false) MultipartFile pictureFile) throws IOException {
		byte[] picture = null;
		if (null != pictureFile) {
			picture = pictureFile.getBytes();
		}
		return mapper.map(manager.createEvent(mapper.map(eventDTO, Event.class), picture, type), EventDTO.class);
	}
}
