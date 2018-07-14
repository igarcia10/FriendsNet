package com.everis.alicante.courses.beca.java.friendsnet.core.manager;

import com.everis.alicante.courses.beca.java.friendsnet.core.Manager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

public interface GroupManager extends Manager<Group, Long> {
	
	public Group addPersons(Long id, Iterable<Person> persons);

}
