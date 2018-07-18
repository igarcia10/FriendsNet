package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

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
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.PersonManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.FriendDTO;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PersonControllerTest extends AbstractControllerTest<PersonDTO, Person, Long> {
	
	public PersonControllerTest() {
		super(Person.class, PersonDTO.class, "/persons");
	}
	
	@Override
	AbstractController<PersonDTO, Person, Long> getController() {
		return controller;
	}

	@Override
	AbstractManager<Person, Long> getManager() {
		return manager;
	}
	
	@InjectMocks
	private PersonController controller;
	
	@Mock
	private PersonManagerImpl manager;
	
	@Test
	public void testRelate() throws Exception {
		//Arrange
		final Person person = new Person();
		final PersonDTO personDTO = new PersonDTO();
		final Person friend1 = new Person();
		final Person friend2 = new Person();
		final FriendDTO friendDTO1 = new FriendDTO();
		final FriendDTO friendDTO2 = new FriendDTO();
		final FriendDTO friendDTOnull = null;
		final Set<FriendDTO> listFriendsDTO = new HashSet<>();
		listFriendsDTO.add(friendDTO1);
		listFriendsDTO.add(friendDTO2);
		listFriendsDTO.add(friendDTOnull);
		Mockito.when(manager.relatePersons(Mockito.anyLong(), Mockito.any())).thenReturn(person);
		Mockito.when(dozerMapper.map(person, PersonDTO.class)).thenReturn(personDTO);
		Mockito.when(dozerMapper.map(friendDTO1, Person.class)).thenReturn(friend1);
		Mockito.when(dozerMapper.map(friendDTO2, Person.class)).thenReturn(friend2);
		//Act
		ResultActions perform = mockMvc.perform(post("/persons/1/relate").content(mapper.writeValueAsString(listFriendsDTO))
														.contentType(MediaType.APPLICATION_JSON)
														.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(personDTO)));
	}

}
