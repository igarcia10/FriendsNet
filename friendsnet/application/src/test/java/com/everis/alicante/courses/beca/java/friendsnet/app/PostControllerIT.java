package com.everis.alicante.courses.beca.java.friendsnet.app;

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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PostDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PostDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class PostControllerIT extends AbstractControllerIT<PostDTO, Post, Long> {

	public PostControllerIT() {
		super("/posts", "[{'id': 1, 'text':'text1'}, {'id': 2, 'text':'text2'}]", "{'id': 1, 'text':'text1'}",
				"{'id': 3, 'text':null}", PostDTO.class);
	}

	@Override
	CrudRepository<Post, Long> getDAO() {
		return dao;
	}

	@Autowired
	private PostDAO dao;
	
	@Test
	@DatabaseSetup("/db/init.xml")
	public void testFindByPersonId() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/posts/person/2"), HttpMethod.GET,
				null, String.class);
		// Assert
		JSONAssert.assertEquals(
				"[{'person':{'id': 2, 'name':'name2', 'surname':'surname2'}, 'id': 2, 'text':'text2'}]",
				response.getBody(), JSONCompareMode.LENIENT);
	}

	@Test
	@DatabaseSetup("/db/init.xml")
	public void testFindByPersonIdNotInDb() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/posts/person/3"), HttpMethod.GET,
				null, String.class);
		// Assert
		JSONAssert.assertEquals("[]", response.getBody(), JSONCompareMode.LENIENT);
	}
	
	//TODO problem with creationDate
	
	@Test
	@DatabaseSetup("/db/init.xml")
	public void testAddLike() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/posts/1/person/1/like/COOL"),
				HttpMethod.POST, null, String.class);
		// Assert
		JSONAssert.assertEquals(
				"{'likes':[{'id': 1, 'type': 'COOL'}], 'id': 1, 'text':'text1'}",
				response.getBody(), JSONCompareMode.LENIENT);
	}
	
	@Test
	@DatabaseSetup("/db/init.xml")
	public void testAddLikePersonNotInDb() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/posts/1/person/3/like/COOL"),
				HttpMethod.POST, null, String.class);
		// Assert
		JSONAssert.assertEquals(
				"{}",
				response.getBody(), JSONCompareMode.LENIENT);
	}
	
	@Test
	@DatabaseSetup("/db/init.xml")
	public void testAddLikePostNotInDb() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/posts/3/person/1/like/COOL"),
				HttpMethod.POST, null, String.class);
		// Assert
		JSONAssert.assertEquals(
				"{}",
				response.getBody(), JSONCompareMode.LENIENT);
	}

}
