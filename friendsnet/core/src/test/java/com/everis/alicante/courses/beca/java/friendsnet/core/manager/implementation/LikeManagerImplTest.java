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
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

@RunWith(MockitoJUnitRunner.class)
public class LikeManagerImplTest {
	
	@InjectMocks
	private LikeManagerImpl manager;

	@Mock
	private LikeDAO dao;

	@Test
	public void testFindAll() {
		// Arrange
		final Iterable<Like> iterable = new ArrayList<>();
		Mockito.when(dao.findAll()).thenReturn(iterable);
		// Act
		final Iterable<Like> result = manager.findAll();
		// Assert
		Assert.assertEquals(iterable, result);
	}
	
	@Test
	public void testFindAllNull() {
		// Arrange
		Mockito.when(dao.findAll()).thenReturn(null);
		// Act
		final Iterable<Like> result = manager.findAll();
		// Assert
		Assert.assertNull(result);
	}

	@Test
	public void testFindById() {
		// Arrange
		final Like like = new Like();
		Mockito.when(dao.findById(1L)).thenReturn(Optional.of(like));
		// Act
		final Like resultLike = manager.findById(1L);
		// Assert
		Assert.assertEquals(like, resultLike);
	}
	
	@Test
	public void testFindByIdNull() {
		// Arrange
		Mockito.when(dao.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final Like like = manager.findById(1L);
		//Assert
		Assert.assertNull(like);
	}

	@Test
	public void testSave() {
		// Arrange
		final Like like = new Like();
		final Post post = new Post();
		post.setText("text");
		like.setPost(post);
		Mockito.when(dao.save(like)).thenReturn(like);
		// Act
		final Like resultLike = manager.save(like);
		// Assert
		Assert.assertEquals(like, resultLike);
		Assert.assertEquals(like.getPost().getText(), resultLike.getPost().getText());
	}

	@Test
	public void testSaveMultiple() {
		// Arrange
		final Post post = new Post();
		post.setText("text");
		final Like like1 = new Like();
		like1.setPost(post);
		final Like like2 = new Like();
		like1.setPost(post);
		final List<Like> likes = new ArrayList<>();
		likes.add(like1);
		likes.add(like2);
		Mockito.when(dao.saveAll(likes)).thenReturn(likes);
		// Act
		final List<Like> resultLikes = (List<Like>) manager.save(likes);
		// Assert
		Assert.assertEquals(likes, resultLikes);
		Assert.assertEquals(likes.get(0).getPost().getText(), resultLikes.get(0).getPost().getText());
	}

	@Test
	public void testUpdate() {
		// Arrange
		final Post post = new Post();
		post.setText("text");
		final Like like = new Like();
		final Like updatedLike = new Like();
		updatedLike.setPost(post);
		Mockito.when(dao.save(like)).thenReturn(updatedLike);
		// Act
		final Like resultLike = manager.update(like);
		// Assert
		Assert.assertEquals(updatedLike, resultLike);
		Assert.assertEquals(updatedLike.getPost().getText(), resultLike.getPost().getText());
	}

	@Test
	public void testUpdateMultiple() {
		// Arrange
		final Post post = new Post();
		post.setText("text");
		final Like like1 = new Like();
		final Like like2 = new Like();
		final Like updatedLike1 = new Like();
		updatedLike1.setPost(post);
		final Like updatedLike2 = new Like();
		updatedLike2.setPost(post);
		final List<Like> likes = new ArrayList<>();
		likes.add(like1);
		likes.add(like2);
		final List<Like> updatedLikes = new ArrayList<>();
		updatedLikes.add(updatedLike1);
		updatedLikes.add(updatedLike2);
		Mockito.when(dao.saveAll(likes)).thenReturn(updatedLikes);
		// Act
		final List<Like> resultLikes = (List<Like>) manager.update(likes);
		// Assert
		Assert.assertEquals(updatedLikes, resultLikes);
		Assert.assertEquals(updatedLikes.get(0).getPost().getText(), resultLikes.get(0).getPost().getText());
	}

	@Test
	public void testRemove() {
		// Arrange
		final Like like = new Like();
		// Act
		manager.remove(like);
		// Assert
		Mockito.verify(dao).delete(like);
	}

}
