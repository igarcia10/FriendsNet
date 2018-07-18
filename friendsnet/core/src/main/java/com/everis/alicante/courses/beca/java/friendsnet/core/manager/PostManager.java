package com.everis.alicante.courses.beca.java.friendsnet.core.manager;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

public interface PostManager extends Manager<Post, Long> {
	
	public Post addLike(Long idPost, Long idPerson);
	
}
