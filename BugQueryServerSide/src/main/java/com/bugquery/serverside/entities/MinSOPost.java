package com.bugquery.serverside.entities;

/** An entity which represents a minimal stackoverflow post. It contains only the question
 * part of the post. This class should be replaced later with a general stack overflow post.
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
