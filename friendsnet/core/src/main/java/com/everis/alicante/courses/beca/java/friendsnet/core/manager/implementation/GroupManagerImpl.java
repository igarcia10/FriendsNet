package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

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
		Group group = this.getDAO().findById(id).get();
		for (Person person : persons) {
			if(null!=person) {
				person = personDAO.findById(person.getId()).get();
				group.getPersons().add(person);
				person.getGroups().add(group);
				personDAO.save(person);
			}
		}
		return this.getDAO().save(group);
	}

	@Override
	protected GroupDAO getDAO() {
		return groupDAO;
	}

}
