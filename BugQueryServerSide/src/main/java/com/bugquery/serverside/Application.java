package com.bugquery.serverside;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bugquery.serverside.examples.ExamplesXMLCreator;
import com.bugquery.serverside.services.StackOverflowService;

/**
 * here everything starts, default class for running spring boot application
 * 
 * @author Amit
 * @since Dec 24, 2016
 *
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	StackOverflowService soService;

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		if (args.length > 0 && "--createExamples".equals(args[0]))
			ExamplesXMLCreator.activate();
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		if (args.length > 0) {
			if ("--updateDB".equals(args[0])) {
				soService.updatePosts();
				log.info("Updated DB");
			}
		}
	}

}