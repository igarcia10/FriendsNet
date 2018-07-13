package com.everis.alicante.courses.beca.java.friendsnet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.everis.alicante.courses.beca.java.friendsnet"})
@EntityScan("com.everis.alicante.courses.beca.java.friendsnet.persistence.entity")
@EnableJpaRepositories("com.everis.alicante.courses.beca.java.friendsnet.persistence.dao")
public class FriendsnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsnetApplication.class, args);
	}
}
