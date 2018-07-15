package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.PostManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.LikeDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PostDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

public class PostManagerImpl implements PostManager {
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private LikeDAO likeDAO;

	@Override
	public Iterable<Post> findAll() {
		return postDAO.findAll();
	}

	@Override
	public Post findById(Long id) {
		return postDAO.findById(id).get();
	}

	@Override
	public Post save(Post post) {
		return postDAO.save(post);
	}

	@Override
	public Iterable<Post> save(Iterable<Post> posts) {
		return postDAO.saveAll(posts);
	}

	@Override
	public Post update(Post post) {
		return postDAO.save(post);
	}

	@Override
	public Iterable<Post> update(Iterable<Post> posts) {
		return postDAO.saveAll(posts);
	}

	@Override
	public void remove(Post post) {
		postDAO.delete(post);
	}

	@Override
	public Post addLike(Long id, Like like) {
		Post post = postDAO.findById(id).get();
		Like likeDB = likeDAO.findById(like.getId()).get();
		post.addLike(likeDB);
		like.setPost(post);
		likeDAO.save(likeDB);
		return postDAO.save(post);
	}

}
