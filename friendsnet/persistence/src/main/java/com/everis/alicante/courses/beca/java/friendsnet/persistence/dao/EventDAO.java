package com.everis.alicante.courses.beca.java.friendsnet.persistence.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Event;

@Repository
public interface EventDAO extends CrudRepository<Event, Long>{
	
	public List<Event> findByPersonsId(Long id);

}
