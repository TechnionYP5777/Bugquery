package com.bugquery.serverside.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * This is the entity which stores a post.
 * 
 * @author zivizhar, amit, tony
 */
@Entity
public class Post extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 0x76130BBB704F3901L;
	@Embedded
	private StackTrace stackTrace;
	@Column(columnDefinition = "Text")
	private String title;
	@Column(columnDefinition = "Text")
	private String question;
	@Column(columnDefinition = "Text")
	private String answer;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Post() {
		this.setId(null);
	}

	public Post(StackTrace stackTrace) {
		this();
		this.stackTrace = stackTrace;
	}

	public Post(String stackContent) {
		this();
		this.stackTrace = new StackTrace(stackContent);
	}

	public Post(Long id) {
		setId(id);
	}

	@Override
	public String toString() {
		return super.toString() + "\nStack Trace:" + stackTrace.getContent();
	}

	public void setStackTrace(StackTrace ¢) {
		this.stackTrace = ¢;
	}

	public StackTrace getStackTrace() {
		return stackTrace;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswerId() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
