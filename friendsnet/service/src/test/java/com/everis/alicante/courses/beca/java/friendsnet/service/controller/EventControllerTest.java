package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.EventManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.EventDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class EventControllerTest extends AbstractControllerTest<EventDTO, Event, Long> {
	
	public EventControllerTest() {
		super(Event.class, EventDTO.class, "/events");
	}
	
	@Override
	AbstractController<EventDTO, Event, Long> getController() {
		return controller;
	}

	@Override
	AbstractManager<Event, Long> getManager() {
		return manager;
	}

	@InjectMocks
	private EventController controller;
	
	@Mock
	private EventManagerImpl manager;
	
	@Test
	public void testFindByPersonsId() throws Exception {
		// Arrange
		final Event event = new Event();
		final List<Event> events = new ArrayList<>();
		events.add(event);
		final EventDTO eventDTO = new EventDTO();
		final List<EventDTO> listDTO = new ArrayList<>();
		listDTO.add(eventDTO);
		Mockito.when(manager.findByPersonsId(1L)).thenReturn(events);
		Mockito.when(dozerMapper.map(Mockito.any(), Mockito.any())).thenReturn(eventDTO);
		// Act
		final ResultActions perform = mockMvc.perform(get("/events/person/1"));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(listDTO)));
	}
	
	@Test
	public void testFindByPersonsIdNull() throws Exception {
		// Arrange
		Mockito.when(manager.findByPersonsId(1L)).thenReturn(null);
		// Act
		final ResultActions perform = mockMvc.perform(get("/events/person/1"));
		// Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testAddPerson() throws Exception {
		//Arrange
		Event event = new Event();
		EventDTO eventDTO = new EventDTO();
		Mockito.when(manager.addPerson(1L, 1L)).thenReturn(event);
		Mockito.when(dozerMapper.map(event, EventDTO.class)).thenReturn(eventDTO);
		//Act
		ResultActions perform = mockMvc.perform(post("/events/1/person/1/add"));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(eventDTO)));
	}
	
	@Test
	public void testAddPersonNull() throws Exception {
		//Arrange
		Mockito.when(manager.addPerson(1L, 1L)).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(post("/events/1/person/1/add"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	//TODO Check with POSTMan or AdvancedRestClient
//	@Test
//	public void testCreateEvent() throws Exception {
//		//Arrange
//		final Event event = new Event();
//		final EventDTO eventDTO = new EventDTO();
//		final String name = "name";
//		final Date startingDate = new Date();
//		final Date endingDate = new Date();
////		final byte[] picture = new byte[10];
//		eventDTO.setName(name);
//		eventDTO.setStartingDate(startingDate);
//		eventDTO.setEndingDate(endingDate);
//		Mockito.when(manager.createEvent(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(event);
//		Mockito.when(dozerMapper.map(Mockito.any(Event.class), Mockito.any())).thenReturn(eventDTO);
//		//Act
//		final ResultActions perform = mockMvc.perform(post("/events/new/PARTY").content(mapper.writeValueAsString(eventDTO))
////																				.content(mapper.writeValueAsBytes(picture))
//																				.contentType(MediaType.APPLICATION_JSON)
//																				.accept(MediaType.APPLICATION_JSON));
//		//Assert
//		perform.andExpect(status().isOk());
//		perform.andExpect(content().json(mapper.writeValueAsString(eventDTO)));
//	}
	
	//TODO Check with POSTMan or AdvancedRestClient
//	@Test
//	public void testCreateEventNull() throws Exception {
//		//Arrange
//		final EventDTO eventDTO = new EventDTO();
//		final String name = "name";
//		final Date startingDate = new Date();
//		final Date endingDate = new Date();
////		final byte[] picture = new byte[10];
//		eventDTO.setName(name);
//		eventDTO.setStartingDate(startingDate);
//		eventDTO.setEndingDate(endingDate);
//		Mockito.when(manager.createEvent(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
//		Mockito.when(dozerMapper.map(Mockito.any(Event.class), Mockito.any())).thenReturn(eventDTO);
//		//Act
//		final ResultActions perform = mockMvc.perform(post("/events/new/GENERAL").content(mapper.writeValueAsString(eventDTO))
////																				.content(mapper.writeValueAsBytes(picture))
//																				.contentType(MediaType.APPLICATION_JSON)
//																				.accept(MediaType.APPLICATION_JSON));
//		//Assert
//		perform.andExpect(status().isOk());
//	}

}
