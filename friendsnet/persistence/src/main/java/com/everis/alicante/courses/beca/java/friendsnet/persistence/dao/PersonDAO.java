package com.everis.alicante.courses.beca.java.friendsnet.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;

@Repository
public interface PersonDAO extends CrudRepository<Person, Long>{

}
