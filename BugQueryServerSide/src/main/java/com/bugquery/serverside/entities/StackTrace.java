package com.bugquery.serverside.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author rodedzats
 *
 */
public class StackTrace {
	public static final String noExceptionFound = "NO_EXCEPTION_FOUND";
	private final String causedByRegex = "Caused by:.*[: \\n]";
	private final String exceptionRegex = "([ \\\\t\\\\n\\\\f\\\\r])*(Exception(.)*\"(.)*\"[: ](.)*[:  \\n])";
	private final int indexOfExceptionNameInCausedBy = 1;
	
	private String exception; // exception type
	private List<String> stackOfCalls;
	private String content; // the whole stack-trace

	public StackTrace(String stackTrace, String exception, List<String> stackOfCalls) {
		this.exception = exception;
		this.stackOfCalls = stackOfCalls;
		this.content = stackTrace;
	}
	
	public StackTrace(String stackTrace) {
		this.exception = this.getException(stackTrace);
		this.stackOfCalls = StackTrace.getStackOfCalls(stackTrace);
		this.content = stackTrace;
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
		String relevantLine = !exceptionLine.contains(":") ? exceptionLine : exceptionLine.split(":")[0].trim();
		return relevantLine.substring(relevantLine.lastIndexOf(" ") + 1);
	}
	
	private String getException(String stackTrace) {
		String exceptionLine = "";
		if(stackTrace.contains("Caused by:")) {
			Pattern p = Pattern.compile(this.causedByRegex);
			for (Matcher ¢ = p.matcher(stackTrace); ¢.find();)
				exceptionLine = ¢.group(0); 
			exceptionLine = exceptionLine.trim();
		} else if (!stackTrace.contains("Exception in")) 
			exceptionLine = stackTrace.split("\n")[0];
		else {
			Pattern p = Pattern.compile(this.exceptionRegex);
			Matcher m = p.matcher(stackTrace);
			if (!m.find())
				return StackTrace.noExceptionFound;
			exceptionLine = m.group(0).trim();
		}
		return getExceptionNameFromExceptionLine(exceptionLine);
	}
	
	private static List<String> getStackOfCalls(String stackTrace) {
		String[] calls = stackTrace.split("(\\t )*(\n)+");
		List<String> $ = new ArrayList<>();
		for(String ¢: calls)
			$.add(¢.trim());
		return $;
	}
	
	@Override
    public boolean equals(Object ¢) {
		return ¢ instanceof StackTrace && (¢ == this || this.getString().equals(((StackTrace) ¢).getString()));
	}

  @Override public int hashCode() {
    return super.hashCode();
  }
}
