package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.repository.CrudRepository;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.FNEntity;

public abstract class AbstractManagerTest<E extends FNEntity, ID> {
	
	private Class<E> entityClass;
	
	abstract AbstractManager<E, Long> getManager();
	
	abstract CrudRepository<E, Long> getDAO();
	
	public AbstractManagerTest(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Test
	public void testFindAll() {
		// Arrange
		final Iterable<E> iterable = new ArrayList<>();
		Mockito.when(getDAO().findAll()).thenReturn(iterable);
		// Act
		final Iterable<E> result = getManager().findAll();
		// Assert
		Assert.assertEquals(iterable, result);
	}

	@Test
	public void testFindAllNull() {
		// Arrange
		Mockito.when(getDAO().findAll()).thenReturn(null);
		// Act
		final Iterable<E> result = getManager().findAll();
		// Assert
		Assert.assertNull(result);
	}

	@Test
	public void testFindById() throws Exception {
		// Arrange
		final E entity = entityClass.newInstance();
		Mockito.when(getDAO().findById(1L)).thenReturn(Optional.of(entity));
		// Act
		final E resultE = getManager().findById(1L);
		// Assert
		Assert.assertEquals(entity, resultE);
	}

	@Test
	public void testFindByIdNull() {
		// Arrange
		Mockito.when(getDAO().findById(1L)).thenReturn(Optional.ofNullable(null));
		// Act
		final E entity = getManager().findById(1L);
		// Assert
		Assert.assertNull(entity);
	}

	@Test
	public void testSave() throws Exception {
		// Arrange
		final E entity = entityClass.newInstance();
		Mockito.when(getDAO().save(entity)).thenReturn(entity);
		// Act
		final E resultE = getManager().save(entity);
		// Assert
		Assert.assertEquals(entity, resultE);
		Mockito.verify(getDAO(), Mockito.times(1)).save(entity);
	}

	@Test
	public void testSaveMultiple() throws Exception {
		// Arrange
		final E entity1 = entityClass.newInstance();
		final E entity2 = entityClass.newInstance();
		final List<E> entitys = new ArrayList<>();
		entitys.add(entity1);
		entitys.add(entity2);
		Mockito.when(getDAO().saveAll(entitys)).thenReturn(entitys);
		// Act
		final List<E> resultEntities = (List<E>) getManager().save(entitys);
		// Assert
		Assert.assertEquals(entitys, resultEntities);
		Mockito.verify(getDAO(), Mockito.times(1)).saveAll(entitys);
	}

	@Test
	public void testUpdate() throws Exception {
		// Arrange
		final E entity = entityClass.newInstance();
		final E updatedEntity = entityClass.newInstance();
		Mockito.when(getDAO().save(entity)).thenReturn(updatedEntity);
		// Act
		final E resultEntity = getManager().update(entity);
		// Assert
		Assert.assertEquals(updatedEntity, resultEntity);
	}

	@Test
	public void testUpdateMultiple() throws Exception {
		// Arrange
		final E entity1 = entityClass.newInstance();
		final E entity2 = entityClass.newInstance();
		final E updatedEntity1 = entityClass.newInstance();
		final E updatedEntity2 = entityClass.newInstance();
		final List<E> entitys = new ArrayList<>();
		entitys.add(entity1);
		entitys.add(entity2);
		final List<E> updatedEs = new ArrayList<>();
		updatedEs.add(updatedEntity1);
		updatedEs.add(updatedEntity2);
		Mockito.when(getDAO().saveAll(entitys)).thenReturn(updatedEs);
		// Act
		final List<E> resultEs = (List<E>) getManager().update(entitys);
		// Assert
		Assert.assertEquals(updatedEs, resultEs);
	}

	@Test
	public void testRemove() throws Exception {
		// Arrange
		final E entity = entityClass.newInstance();
		// Act
		getManager().remove(entity);
		// Assert
		Mockito.verify(getDAO()).delete(entity);
	}

}
