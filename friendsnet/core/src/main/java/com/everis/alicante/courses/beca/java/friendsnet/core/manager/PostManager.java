package com.everis.alicante.courses.beca.java.friendsnet.core.manager;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;

public interface PostManager extends Manager<Post, Long> {
	
	public Post addLike(Long id, Like like);
	
}
