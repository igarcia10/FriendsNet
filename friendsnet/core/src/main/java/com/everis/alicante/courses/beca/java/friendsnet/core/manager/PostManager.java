package com.everis.alicante.courses.beca.java.friendsnet.core.manager;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.LikeType;

public interface PostManager extends Manager<Post, Long> {
	
	public Post addLike(Long idPost, Long idPerson, LikeType type);
	
}
