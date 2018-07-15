package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.PersonManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@Service
public class PersonManagerImpl implements PersonManager {
	
	@Autowired
	private PersonDAO dao;

	@Override
	public Iterable<Person> findAll() {
		return dao.findAll();
	}

	@Override
	public Person findById(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public Person save(Person person) {
		return dao.save(person);
	}

	@Override
	public Iterable<Person> save(Iterable<Person> persons) {
		return dao.saveAll(persons);
	}

	@Override
	public Person update(Person person) {
		return dao.save(person);
	}

	@Override
	public Iterable<Person> update(Iterable<Person> persons) {
		return dao.saveAll(persons);
	}

	@Override
	public void remove(Person person) {
		dao.delete(person);
	}

	@Override
	public Person relatePersons(Long id, Iterable<Person> friends) {
		Person person = dao.findById(id).get();
		List<Person> friendsDB = new ArrayList<>();
		for (Person friend : friends) {
			friendsDB.add(dao.findById(friend.getId()).get());
		}
		for (Person friendDB : friendsDB) {
			if(null!=friendDB) {
				friendDB.getFriends().add(person);
				friendDB.getFriendOf().add(person);
				person.getFriends().add(friendDB);
				person.getFriendOf().add(friendDB);
			}
		}
		dao.saveAll(friends);
		return dao.save(person);
	}

}
