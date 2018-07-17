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

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.GroupManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.GroupDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class GroupControllerTest {
	
	@InjectMocks
	private GroupController controller;
	
	@Mock
	private GroupManagerImpl manager;
	
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
		final ResultActions perform = mockMvc.perform(get("/groups"));
		// Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllWithContent() throws Exception {
		// Arrange
		final Group group1 = new Group();
		group1.setName("group1");
		final GroupDTO groupDTO1 = new GroupDTO();
		groupDTO1.setName("group1");
		final Group group2 = new Group();
		group2.setName("group2");
		final GroupDTO groupDTO2 = new GroupDTO();
		groupDTO2.setName("group2");
		final List<Group> groupList = new ArrayList<>();
		groupList.add(group1);
		groupList.add(group2);
		final List<GroupDTO> groupDTOList = new ArrayList<>();
		groupDTOList.add(groupDTO1);
		groupDTOList.add(groupDTO2);
		Mockito.when(manager.findAll()).thenReturn(groupList);
		Mockito.when(dozerMapper.map(group1, GroupDTO.class)).thenReturn(groupDTO1);
		Mockito.when(dozerMapper.map(group2, GroupDTO.class)).thenReturn(groupDTO2);
		// Act
		final ResultActions perform = mockMvc.perform(get("/groups"));
		// Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(groupDTOList)));
	}
	
	@Test
	public void testFindByIdNull() throws Exception {
		//Arrange
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		final ResultActions perform = mockMvc.perform(get("/groups/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testFindByIdWithContent() throws Exception {
		//Arrange
		final Group group = new Group();
		group.setName("group");
		final GroupDTO groupDTO = new GroupDTO();
		groupDTO.setName("group");
		Mockito.when(manager.findById(1L)).thenReturn(group);
		Mockito.when(dozerMapper.map(group, GroupDTO.class)).thenReturn(groupDTO);
		//Act
		final ResultActions perform = mockMvc.perform(get("/groups/1"));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(groupDTO)));
	}
	
	@Test
	public void testCreateNull() throws Exception {
		//Arrange
		final Group group = new Group();
		final GroupDTO groupDTO = new GroupDTO();
		Mockito.when(manager.save(group)).thenReturn(null);
		Mockito.when(dozerMapper.map(group, GroupDTO.class)).thenReturn(null);
		Mockito.when(dozerMapper.map(Mockito.any(GroupDTO.class), Mockito.any())).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(post("/groups").content(mapper.writeValueAsString(groupDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testCreate() throws Exception {
		//Arrange
		final Group group = new Group();
		group.setName("name");
		final GroupDTO groupDTO = new GroupDTO();
		groupDTO.setName("name");
		Mockito.when(manager.save(group)).thenReturn(group);
		Mockito.when(dozerMapper.map(group, GroupDTO.class)).thenReturn(groupDTO);
		Mockito.when(dozerMapper.map(Mockito.any(GroupDTO.class), Mockito.any())).thenReturn(group);
		//Act
		ResultActions perform = mockMvc.perform(post("/groups").content(mapper.writeValueAsString(groupDTO))
																.contentType(MediaType.APPLICATION_JSON)
																.accept(MediaType.APPLICATION_JSON));
		//Assert
		perform.andExpect(status().isOk());
		perform.andExpect(content().json(mapper.writeValueAsString(groupDTO)));
	}
	
	@Test
	public void testRemove() throws Exception {
		//Arrange
		final Group group = new Group();
		Mockito.when(manager.findById(1L)).thenReturn(group);
		//Act
		ResultActions perform = mockMvc.perform(delete("/groups/1"));
		//Assert
		perform.andExpect(status().isOk());
	}
	
	@Test
	public void testRemoveNotInDb() throws Exception {
		//Arrange
		Mockito.when(manager.findById(1L)).thenReturn(null);
		//Act
		ResultActions perform = mockMvc.perform(delete("/groups/1"));
		//Assert
		perform.andExpect(status().isOk());
	}

}
