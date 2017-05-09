package com.bugquery.springdataexperimentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bugquery.springdataexperimentation.entities.Post;
import com.bugquery.springdataexperimentation.repositories.PostRepository;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(PostRepository repository) {
		return (args) -> {
			// save a couple of posts
			repository.save(new Post("a stack trace"));
			repository.save(new Post("another stack trace"));
			repository.save(new Post("third stack trace!"));
			repository.save(new Post("so many stack traces"));
			repository.save(new Post("so many stack traces"));

			// fetch all posts
			log.info("Posts found with findAll():");
			log.info("-------------------------------");
			for (Post post : repository.findAll()) {
				log.info(post.toString());
			}
			log.info("");

			// fetch an individual post by ID
			Post post = repository.findOne(1L);
			log.info("Post found with findOne(1L):");
			log.info("--------------------------------");
			log.info(post.toString());
			log.info("");

			// fetch posts by stack trace
			log.info("Post found with findByStackTrace('a stack trace'):");
			log.info("--------------------------------------------");
			for (Post p : repository.findByStackTrace("a stack trace")) {
				log.info(p.toString());
			}
			log.info("");
			
			log.info("Post found with findByStackTrace('so many stack traces'):");
			log.info("--------------------------------------------");
			for (Post p : repository.findByStackTrace("so many stack traces")) {
				log.info(p.toString());
			}
			log.info("");
		};
	}

}