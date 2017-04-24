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
	public String id;
	public int postTypeId;
	public String parentId;
	public String acceptedAnswerId;
	public int score;
	@Column(columnDefinition = "Text")
	public String body;
	public String title;
	public String tags;
	public int answerCount;

	public StackOverflowPost(String id, int postTypeId, String parentId, String acceptedAnswerId, int score,
			String body, String title, String tags, int answerCount) {
		super(getStackTrace(body));
		this.id = id;
		this.postTypeId = postTypeId;
		this.parentId = parentId;
		this.acceptedAnswerId = acceptedAnswerId;
		this.score = score;
		this.body = body;
		this.title = title;
		this.tags = tags;
		this.answerCount = answerCount;
	}

	//TODO: need to decide which stacktrace to choose, for now, first one.
	// TODO why not use all as keys? --yg
	private static String getStackTrace(String body){
		return StackTraceExtractor.extract(body).get(0).getString();

	}
}
