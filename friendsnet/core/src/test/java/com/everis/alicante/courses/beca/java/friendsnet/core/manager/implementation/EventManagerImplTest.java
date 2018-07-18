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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.EventDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@RunWith(MockitoJUnitRunner.class)
public class EventManagerImplTest {

	@InjectMocks
	private EventManagerImpl manager;

	@Mock
	private EventDAO eventDAO;

	@Mock
	private PersonDAO personDAO;

	@Test
	public void testFindAll() {
		// Arrange
		final Iterable<Event> iterable = new ArrayList<>();
		Mockito.when(eventDAO.findAll()).thenReturn(iterable);
		// Act
		final Iterable<Event> result = manager.findAll();
		// Assert
		Assert.assertEquals(iterable, result);
	}

	@Test
	public void testFindAllNull() {
		// Arrange
		Mockito.when(eventDAO.findAll()).thenReturn(null);
		// Act
		final Iterable<Event> result = manager.findAll();
		// Assert
		Assert.assertNull(result);
	}

	@Test
	public void testFindById() {
		// Arrange
		final Event event = new Event();
		Mockito.when(eventDAO.findById(1L)).thenReturn(Optional.of(event));
		// Act
		final Event resultEvent = manager.findById(1L);
		// Assert
		Assert.assertEquals(event, resultEvent);
	}

	@Test
	public void testFindByIdNull() {
		// Arrange
		Mockito.when(eventDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final Event event = manager.findById(1L);
		// Assert
		Assert.assertNull(event);
	}

	@Test
	public void testSave() {
		// Arrange
		final Event event = new Event();
		event.setName("event1");
		Mockito.when(eventDAO.save(event)).thenReturn(event);
		// Act
		final Event resultEvent = manager.save(event);
		// Assert
		Assert.assertEquals(event, resultEvent);
		Assert.assertEquals(event.getName(), resultEvent.getName());
		Mockito.verify(eventDAO, Mockito.times(1)).save(event);
	}

	@Test
	public void testSaveMultiple() {
		// Arrange
		final Event event1 = new Event();
		event1.setName("event1");
		final Event event2 = new Event();
		event2.setName("event2");
		final List<Event> events = new ArrayList<>();
		events.add(event1);
		events.add(event2);
		Mockito.when(eventDAO.saveAll(events)).thenReturn(events);
		// Act
		final List<Event> resultEvents = (List<Event>) manager.save(events);
		// Assert
		Assert.assertEquals(events, resultEvents);
		Assert.assertEquals(events.get(0).getName(), resultEvents.get(0).getName());
		Mockito.verify(eventDAO, Mockito.times(1)).saveAll(events);
	}

	@Test
	public void testUpdate() {
		// Arrange
		final Event event = new Event();
		final Event updatedEvent = new Event();
		updatedEvent.setName("updatedEvent1");
		Mockito.when(eventDAO.save(event)).thenReturn(updatedEvent);
		// Act
		final Event resultEvent = manager.update(event);
		// Assert
		Assert.assertEquals(updatedEvent, resultEvent);
		Assert.assertEquals(updatedEvent.getName(), resultEvent.getName());
	}

	@Test
	public void testUpdateMultiple() {
		// Arrange
		final Event event1 = new Event();
		final Event event2 = new Event();
		final Event updatedEvent1 = new Event();
		updatedEvent1.setName("updatedEvent1");
		final Event updatedEvent2 = new Event();
		updatedEvent2.setName("updatedEvent2");
		final List<Event> events = new ArrayList<>();
		events.add(event1);
		events.add(event2);
		final List<Event> updatedEvents = new ArrayList<>();
		updatedEvents.add(event1);
		updatedEvents.add(event2);
		Mockito.when(eventDAO.saveAll(events)).thenReturn(updatedEvents);
		// Act
		final List<Event> resultEvents = (List<Event>) manager.update(events);
		// Assert
		Assert.assertEquals(updatedEvents, resultEvents);
		Assert.assertEquals(updatedEvents.get(0).getName(), resultEvents.get(0).getName());
	}

	@Test
	public void testRemove() {
		// Arrange
		final Event event = new Event();
		// Act
		manager.remove(event);
		// Assert
		Mockito.verify(eventDAO).delete(event);
	}

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
