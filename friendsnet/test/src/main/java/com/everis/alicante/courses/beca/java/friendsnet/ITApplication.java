package com.everis.alicante.courses.beca.java.friendsnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.everis.alicante.courses.beca.java.friendsnet"})
@EntityScan("com.everis.alicante.courses.beca.java.friendsnet.persistence.entity")
public class ITApplication {

	public static void main(String[] args) {
		SpringApplication.run(ITApplication.class, args);
	}
}
