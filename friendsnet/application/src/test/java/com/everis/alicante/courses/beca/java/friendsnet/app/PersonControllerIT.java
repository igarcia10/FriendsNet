package com.everis.alicante.courses.beca.java.friendsnet.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class PersonControllerIT {

	@LocalServerPort
	private int port;

	@Autowired
	private PersonDAO dao;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private ObjectMapper mapper;

	@Before
	public void setup() {
		this.mapper = new ObjectMapper();
	}

	@After
	public void solveH2Shit() {
		dao.deleteAll();
	}

	@Test
	public void testFindAll() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/persons"), HttpMethod.GET, null,
				String.class);
		// Assert
		JSONAssert.assertEquals("[]", response.getBody(), false);
	}

	@Test
	@DatabaseSetup("/db/person/init.xml")
	public void testFindAllWithContent() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/persons"), HttpMethod.GET, null,
				String.class);

		// Assert
		JSONAssert.assertEquals(
				"[{'id': 1, 'name':'name1', 'surname':'surname1'}, {'id': 2, 'name':'name2', 'surname':'surname2'}]",
				response.getBody(), false);
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
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/persons/1/relate"), HttpMethod.POST,
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
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/persons/1/relate"), HttpMethod.POST,
				this.getPostRequest(dtos), String.class);

		// Assert
		JSONAssert.assertEquals("{'id': 1, 'name':'name1', 'surname':'surname1'}", response.getBody(),
				JSONCompareMode.LENIENT);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private HttpEntity<String> getPostRequest(final Object object) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			return new HttpEntity<>(mapper.writeValueAsString(object), headers);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
