package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.EventManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.EventDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;

public class EventManagerImpl implements EventManager {
	
	@Autowired
	private EventDAO dao;

	@Override
	public Iterable<Event> findAll() {
		return dao.findAll();
	}

	@Override
	public Event findById(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public Event save(Event event) {
		return dao.save(event);
	}

	@Override
	public Iterable<Event> save(Iterable<Event> events) {
		return dao.saveAll(events);
	}

	@Override
	public Event update(Event event) {
		return dao.save(event);
	}

	@Override
	public Iterable<Event> update(Iterable<Event> events) {
		return dao.saveAll(events);
	}

	@Override
	public void remove(Event event) {
		dao.delete(event);
	}

}
