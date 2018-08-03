package com.everis.alicante.courses.beca.java.friendsnet.persistence.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;

@Repository
public interface LikeDAO extends CrudRepository<Like, Long> {

	public Like findByPersonIdAndPostId(Long idPerson, Long idPost);
	public List<Like> findByPostId(Long id);

}
