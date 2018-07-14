package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.GroupManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.GroupDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@Service
public class GroupManagerImpl implements GroupManager {

	@Autowired
	private GroupDAO groupDAO;
	
	@Autowired
	private PersonDAO personDAO;

	@Override
	public Iterable<Group> findAll() {
		return groupDAO.findAll();
	}

	@Override
	public Group findById(Long id) {
		return groupDAO.findById(id).get();
	}

	@Override
	public Group save(Group group) {
		return groupDAO.save(group);
	}

	@Override
	public Iterable<Group> save(Iterable<Group> groups) {
		return groupDAO.saveAll(groups);
	}

	@Override
	public Group update(Group group) {
		return groupDAO.save(group);
	}

	@Override
	public Iterable<Group> update(Iterable<Group> groups) {
		return groupDAO.saveAll(groups);
	}

	@Override
	public void remove(Group group) {
		groupDAO.delete(group);
	}

	@Override
	public Group addPersons(Long id, Iterable<Person> persons) {
		Group group = groupDAO.findById(id).get();
		for (Person person : persons) {
			if(null!=person) {
				person = personDAO.findById(person.getId()).get();
				group.getPersons().add(person);
				person.getGroups().add(group);
				personDAO.save(person);
			}
		}
		return groupDAO.save(group);
	}

}
