package com.bugquery.serverside;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * here everything starts, default class for running spring boot application
 * 
 * @author Amit
 * @since Dec 24, 2016
 *
 */
@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
	
	
	// Has to be non-static, doesn't work otherwise!
	@Bean
	@SuppressWarnings("static-method")
	public CommandLineRunner checkDBargs() {
		return args -> {
			if (args.length > 0) {
				if ("--createDB".equals(args[0])) {
					// TODO: create db
					log.info("Created DB");
				}
				if ("--updateDB".equals(args[0])) {
					// TODO: update db
					log.info("Updated DB");
				}
			}
		};
	}

}