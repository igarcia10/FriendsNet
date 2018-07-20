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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.GroupManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.GroupDTO;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class GroupControllerTest extends AbstractControllerTest<GroupDTO, Group, Long> {
	
	public GroupControllerTest() {
		super(Group.class, GroupDTO.class, "/groups");
	}
	
	@Override
	AbstractController<GroupDTO, Group, Long> getController() {
		return controller;
	}

	@Override
	AbstractManager<Group, Long> getManager() {
		return manager;
	}

	@InjectMocks
	private GroupController controller;

	@Mock
	private GroupManagerImpl manager;

	@Test
	public void testFindByPersonsId() throws Exception {
		// Arrange
		final Group group = new Group();
		final List<Group> groups = new ArrayList<>();
		groups.add(group);
		final GroupDTO groupDTO = new GroupDTO();
		final List<GroupDTO> listDTO = new ArrayList<>();
		listDTO.add(groupDTO);
		Mockito.when(manager.findByPersonsId(1L)).thenReturn(groups);
		Mockito.when(dozerMapper.map(Mockito.any(), Mockito.any())).thenReturn(groupDTO);
		// Act
		final ResultActions perform = mockMvc.perform(get("/groups/person/1"));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(listDTO)));
	}

	@Test
	public void testFindByPersonsIdNull() throws Exception {
		// Arrange
		Mockito.when(manager.findByPersonsId(1L)).thenReturn(null);
		// Act
		final ResultActions perform = mockMvc.perform(get("/groups/person/1"));
		// Assert
		perform.andExpect(status().isOk());
	}

	@Test
	public void testAddPersons() throws Exception {
		// Arrange
		final GroupDTO groupDTO = new GroupDTO();
		final Group group = new Group();
		final Person person = new Person();
		final PersonDTO personDTO = new PersonDTO();
		final PersonDTO personDTOnull = null;
		final List<Person> personList = new ArrayList<>();
		personList.add(person);
		final List<PersonDTO> personDTOlist = new ArrayList<>();
		personDTOlist.add(personDTO);
		personDTOlist.add(personDTOnull);
		Mockito.when(dozerMapper.map(personDTO, Person.class)).thenReturn(person);
		Mockito.when(dozerMapper.map(group, GroupDTO.class)).thenReturn(groupDTO);
		Mockito.when(manager.addPersons(Mockito.anyLong(), Mockito.any())).thenReturn(group);
		// Act
		final ResultActions perform = mockMvc.perform(post("/groups/1/relate").content(mapper.writeValueAsString(personDTOlist))
																				.contentType(MediaType.APPLICATION_JSON)
																				.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(groupDTO)));
	}
	
	@Test
	public void testAddPersonsNull() throws Exception {
		// Arrange
		final GroupDTO groupDTO = new GroupDTO();
		final Group group = new Group();
		final Person person = new Person();
		final PersonDTO personDTO = new PersonDTO();
		final PersonDTO personDTOnull = null;
		final List<Person> personList = new ArrayList<>();
		personList.add(person);
		final List<PersonDTO> personDTOlist = new ArrayList<>();
		personDTOlist.add(personDTO);
		personDTOlist.add(personDTOnull);
		Mockito.when(dozerMapper.map(personDTO, Person.class)).thenReturn(person);
		Mockito.when(dozerMapper.map(group, GroupDTO.class)).thenReturn(groupDTO);
		Mockito.when(manager.addPersons(Mockito.anyLong(), Mockito.any())).thenReturn(group);
		// Act
		final ResultActions perform = mockMvc.perform(post("/groups/1/relate").content(mapper.writeValueAsString(personDTOlist))
																				.contentType(MediaType.APPLICATION_JSON)
																				.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(groupDTO)));
	}

}
