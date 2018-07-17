package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PostDTO;

@RestController
@RequestMapping("/posts")
public class PostController extends AbstractController<PostDTO, Post, Long> {

	protected PostController() {
		super(Post.class, PostDTO.class);
	}

	// TODO
	@GetMapping("/person/{id}")
	public Post getByPersonId() {
		return null;
	}

}
