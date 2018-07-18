package com.everis.alicante.courses.beca.java.friendsnet.core.manager;

import org.springframework.data.repository.CrudRepository;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.FNEntity;

public abstract class AbstractManager<E extends FNEntity, ID> implements Manager<E, ID> {

	protected abstract CrudRepository<E, ID> getDAO();
	
	@Override
	public Iterable<E> findAll() {
		return this.getDAO().findAll();
	}

	@Override
	public E findById(ID id) {
		return this.getDAO().findById(id).orElse(null);
	}

	@Override
	public E save(E entity) {
		return this.getDAO().save(entity);
	}

	@Override
	public Iterable<E> save(Iterable<E> entitys) {
		return this.getDAO().saveAll(entitys);
	}

	@Override
	public E update(E entity) {
		return this.getDAO().save(entity);
	}

	@Override
	public Iterable<E> update(Iterable<E> entitys) {
		return this.getDAO().saveAll(entitys);
	}

	@Override
	public void remove(E entity) {
		this.getDAO().delete(entity);
	}

}
