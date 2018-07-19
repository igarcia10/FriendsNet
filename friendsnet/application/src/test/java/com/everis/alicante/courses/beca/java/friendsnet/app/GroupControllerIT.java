package com.everis.alicante.courses.beca.java.friendsnet.app;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.everis.alicante.courses.beca.java.friendsnet.persistence.dao.GroupDAO;
import com.everis.alicante.courses.beca.java.friendsnet.persistence.entity.Group;
import com.everis.alicante.courses.beca.java.friendsnet.service.dto.GroupDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class GroupControllerIT extends AbstractControllerIT<GroupDTO, Group, Long> {

	public GroupControllerIT(String URI, String findAllResult, String findByIdResult, String afterCreateResult,
			Class<GroupDTO> dtoClass) {
		super("/groups", findAllResult, findByIdResult, afterCreateResult, dtoClass);
	}

	@Override
	CrudRepository<Group, Long> getDAO() {
		return dao;
	}
	
	@Autowired
	private GroupDAO dao;

}
