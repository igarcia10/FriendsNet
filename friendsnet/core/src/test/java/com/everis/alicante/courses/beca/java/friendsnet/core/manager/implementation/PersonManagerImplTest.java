package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@RunWith(MockitoJUnitRunner.class)
public class PersonManagerImplTest extends AbstractManagerTest<Person, Long> {

	public PersonManagerImplTest() {
		super(Person.class);
	}

	@Override
	AbstractManager<Person, Long> getManager() {
		return manager;
	}

	@Override
	CrudRepository<Person, Long> getDAO() {
		return dao;
	}

	@InjectMocks
	private PersonManagerImpl manager;

	@Mock
	private PersonDAO dao;

	@Test
	public void testRelatePersons() {
		// Arrange
		final Person person = new Person();
		person.setId(1L);
		final Person friend2 = new Person();
		friend2.setId(2L);
		final Person friend3 = new Person();
		friend3.setId(3L);
		final Set<Person> friends = new HashSet<>();
		friends.add(friend2);
		friends.add(friend3);
		Mockito.when(dao.findById(1L)).thenReturn(Optional.of(person));
		Mockito.when(dao.findById(2L)).thenReturn(Optional.of(friend2));
		Mockito.when(dao.findById(3L)).thenReturn(Optional.of(friend3));
		Mockito.when(dao.save(person)).thenReturn(person);
		// Act
		final Person resultPerson = manager.relatePersons(1L, friends);
		// Assert
		Assert.assertEquals(person, resultPerson);
		Assert.assertEquals(person.getFriends().size(), resultPerson.getFriends().size());
		Mockito.verify(dao, Mockito.times(1)).save(person);
	}
	
	@Test
	public void testRelatePersonsNullId() {
		final Person friend2 = new Person();
		final Person friend3 = new Person();
		final Set<Person> friends = new HashSet<>();
		friends.add(friend2);
		friends.add(friend3);
		// Act
		final Person resultPerson = manager.relatePersons(1L, friends);
		// Assert
		Assert.assertNull(resultPerson);
	}
	
	@Test
	public void testRelatePersonsNullFriends() {
		// Arrange
		final Person person = new Person();
		Mockito.when(dao.findById(1L)).thenReturn(Optional.of(person));
		// Act
		final Person resultPerson = manager.relatePersons(1L, null);
		// Assert
		Assert.assertNull(resultPerson);
	}
	
	@Test
	public void testRelatePersonsOneFriendNull() {
		// Arrange
		final Person person = new Person();
		person.setId(1L);
		final Person friend2 = new Person();
		friend2.setId(2L);
		final Person friend3 = null;
		final Set<Person> friends = new HashSet<>();
		friends.add(friend2);
		friends.add(friend3);
		Mockito.when(dao.findById(1L)).thenReturn(Optional.of(person));
		Mockito.when(dao.findById(2L)).thenReturn(Optional.of(friend2));
		Mockito.when(dao.save(person)).thenReturn(person);
		// Act
		final Person resultPerson = manager.relatePersons(1L, friends);
		// Assert
		Assert.assertEquals(1, resultPerson.getFriends().size());
		Mockito.verify(dao, Mockito.times(1)).save(person);
	}
	
	@Test
	public void testRelatePersonsPersonNotInDb() {
		// Arrange
		final List<Person> friends = new ArrayList<>();
		Mockito.when(dao.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final Person resultPerson = manager.relatePersons(1L, friends);
		// Assert
		Assert.assertNull(resultPerson);
	}
	
	@Test
	public void testRelatePersonsOneFriendNotInDb() {
		// Arrange
		final Person person = new Person();
		person.setId(1L);
		final Person friend2 = new Person();
		friend2.setId(2L);
		final Person friend3 = new Person();
		friend3.setId(3L);
		final Set<Person> friends = new HashSet<>();
		friends.add(friend2);
		friends.add(friend3);
		Mockito.when(dao.findById(1L)).thenReturn(Optional.of(person));
		Mockito.when(dao.findById(2L)).thenReturn(Optional.of(friend2));
		Mockito.when(dao.findById(3L)).thenReturn(Optional.ofNullable(null));
		Mockito.when(dao.save(person)).thenReturn(person);
		// Act
		final Person resultPerson = manager.relatePersons(1L, friends);
		// Assert
		Assert.assertEquals(1, resultPerson.getFriends().size());
		Mockito.verify(dao, Mockito.times(1)).save(person);
	}

}
