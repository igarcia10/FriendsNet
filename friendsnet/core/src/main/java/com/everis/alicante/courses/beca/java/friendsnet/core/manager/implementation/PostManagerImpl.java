package com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation;

import java.util.Date;
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
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.LikeType;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.PostType;

@Service
public class PostManagerImpl extends AbstractManager<Post, Long> implements PostManager {

	@Autowired
	private PostDAO postDAO;

	@Autowired
	private PersonDAO personDAO;

	@Autowired
	private LikeDAO likeDAO;

	@Override
	public Post addLike(final Long idPost, Long idPerson, LikeType type) {
		Post post = postDAO.findById(idPost).orElse(null);
		Person person = personDAO.findById(idPerson).orElse(null);
		if (null != post && null != person) {
			Like like = likeDAO.findByPersonId(idPerson);
			if (null == like) {
				like = this.createLike(idPost, idPerson, type);
				post.getLikes().add(like);
				person.getLikes().add(like);
				postDAO.save(post);
			} else {
				like.setType(type);
				likeDAO.save(like);
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
		if (null != person) {
			posts = postDAO.findByPersonId(id);
		}
		return posts;
	}

	private Like createLike(Long idPost, Long idPerson, LikeType type) {
		final Like like = new Like();
		like.setPost(postDAO.findById(idPost).get());
		like.setPerson(personDAO.findById(idPerson).get());
		like.setType(type);
		Date creationDate = new Date();
		like.setCreationDate(creationDate);
		return like;
	}

	public Post createPost(String text, byte[] picture, PostType type) {
		final Post post = new Post();
		final Date creationDate = new Date();
		post.setCreationDate(creationDate);
		post.setType(type);
		post.setText(text);
		post.setPicture(picture);
		return postDAO.save(post);
	}

}
