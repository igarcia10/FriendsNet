package com.everis.alicante.courses.beca.java.friendsnet.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.alicante.courses.beca.java.friendsnet.core.manager.implementation.LikeManagerImpl;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Like;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.LikeDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/likes")
public class LikeController extends AbstractController<LikeDTO, Like, Long> {
	
	protected LikeController() {
		super(Like.class, LikeDTO.class);
	}
	
	@Autowired
	private LikeManagerImpl manager;
	
	@GetMapping("/post/{id}")
	public List<LikeDTO> getByPostId(@PathVariable("id") Long id) {
		List<Like> resultList = manager.getByPostId(id);
		List<LikeDTO> listDTO = new ArrayList<>();
		if (null != resultList) {
			for (Like like : resultList) {
				listDTO.add(mapper.map(like, LikeDTO.class));
			}
		}
		return listDTO;
	}

}
