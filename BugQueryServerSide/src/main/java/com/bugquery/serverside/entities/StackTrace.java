package com.bugquery.serverside.entities;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import com.bugquery.serverside.stacktrace.StackTraceExtractor;
import com.bugquery.serverside.stacktrace.StackTraceUtility;

/**
 * An entity which represents a single stack trace. A StackTrace object can be built from 
 * a string which contains a stack trace. The created object will contain more information
 * about the stack trace, such as the exception type and stack of calls.
 * @author rodedzats
 * @since 14.12.2016
 */
@Embeddable
public class StackTrace {
	public static final String noExceptionFound = "NO_EXCEPTION_FOUND";
	
	@Column(columnDefinition = "Text")
	private String exception;
	@Column(columnDefinition = "Text")
	@ElementCollection
	private List<String> stackOfCalls;
	@Access(AccessType.PROPERTY)
	@Column(columnDefinition = "Text")
	private String content; // the whole stack-trace

	@SuppressWarnings("unused")
	private StackTrace() {
		// Empty c'tor for JPA
	}
	
	public StackTrace(String content, String exception, List<String> stackOfCalls) {
		this.exception = exception;
		this.stackOfCalls = stackOfCalls;
		this.content = content;
	}
	
	public StackTrace(String content) {
		this.setContent(content);
	}
	
	public String getException() {
		return this.exception;
	}
	
	public List<String> getStackOfCalls() {
		return this.stackOfCalls;
	}
	
	private void setContent(String content){
		if (!StackTraceExtractor.isStackTrace(content)) {
			this.exception = StackTrace.noExceptionFound;
			this.stackOfCalls = null;
		} else {
			this.exception = StackTraceUtility.getException(content);
			this.stackOfCalls = StackTraceUtility.getStackOfCalls(content);
		}
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	@Override public boolean equals(Object ¢) {
		return ¢ instanceof StackTrace && (¢ == this || this.getContent().equals(((StackTrace) ¢).getContent()));
	}

	@Override public int hashCode() {
		return super.hashCode();
	}
}
