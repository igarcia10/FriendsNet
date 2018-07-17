package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.FNEntity;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.DTOEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public abstract class AbstractControllerTest<DTO extends DTOEntity, E extends FNEntity, ID> {
	
	private Class<E> entityClass;
	private Class<DTO> dtoClass;
	private String URI;

	protected AbstractControllerTest(Class<E> entityClass, Class<DTO> dtoClass, String URI) {
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
		this.URI=URI;
	}
	
	@InjectMocks
	private AbstractController<DTO, E, ID> controller;
	
	@Mock
	private AbstractManager<E, ID> manager;
	
	@Mock
	protected DozerBeanMapper dozerMapper;
	
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
		Mockito.when(manager.findAll()).thenReturn(entityList);
		Mockito.when(dozerMapper.map(entity1, dtoClass)).thenReturn(entityDTO1);
		Mockito.when(dozerMapper.map(entity2, dtoClass)).thenReturn(entityDTO2);
		// Act
		final ResultActions perform = mockMvc.perform(get(URI));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(entityDTOList)));
	}

}
