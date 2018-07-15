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
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PostDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

@RunWith(MockitoJUnitRunner.class)
public class PostManagerImplTest {

	@InjectMocks
	private PostManagerImpl manager;

	@Mock
	private PostDAO postDAO;
	
	@Mock
	private LikeDAO likeDAO;

	@Test
	public void testFindAll() {
		// Arrange
		final Iterable<Post> iterable = new ArrayList<>();
		Mockito.when(postDAO.findAll()).thenReturn(iterable);
		// Act
		Iterable<Post> result = manager.findAll();
		// Assert
		Assert.assertEquals(iterable, result);
	}

	@Test
	public void testFindById() {
		// Arrange
		final Post post = new Post();
		Mockito.when(postDAO.findById(1L)).thenReturn(Optional.of(post));
		// Act
		Post resultPost = manager.findById(1L);
		// Assert
		Assert.assertEquals(post, resultPost);
	}

	@Test
	public void testSave() {
		// Arrange
		final Post post = new Post();
		post.setText("post1");
		Mockito.when(postDAO.save(Mockito.any())).thenReturn(post);
		Post resultPost = new Post();
		// Act
		resultPost = manager.save(post);
		// Assert
		Assert.assertEquals(post, resultPost);
		Assert.assertEquals(post.getText(), resultPost.getText());
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
		Mockito.when(postDAO.saveAll(Mockito.any())).thenReturn(posts);
		List<Post> resultPosts = new ArrayList<>();
		// Act
		resultPosts = (List<Post>) manager.save(posts);
		// Assert
		Assert.assertEquals(posts, resultPosts);
		Assert.assertEquals(posts.get(0).getText(), resultPosts.get(0).getText());
	}

	@Test
	public void testUpdate() {
		// Arrange
		final Post post = new Post();
		final Post updatedPost = new Post();
		updatedPost.setText("updatedPost1");
		Mockito.when(postDAO.save(Mockito.any())).thenReturn(updatedPost);
		Post resultPost = new Post();
		// Act
		resultPost = manager.update(post);
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
		Mockito.when(postDAO.saveAll(Mockito.any())).thenReturn(updatedPosts);
		List<Post> resultPosts = new ArrayList<>();
		// Act
		resultPosts = (List<Post>) manager.update(posts);
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
	public void testAddLike() {
		//Arrage
		final Post post = new Post();
		final Like like = new Like();
		post.addLike(like);
		Mockito.when(postDAO.findById(1L)).thenReturn(Optional.of(post));
		Mockito.when(postDAO.save(Mockito.any())).thenReturn(post);
		Mockito.when(likeDAO.findById(Mockito.any())).thenReturn(Optional.of(like));
		Mockito.when(likeDAO.save(Mockito.any())).thenReturn(like);
		//Act
		Post resultPost = manager.addLike(1L, like);
		//Assert
		Assert.assertEquals(post, resultPost);
		Assert.assertEquals(post.getLikes().size(), resultPost.getLikes().size());
	}

}
