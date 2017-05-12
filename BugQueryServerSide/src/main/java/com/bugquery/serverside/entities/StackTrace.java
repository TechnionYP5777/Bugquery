package com.bugquery.serverside.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private static final String exceptionRegex = "([ \\\\t\\\\n\\\\f\\\\r])*(Exception(.)*\"(.)*\"[: ](.)*[: ((\\r)*\\n)])";
	private static final int indexOfExceptionNameInCausedBy = 1;
	
	private String exception; // exception type
	@ElementCollection
	private List<String> stackOfCalls;
	private String content; // the whole stack-trace

	public StackTrace(String content, String exception, List<String> stackOfCalls) {
		this.exception = exception;
		this.stackOfCalls = stackOfCalls;
		this.content = content;
	}
	
	public StackTrace(String content) {
		if (!StackTraceExtractor.isStackTrace(content)) {
			this.exception = StackTrace.noExceptionFound;
			this.stackOfCalls = null;
		} else {
			this.exception = this.getException(content);
			this.stackOfCalls = StackTrace.getStackOfCalls(content);
		}
		this.content = content;
	}
	
	public String getException() {
		return this.exception;
	}
	
	public List<String> getStackOfCalls() {
		return this.stackOfCalls;
	}
	
	public String getString() {
		return this.content;
	}
	
	private String getExceptionNameFromExceptionLine(String exceptionLine) {
		if(exceptionLine.contains("Caused by:"))
			return exceptionLine.split(":")[indexOfExceptionNameInCausedBy].trim();
		String $ = !exceptionLine.contains(":") ? exceptionLine : exceptionLine.split(":")[0].trim();
		return $.substring($.lastIndexOf(" ") + 1);
	}
	
	private String getException(String stackTrace) {
		String $ = "";
		if(stackTrace.contains("Caused by:")) {
			Pattern p = Pattern.compile(this.causedByRegex);
			for (Matcher ¢ = p.matcher(stackTrace); ¢.find();)
				$ = ¢.group(0); 
			$ = $.trim();
		} else if (!stackTrace.contains("Exception in")) 
			$ = stackTrace.split("\n")[0];
		else {
			Matcher m = Pattern.compile(this.exceptionRegex).matcher(stackTrace);
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
		return ¢ instanceof StackTrace && (¢ == this || this.getString().equals(((StackTrace) ¢).getString()));
	}

	@Override public int hashCode() {
		return super.hashCode();
	}
}
