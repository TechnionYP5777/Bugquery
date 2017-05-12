package com.bugquery.serverside.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * @author zivizhar
 */
@Entity
public class Post extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 0x76130BBB704F3901L;
	@Embedded
	private StackTrace stackTrace;
	@Column(columnDefinition = "Text")
	private String question;
	@Column(columnDefinition = "Text")
	private String answer; 
	
	public Post() { 
		this.setId(null);
	}
	
	public Post(StackTrace stackTrace) {
		this();
		this.stackTrace = stackTrace;
	}

		
	public Post(String stackContent) {
		this();
		this.stackTrace = new StackTrace(stackContent);
	}
	
	public Post(Long id) {
		setId(id);
	}	

	@Override
	public String toString() {
		return super.toString() + "\nStack Trace:" + stackTrace.getString();
	}
	
	public void setStackTrace(StackTrace ¢) {
		this.stackTrace = ¢;
	}
	
	public StackTrace getStackTrace() {
		return stackTrace;
	}	

}
