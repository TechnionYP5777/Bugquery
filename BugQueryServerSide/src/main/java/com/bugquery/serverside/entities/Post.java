package com.bugquery.serverside.entities;

/**
 * @author zivizhar
 */
public abstract class Post {
	public StackTrace stackTrace;
	public Post(StackTrace stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	public Post(String stString) {
		this.stackTrace = new StackTrace(stString);
	}
	
	@Override
	public String toString() {
		return stackTrace.getStackTrace();
	}
}
