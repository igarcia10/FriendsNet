package com.everis.alicante.courses.beca.java.friendsnet.persistence.dao;

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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class PostDAOTest {
	
	@Autowired
	private PostDAO dao;

	@Test
	@DatabaseSetup("/db/post/init.xml")
	public void testFindAll() {
		//Act
		List<Post> posts = (List<Post>) dao.findAll();
		//Assert
		Assert.assertEquals(2, posts.size());
	}
	
	@Test
	@DatabaseSetup("/db/post/init.xml")
	public void testFindById() {
		//Act
		Post post = dao.findById(1L).get();
		//Assert
		Assert.assertEquals("text1", post.getText());
	}
	
	@Test
	@DatabaseSetup("/db/post/init.xml")
	@ExpectedDatabase(value = "/db/post/afterSavingPost.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testSave() {
		//Arrange
		Post postDB = new Post();
		postDB.setText("text3");
		//Act
		Post post = dao.save(postDB);
		//Assert
		Assert.assertEquals(postDB, post);
	}
	
	@Test
	@DatabaseSetup("/db/post/init.xml")
	@ExpectedDatabase(value = "/db/post/afterDeletingPost.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testDelete() {
		//Act
		dao.delete(dao.findById(1L).get());
	}

}
