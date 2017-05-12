package com.bugquery.serverside.repositories;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.repositories.PostRepository;



@DataJpaTest
@RunWith(SpringRunner.class)
@Transactional
public class PostRepositoryTest {
	@Autowired
	PostRepository repository;

	@Test
	public void test() {
		Post p = repository.save(new Post(new StackTrace("hi")));
		List<Post> returnedPosts = Lists.newArrayList(repository.findAll());
		assertThat(returnedPosts.size(), is(1));
		assertThat(returnedPosts.get(0), is(p));
	}
}
