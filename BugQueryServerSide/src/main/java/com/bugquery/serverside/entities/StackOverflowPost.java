package com.bugquery.serverside.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bugquery.serverside.stacktrace.StackTraceExtractor;

/**
 * @author zivizhar
 */
@Entity
public class StackOverflowPost extends Post {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long localId;
	private int stackOvaerflowId;
	private int postTypeId;
	private int parentId;
	private int acceptedAnswerId;
	private int score;
	@Column(columnDefinition = "Text")
	private String body;
	private String title;
	private String tags;
	private int answerCount;
	
	public StackOverflowPost() {
		
	}
	
	public StackOverflowPost(int id, int postTypeId, int parentId, int acceptedAnswerId, int score,
			String body, String title, String tags, int answerCount) {
		super(getStackTrace(body));
		this.stackOvaerflowId = id;
		this.postTypeId = postTypeId;
		this.parentId = parentId;
		this.acceptedAnswerId = acceptedAnswerId;
		this.score = score;
		this.body = body;
		this.title = title;
		this.tags = tags;
		this.answerCount = answerCount;
	}
	

	public void setStackOverflowId(int id) {
		this.stackOvaerflowId = id;
	}

	public void setPostTypeId(int postTypeId) {
		this.postTypeId = postTypeId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setAcceptedAnswerId(int acceptedAnswerId) {
		this.acceptedAnswerId = acceptedAnswerId;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	
	

	//TODO: need to decide which stacktrace to choose, for now, first one.
	// TODO why not use all as keys? --yg
	private static String getStackTrace(String body){
		return StackTraceExtractor.extract(body).get(0).getString();

	}
}
