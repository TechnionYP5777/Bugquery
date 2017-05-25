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

	// For demonstration purposes - commented out because messes with tests
//	@Bean
//	public CommandLineRunner demo(PostRepository r) {
//		return args -> {
//			r.save(new Post("a stack trace"));
//			r.save(new Post("another stack trace"));
//			r.save(new Post("third stack trace!"));
//			r.save(new Post("so many stack traces"));
//			r.save(new Post("so many stack traces"));
//			log.info("Posts found with findAll():");
//			log.info("-------------------------------");
//			for (Post post : r.findAll())
//				log.info(post.toString());
//			log.info("");
//			Post post = r.findOne(1L);
//			log.info("Post found with findOne(1L):");
//			log.info("--------------------------------");
//			log.info(post.toString());
//			log.info("");
//			log.info("Post found with findByStackTrace('a stack trace'):");
//			log.info("--------------------------------------------");
//			for (Post p : r.findByStackTrace("a stack trace"))
//				log.info(p.toString());
//			log.info("");
//			log.info("Post found with findByStackTrace('so many stack traces'):");
//			log.info("--------------------------------------------");
//			for (Post p : r.findByStackTrace("so many stack traces"))
//				log.info(p.toString());
//			log.info("");
//		};
//	}


}
