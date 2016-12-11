package com.bugquery.serverside.entities;

public class StackOverflowPost extends Post {
	int postTypeId;
	int parentId;
	int acceptedAnswerId;
	int score;
	String body;
	String title;
	String tags;
	int answerCount;
}
