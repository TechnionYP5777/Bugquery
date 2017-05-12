package com.bugquery.serverside.repositories;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bugquery.serverside.entities.MyPost;

/**
 * @author Amit Ohayon
 * @since May 9, 2017
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class MyPostRepositoryTest {
	@Autowired
	MyPostRepository repository;

	@Test
	public void findPostByStackTrace() {
		List<MyPost> posts = 
				Arrays.asList(
						new MyPost("a stack trace"), 
						new MyPost("another stack trace"),
						new MyPost("another stack trace")
				), insertedList = new ArrayList<>(), returnedList;
		
		for (MyPost p : posts) 
			insertedList.add(repository.save(p));
		
		returnedList = repository.findByStackTrace("a stack trace"); 
		assertThat(returnedList.size(), is(1));
		assertThat(returnedList.get(0), is(insertedList.get(0)));
		
		returnedList = repository.findByStackTrace("another stack trace");
		assertThat(returnedList.size(), is(2));
		assertThat(returnedList.get(0), is(insertedList.get(1)));
		assertThat(returnedList.get(1), is(insertedList.get(2)));
	}
}
