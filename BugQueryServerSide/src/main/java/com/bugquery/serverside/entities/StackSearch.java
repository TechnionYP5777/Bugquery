package com.bugquery.serverside.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StackSearch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String trace;

	// empty c'tor for the sake of JPA
	protected StackSearch() {
	}

	public StackSearch(String trace) {
		try {
			this.trace = URLDecoder.decode(trace, "UTF-8");
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