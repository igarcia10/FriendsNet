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
		Person person = dao.findById(id).orElse(null);
		if (null != person && null != friends) {
			for (final Person friend : friends) {
				if (null != friend) {
					final Person friendPerson = dao.findById(friend.getId()).orElse(null);
					if (null != friendPerson) {
						friendPerson.getFriends().add(person);
						friendPerson.getFriendOf().add(person);
						person.getFriends().add(friendPerson);
						person.getFriendOf().add(friendPerson);
					}
				}
			}
			person = dao.save(person);
		} else {
			person = null;
		}
		return person;
	}

	@Override
	protected CrudRepository<Person, Long> getDAO() {
		return dao;
	}

}
