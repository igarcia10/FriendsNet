package com.everis.alicante.courses.beca.java.friendsnet.app;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseSetup;

public abstract class AbstractControllerIT<E, ID> {

	@LocalServerPort
	private int port;

	abstract CrudRepository<E, Long> getDAO();

	protected String URI;
	protected String findAllResult;

	public AbstractControllerIT(String URI, String findAllResult) {
		this.URI = URI;
		this.findAllResult = findAllResult;
	}

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private ObjectMapper mapper;

	@Before
	public void setup() {
		this.mapper = new ObjectMapper();
	}

	@After
	public void solveH2Shit() {
		this.getDAO().deleteAll();
	}

	@Test
	public void testFindAll() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI), HttpMethod.GET, null,
				String.class);
		// Assert
		JSONAssert.assertEquals("[]", response.getBody(), false);
	}

	@Test
	@DatabaseSetup("/db/person/init.xml")
	public void testFindAllWithContent() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI), HttpMethod.GET, null,
				String.class);

		// Assert
		JSONAssert.assertEquals(findAllResult, response.getBody(), false);
	}

	protected String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	protected HttpEntity<String> getPostRequest(final Object object) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			return new HttpEntity<>(mapper.writeValueAsString(object), headers);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
