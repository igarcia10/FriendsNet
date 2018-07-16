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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.GroupDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@RunWith(MockitoJUnitRunner.class)
public class GroupManagerImplTest {
	
	@InjectMocks
	private GroupManagerImpl manager;

	@Mock
	private GroupDAO groupDAO;
	
	@Mock
	private PersonDAO personDAO;

	@Test
	public void testFindAll() {
		// Arrange
		final Iterable<Group> iterable = new ArrayList<>();
		Mockito.when(groupDAO.findAll()).thenReturn(iterable);
		// Act
		Iterable<Group> result = manager.findAll();
		// Assert
		Assert.assertEquals(iterable, result);
	}

	@Test
	public void testFindById() {
		// Arrange
		final Group group = new Group();
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.of(group));
		// Act
		Group resultGroup = manager.findById(1L);
		// Assert
		Assert.assertEquals(group, resultGroup);
	}

	@Test
	public void testSave() {
		// Arrange
		final Group group = new Group();
		group.setName("group1");
		Mockito.when(groupDAO.save(Mockito.any())).thenReturn(group);
		Group resultGroup = new Group();
		// Act
		resultGroup = manager.save(group);
		// Assert
		Assert.assertEquals(group, resultGroup);
		Assert.assertEquals(group.getName(), resultGroup.getName());
	}

	@Test
	public void testSaveMultiple() {
		// Arrange
		final Group group1 = new Group();
		group1.setName("group1");
		final Group group2 = new Group();
		group2.setName("group2");
		final List<Group> groups = new ArrayList<>();
		groups.add(group1);
		groups.add(group2);
		Mockito.when(groupDAO.saveAll(Mockito.any())).thenReturn(groups);
		List<Group> resultGroups = new ArrayList<>();
		// Act
		resultGroups = (List<Group>) manager.save(groups);
		// Assert
		Assert.assertEquals(groups, resultGroups);
		Assert.assertEquals(groups.get(0).getName(), resultGroups.get(0).getName());
	}

	@Test
	public void testUpdate() {
		// Arrange
		final Group group = new Group();
		final Group updatedGroup = new Group();
		updatedGroup.setName("updatedGroup1");
		Mockito.when(groupDAO.save(Mockito.any())).thenReturn(updatedGroup);
		Group resultGroup = new Group();
		// Act
		resultGroup = manager.update(group);
		// Assert
		Assert.assertEquals(updatedGroup, resultGroup);
		Assert.assertEquals(updatedGroup.getName(), resultGroup.getName());
	}

	@Test
	public void testUpdateMultiple() {
		// Arrange
		final Group group1 = new Group();
		final Group group2 = new Group();
		final Group updatedGroup1 = new Group();
		updatedGroup1.setName("updatedGroup1");
		final Group updatedGroup2 = new Group();
		updatedGroup2.setName("updatedGroup2");
		final List<Group> groups = new ArrayList<>();
		groups.add(group1);
		groups.add(group2);
		final List<Group> updatedGroups = new ArrayList<>();
		updatedGroups.add(group1);
		updatedGroups.add(group2);
		Mockito.when(groupDAO.saveAll(Mockito.any())).thenReturn(updatedGroups);
		List<Group> resultGroups = new ArrayList<>();
		// Act
		resultGroups = (List<Group>) manager.update(groups);
		// Assert
		Assert.assertEquals(updatedGroups, resultGroups);
		Assert.assertEquals(updatedGroups.get(0).getName(), resultGroups.get(0).getName());
	}

	@Test
	public void testRemove() {
		// Arrange
		final Group group = new Group();
		// Act
		manager.remove(group);
		// Assert
		Mockito.verify(groupDAO).delete(group);
	}
	
	@Test
	public void testAddPersons() {
		//Arrange
		Group group = new Group();
		Person person1 = new Person();
		person1.setName("name");
		Person person2 = new Person();
		List<Person> persons = new ArrayList<>();
		persons.add(person1);
		persons.add(person2);
		group.getPersons().add(person1);
		group.getPersons().add(person2);
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.of(group));
		Mockito.when(groupDAO.save(group)).thenReturn(group);
		Mockito.when(personDAO.findById(Mockito.any())).thenReturn(Optional.of(person1));
		Mockito.when(personDAO.save(Mockito.any())).thenReturn(person1);
		//Act
		Group resultGroup = manager.addPersons(1L, persons);
		//Assert
		Assert.assertEquals(group, resultGroup);
		Assert.assertEquals(2, resultGroup.getPersons().size());
	}
	
	@Test
	public void testAddPersonsWithNullList() {
		//Arrange
		Group group = new Group();
		List<Person> persons = null;
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.of(group));
		//Act
		Group resultGroup = manager.addPersons(1L, persons);
		//Assert
		Assert.assertNull(resultGroup);
	}
	
	@Test
	public void testAddPersonsWithGroupNotInDB() {
		//Arrange
		List<Person> persons = new ArrayList<>();
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		//Act
		Group resultGroup = manager.addPersons(1L, persons);
		//Assert
		Assert.assertNull(resultGroup);
	}
	
	@Test
	public void testAddPersonsWithPersonNotInDB() {
		//Arrange
				Group group = new Group();
				Person person1 = new Person();
				person1.setId(2L);
				Person person2 = null;
				Set<Person> persons = new HashSet<>();
				persons.add(person1);
				persons.add(person2);
				Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.of(group));
				Mockito.when(groupDAO.save(group)).thenReturn(group);
				Mockito.when(personDAO.findById(2L)).thenReturn(Optional.ofNullable(null));
				//Act
				Group resultGroup = manager.addPersons(1L, persons);
				//Assert
				Assert.assertEquals(0, resultGroup.getPersons().size());
	}

}
