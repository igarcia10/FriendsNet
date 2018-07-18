package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

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
		return eventDAO.findByPersonsId(id);
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

}
