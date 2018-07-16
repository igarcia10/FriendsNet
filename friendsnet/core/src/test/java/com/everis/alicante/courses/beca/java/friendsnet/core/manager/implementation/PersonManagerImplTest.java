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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@RunWith(MockitoJUnitRunner.class)
public class PersonManagerImplTest {

	@InjectMocks
	private PersonManagerImpl manager;

	@Mock
	private PersonDAO dao;

	@Test
	public void testFindAll() {
		// Arrange
		final Iterable<Person> iterable = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(iterable);
		// Act
		Iterable<Person> result = manager.findAll();
		// Assert
		Assert.assertEquals(iterable, result);
	}

	@Test
	public void testFindById() {
		// Arrange
		final Person person = new Person();
		Mockito.when(dao.findById(1L)).thenReturn(Optional.of(person));
		// Act
		Person resultPerson = manager.findById(1L);
		// Assert
		Assert.assertEquals(person, resultPerson);
	}

	@Test
	public void testSave() {
		// Arrange
		final Person person = new Person();
		person.setName("person1");
		Mockito.when(dao.save(Mockito.any())).thenReturn(person);
		Person resultPerson = new Person();
		// Act
		resultPerson = manager.save(person);
		// Assert
		Assert.assertEquals(person, resultPerson);
		Assert.assertEquals(person.getName(), resultPerson.getName());
	}

	@Test
	public void testSaveMultiple() {
		// Arrange
		final Person person1 = new Person();
		person1.setName("person1");
		final Person person2 = new Person();
		person2.setName("person2");
		final List<Person> persons = new ArrayList<>();
		persons.add(person1);
		persons.add(person2);
		Mockito.when(dao.saveAll(Mockito.any())).thenReturn(persons);
		List<Person> resultPersons = new ArrayList<>();
		// Act
		resultPersons = (List<Person>) manager.save(persons);
		// Assert
		Assert.assertEquals(persons, resultPersons);
		Assert.assertEquals(persons.get(0).getName(), resultPersons.get(0).getName());
	}

	@Test
	public void testUpdate() {
		// Arrange
		final Person person = new Person();
		final Person updatedPerson = new Person();
		updatedPerson.setName("updatedPerson1");
		Mockito.when(dao.save(Mockito.any())).thenReturn(updatedPerson);
		Person resultPerson = new Person();
		// Act
		resultPerson = manager.update(person);
		// Assert
		Assert.assertEquals(updatedPerson, resultPerson);
		Assert.assertEquals(updatedPerson.getName(), resultPerson.getName());
	}

	@Test
	public void testUpdateMultiple() {
		// Arrange
		final Person person1 = new Person();
		final Person person2 = new Person();
		final Person updatedPerson1 = new Person();
		updatedPerson1.setName("updatedPerson1");
		final Person updatedPerson2 = new Person();
		updatedPerson2.setName("updatedPerson2");
		final List<Person> persons = new ArrayList<>();
		persons.add(person1);
		persons.add(person2);
		final List<Person> updatedPersons = new ArrayList<>();
		updatedPersons.add(person1);
		updatedPersons.add(person2);
		Mockito.when(dao.saveAll(Mockito.any())).thenReturn(updatedPersons);
		List<Person> resultPersons = new ArrayList<>();
		// Act
		resultPersons = (List<Person>) manager.update(persons);
		// Assert
		Assert.assertEquals(updatedPersons, resultPersons);
		Assert.assertEquals(updatedPersons.get(0).getName(), resultPersons.get(0).getName());
	}

	@Test
	public void testRemove() {
		// Arrange
		final Person person = new Person();
		// Act
		manager.remove(person);
		// Assert
		Mockito.verify(dao).delete(person);
	}

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
		Person resultPerson = manager.relatePersons(1L, friends);
		// Assert
		Assert.assertEquals(person, resultPerson);
		Assert.assertEquals(person.getFriends().size(), resultPerson.getFriends().size());
		Mockito.verify(dao, Mockito.times(1)).save(person);
	}

}
