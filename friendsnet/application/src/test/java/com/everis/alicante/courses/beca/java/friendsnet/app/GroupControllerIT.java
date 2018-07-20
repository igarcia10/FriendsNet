package com.everis.alicante.courses.beca.java.friendsnet.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.GroupDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.GroupDTO;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class GroupControllerIT extends AbstractControllerIT<GroupDTO, Group, Long> {

	public GroupControllerIT() {
		super("/groups", "[{'id': 1, 'name':'group1'}, {'id': 2, 'name':'group2'}]", "{'id': 1, 'name':'group1'}",
				"{'id': 3, 'name':null}", GroupDTO.class);
	}

	@Override
	CrudRepository<Group, Long> getDAO() {
		return dao;
	}

	@Autowired
	private GroupDAO dao;
	
	@Test
	@DatabaseSetup("/db/init.xml")
	public void testFindByPersonId() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/groups/person/2"), HttpMethod.GET,
				null, String.class);
		// Assert
		JSONAssert.assertEquals(
				"[{'persons':[{'id': 2, 'name':'name2', 'surname':'surname2'}], 'id': 2, 'name':'group2'}]",
				response.getBody(), JSONCompareMode.LENIENT);
	}

	@Test
	@DatabaseSetup("/db/init.xml")
	public void testFindByPersonIdNotInDb() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/groups/person/3"), HttpMethod.GET,
				null, String.class);
		// Assert
		JSONAssert.assertEquals("[]", response.getBody(), JSONCompareMode.LENIENT);
	}
	
	@Test
	@DatabaseSetup("/db/init.xml")
	public void testRelate() throws JSONException {
		// Arrange
		List<PersonDTO> dtos = new ArrayList<>();
		PersonDTO dto = new PersonDTO();
		PersonDTO nullDto = null;
		dtos.add(dto);
		dtos.add(nullDto);
		dto.setId(2L);
		dto.setName("name2");
		dto.setSurname("surname2");
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/groups/1/relate"), HttpMethod.POST,
				this.getPostRequest(dtos), String.class);

		// Assert
		JSONAssert.assertEquals(
				"{'persons':[{'id': 2, 'name':'name2', 'surname':'surname2'}], 'id': 1, 'name':'group1'}",
				response.getBody(), JSONCompareMode.LENIENT);
	}
	
	@Test
	@DatabaseSetup("/db/init.xml")
	public void testRelateNull() throws JSONException {
		// Arrange
		List<PersonDTO> dtos = new ArrayList<>();
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/groups/1/relate"), HttpMethod.POST,
				this.getPostRequest(dtos), String.class);

		// Assert
		JSONAssert.assertEquals("{'id': 1, 'name':'group1'}", response.getBody(),
				JSONCompareMode.LENIENT);
	}

}
