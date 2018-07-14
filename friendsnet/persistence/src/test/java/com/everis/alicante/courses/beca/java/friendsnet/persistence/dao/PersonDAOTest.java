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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class PersonDAOTest {

	@Autowired
	private PersonDAO dao;

	@Test
	@DatabaseSetup("/db/person/init.xml")
	public void testFindAll() {
		//Act
		List<Person> persons = (List<Person>) dao.findAll();
		//Assert
		Assert.assertEquals(2, persons.size());
	}
	
	@Test
	@DatabaseSetup("/db/person/init.xml")
	public void testFindById() {
		//Act
		Person person = dao.findById(1L).get();
		//Assert
		Assert.assertEquals("name1", person.getName());
		Assert.assertEquals("surname1", person.getSurname());
	}
	
	@Test
	@DatabaseSetup("/db/person/init.xml")
	@ExpectedDatabase(value = "/db/person/afterSavingPerson.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testSave() {
		//Arrange
		Person personDB = new Person();
		personDB.setName("name3");
		personDB.setSurname("surname3");
		//Act
		Person person = dao.save(personDB);
		//Assert
		Assert.assertEquals(personDB.getName(), person.getName());
	}
	
	@Test
	@DatabaseSetup("/db/person/init.xml")
	@ExpectedDatabase(value = "/db/person/afterSavingMultiplePersons.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testSaveAll() {
		//Arrange
		Person personDB1 = new Person();
		personDB1.setName("name4");
		personDB1.setSurname("surname4");
		Person personDB2 = new Person();
		personDB2.setName("name5");
		personDB2.setSurname("surname5");
		List<Person> personsDB = new ArrayList<>();
		personsDB.add(personDB1);
		personsDB.add(personDB2);
		//Act
		List<Person> persons = (List<Person>) dao.saveAll(personsDB);
		//Assert
		Assert.assertEquals(persons.get(0).getName(), personsDB.get(0).getName());
	}
	
	@Test
	@DatabaseSetup("/db/person/init.xml")
	@ExpectedDatabase(value = "/db/person/afterUpdatingPerson.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testUpdate() {
		//Arrange
		Person personDB = new Person();
		personDB.setId(1L);
		personDB.setName("updatedName1");
		personDB.setSurname("surname1");
		//Act
		Person person = dao.save(personDB);
		//Assert
		Assert.assertEquals(personDB.getName(), person.getName());
	}
	
	@Test
	@DatabaseSetup("/db/person/init.xml")
	@ExpectedDatabase(value = "/db/person/afterUpdatingMultiplePersons.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testUpdateAll() {
		//Arrange
		Person personDB1 = new Person();
		personDB1.setId(1L);
		personDB1.setName("updatedName1");
		personDB1.setSurname("surname1");
		Person personDB2 = new Person();
		personDB2.setId(2L);
		personDB2.setName("updatedName2");
		personDB2.setSurname("surname2");
		List<Person> personsDB = new ArrayList<>();
		personsDB.add(personDB1);
		personsDB.add(personDB2);
		//Act
		List<Person> persons = (List<Person>) dao.saveAll(personsDB);
		//Assert
		Assert.assertEquals(persons.get(0).getName(), personsDB.get(0).getName());
	}
	
	@Test
	@DatabaseSetup("/db/person/init.xml")
	@ExpectedDatabase(value = "/db/person/afterDeletingPerson.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testDelete() {
		//Act
		dao.delete(dao.findById(1L).get());
	}

}
