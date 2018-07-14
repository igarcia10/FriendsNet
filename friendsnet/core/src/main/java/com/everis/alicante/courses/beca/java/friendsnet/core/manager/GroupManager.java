package com.everis.alicante.courses.beca.java.friendsnet.core.manager;

import com.everis.alicante.courses.beca.java.friendsnet.core.Manager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;

public interface GroupManager extends Manager<Group, Long> {
	
	public Group addPersons(Iterable<Group> groups);

}
