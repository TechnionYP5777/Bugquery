package com.bugquery.serverside.entities;

import javax.persistence.Embedded;

/**
 * @author zivizhar
 */
public class Post {
	Long id;
	public StackTrace stackTrace;
	
	public Post(StackTrace stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	public Post(Long id, StackTrace stackTrace) {
		this.id = id;
		this.stackTrace = stackTrace;
	}
	
	public Post(String stString) {
		this.stackTrace = new StackTrace(stString);
	}
	
	public Post(Long id, String stString) {
		this.id = id;
		this.stackTrace = new StackTrace(stString);
	}
	
	@Override
	public String toString() {
		return stackTrace.getString();
	}
	public void setStackTrace(StackTrace ¢) {
		this.stackTrace = ¢;
	}
	
}
