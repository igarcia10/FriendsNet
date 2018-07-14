package com.everis.alicante.courses.beca.java.friendsnet.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class EventDAOTest {
	
	@Autowired
	private EventDAO dao;

	@Test
	@DatabaseSetup("/db/event/init.xml")
	public void testFindAll() {
		//Act
		List<Event> events = (List<Event>) dao.findAll();
		//Assert
		Assert.assertEquals(2, events.size());
	}
	
	@Test
	@DatabaseSetup("/db/event/init.xml")
	public void testFindById() {
		//Act
		Event event = dao.findById(1L).get();
		//Assert
		Assert.assertEquals("name1", event.getName());
	}
	
	@Test
	@DatabaseSetup("/db/event/init.xml")
	@ExpectedDatabase(value = "/db/event/afterSavingEvent.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testSave() {
		//Arrange
		Event eventDB = new Event();
		eventDB.setName("name3");
		//Act
		Event event = dao.save(eventDB);
		//Assert
		Assert.assertEquals(eventDB, event);
	}
	
	@Test
	@DatabaseSetup("/db/event/init.xml")
	@ExpectedDatabase(value = "/db/event/afterSavingMultipleEvents.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testSaveAll() {
		//Arrange
		Event eventDB1 = new Event();
		eventDB1.setName("name4");
		Event eventDB2 = new Event();
		eventDB2.setName("name5");
		List<Event> eventsDB = new ArrayList<>();
		eventsDB.add(eventDB1);
		eventsDB.add(eventDB2);
		//Act
		List<Event> events = (List<Event>) dao.saveAll(eventsDB);
		//Assert
		Assert.assertEquals(events.get(0).getName(), eventsDB.get(0).getName());
	}
	
	@Test
	@DatabaseSetup("/db/event/init.xml")
	@ExpectedDatabase(value = "/db/event/afterUpdatingEvent.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testUpdate() {
		//Arrange
		Event eventDB = new Event();
		eventDB.setId(1L);
		eventDB.setName("updatedName1");
		//Act
		Event event = dao.save(eventDB);
		//Assert
		Assert.assertEquals(eventDB.getName(), event.getName());
	}
	
	@Test
	@DatabaseSetup("/db/event/init.xml")
	@ExpectedDatabase(value = "/db/event/afterUpdatingMultipleEvents.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testUpdateAll() {
		//Arrange
		Event eventDB1 = new Event();
		eventDB1.setId(1L);
		eventDB1.setName("updatedName1");
		Event eventDB2 = new Event();
		eventDB2.setId(2L);
		eventDB2.setName("updatedName2");
		List<Event> eventsDB = new ArrayList<>();
		eventsDB.add(eventDB1);
		eventsDB.add(eventDB2);
		//Act
		List<Event> events = (List<Event>) dao.saveAll(eventsDB);
		//Assert
		Assert.assertEquals(events.get(0).getName(), eventsDB.get(0).getName());
	}
	
	@Test
	@DatabaseSetup("/db/event/init.xml")
	@ExpectedDatabase(value = "/db/event/afterDeletingEvent.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testDelete() {
		//Act
		dao.delete(dao.findById(1L).get());
	}

}
