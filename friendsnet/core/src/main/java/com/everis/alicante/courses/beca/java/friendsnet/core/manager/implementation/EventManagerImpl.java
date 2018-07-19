package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.EventManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.EventDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.EventType;

@Service
public class EventManagerImpl extends AbstractManager<Event, Long> implements EventManager {

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private PersonDAO personDAO;

	@Override
	protected CrudRepository<Event, Long> getDAO() {
		return eventDAO;
	}

	public List<Event> findByPersonsId(Long id) {
		final Person person = personDAO.findById(id).orElse(null);
		List<Event> events = null;
		if (null != person) {
			events = eventDAO.findByPersonsId(id);
		}
		return events;
	}

	public Event addPerson(Long id, Long idPerson) {
		Event event = this.getDAO().findById(id).orElse(null);
		if (null != event) {
			Person person = personDAO.findById(id).orElse(null);
			if (null != person) {
				event.getPersons().add(person);
				person.getEvents().add(event);
				personDAO.save(person);
			}
		}
		return this.getDAO().save(event);
	}

	public Event createEvent(String name, Date startingDate, Date endingDate, byte[] picture, EventType type) {
		final Event event = new Event();
		event.setName(name);
		event.setStartingDate(startingDate);
		event.setEndingDate(endingDate);
		event.setPicture(picture);
		event.setType(type);
		return eventDAO.save(event);
	}

}
