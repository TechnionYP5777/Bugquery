package com.bugquery.serverside.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.exceptions.InternalServerException;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.stacktrace.StackTraceRetriever;

/**
 * 
 * @author Amit
 * @since Dec 24, 2016
 * This class represents a stack trace search
 * 
 */
@Entity
public class StackSearch {
	public enum Status {
		NEW, READY
	}

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	@Column(columnDefinition = "Text")
	private String trace;
	public Status status;
	@ElementCollection
	private List<Long> relatedPostsIds;

	// empty c'tor for the sake of JPA
	protected StackSearch() {
	}

	public StackSearch(String encodedTrace) {
		try {
			trace = URLDecoder.decode(encodedTrace, "UTF-8");
		} catch (@SuppressWarnings("unused") UnsupportedEncodingException e) {
			// Happens when encoding is not recognized. Since we use hard-coded
			// encoding "UTF-8", this cannot happen.
		}
		status = Status.NEW;
		relatedPostsIds = new ArrayList<>();
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

	public void setId(Long id) {
		this.id = id;
	}

	public void getReady(StackTraceRetriever r) throws InvalidStackTraceException {
		List<Post> $;
		try {
			$ = r.getMostRelevantPosts(trace, 10);
		} catch (GeneralDBException ¢) {
			throw new InternalServerException(¢);
		}
		for (Post p : $)
			relatedPostsIds.add(p.getId());
		status = Status.READY;
	}

	public List<Long> getRelatedPostsIds() {
		return relatedPostsIds;
	}
}