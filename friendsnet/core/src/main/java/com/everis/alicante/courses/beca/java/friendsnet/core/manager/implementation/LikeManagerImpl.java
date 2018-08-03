package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.LikeManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.LikeDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;

public class LikeManagerImpl extends AbstractManager<Like, Long> implements LikeManager {
	
	@Autowired
	private LikeDAO likeDAO;

	@Override
	protected CrudRepository<Like, Long> getDAO() {
		return this.likeDAO;
	}
	
	public List<Like> getByPostId(Long id) {
		return this.likeDAO.findByPostId(id);
	}

}
