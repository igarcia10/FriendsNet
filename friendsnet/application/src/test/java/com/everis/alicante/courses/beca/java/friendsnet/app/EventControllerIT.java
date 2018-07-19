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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.EventDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.EventDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class EventControllerIT extends AbstractControllerIT<EventDTO, Event, Long> {

	public EventControllerIT() {
		super("/events", "[{'id': 1, 'name':'event1'}, {'id': 2, 'name':'event2'}]", "{'id': 1, 'name':'event1'}",
				"{'id': 3, 'name':null}", EventDTO.class);
	}

	@Autowired
	private EventDAO dao;

	@Override
	CrudRepository<Event, Long> getDAO() {
		return dao;
	}

	@Test
	@DatabaseSetup("/db/init.xml")
	public void testAddPerson() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/events/1/person/1/add"),
				HttpMethod.POST, null, String.class);
		// Assert
		JSONAssert.assertEquals(
				"{'persons':[{'id': 1, 'name':'name1', 'surname':'surname1'}], 'id': 1, 'name':'event1'}",
				response.getBody(), JSONCompareMode.LENIENT);
	}

	@Test
	@DatabaseSetup("/db/init.xml")
	public void testAddPersonNull() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/events/1/person/3/add"),
				HttpMethod.POST, null, String.class);
		// Assert
		JSONAssert.assertEquals("{'id': 1, 'name':'event1'}", response.getBody(), JSONCompareMode.LENIENT);
	}

	@Test
	@DatabaseSetup("/db/init.xml")
	public void testFindByPersonId() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/events/person/2"), HttpMethod.GET,
				null, String.class);
		// Assert
		JSONAssert.assertEquals(
				"[{'persons':[{'id': 2, 'name':'name2', 'surname':'surname2'}], 'id': 2, 'name':'event2'}]",
				response.getBody(), JSONCompareMode.LENIENT);
	}

	@Test
	@DatabaseSetup("/db/init.xml")
	public void testFindByPersonIdNotInDb() throws JSONException {
		// Act
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/events/person/3"), HttpMethod.GET,
				null, String.class);
		// Assert
		JSONAssert.assertEquals("[]", response.getBody(), JSONCompareMode.LENIENT);
	}

	//TODO try with POSTman or with AdvancesRestClient
//	@Test
//	@DatabaseSetup("/db/init.xml")
//	public void testCreateEvent() throws JSONException {
//		// Arrange
//		final Long counter = dao.count();
//		final String name = "event3";
//		final Date startingDate = new Date();
//		final Date endingDate = new Date();
//		final EventDTO body = new EventDTO();
//		body.setName(name);
//		body.setStartingDate(startingDate);
//		body.setEndingDate(endingDate);
//		// Act
//		@SuppressWarnings("unused")
//		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/events/new/PARTY"), HttpMethod.POST,
//				this.getPostRequest(body), String.class);
//		// Assert
////		JSONAssert.assertEquals(
////				"{'id': 1, 'name':'event1', 'startingDate':'2018-07-19T15:51:58.678Z', 'endingDate':'2018-07-19T15:51:58.678Z', 'type': 'PARTY'}",
////				response.getBody(), JSONCompareMode.LENIENT);
//		Assert.assertEquals(counter+1, dao.count());
//	}

}
