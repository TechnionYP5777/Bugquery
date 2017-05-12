package com.bugquey.serverside.repositories;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.repositories.PostRepository;



@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class PostRepositoryTest {
	@Autowired
	PostRepository repository;
	
	public void test() {
		repository.save(new Post(new StackTrace("hi")));
		for (Post p: repository.findAll()){
			System.out.println(p.stackTrace.getString());
		}
			
	}

}
