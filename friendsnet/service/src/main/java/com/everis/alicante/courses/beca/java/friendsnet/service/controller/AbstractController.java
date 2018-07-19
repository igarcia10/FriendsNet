package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.FNEntity;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.DTOEntity;

public abstract class AbstractController<DTO extends DTOEntity, E extends FNEntity, ID> {
	
	@Autowired
	private AbstractManager<E, ID> manager;

	@Autowired
	protected DozerBeanMapper mapper;
	
	private Class<E> entityClass;
	private Class<DTO> dtoClass;

	protected AbstractController(Class<E> entityClass, Class<DTO> dtoClass) {
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
	}

	@GetMapping
	public List<DTO> getAll() {
		List<E> entityList = (List<E>) manager.findAll();
		List<DTO> entityDTOList = new ArrayList<>();
		if (null != entityList) {
			for (E entity : entityList) {
				entityDTOList.add(mapper.map(entity, dtoClass));
			}
		}
		return entityDTOList;
	}

	@GetMapping("/{id}")
	public DTO getById(@PathVariable("id") ID id) {
		final E entity = manager.findById(id);
		DTO entityDTO = null;
		if (null != entity) {
			entityDTO = mapper.map(entity, dtoClass);
		}
		return entityDTO;
	}

	@PostMapping
	public DTO create(@RequestBody DTO entity) {
		E entityDB = manager.save(mapper.map(entity, entityClass));
		DTO entityDTO = null;
		if (null != entityDB) {
			entityDTO = mapper.map(entityDB, dtoClass);
		}
		return entityDTO;
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable("id") ID id) {
		final E entity = manager.findById(id);
		if (null != entity) {
			manager.remove(entity);
		}
	}

}
