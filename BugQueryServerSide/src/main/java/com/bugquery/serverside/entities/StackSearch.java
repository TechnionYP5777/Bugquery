package com.bugquery.serverside.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Amit
 * @since Dec 24, 2016
 * This class represents a stack trace search
 * 
 */
@Entity
public class StackSearch {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	
	@Column(columnDefinition="Text")
	private String trace;

	// empty c'tor for the sake of JPA
	protected StackSearch() {
	}

	public StackSearch(String encodedTrace) {
		try {
			this.trace = URLDecoder.decode(encodedTrace, "UTF-8");
		} catch (@SuppressWarnings("unused") UnsupportedEncodingException e) {
		  // TODO : handle this exception
		}
	}

	@Override
	public String toString() {
		return "StackSearch [id=" + id + ", trace=" + trace + "]";
	}

	public Long getId() {
		return id;
	}

	public String getTrace() {
		return trace;
	}
}