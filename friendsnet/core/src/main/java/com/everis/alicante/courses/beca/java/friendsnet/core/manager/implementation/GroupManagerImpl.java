package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.GroupManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.GroupDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@Service
public class GroupManagerImpl extends AbstractManager<Group, Long> implements GroupManager {

	@Autowired
	private GroupDAO groupDAO;

	@Autowired
	private PersonDAO personDAO;

	@Override
	public Group addPersons(Long id, Iterable<Person> persons) {
		Group group = this.getDAO().findById(id).orElse(null);
		if (null != persons && null != group) {
			for (Person person : persons) {
				if (null != person) {
					person = personDAO.findById(person.getId()).orElse(null);
					if (null != person) {
						group.getPersons().add(person);
						person.getGroups().add(group);
						personDAO.save(person);
					}
				}
			}
			group = this.getDAO().save(group);
		} else if (null != group) {
			group = null;
		}
		return group;
	}

	@Override
	protected GroupDAO getDAO() {
		return groupDAO;
	}

	public List<Group> findByPersonsId(Long id) {
		final Person person = personDAO.findById(id).orElse(null);
		List<Group> groups = null;
		if (null != person) {
			groups = this.getDAO().findByPersonsId(id);
		}
		return groups;
	}

	public Group createGroup(String name, byte[] picture) {
		final Group group = new Group();
		group.setName(name);
		group.setPicture(picture);
		return groupDAO.save(group);
	}

}
