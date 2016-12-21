package com.bugquery.serverside.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bugquery.serverside.webapp")
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}