package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.PostManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.LikeType;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PostDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PostControllerTest extends AbstractControllerTest<PostDTO, Post, Long> {
	
	public PostControllerTest() {
		super(Post.class, PostDTO.class, "/posts");
	}
	
	@Override
	AbstractController<PostDTO, Post, Long> getController() {
		return controller;
	}

	@Override
	AbstractManager<Post, Long> getManager() {
		return manager;
	}
	
	@InjectMocks
	private PostController controller;
	
	@Mock
	private PostManagerImpl manager;
	
	@Test
	public void testFindByPersonsId() throws Exception {
		// Arrange
		final Post post = new Post();
		final List<Post> posts = new ArrayList<>();
		posts.add(post);
		final PostDTO postDTO = new PostDTO();
		final List<PostDTO> listDTO = new ArrayList<>();
		listDTO.add(postDTO);
		Mockito.when(manager.findByPersonId(1L)).thenReturn(posts);
		Mockito.when(dozerMapper.map(Mockito.any(), Mockito.any())).thenReturn(postDTO);
		// Act
		final ResultActions perform = mockMvc.perform(get("/posts/person/1"));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(listDTO)));
	}
	
	@Test
	public void testFindByPersonIdNull() throws Exception {
		//Arrange
		Mockito.when(manager.findByPersonId(1L)).thenReturn(null);
		//Act
		final ResultActions perform = mockMvc.perform(get("/posts/person/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testAddLike() throws Exception {
		//Arrange
		final Post post = new Post();
		final PostDTO postDTO = new PostDTO();
		Mockito.when(manager.addLike(1L, 1L, LikeType.COOL)).thenReturn(post);
		Mockito.when(dozerMapper.map(post, PostDTO.class)).thenReturn(postDTO);
		//Act
		final ResultActions perform = mockMvc.perform(post("/posts/1/person/1/like/COOL"));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(postDTO)));
	}
	
	@Test
	public void testAddLikeNull() throws Exception {
		//Arrange
		Mockito.when(manager.addLike(1L, 1L, LikeType.DONTCARE)).thenReturn(null);
		//Act
		final ResultActions perform = mockMvc.perform(post("/posts/1/person/1/like/COOL"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testCreatePost() throws Exception {
		//Arrange
		final Post post = new Post();
		final PostDTO postDTO = new PostDTO();
		final String text = "text";
		final byte[] picture = new byte[10];
		Mockito.when(manager.createPost(Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(post);
		Mockito.when(dozerMapper.map(Mockito.any(Post.class), Mockito.any())).thenReturn(postDTO);
		//Act
		final ResultActions perform = mockMvc.perform(post("/posts/new/STATUS").content(mapper.writeValueAsString(text))
																			.content(mapper.writeValueAsBytes(picture))
																			.contentType(MediaType.APPLICATION_JSON)
																			.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(postDTO)));
	}
	
	@Test
	public void testCreatePostNull() throws Exception {
		//Arrange
		final PostDTO postDTO = new PostDTO();
		final String text = "text";
		final byte[] picture = new byte[10];
		Mockito.when(manager.createPost(Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(null);
		Mockito.when(dozerMapper.map(Mockito.any(Post.class), Mockito.any())).thenReturn(postDTO);
		//Act
		final ResultActions perform = mockMvc.perform(post("/posts/new/STATUS").content(mapper.writeValueAsString(text))
																			.content(mapper.writeValueAsBytes(picture))
																			.contentType(MediaType.APPLICATION_JSON)
																			.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
	}
	
}
