package com.everis.alicante.courses.beca.java.friendsnet.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

public interface GroupDAO extends CrudRepository<Group, Long> {
	
	public Group addPersons(Iterable<Person> persons);

}