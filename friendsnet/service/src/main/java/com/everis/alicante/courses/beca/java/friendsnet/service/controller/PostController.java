package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.PostManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PostDTO;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostManagerImpl manager;

	@Autowired
	private DozerBeanMapper mapper;

	@GetMapping
	public List<PostDTO> getAll() {
		List<Post> postList = (List<Post>) manager.findAll();
		List<PostDTO> postDTOList = new ArrayList<>();
		if (null != postList) {
			for (Post post : postList) {
				postDTOList.add(mapper.map(post, PostDTO.class));
			}
		}
		return postDTOList;
	}

	@GetMapping("/{id}")
	public PostDTO getById(@PathVariable("id") Long id) {
		final Post post = manager.findById(id);
		PostDTO postDTO = null;
		if (null != post) {
			postDTO = mapper.map(post, PostDTO.class);
		}
		return postDTO;
	}

	@PostMapping
	public PostDTO create(@RequestBody PostDTO post) {
		Post postDB = manager.save(mapper.map(post, Post.class));
		PostDTO postDTO = null;
		if (null != postDB) {
			postDTO = mapper.map(postDB, PostDTO.class);
		}
		return postDTO;
	}

	// TODO
	@GetMapping("/person/{id}")
	public Post getByPersonId() {
		return null;
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable("id") Long id) {
		final Post post = manager.findById(id);
		if (null != post) {
			manager.remove(post);
		}
	}

}
