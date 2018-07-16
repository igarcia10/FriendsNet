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
	public E save(E event) {
		return this.getDAO().save(event);
	}

	@Override
	public Iterable<E> save(Iterable<E> events) {
		return this.getDAO().saveAll(events);
	}

	@Override
	public E update(E event) {
		return this.getDAO().save(event);
	}

	@Override
	public Iterable<E> update(Iterable<E> events) {
		return this.getDAO().saveAll(events);
	}

	@Override
	public void remove(E event) {
		this.getDAO().delete(event);
	}

}
