package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.PostManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Post;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.LikeType;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.PostType;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PostDTO;

@RestController
@RequestMapping("/posts")
public class PostController extends AbstractController<PostDTO, Post, Long> {

	protected PostController() {
		super(Post.class, PostDTO.class);
	}

	@Autowired
	private PostManagerImpl manager;

	@GetMapping("/person/{id}")
	public List<PostDTO> getByPersonId(@PathVariable("id") Long id) {
		List<Post> resultList = manager.findByPersonId(id);
		List<PostDTO> listDTO = new ArrayList<>();
		if (null != resultList) {
			for (Post group : resultList) {
				listDTO.add(mapper.map(group, PostDTO.class));
			}
		}
		return listDTO;
	}

	@PostMapping("/{id}/person/{idperson}/like/{liketype}")
	public PostDTO addLike(@PathVariable("id") Long id, @PathVariable("idperson") Long idperson,
			@PathVariable("liketype") LikeType type) {
		return mapper.map(manager.addLike(id, idperson, type), PostDTO.class);
	}

	@PostMapping(value = "/new/{posttype}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public PostDTO createPost(@RequestBody PostDTO postDTO, @RequestPart(required = false) MultipartFile pictureFile,
			@PathVariable("posttype") PostType type) throws IOException {
		byte[] picture = null;
		if (null != pictureFile) {
			picture = pictureFile.getBytes();
		}
		return mapper.map(manager.createPost(mapper.map(postDTO, Post.class), picture, type), PostDTO.class);
	}

}
