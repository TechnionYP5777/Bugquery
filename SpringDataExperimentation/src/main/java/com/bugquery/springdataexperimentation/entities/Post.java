package com.bugquery.springdataexperimentation.entities;


import java.util.Arrays;
import java.util.List;

import javax.persistence.ElementCollection;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Post class.
 * 
 * @author Amit Ohayon
 * @since May 9, 2017
 */
@Entity
public class Post extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -0x630A44B439FC66FAL;
	private String stackTrace;
	
	@ElementCollection
	private List<String> l;
	
	public Post() {
		this.setId(null);
		l = Arrays.asList("hi","shalom","kise","leyad","halon");

	}
	
	public Post(String stackTrace) {
		this();
		this.stackTrace = stackTrace;
	}

	/**
	 * Creates a new post instance.
	 */
	public Post(Long id) {
		this.setId(id);
	}

	/**
	 * Returns the stack trace.
	 * 
	 * @return
	 */
	public String getStackTrace() {

		return stackTrace;
	}

	/**
	 * @param stackTrace
	 *            the stack trace to set
	 */
	public void setStackTracee(String stackTrace) {
		this.stackTrace = stackTrace;
	}

}
