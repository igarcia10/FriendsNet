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
import org.springframework.data.repository.CrudRepository;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.LikeDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PostDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.LikeType;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.PostType;

@RunWith(MockitoJUnitRunner.class)
public class PostManagerImplTest extends AbstractManagerTest<Post, Long> {

	public PostManagerImplTest() {
		super(Post.class);
	}

	@Override
	AbstractManager<Post, Long> getManager() {
		return manager;
	}

	@Override
	CrudRepository<Post, Long> getDAO() {
		return postDAO;
	}

	@InjectMocks
	private PostManagerImpl manager;

	@Mock
	private PostDAO postDAO;

	@Mock
	private LikeDAO likeDAO;

	@Mock
	private PersonDAO personDAO;

	@Test
	public void testAddLikeNewLike() {
		// Arrange
		final Post post = new Post();
		final Person person = new Person();
		Mockito.when(likeDAO.findByPersonIdAndPostId(1L, 1L)).thenReturn(null);
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
		Mockito.when(likeDAO.findByPersonIdAndPostId(1L, 1L)).thenReturn(like);
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
		// Arrange
		final Person person = new Person();
		final Post post = new Post();
		final List<Post> posts = new ArrayList<>();
		posts.add(post);
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(person));
		Mockito.when(postDAO.findByPersonId(1L)).thenReturn(posts);
		// Act
		final List<Post> postsResult = manager.findByPersonId(1L);
		// Assert
		Assert.assertEquals(posts, postsResult);
		Assert.assertEquals(1, postsResult.size());
	}

	@Test
	public void testFindByPersonsIdNullPerson() {
		// Arrange
		Mockito.when(personDAO.findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final List<Post> postsResult = manager.findByPersonId(1L);
		// Assert
		Assert.assertNull(postsResult);
	}

	@Test
	public void testCreatePost() {
		// Arrange
		final Post post = new Post();
		final byte[] picture = new byte[10];
		final String text = "text";
		post.setText(text);
		final PostType type = PostType.BIOGRAPHY;
		post.setText("text");
		post.setPicture(picture);
		post.setType(type);
		Mockito.when(postDAO.save(Mockito.any(Post.class))).thenReturn(post);
		// Act
		final Post resultPost = manager.createPost(post, picture, type);
		// Assert
		Assert.assertEquals(text, resultPost.getText());
		Assert.assertEquals(type, resultPost.getType());
		Assert.assertEquals(picture, resultPost.getPicture());
		Mockito.verify(postDAO, Mockito.times(1)).save(Mockito.any(Post.class));
	}

	@Test
	public void testCreatePostNull() {
		// Arrange
		final Post post = new Post();
		final byte[] picture = null;
		final String text = null;
		post.setText(text);
		final PostType type = null;
		Mockito.when(postDAO.save(Mockito.any(Post.class))).thenReturn(post);
		// Act
		final Post resultPost = manager.createPost(post, picture, type);
		// Assert
		Assert.assertEquals(text, resultPost.getText());
		Assert.assertEquals(type, resultPost.getType());
		Assert.assertEquals(picture, resultPost.getPicture());
		Mockito.verify(postDAO, Mockito.times(1)).save(Mockito.any(Post.class));
	}

}
