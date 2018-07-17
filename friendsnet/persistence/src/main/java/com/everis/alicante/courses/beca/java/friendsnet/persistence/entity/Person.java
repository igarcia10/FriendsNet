package com.everis.alicante.courses.beca.java.friendsnet.persistence.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "person_table")
public class Person implements FNEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String surname;
	private byte[] picture;

	@ManyToMany(mappedBy = "persons", fetch = FetchType.LAZY)
	private Set<Group> groups = new HashSet<>();

	@ManyToMany(mappedBy = "persons", fetch = FetchType.LAZY)
	private Set<Event> events = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "person_friends", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private Set<Person> friends;

	@ManyToMany(mappedBy="friends", fetch = FetchType.LAZY)
	private Set<Person> friendOf;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Like> likes = new ArrayList<>();
	
	public void addPost(Post post) {
		posts.add(post);
		post.setPerson(this);
    }
 
    public void removePost(Post post) {
        posts.remove(post);
        post.setPerson(null);
    }
    
    public void addLike(Like like) {
    	likes.add(like);
    	like.setPerson(this);
    }
 
    public void removeLike(Like like) {
    	likes.remove(like);
    	like.setPerson(null);
    }
    
    public Set<Person> getFriends(){
    	if(null==this.friends) {
    		friends=new HashSet<>();
    	}
    	return this.friends;
    }
    
    public Set<Person> getFriendOf(){
    	if(null==this.friendOf) {
    		friendOf=new HashSet<>();
    	}
    	return this.friendOf;
    }

}
