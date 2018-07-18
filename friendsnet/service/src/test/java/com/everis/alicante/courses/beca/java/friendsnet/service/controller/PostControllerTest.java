package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.PostManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PostDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PostControllerTest {
	
	@InjectMocks
	private PostController controller;
	
	@Mock
	private PostManagerImpl manager;
	
	@Mock
	private DozerBeanMapper dozerMapper;

	private MockMvc mockMvc;

	private ObjectMapper mapper;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void testFindAllNull() throws Exception {
		// Arrange
		Mockito.when(manager.findAll()).thenReturn(null);
		// Act
		final ResultActions perform = mockMvc.perform(get("/posts"));
		// Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllWithContent() throws Exception {
		// Arrange
		final Post post1 = new Post();
		post1.setText("post1");
		final PostDTO postDTO1 = new PostDTO();
		postDTO1.setText("post1");
		final Post post2 = new Post();
		post2.setText("post2");
		final PostDTO postDTO2 = new PostDTO();
		postDTO2.setText("post2");
		final List<Post> postList = new ArrayList<>();
		postList.add(post1);
		postList.add(post2);
		final List<PostDTO> postDTOList = new ArrayList<>();
		postDTOList.add(postDTO1);
		postDTOList.add(postDTO2);
		Mockito.when(manager.findAll()).thenReturn(postList);
		Mockito.when(dozerMapper.map(post1, PostDTO.class)).thenReturn(postDTO1);
		Mockito.when(dozerMapper.map(post2, PostDTO.class)).thenReturn(postDTO2);
		// Act
		final ResultActions perform = mockMvc.perform(get("/posts"));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(postDTOList)));
	}
	
	@Test
	public void testFindByIdNull() throws Exception {
		//Arrange
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		final ResultActions perform = mockMvc.perform(get("/posts/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindByIdWithContent() throws Exception {
		//Arrange
		final Post post = new Post();
		post.setText("post");
		final PostDTO postDTO = new PostDTO();
		postDTO.setText("post");
		Mockito.when(manager.findById(1L)).thenReturn(post);
		Mockito.when(dozerMapper.map(post, PostDTO.class)).thenReturn(postDTO);
		//Act
		final ResultActions perform = mockMvc.perform(get("/posts/1"));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(postDTO)));
	}
	
	@Test
	public void testCreateNull() throws Exception {
		//Arrange
		final Post post = new Post();
		final PostDTO postDTO = new PostDTO();
		Mockito.when(manager.save(post)).thenReturn(null);
		Mockito.when(dozerMapper.map(post, PostDTO.class)).thenReturn(null);
		Mockito.when(dozerMapper.map(Mockito.any(PostDTO.class), Mockito.any())).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(post("/posts").content(mapper.writeValueAsString(postDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testCreate() throws Exception {
		//Arrange
		final Post post = new Post();
		post.setText("text");
		final PostDTO postDTO = new PostDTO();
		postDTO.setText("text");
		Mockito.when(manager.save(post)).thenReturn(post);
		Mockito.when(dozerMapper.map(post, PostDTO.class)).thenReturn(postDTO);
		Mockito.when(dozerMapper.map(Mockito.any(PostDTO.class), Mockito.any())).thenReturn(post);
		//Act
		ResultActions perform = mockMvc.perform(post("/posts").content(mapper.writeValueAsString(postDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(postDTO)));
	}
	
	@Test
	public void testRemove() throws Exception {
		//Arrange
		final Post post = new Post();
		Mockito.when(manager.findById(1L)).thenReturn(post);
		//Act
		ResultActions perform = mockMvc.perform(delete("/posts/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveNotInDb() throws Exception {
		//Arrange
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(delete("/posts/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
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
		ResultActions perform = mockMvc.perform(get("/posts/person/1"));
		//Assert
		perform.andExpect(status().isOk());
	}

}
