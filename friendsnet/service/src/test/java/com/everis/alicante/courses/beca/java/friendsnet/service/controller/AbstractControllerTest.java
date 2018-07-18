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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.FNEntity;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.DTOEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractControllerTest<DTO extends DTOEntity, E extends FNEntity, ID> {
	
	private Class<E> entityClass;
	private Class<DTO> dtoClass;
	private String URI;

	protected AbstractControllerTest(Class<E> entityClass, Class<DTO> dtoClass, String URI) {
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
		this.URI=URI;
	}
	
	abstract AbstractController<DTO, E, ID> getController();
	
	abstract AbstractManager<E, Long> getManager();
	
	@Mock
	protected DozerBeanMapper dozerMapper;
	
	protected MockMvc mockMvc;

	protected ObjectMapper mapper;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.getController()).build();
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void testFindAllNull() throws Exception {
		// Arrange
		Mockito.when(this.getManager().findAll()).thenReturn(null);
		// Act
		final ResultActions perform = mockMvc.perform(get(URI));
		// Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllWithContent() throws Exception {
		// Arrange
		final E entity1= entityClass.newInstance();
		final DTO entityDTO1 = dtoClass.newInstance();
		final E entity2 = entityClass.newInstance();
		final DTO entityDTO2 = dtoClass.newInstance();
		final List<E> entityList = new ArrayList<>();
		entityList.add(entity1);
		entityList.add(entity2);
		final List<DTO> entityDTOList = new ArrayList<>();
		entityDTOList.add(entityDTO1);
		entityDTOList.add(entityDTO2);
		Mockito.when(this.getManager().findAll()).thenReturn(entityList);
		Mockito.when(dozerMapper.map(entity1, dtoClass)).thenReturn(entityDTO1);
		Mockito.when(dozerMapper.map(entity2, dtoClass)).thenReturn(entityDTO2);
		// Act
		final ResultActions perform = mockMvc.perform(get(URI));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(entityDTOList)));
	}
	
	@Test
	public void testFindByIdNull() throws Exception {
		//Arrange
		Mockito.when(this.getManager().findById(1L)).thenReturn(null);
		//Act
		final ResultActions perform = mockMvc.perform(get(URI+"/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindByIdWithContent() throws Exception {
		//Arrange
		final E entity= entityClass.newInstance();
		final DTO entityDTO = dtoClass.newInstance();
		Mockito.when(this.getManager().findById(1L)).thenReturn(entity);
		Mockito.when(dozerMapper.map(entity, dtoClass)).thenReturn(entityDTO);
		//Act
		final ResultActions perform = mockMvc.perform(get(URI+"/1"));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(entityDTO)));
	}
	
	@Test
	public void testCreateNull() throws Exception {
		//Arrange
		final E entity= entityClass.newInstance();
		final DTO entityDTO = dtoClass.newInstance();
		Mockito.when(this.getManager().save(entity)).thenReturn(null);
		Mockito.when(dozerMapper.map(entity, dtoClass)).thenReturn(null);
		Mockito.when(dozerMapper.map(Mockito.any(dtoClass), Mockito.any())).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(post(URI).content(mapper.writeValueAsString(entityDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testCreate() throws Exception {
		//Arrange
		final E entity= entityClass.newInstance();
		final DTO entityDTO = dtoClass.newInstance();
		Mockito.when(this.getManager().save(entity)).thenReturn(entity);
		Mockito.when(dozerMapper.map(entity, dtoClass)).thenReturn(entityDTO);
		Mockito.when(dozerMapper.map(Mockito.any(dtoClass), Mockito.any())).thenReturn(entity);
		//Act
		ResultActions perform = mockMvc.perform(post(URI).content(mapper.writeValueAsString(entityDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(entityDTO)));
	}
	
	@Test
	public void testRemove() throws Exception {
		//Arrange
		final E entity= entityClass.newInstance();
		Mockito.when(this.getManager().findById(1L)).thenReturn(entity);
		//Act
		ResultActions perform = mockMvc.perform(delete(URI+"/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveNotInDb() throws Exception {
		//Arrange
		Mockito.when(this.getManager().findById(1L)).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(delete(URI+"/1"));
		//Assert
		perform.andExpect(status().isOk());
	}

}
