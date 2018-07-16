package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.PostManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.LikeDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PostDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

public class PostManagerImpl extends AbstractManager<Post, Long> implements PostManager {
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private LikeDAO likeDAO;

	@Override
	public Post addLike(Long id, Like like) {
		Post post = postDAO.findById(id).get();
		Like likeDB = likeDAO.findById(like.getId()).get();
		post.addLike(likeDB);
		like.setPost(post);
		likeDAO.save(likeDB);
		return postDAO.save(post);
	}

	@Override
	protected CrudRepository<Post, Long> getDAO() {
		return postDAO;
	}

}
