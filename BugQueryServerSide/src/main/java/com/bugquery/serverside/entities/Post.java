package com.bugquery.serverside.entities;

public abstract class Post {
	public String Id;
	public String stackTrace;
	public Post(String Id, String stackTrace) {
		this.Id = Id;
		this.stackTrace = stackTrace;
	}
}
