package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.PersonManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.FriendDTO;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PersonControllerTest {
	
	@InjectMocks
	private PersonController controller;
	
	@Mock
	private PersonManagerImpl manager;
	
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
		final ResultActions perform = mockMvc.perform(get("/persons"));
		// Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllWithContent() throws Exception {
		// Arrange
		final Person person1 = new Person();
		person1.setName("person1");
		final PersonDTO personDTO1 = new PersonDTO();
		personDTO1.setName("person1");
		final Person person2 = new Person();
		person2.setName("person2");
		final PersonDTO personDTO2 = new PersonDTO();
		personDTO2.setName("person2");
		final List<Person> personList = new ArrayList<>();
		personList.add(person1);
		personList.add(person2);
		final List<PersonDTO> personDTOList = new ArrayList<>();
		personDTOList.add(personDTO1);
		personDTOList.add(personDTO2);
		Mockito.when(manager.findAll()).thenReturn(personList);
		Mockito.when(dozerMapper.map(person1, PersonDTO.class)).thenReturn(personDTO1);
		Mockito.when(dozerMapper.map(person2, PersonDTO.class)).thenReturn(personDTO2);
		// Act
		final ResultActions perform = mockMvc.perform(get("/persons"));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(personDTOList)));
	}
	
	@Test
	public void testFindByIdNull() throws Exception {
		//Arrange
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		final ResultActions perform = mockMvc.perform(get("/persons/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindByIdWithContent() throws Exception {
		//Arrange
		final Person person = new Person();
		person.setName("person");
		final PersonDTO personDTO = new PersonDTO();
		personDTO.setName("person");
		Mockito.when(manager.findById(1L)).thenReturn(person);
		Mockito.when(dozerMapper.map(person, PersonDTO.class)).thenReturn(personDTO);
		//Act
		final ResultActions perform = mockMvc.perform(get("/persons/1"));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(personDTO)));
	}
	
	@Test
	public void testCreateNull() throws Exception {
		//Arrange
		final Person person = new Person();
		final PersonDTO personDTO = new PersonDTO();
		Mockito.when(manager.save(person)).thenReturn(null);
		Mockito.when(dozerMapper.map(person, PersonDTO.class)).thenReturn(null);
		Mockito.when(dozerMapper.map(Mockito.any(PersonDTO.class), Mockito.any())).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(post("/persons").content(mapper.writeValueAsString(personDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testCreate() throws Exception {
		//Arrange
		final Person person = new Person();
		person.setName("name");
		final PersonDTO personDTO = new PersonDTO();
		personDTO.setName("name");
		Mockito.when(manager.save(person)).thenReturn(person);
		Mockito.when(dozerMapper.map(person, PersonDTO.class)).thenReturn(personDTO);
		Mockito.when(dozerMapper.map(Mockito.any(PersonDTO.class), Mockito.any())).thenReturn(person);
		//Act
		ResultActions perform = mockMvc.perform(post("/persons").content(mapper.writeValueAsString(personDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(personDTO)));
	}
	
	@Test
	public void testRemove() throws Exception {
		//Arrange
		final Person person = new Person();
		Mockito.when(manager.findById(1L)).thenReturn(person);
		//Act
		ResultActions perform = mockMvc.perform(delete("/persons/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveNotInDb() throws Exception {
		//Arrange
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(delete("/persons/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testRelate() throws Exception {
		//Arrange
		final Person person = new Person();
		person.setId(1L);
		final PersonDTO personDTO = new PersonDTO();
		personDTO.setId(1L);
		final Person friend1 = new Person();
		friend1.setId(2L);
		final Person friend2 = new Person();
		friend2.setId(3L);
		final FriendDTO friendDTO1 = new FriendDTO();
		friendDTO1.setId(2L);
		final FriendDTO friendDTO2 = new FriendDTO();
		friendDTO2.setId(3L);
		final FriendDTO friendDTOnull = null;
		final Set<FriendDTO> listFriendsDTO = new HashSet<>();
		listFriendsDTO.add(friendDTO1);
		listFriendsDTO.add(friendDTO2);
		listFriendsDTO.add(friendDTOnull);
		Mockito.when(manager.findById(1L)).thenReturn(person);
		Mockito.when(manager.findById(2L)).thenReturn(friend1);
		Mockito.when(manager.findById(3L)).thenReturn(friend2);
		Mockito.when(manager.relatePersons(Mockito.anyLong(), Mockito.any())).thenReturn(person);
		Mockito.when(dozerMapper.map(person, PersonDTO.class)).thenReturn(personDTO);
		//Act
		ResultActions perform = mockMvc.perform(post("/persons/1/relate").content(mapper.writeValueAsString(listFriendsDTO))
														.contentType(MediaType.APPLICATION_JSON)
														.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(personDTO)));
	}
	
	@Test
	public void testRelatePersonNotInDB() throws Exception {
		//Arrange
		final Person person = new Person();
		person.setId(1L);
		final FriendDTO friendDTO1 = new FriendDTO();
		final FriendDTO friendDTO2 = new FriendDTO();
		final Set<FriendDTO> listFriendsDTO = new HashSet<>();
		listFriendsDTO.add(friendDTO1);
		listFriendsDTO.add(friendDTO2);
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(post("/persons/1/relate").content(mapper.writeValueAsString(listFriendsDTO))
														.contentType(MediaType.APPLICATION_JSON)
														.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testRelateOneFriendNotInDb() throws Exception {
		//Arrange
		final Person person = new Person();
		person.setId(1L);
		final PersonDTO personDTO = new PersonDTO();
		personDTO.setId(1L);
		final Person friend1 = new Person();
		friend1.setId(2L);
		final FriendDTO friendDTO1 = new FriendDTO();
		friendDTO1.setId(2L);
		final FriendDTO friendDTO2 = new FriendDTO();
		friendDTO2.setId(3L);
		final Set<FriendDTO> listFriendsDTO = new HashSet<>();
		listFriendsDTO.add(friendDTO1);
		listFriendsDTO.add(friendDTO2);
		Mockito.when(manager.findById(1L)).thenReturn(person);
		Mockito.when(manager.findById(2L)).thenReturn(friend1);
		Mockito.when(manager.findById(3L)).thenReturn(null);
		Mockito.when(manager.relatePersons(Mockito.anyLong(), Mockito.any())).thenReturn(person);
		Mockito.when(dozerMapper.map(person, PersonDTO.class)).thenReturn(personDTO);
		//Act
		ResultActions perform = mockMvc.perform(post("/persons/1/relate").content(mapper.writeValueAsString(listFriendsDTO))
														.contentType(MediaType.APPLICATION_JSON)
														.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(personDTO)));
	}

}
