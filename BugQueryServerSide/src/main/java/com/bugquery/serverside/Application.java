package com.bugquery.serverside;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bugquery.serverside.examplesparser.ExamplesXMLCreator;
import com.bugquery.serverside.repositories.StackSearchRepository;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;

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
	
	@Autowired
	private StackTraceRetriever retriever;
	
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
				
				if ("--createExamples".equals(args[0])){
					new ExamplesXMLCreator(retriever).createExamplesXML();
					log.info("Created Examples");
				}
			}
		};
	}

}