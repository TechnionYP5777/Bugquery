package com.bugquery.serverside.entities;

import javax.persistence.Embedded;

/**
 * @author zivizhar
 */
public abstract class Post {
	@Embedded
	public StackTrace stackTrace;
	public Post(StackTrace stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	public Post(String stString) {
		this.stackTrace = new StackTrace(stString);
	}
	
	@Override
	public String toString() {
		return stackTrace.getString();
	}
}
