package com.bugquery.fixer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bugquery.fixer.StackTraceExtractor;

/**
 * 
 * @author rodedzats
 * @since 14.12.2016
 */
public class StackTrace {
	public static final String noExceptionFound = "NO_EXCEPTION_FOUND";
	private final String atRegex = "at .*:[0-9]+";
	private final String causedByRegex = "Caused by:.*[: ((\\r)*\\n)]";
	private final String exceptionRegex = "([ \\\\t\\\\n\\\\f\\\\r])*(Exception(.)*\"(.)*\"[: ](.)*[: ((\\r)*\\n)])";
	private final int indexOfExceptionNameInCausedBy = 1;
	
	private String exception; // exception type
	private List<String> stackOfCalls;
	private String content; // the whole stack-trace
	private int line; // line of the exception

	public StackTrace(String stackTrace, String exception, List<String> stackOfCalls, int line) {
		this.exception = exception;
		this.stackOfCalls = stackOfCalls;
		this.content = stackTrace;
		this.line = line;
	}
	
	public static StackTrace of(String stackTrace) {
		return new StackTrace(stackTrace);
	}
	
	public StackTrace(String stackTrace) {
		if (!StackTraceExtractor.isStackTrace(stackTrace)) {
			this.exception = StackTrace.noExceptionFound;
			this.stackOfCalls = null;
			this.line = -1;
		} else {
			this.exception = this.getException(stackTrace);
			this.stackOfCalls = StackTrace.getStackOfCalls(stackTrace);
			this.line = this.getLine(stackTrace);
		}
		this.content = stackTrace;
	}
	
	public String getException() {
		return this.exception;
	}
	
	public int getLine() {
		return this.line;
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
	
	private int getLine(String stackTrace) {
		// TODO: Doron, implement
		Matcher m = Pattern.compile(this.atRegex).matcher(stackTrace);
		boolean f = m.find();
		if(!f) {
			return -1;
		}
		return getLineNumberFromExceptionLine(m.group(0));
	}
	
	private int getLineNumberFromExceptionLine(String exceptionLine) {
		int i = exceptionLine.length()-1;
		while(exceptionLine.charAt(i) >= '0' && exceptionLine.charAt(i) <= '9')
			--i;
		return Integer.parseInt(exceptionLine.substring(i+1, exceptionLine.length()));
	}

	@Override public boolean equals(Object ¢) {
		return ¢ instanceof StackTrace && (¢ == this || this.getString().equals(((StackTrace) ¢).getString()));
	}

	@Override public int hashCode() {
		return super.hashCode();
	}
}
