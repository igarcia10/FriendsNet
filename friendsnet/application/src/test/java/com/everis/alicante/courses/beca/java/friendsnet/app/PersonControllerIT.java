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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class PersonControllerIT extends AbstractControllerIT<Person, Long> {

	public PersonControllerIT() {
		super("/persons", "[{'id': 1, 'name':'name1', 'surname':'surname1'}, {'id': 2, 'name':'name2', 'surname':'surname2'}]");
	}

	@Autowired
	private PersonDAO dao;

	@Override
	CrudRepository<Person, Long> getDAO() {
		return dao;
	}
	
	@Test
	@DatabaseSetup("/db/person/init.xml")
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
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI + "/1/relate"), HttpMethod.POST,
				this.getPostRequest(dtos), String.class);

		// Assert
		JSONAssert.assertEquals(
				"{'friends':[{'id': 2, 'name':'name2', 'surname':'surname2'}], 'id': 1, 'name':'name1', 'surname':'surname1'}",
				response.getBody(), JSONCompareMode.LENIENT);
	}

	@Test
	@DatabaseSetup("/db/person/init.xml")
	public void testRelatePersonNotInDb() throws JSONException {
		// Arrange
		List<PersonDTO> dtos = new ArrayList<>();
		PersonDTO dto = new PersonDTO();
		dtos.add(dto);
		dto.setId(3L);
		dto.setName("name3");
		dto.setSurname("surname3");
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI + "/1/relate"), HttpMethod.POST,
				this.getPostRequest(dtos), String.class);

		// Assert
		JSONAssert.assertEquals("{'id': 1, 'name':'name1', 'surname':'surname1'}", response.getBody(),
				JSONCompareMode.LENIENT);
	}

}
