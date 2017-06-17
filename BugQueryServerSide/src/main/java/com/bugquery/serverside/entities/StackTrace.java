package com.bugquery.serverside.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import com.bugquery.serverside.stacktrace.StackTraceExtractor;

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
	private static final String causedByRegex = "Caused by:.*[: ((\\r)*\\n)]";
	private static final String exceptionRegex = "([ \t\n\f\r])*(Exception(.)*\"(.)*\"[: ](.)*[: ((\\r)*\\n)])";
	private static final int indexOfExceptionNameInCausedBy = 1;
	private static final Pattern causedByPattern = Pattern.compile(StackTrace.causedByRegex);
	private static final Pattern exceptionPattern = Pattern.compile(StackTrace.exceptionRegex);
	
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
	
	public void setContent(String content){
		if (!StackTraceExtractor.isStackTrace(content)) {
			this.exception = StackTrace.noExceptionFound;
			this.stackOfCalls = null;
		} else {
			this.exception = StackTrace.getException(content);
			this.stackOfCalls = StackTrace.getStackOfCalls(content);
		}
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	private static String getExceptionNameFromExceptionLine(String exceptionLine) {
		if(exceptionLine.contains("Caused by:"))
			return exceptionLine.split(":")[indexOfExceptionNameInCausedBy].trim();
		String $ = !exceptionLine.contains(":") ? exceptionLine : exceptionLine.split(":")[0].trim();
		return $.substring($.lastIndexOf(" ") + 1);
	}
	
	private static String getException(String stackTrace) {
		String $ = "";
		if(stackTrace.contains("Caused by:")) {
			for (Matcher ¢ = StackTrace.causedByPattern.matcher(stackTrace); ¢.find();)
				$ = ¢.group(0); 
			$ = $.trim();
		} else if (!stackTrace.contains("Exception in")) {
			$ = stackTrace.split("[\\r\\n]+")[0];
		} else {
			Matcher m = StackTrace.exceptionPattern.matcher(stackTrace);
			if (!m.find())
				return StackTrace.noExceptionFound;
			$ = m.group(0).trim();
		}
		return getExceptionNameFromExceptionLine($);
	}
	
	private static List<String> getStackOfCalls(String stackTrace) {
		String[] calls = stackTrace.split("(\\t )*(\n)+");
		List<String> $ = new ArrayList<>();
		for(String ¢: calls)
			$.add(¢.trim());
		return $;
	}
	
	@Override public boolean equals(Object ¢) {
		return ¢ instanceof StackTrace && (¢ == this || this.getContent().equals(((StackTrace) ¢).getContent()));
	}

	@Override public int hashCode() {
		return super.hashCode();
	}
}
