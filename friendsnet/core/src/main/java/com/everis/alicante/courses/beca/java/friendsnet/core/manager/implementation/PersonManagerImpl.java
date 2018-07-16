package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.PersonManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@Service
public class PersonManagerImpl extends AbstractManager<Person, Long> implements PersonManager {

	@Autowired
	private PersonDAO dao;

	@Override
	public Person relatePersons(Long id, Iterable<Person> friends) {
		final Person person = dao.findById(id).get();
		for (final Person friend : friends) {
			final Person friendPerson = dao.findById(friend.getId()).get();
			friendPerson.getFriends().add(person);
			friendPerson.getFriendOf().add(person);
			person.getFriends().add(friendPerson);
			person.getFriendOf().add(friendPerson);
		}
		return dao.save(person);
	}

	@Override
	protected CrudRepository<Person, Long> getDAO() {
		return dao;
	}

}
