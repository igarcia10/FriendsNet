package com.everis.alicante.courses.beca.java.friendsnet.persistence.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.enums.PostType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "post_table")
public class Post implements FNEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String text;
	
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@Enumerated(EnumType.STRING)
	private PostType type;
	private byte[] picture;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id")
    private Person person;
	
	@OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Like> likes = new ArrayList<>();
	
	public void addLike(Like like) {
    	likes.add(like);
    	like.setPost(this);
    }
 
    public void removeLike(Like like) {
    	likes.remove(like);
    	like.setPost(null);
    }

}
