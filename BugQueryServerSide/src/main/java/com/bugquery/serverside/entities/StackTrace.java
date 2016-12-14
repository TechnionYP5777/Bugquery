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
	private String exception;
	private List<String> stackOfCalls;
	private final String causedByRegex = "Caused by:.*[: \\n]";
	private final String exceptionRegex = "([ \\\\t\\\\n\\\\f\\\\r])*(Exception(.)*\"(.)*\"[: ](.)*[:  \\n])";
	private final int indexOfExceptionNameInCausedBy = 1;
	public StackTrace(String exception, List<String> stackOfCalls) {
		this.exception = exception;
		this.stackOfCalls = stackOfCalls;
	}
	
	public StackTrace(String stackTrace) {
		this.exception = this.getException(stackTrace);
		this.stackOfCalls = this.getStackOfCalls(stackTrace);
	}
	
	public String getException() {
		return this.exception;
	}
	
	public List<String> getStackOfCalls() {
		return this.stackOfCalls;
	}
	
	private String getExceptionNameFromExceptionLine(String exceptionLine) {
		return !exceptionLine.contains(":") ? exceptionLine.substring(exceptionLine.lastIndexOf(" ") + 1)
				: exceptionLine.split(":")[indexOfExceptionNameInCausedBy].trim();
	}
	
	private String getException(String stackTrace) {
		String exceptionLine = "";
		if(stackTrace.contains("Caused by:")) {
			Pattern p = Pattern.compile(this.causedByRegex);
			for (Matcher ¢ = p.matcher(stackTrace); ¢.find();)
				exceptionLine = ¢.group(0); 
			exceptionLine = exceptionLine.trim();
		} else {
			Pattern p = Pattern.compile(this.exceptionRegex);
			Matcher m = p.matcher(stackTrace);
			if (!m.find())
				throw new RuntimeException("Can't get exception from stacktrace: " + stackTrace);
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
}
