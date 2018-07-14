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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class GroupDAOTest {
	
	@Autowired
	private GroupDAO dao;

	@Test
	@DatabaseSetup("/db/group/init.xml")
	public void testFindAll() {
		//Act
		List<Group> groups = (List<Group>) dao.findAll();
		//Assert
		Assert.assertEquals(2, groups.size());
	}
	
	@Test
	@DatabaseSetup("/db/group/init.xml")
	public void testFindById() {
		//Act
		Group group = dao.findById(1L).get();
		//Assert
		Assert.assertEquals("name1", group.getName());
	}
	
	@Test
	@DatabaseSetup("/db/group/init.xml")
	@ExpectedDatabase(value = "/db/group/afterSavingGroup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testSave() {
		//Arrange
		Group groupDB = new Group();
		groupDB.setName("name3");
		//Act
		Group group = dao.save(groupDB);
		//Assert
		Assert.assertEquals(groupDB, group);
	}
	
	@Test
	@DatabaseSetup("/db/group/init.xml")
	@ExpectedDatabase(value = "/db/group/afterSavingMultipleGroups.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testSaveAll() {
		//Arrange
		Group groupDB1 = new Group();
		groupDB1.setName("name4");
		Group groupDB2 = new Group();
		groupDB2.setName("name5");
		List<Group> groupsDB = new ArrayList<>();
		groupsDB.add(groupDB1);
		groupsDB.add(groupDB2);
		//Act
		List<Group> groups = (List<Group>) dao.saveAll(groupsDB);
		//Assert
		Assert.assertEquals(groups.get(0).getName(), groupsDB.get(0).getName());
	}
	
	@Test
	@DatabaseSetup("/db/group/init.xml")
	@ExpectedDatabase(value = "/db/group/afterUpdatingGroup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testUpdate() {
		//Arrange
		Group groupDB = new Group();
		groupDB.setId(1L);
		groupDB.setName("updatedName1");
		//Act
		Group group = dao.save(groupDB);
		//Assert
		Assert.assertEquals(groupDB.getName(), group.getName());
	}
	
	@Test
	@DatabaseSetup("/db/group/init.xml")
	@ExpectedDatabase(value = "/db/group/afterUpdatingMultipleGroups.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testUpdateAll() {
		//Arrange
		Group groupDB1 = new Group();
		groupDB1.setId(1L);
		groupDB1.setName("updatedName1");
		Group groupDB2 = new Group();
		groupDB2.setId(2L);
		groupDB2.setName("updatedName2");
		List<Group> groupsDB = new ArrayList<>();
		groupsDB.add(groupDB1);
		groupsDB.add(groupDB2);
		//Act
		List<Group> groups = (List<Group>) dao.saveAll(groupsDB);
		//Assert
		Assert.assertEquals(groups.get(0).getName(), groupsDB.get(0).getName());
	}
	
	@Test
	@DatabaseSetup("/db/group/init.xml")
	@ExpectedDatabase(value = "/db/group/afterDeletingGroup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testDelete() {
		//Act
		dao.delete(dao.findById(1L).get());
	}

}
