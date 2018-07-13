package com.everis.alicante.courses.beca.java.friendsnet.core.manager;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

public interface PersonManager extends Manager<Person, Long> {
	
	public Person relatePersons(Iterable<Person> persons);

}
