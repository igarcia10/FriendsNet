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

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.LikeDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PostDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.LikeType;

@RunWith(MockitoJUnitRunner.class)
public class PostManagerImplTest {

	@InjectMocks
	private PostManagerImpl manager;

	@Mock
	private PostDAO postDAO;

	@Mock
	private LikeDAO likeDAO;
	
	@Mock
	private PersonDAO personDAO;

	@Test
	public void testFindAll() {
		// Arrange
		final Iterable<Post> iterable = new ArrayList<>();
		Mockito.when(postDAO.findAll()).thenReturn(iterable);
		// Act
		final Iterable<Post> result = manager.findAll();
		// Assert
		Assert.assertEquals(iterable, result);
	}

	@Test
	public void testFindAllNull() {
		// Arrange
		Mockito.when(postDAO.findAll()).thenReturn(null);
		// Act
		final Iterable<Post> result = manager.findAll();
		// Assert
		Assert.assertNull(result);
	}

	@Test
	public void testFindById() {
		// Arrange
		final Post post = new Post();
		Mockito.when(postDAO.findById(1L)).thenReturn(Optional.of(post));
		// Act
		final Post resultPost = manager.findById(1L);
		// Assert
		Assert.assertEquals(post, resultPost);
	}

	@Test
	public void testFindByIdNull() {
		// Arrange
		Mockito.when(postDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final Post post = manager.findById(1L);
		// Assert
		Assert.assertNull(post);
	}

	@Test
	public void testSave() {
		// Arrange
		final Post post = new Post();
		post.setText("post1");
		Mockito.when(postDAO.save(post)).thenReturn(post);
		// Act
		final Post resultPost = manager.save(post);
		// Assert
		Assert.assertEquals(post, resultPost);
		Assert.assertEquals(post.getText(), resultPost.getText());
		Mockito.verify(postDAO, Mockito.times(1)).save(post);
	}

	@Test
	public void testSaveMultiple() {
		// Arrange
		final Post post1 = new Post();
		post1.setText("post1");
		final Post post2 = new Post();
		post2.setText("post2");
		final List<Post> posts = new ArrayList<>();
		posts.add(post1);
		posts.add(post2);
		Mockito.when(postDAO.saveAll(posts)).thenReturn(posts);
		// Act
		final List<Post> resultPosts = (List<Post>) manager.save(posts);
		// Assert
		Assert.assertEquals(posts, resultPosts);
		Assert.assertEquals(posts.get(0).getText(), resultPosts.get(0).getText());
		Mockito.verify(postDAO, Mockito.times(1)).saveAll(posts);
	}

	@Test
	public void testUpdate() {
		// Arrange
		final Post post = new Post();
		final Post updatedPost = new Post();
		updatedPost.setText("updatedPost1");
		Mockito.when(postDAO.save(post)).thenReturn(updatedPost);
		// Act
		final Post resultPost = manager.update(post);
		// Assert
		Assert.assertEquals(updatedPost, resultPost);
		Assert.assertEquals(updatedPost.getText(), resultPost.getText());
	}

	@Test
	public void testUpdateMultiple() {
		// Arrange
		final Post post1 = new Post();
		final Post post2 = new Post();
		final Post updatedPost1 = new Post();
		updatedPost1.setText("updatedPost1");
		final Post updatedPost2 = new Post();
		updatedPost2.setText("updatedPost2");
		final List<Post> posts = new ArrayList<>();
		posts.add(post1);
		posts.add(post2);
		final List<Post> updatedPosts = new ArrayList<>();
		updatedPosts.add(post1);
		updatedPosts.add(post2);
		Mockito.when(postDAO.saveAll(posts)).thenReturn(updatedPosts);
		// Act
		final List<Post> resultPosts = (List<Post>) manager.update(posts);
		// Assert
		Assert.assertEquals(updatedPosts, resultPosts);
		Assert.assertEquals(updatedPosts.get(0).getText(), resultPosts.get(0).getText());
	}

	@Test
	public void testRemove() {
		// Arrange
		final Post post = new Post();
		// Act
		manager.remove(post);
		// Assert
		Mockito.verify(postDAO).delete(post);
	}

	@Test
	public void testAddLikeNewLike() {
		// Arrange
		final Post post = new Post();
		final Person person = new Person();
		Mockito.when(likeDAO.findByPersonId(1L)).thenReturn(null);
		Mockito.when(postDAO.findById(1L)).thenReturn(Optional.of(post));
		Mockito.when(postDAO.save(post)).thenReturn(post);
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.of(person));
		// Act
		final Post resultPost = manager.addLike(1L, 1L, LikeType.COOL);
		// Assert
		Assert.assertEquals(post, resultPost);
		Assert.assertEquals(1, resultPost.getLikes().size());
	}
	
	@Test
	public void testAddLikeExistingLike() {
		// Arrange
		final Post post = new Post();
		final Person person = new Person();
		final Like like = new Like();
		post.getLikes().add(like);
		Mockito.when(likeDAO.findByPersonId(1L)).thenReturn(like);
		Mockito.when(likeDAO.save(like)).thenReturn(like);
		Mockito.when(postDAO.findById(1L)).thenReturn(Optional.of(post));
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.of(person));
		// Act
		final Post resultPost = manager.addLike(1L, 1L, LikeType.COOL);
		// Assert
		Assert.assertEquals(post, resultPost);
		Assert.assertEquals(1, resultPost.getLikes().size());
	}

	@Test
	public void testAddLikeNullPostAndPerson() {
		// Act
		final Post resultPost = manager.addLike(null, null, LikeType.COOL);
		// Assert
		Assert.assertNull(resultPost);
	}

	@Test
	public void testAddLikeNullPost() {
		// Act
		final Post resultPost = manager.addLike(null, 1L, LikeType.COOL);
		// Assert
		Assert.assertNull(resultPost);
	}
	
	@Test
	public void testAddLikeNullPerson() {
		// Act
		final Post resultPost = manager.addLike(1L, null, LikeType.COOL);
		// Assert
		Assert.assertNull(resultPost);
	}

	@Test
	public void testAddLikePostNotInDB() {
		// Arrange
		Mockito.when(postDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final Post resultPost = manager.addLike(1L, 1L, LikeType.COOL);
		// Assert
		Assert.assertNull(resultPost);
	}
	
	@Test
	public void testAddLikePersonNotInDB() {
		// Arrange
		final Post post = new Post();
		Mockito.when(postDAO.findById(1L)).thenReturn(Optional.ofNullable(post));
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final Post resultPost = manager.addLike(1L, 1L, LikeType.COOL);
		// Assert
		Assert.assertNull(resultPost);
	}
	
	@Test
	public void testFindByPersonsId() {
		//Arrange
		final Person person = new Person();
		final Post group = new Post();
		final List<Post> posts = new ArrayList<>();
		posts.add(group);
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(person));
		Mockito.when(postDAO.findByPersonId(1L)).thenReturn(posts);
		//Act
		final List<Post> groupsResult = manager.findByPersonId(1L);
		//Assert
		Assert.assertEquals(posts, groupsResult);
		Assert.assertEquals(1, groupsResult.size());
	}
	
	@Test
	public void testFindByPersonsIdNullPerson() {
		//Arrange
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		//Act
		final List<Post> postsResult = manager.findByPersonId(1L);
		//Assert
		Assert.assertNull(postsResult);
	}

}
