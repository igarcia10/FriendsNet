package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.GroupManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.GroupDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;

@Service
public class GroupManagerImpl implements GroupManager {

	@Autowired
	private GroupDAO dao;

	@Override
	public Iterable<Group> findAll() {
		return dao.findAll();
	}

	@Override
	public Group findById(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public Group save(Group group) {
		return dao.save(group);
	}

	@Override
	public Iterable<Group> save(Iterable<Group> groups) {
		return dao.saveAll(groups);
	}

	@Override
	public Group update(Group group) {
		return dao.save(group);
	}

	@Override
	public Iterable<Group> update(Iterable<Group> groups) {
		return dao.saveAll(groups);
	}

	@Override
	public void remove(Group group) {
		dao.delete(group);
	}

	@Override
	public Group addPersons(Iterable<Group> groups) {
		// TODO Auto-generated method stub
		return null;
	}

}
