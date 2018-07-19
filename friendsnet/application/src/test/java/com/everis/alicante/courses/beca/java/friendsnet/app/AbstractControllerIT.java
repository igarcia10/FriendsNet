package com.everis.alicante.courses.beca.java.friendsnet.app;

import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.FNEntity;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.DTOEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseSetup;


public abstract class AbstractControllerIT<DTO extends DTOEntity, E extends FNEntity, ID> {

	@LocalServerPort
	private int port;

	abstract CrudRepository<E, Long> getDAO();

	private String URI;
	private String findAllResult;
	private String findByIdResult;
	private String afterCreateResult;
	private Class<DTO> dtoClass;

	public AbstractControllerIT(String URI, String findAllResult, String findByIdResult, String afterCreateResult, Class<DTO> dtoClass) {
		this.URI = URI;
		this.findAllResult = findAllResult;
		this.findByIdResult = findByIdResult;
		this.afterCreateResult = afterCreateResult;
		this.dtoClass = dtoClass;
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
	@DatabaseSetup("/db/abstract/init.xml")
	public void testFindAllWithContent() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI), HttpMethod.GET, null,
				String.class);

		// Assert
		JSONAssert.assertEquals(findAllResult, response.getBody(), false);
	}

	@Test
	@DatabaseSetup("/db/abstract/init.xml")
	public void testFindById() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI + "/1"), HttpMethod.GET, null,
				String.class);

		// Assert
		JSONAssert.assertEquals(findByIdResult, response.getBody(), false);
	}

	@Test
	@DatabaseSetup("/db/abstract/init.xml")
	public void testFindByIdNotInDb() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI + "/3"), HttpMethod.GET, null,
				String.class);

		// Assert
		JSONAssert.assertEquals(null, response.getBody(), false);
	}

	@Test
	@DatabaseSetup("/db/abstract/init.xml")
	public void testCreate() throws Exception {
		//Arrange
		DTO dto = dtoClass.newInstance();
		final Long counter = this.getDAO().count();
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI), HttpMethod.POST, this.getPostRequest(dto),
				String.class);
		// Assert
		JSONAssert.assertEquals(afterCreateResult, response.getBody(), false);
		Assert.assertEquals(counter+1, this.getDAO().count());
	}
	
	@Test
	@DatabaseSetup("/db/abstract/init.xml")
	public void testCreateNull() throws Exception {
		//Arrange
		DTO dto = null;
		final Long counter = this.getDAO().count();
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI), HttpMethod.POST, this.getPostRequest(dto),
				String.class);
		// Assert
		JSONAssert.assertEquals("{}", response.getBody(), false);
		Assert.assertEquals(counter.longValue(), this.getDAO().count());
	}
	
	@Test
	@DatabaseSetup("/db/abstract/init.xml")
	public void testRemove() throws JSONException {
		//Arrange
		final Long counter = this.getDAO().count();
		//Act
		@SuppressWarnings("unused")
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI+"/2"), HttpMethod.DELETE, null, String.class);
		//Assert
		Assert.assertEquals(counter-1, this.getDAO().count());
	}
	
	@Test
	@DatabaseSetup("/db/abstract/init.xml")
	public void testRemoveNotInDb() throws JSONException {
		//Arrange
		final Long counter = this.getDAO().count();
		//Act
		@SuppressWarnings("unused")
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(URI+"/3"), HttpMethod.DELETE, null, String.class);
		//Assert
		Assert.assertEquals(counter.longValue(), this.getDAO().count());
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
