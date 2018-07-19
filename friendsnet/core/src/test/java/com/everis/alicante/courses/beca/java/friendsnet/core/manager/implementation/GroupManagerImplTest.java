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
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.GroupDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@RunWith(MockitoJUnitRunner.class)
public class GroupManagerImplTest extends AbstractManagerTest<Group, Long> {

	public GroupManagerImplTest() {
		super(Group.class);
	}

	@Override
	AbstractManager<Group, Long> getManager() {
		return manager;
	}

	@Override
	CrudRepository<Group, Long> getDAO() {
		return groupDAO;
	}

	@InjectMocks
	private GroupManagerImpl manager;

	@Mock
	private GroupDAO groupDAO;

	@Mock
	private PersonDAO personDAO;

	@Test
	public void testAddPersons() {
		// Arrange
		final Group group = new Group();
		final Person person1 = new Person();
		person1.setId(1L);
		person1.setName("name1");
		final Person person2 = new Person();
		person2.setId(2L);
		final List<Person> persons = new ArrayList<>();
		persons.add(person1);
		persons.add(person2);
		group.getPersons().add(person1);
		group.getPersons().add(person2);
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.of(group));
		Mockito.when(groupDAO.save(group)).thenReturn(group);
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.of(person1));
		Mockito.when(personDAO.findById(2L)).thenReturn(Optional.of(person2));
		Mockito.when(personDAO.save(person1)).thenReturn(person1);
		Mockito.when(personDAO.save(person2)).thenReturn(person2);
		// Act
		final Group resultGroup = manager.addPersons(1L, persons);
		// Assert
		Assert.assertEquals(group, resultGroup);
		Assert.assertEquals(2, resultGroup.getPersons().size());
	}

	@Test
	public void testAddPersonsWithNullList() {
		// Arrange
		final Group group = new Group();
		final List<Person> persons = null;
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.of(group));
		// Act
		final Group resultGroup = manager.addPersons(1L, persons);
		// Assert
		Assert.assertNull(resultGroup);
	}

	@Test
	public void testAddPersonsWithNullId() {
		// Arrange
		final List<Person> persons = null;
		// Act
		final Group resultGroup = manager.addPersons(null, persons);
		// Assert
		Assert.assertNull(resultGroup);
	}

	@Test
	public void testAddPersonsWithGroupNotInDB() {
		// Arrange
		final List<Person> persons = new ArrayList<>();
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final Group resultGroup = manager.addPersons(1L, persons);
		// Assert
		Assert.assertNull(resultGroup);
	}

	@Test
	public void testAddPersonsWithOnePersonNull() {
		// Arrange
		final Group group = new Group();
		final Person person1 = new Person();
		person1.setId(2L);
		final Person person2 = null;
		final Set<Person> persons = new HashSet<>();
		persons.add(person1);
		persons.add(person2);
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.of(group));
		Mockito.when(groupDAO.save(group)).thenReturn(group);
		Mockito.when(personDAO.findById(2L)).thenReturn(Optional.of(person1));
		// Act
		final Group resultGroup = manager.addPersons(1L, persons);
		// Assert
		Assert.assertEquals(1, resultGroup.getPersons().size());
	}

	@Test
	public void testAddPersonsWithOnePersonNotInDb() {
		// Arrange
		final Group group = new Group();
		final Person person1 = new Person();
		person1.setId(2L);
		final Person person2 = new Person();
		person2.setId(3L);
		final Set<Person> persons = new HashSet<>();
		persons.add(person1);
		persons.add(person2);
		Mockito.when(groupDAO.findById(1L)).thenReturn(Optional.of(group));
		Mockito.when(groupDAO.save(group)).thenReturn(group);
		Mockito.when(personDAO.findById(2L)).thenReturn(Optional.of(person1));
		Mockito.when(personDAO.findById(3L)).thenReturn(Optional.ofNullable(null));
		// Act
		final Group resultGroup = manager.addPersons(1L, persons);
		// Assert
		Assert.assertEquals(1, resultGroup.getPersons().size());
		Mockito.verify(groupDAO, Mockito.times(1)).save(group);
	}

	@Test
	public void testFindByPersonsId() {
		// Arrange
		final Person person = new Person();
		final Group group = new Group();
		final List<Group> groups = new ArrayList<>();
		groups.add(group);
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(person));
		Mockito.when(groupDAO.findByPersonsId(1L)).thenReturn(groups);
		// Act
		final List<Group> groupsResult = manager.findByPersonsId(1L);
		// Assert
		Assert.assertEquals(groups, groupsResult);
		Assert.assertEquals(1, groupsResult.size());
	}

	@Test
	public void testFindByPersonsIdNullPerson() {
		// Arrange
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final List<Group> groupsResult = manager.findByPersonsId(1L);
		// Assert
		Assert.assertNull(groupsResult);
	}

	@Test
	public void testCreateGroup() {
		// Arrange
		final Group group = new Group();
		final byte[] picture = new byte[10];
		final String name = "name";
		group.setName("name");
		group.setPicture(picture);
		Mockito.when(groupDAO.save(Mockito.any(Group.class))).thenReturn(group);
		// Act
		final Group resultGroup = manager.createGroup(name, picture);
		// Assert
		Assert.assertEquals(name, resultGroup.getName());
		Assert.assertEquals(picture, resultGroup.getPicture());
		Mockito.verify(groupDAO, Mockito.times(1)).save(Mockito.any(Group.class));
	}
	
	@Test
	public void testCreateGroupNull() {
		// Arrange
		final Group group = new Group();
		final byte[] picture = null;
		final String name = null;
		Mockito.when(groupDAO.save(Mockito.any(Group.class))).thenReturn(group);
		// Act
		final Group resultGroup = manager.createGroup(name, picture);
		// Assert
		Assert.assertEquals(name, resultGroup.getName());
		Assert.assertEquals(picture, resultGroup.getPicture());
		Assert.assertNotNull(resultGroup);
		Mockito.verify(groupDAO, Mockito.times(1)).save(Mockito.any(Group.class));
	}

}
