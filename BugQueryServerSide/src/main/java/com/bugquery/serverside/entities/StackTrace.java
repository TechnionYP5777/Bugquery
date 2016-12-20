package com.bugquery.serverside.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author rodedzats
 *
 */
public class StackTrace {
	private String exception; // exception type
	private List<String> stackOfCalls;
	private String stackTrace; // the whole stack-trace
	private final String causedByRegex = "Caused by:.*[: \\n]";
	private final String exceptionRegex = "([ \\\\t\\\\n\\\\f\\\\r])*(Exception(.)*\"(.)*\"[: ](.)*[:  \\n])";
	private final int indexOfExceptionNameInCausedBy = 1;
	public StackTrace(String stackTrace, String exception, List<String> stackOfCalls) {
		this.exception = exception;
		this.stackOfCalls = stackOfCalls;
		this.stackTrace = stackTrace;
	}
	
	public StackTrace(String stackTrace) {
		this.exception = this.getException(stackTrace);
		this.stackOfCalls = this.getStackOfCalls(stackTrace);
		this.stackTrace = stackTrace;
	}
	
	public String getException() {
		return this.exception;
	}
	
	public List<String> getStackOfCalls() {
		return this.stackOfCalls;
	}
	
	public String getStackTrace() {
		return this.stackTrace;
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
				throw new RuntimeException("Can't get exception from stack trace: " + stackTrace);
			exceptionLine = m.group(0).trim();
		}
		return getExceptionNameFromExceptionLine(exceptionLine);
	}
	
	private List<String> getStackOfCalls(String stackTrace) {
		String[] calls = stackTrace.split("(\\t )*(\n)+");
		List<String> $ = new ArrayList<>();
		for(String ¢: calls)
			$.add(¢.trim());
		return $;
	}
	
	@Override
    public boolean equals(Object ¢) {
		return ¢ instanceof StackTrace && (¢ == this || this.getStackTrace().equals(((StackTrace) ¢).getStackTrace()));
	}
}
