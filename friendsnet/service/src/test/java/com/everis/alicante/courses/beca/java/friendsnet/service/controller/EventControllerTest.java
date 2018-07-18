package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.EventManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.EventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class EventControllerTest {
	
	@InjectMocks
	private EventController controller;
	
	@Mock
	private EventManagerImpl manager;
	
	@Mock
	private DozerBeanMapper dozerMapper;

	private MockMvc mockMvc;

	private ObjectMapper mapper;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void testFindAllNull() throws Exception {
		// Arrange
		Mockito.when(manager.findAll()).thenReturn(null);
		// Act
		final ResultActions perform = mockMvc.perform(get("/events"));
		// Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllWithContent() throws Exception {
		// Arrange
		final Event event1 = new Event();
		event1.setName("event1");
		final EventDTO eventDTO1 = new EventDTO();
		eventDTO1.setName("event1");
		final Event event2 = new Event();
		event2.setName("event2");
		final EventDTO eventDTO2 = new EventDTO();
		eventDTO2.setName("event2");
		final List<Event> eventList = new ArrayList<>();
		eventList.add(event1);
		eventList.add(event2);
		final List<EventDTO> eventDTOList = new ArrayList<>();
		eventDTOList.add(eventDTO1);
		eventDTOList.add(eventDTO2);
		Mockito.when(manager.findAll()).thenReturn(eventList);
		Mockito.when(dozerMapper.map(event1, EventDTO.class)).thenReturn(eventDTO1);
		Mockito.when(dozerMapper.map(event2, EventDTO.class)).thenReturn(eventDTO2);
		// Act
		final ResultActions perform = mockMvc.perform(get("/events"));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(eventDTOList)));
	}
	
	@Test
	public void testFindByIdNull() throws Exception {
		//Arrange
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		final ResultActions perform = mockMvc.perform(get("/events/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindByIdWithContent() throws Exception {
		//Arrange
		final Event event = new Event();
		event.setName("event");
		final EventDTO eventDTO = new EventDTO();
		eventDTO.setName("event");
		Mockito.when(manager.findById(1L)).thenReturn(event);
		Mockito.when(dozerMapper.map(event, EventDTO.class)).thenReturn(eventDTO);
		//Act
		final ResultActions perform = mockMvc.perform(get("/events/1"));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(eventDTO)));
	}
	
	@Test
	public void testCreateNull() throws Exception {
		//Arrange
		final Event event = new Event();
		final EventDTO eventDTO = new EventDTO();
		Mockito.when(manager.save(event)).thenReturn(null);
		Mockito.when(dozerMapper.map(event, EventDTO.class)).thenReturn(null);
		Mockito.when(dozerMapper.map(Mockito.any(EventDTO.class), Mockito.any())).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(post("/events").content(mapper.writeValueAsString(eventDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testCreate() throws Exception {
		//Arrange
		final Event event = new Event();
		event.setName("name");
		final EventDTO eventDTO = new EventDTO();
		eventDTO.setName("name");
		Mockito.when(manager.save(event)).thenReturn(event);
		Mockito.when(dozerMapper.map(event, EventDTO.class)).thenReturn(eventDTO);
		Mockito.when(dozerMapper.map(Mockito.any(EventDTO.class), Mockito.any())).thenReturn(event);
		//Act
		ResultActions perform = mockMvc.perform(post("/events").content(mapper.writeValueAsString(eventDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(eventDTO)));
	}
	
	@Test
	public void testRemove() throws Exception {
		//Arrange
		final Event event = new Event();
		Mockito.when(manager.findById(1L)).thenReturn(event);
		//Act
		ResultActions perform = mockMvc.perform(delete("/events/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveNotInDb() throws Exception {
		//Arrange
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(delete("/events/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
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

}
