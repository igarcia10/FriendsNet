package com.everis.alicante.courses.beca.java.friendsnet.persistence.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;

@Repository
public interface GroupDAO extends CrudRepository<Group, Long> {
	
	public List<Group> findByPersonsId(Long id);

}
