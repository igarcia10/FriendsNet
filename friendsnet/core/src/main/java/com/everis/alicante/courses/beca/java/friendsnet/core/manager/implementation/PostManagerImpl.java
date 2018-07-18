package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.AbstractManager;
import com.everis.alicante.courses.beca.java.friendsnet.core.manager.PostManager;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.LikeDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PersonDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.PostDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

@Service
public class PostManagerImpl extends AbstractManager<Post, Long> implements PostManager {

	@Autowired
	private PostDAO postDAO;

	@Autowired
	private LikeDAO likeDAO;
	
	@Autowired
	private PersonDAO personDAO;

	@Override
	public Post addLike(final Long id, final Like like) {
		Post post = postDAO.findById(id).orElse(null);
		if (null != post && null != like) {
			Like likeDB = likeDAO.findById(like.getId()).orElse(null);
			if (null != likeDB) {
				post.addLike(likeDB);
				like.setPost(post);
				likeDAO.save(likeDB);
				post = postDAO.save(post);
			}
		} else {
			post = null;
		}
		return post;
	}

	@Override
	protected CrudRepository<Post, Long> getDAO() {
		return postDAO;
	}
	
	public List<Post> findByPersonId(Long id) {
		final Person person = personDAO.findById(id).orElse(null);
		List<Post> posts = null;
		if(null!=person) {
			posts = postDAO.findByPersonId(id);
		}
		return posts;
	}

}
