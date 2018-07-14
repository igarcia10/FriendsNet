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
		Assert.assertEquals(postDB.getText(), post.getText());
	}
	
	@Test
	@DatabaseSetup("/db/post/init.xml")
	@ExpectedDatabase(value = "/db/post/afterSavingMultiplePosts.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testSaveAll() {
		//Arrange
		Post postDB1 = new Post();
		postDB1.setText("text4");
		Post postDB2 = new Post();
		postDB2.setText("text5");
		List<Post> postsDB = new ArrayList<>();
		postsDB.add(postDB1);
		postsDB.add(postDB2);
		//Act
		List<Post> posts = (List<Post>) dao.saveAll(postsDB);
		//Assert
		Assert.assertEquals(posts.get(0).getText(), postsDB.get(0).getText());
	}
	
	@Test
	@DatabaseSetup("/db/post/init.xml")
	@ExpectedDatabase(value = "/db/post/afterUpdatingPost.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testUpdate() {
		//Arrange
		Post postDB = new Post();
		postDB.setId(1L);
		postDB.setText("updatedText1");
		//Act
		Post post = dao.save(postDB);
		//Assert
		Assert.assertEquals(postDB.getText(), post.getText());
	}
	
	@Test
	@DatabaseSetup("/db/post/init.xml")
	@ExpectedDatabase(value = "/db/post/afterUpdatingMultiplePosts.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testUpdateAll() {
		//Arrange
		Post postDB1 = new Post();
		postDB1.setId(1L);
		postDB1.setText("updatedText1");
		Post postDB2 = new Post();
		postDB2.setId(2L);
		postDB2.setText("updatedText2");
		List<Post> postsDB = new ArrayList<>();
		postsDB.add(postDB1);
		postsDB.add(postDB2);
		//Act
		List<Post> posts = (List<Post>) dao.saveAll(postsDB);
		//Assert
		Assert.assertEquals(posts.get(0).getText(), postsDB.get(0).getText());
	}
	
	@Test
	@DatabaseSetup("/db/post/init.xml")
	@ExpectedDatabase(value = "/db/post/afterDeletingPost.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void testDelete() {
		//Act
		dao.delete(dao.findById(1L).get());
	}

}
