package com.everis.alicante.courses.beca.java.friendsnet.service.controller.abstracttest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Person;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.PersonDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FooPersonTest extends AbstractControllerTest<PersonDTO, Person, Long>{

	public FooPersonTest() {
		super(Person.class, PersonDTO.class, "/persons");
	}
	
	@Test
	public void testFindAll() throws Exception {
		this.testFindAll();
	}

}
