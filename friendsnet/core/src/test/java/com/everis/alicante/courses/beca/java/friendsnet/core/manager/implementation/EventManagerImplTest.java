package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.EventDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@RunWith(MockitoJUnitRunner.class)
public class EventManagerImplTest extends AbstractManagerTest<Event, Long> {

	public EventManagerImplTest() {
		super(Event.class);
	}
	
	@Override
	AbstractManager<Event, Long> getManager() {
		return manager;
	}

	@Override
	CrudRepository<Event, Long> getDAO() {
		return eventDAO;
	}

	@InjectMocks
	private EventManagerImpl manager;

	@Mock
	private EventDAO eventDAO;

	@Mock
	private PersonDAO personDAO;

	@Test
	public void testFindByPersonsId() {
		// Arrange
		final Person person = new Person();
		final Event event = new Event();
		final List<Event> events = new ArrayList<>();
		events.add(event);
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(person));
		Mockito.when(eventDAO.findByPersonsId(1L)).thenReturn(events);
		// Act
		final List<Event> resultEvents = manager.findByPersonsId(1L);
		// Assert
		Assert.assertEquals(events, resultEvents);
		Assert.assertEquals(1, resultEvents.size());
	}

	@Test
	public void testFindByPersonsIdNullPerson() {
		// Arrange
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final List<Event> resultEvents = manager.findByPersonsId(1L);
		// Assert
		Assert.assertNull(resultEvents);
	}

	@Test
	public void testAddPerson() {
		// Arrange
		final Person person = new Person();
		final Event event = new Event();
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(person));
		Mockito.when(eventDAO.findById(1L)).thenReturn(Optional.ofNullable(event));
		Mockito.when(personDAO.save(person)).thenReturn(person);
		Mockito.when(eventDAO.save(event)).thenReturn(event);
		//Act
		final Event eventResult = manager.addPerson(1L, 1L);
		//Assert
		Assert.assertEquals(event, eventResult);
		Assert.assertEquals(1, eventResult.getPersons().size());
	}
	
	@Test
	public void testAddPersonNullEvent() {
		// Arrange
		Mockito.when(eventDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		//Act
		final Event eventResult = manager.addPerson(1L, 1L);
		//Assert
		Assert.assertNull(eventResult);
	}
	
	@Test
	public void testAddPersonNullPerson() {
		// Arrange
		final Event event = new Event();
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		Mockito.when(eventDAO.findById(1L)).thenReturn(Optional.ofNullable(event));
		Mockito.when(eventDAO.save(event)).thenReturn(event);
		//Act
		final Event eventResult = manager.addPerson(1L, 1L);
		//Assert
		Assert.assertEquals(event, eventResult);
		Assert.assertEquals(0, eventResult.getPersons().size());
	}

}
