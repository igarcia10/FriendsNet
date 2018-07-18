package com.everis.alicante.courses.beca.java.friendsnet.persistence.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

@Repository
public interface PostDAO extends CrudRepository<Post, Long> {
	
	public List<Post> findByPersonId(Long id);

}
