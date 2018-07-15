package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.LikeManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.LikeDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;

public class LikeManagerImpl implements LikeManager {
	
	@Autowired
	private LikeDAO dao;

	@Override
	public Iterable<Like> findAll() {
		return dao.findAll();
	}

	@Override
	public Like findById(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public Like save(Like like) {
		return dao.save(like);
	}

	@Override
	public Iterable<Like> save(Iterable<Like> likes) {
		return dao.saveAll(likes);
	}

	@Override
	public Like update(Like like) {
		return dao.save(like);
	}

	@Override
	public Iterable<Like> update(Iterable<Like> likes) {
		return dao.saveAll(likes);
	}

	@Override
	public void remove(Like like) {
		dao.delete(like);
	}

}
