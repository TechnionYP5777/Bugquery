package com.bugquery.serverside.entities;

/**
 * @author yonzarecki
 * @since 21.12.16
 */
public class MinSOPost extends Post {

	public String questionString;
	
	public MinSOPost(StackTrace stackTrace, String questionString) {
		super(stackTrace);
		this.questionString = questionString;
	}
	
	public MinSOPost(String stString, String questionString) {
		super(stString);
		this.questionString = questionString;
	}

}
