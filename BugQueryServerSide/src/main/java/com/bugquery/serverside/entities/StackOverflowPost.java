package com.bugquery.serverside.entities;

import com.bugquery.serverside.stacktrace.StackTraceExtractor;

/**
 * @author zivizhar
 */
public class StackOverflowPost extends Post {
	public String id;
	public int postTypeId;
	public String parentId;
	public String acceptedAnswerId;
	public int score;
	public String body;
	public String title;
	public String tags;
	public int answerCount;
	public StackOverflowPost(String id, int postTypeId, String parentId,
			String acceptedAnswerId, int score ,String body, String title, String tags, int answerCount) {
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
