package com.bugquery.serverside.entities;

public class StackOverflowPost extends Post {
	public int postTypeId;
	public int parentId;
	public int acceptedAnswerId;
	public int score;
	public String body;
	public String title;
	public String tags;
	public int answerCount;
	public StackOverflowPost(String Id, String stackTrace, int postTypeId, int parentId,
			int acceptedAnswerId, int score ,String body, String title, String tags, int answerCount) {
		super(Id, stackTrace);
		this.postTypeId = postTypeId;
		this.parentId = parentId;
		this.acceptedAnswerId = acceptedAnswerId;
		this.score = score;
		this.body = body;
		this.title = title;
		this.tags = tags;
		this.answerCount = answerCount;
	}
}
