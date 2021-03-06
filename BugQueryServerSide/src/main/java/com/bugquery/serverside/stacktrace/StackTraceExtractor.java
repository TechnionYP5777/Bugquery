package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bugquery.serverside.entities.StackTrace;

/**
 * This class should be used for finding and extracting stack traces from stackoverflow
 * posts. It is based on the ExtractTrace class of yosefraisman
 * The main purpose of this class is to get a string represnting a stackoverflow
 * post and returning the stacktrace in it.
 * @author rodedzats
 * @since 7.12.2016
 */
public class StackTraceExtractor {
	private static final String htmlNewLine = "&#xA;";
	private static final String htmlTag = "<[a-zA-Z0-9/]*>";
	private static final String htmlElement = "<[a-zA-Z0-9/]*>&#xA;&#xA;<[a-zA-Z0-9/]*>";
	private static final String stackTraceRegularExpression = "(\\n|^)([ \\t\\f\\r])*([a-zA-Z0-9\\.]*Exception)(.*)(\n|\r\n)(([ \t\f\r])*at(.+)(\n|\r\n))*([ \t\f\r])*at(.*)((\n|\r\n)*([ \t\f\r])*(Caused by:)?(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*(...(.*)(more)(\\n|\\r\\n)))*";
	private static final String semiStackTraceRegularExpression = "((\\n|\\r\\n)*([ \\t\\f\\r])*(Caused by:)(.*)(\\n|\\r\\n)(([ \\t\\f\\r])*at(.+)(\\n|\\r\\n))*(...(.*)(more)(\\n|\\r\\n))*)";
	private static final Pattern stackTraceRegexPattern = Pattern.compile(stackTraceRegularExpression);
	private static final Pattern semiStackTraceRegexPattern = Pattern.compile(semiStackTraceRegularExpression);
	
	/**
	 * This function finds all stack traces in a post 
	 * @param ¢ the string representing the post
	 * @return list of all stack traces in the posts
	 */
	public static List<StackTrace> extract(String ¢) {
		List<StackTrace> $ = new ArrayList<>();
		if(¢ == null)
			return $;
		StackTraceExtractor.addToListAllMatchingRegex($, StackTraceExtractor.stackTraceRegexPattern, ¢);
		if($.isEmpty())
			StackTraceExtractor.addToListAllMatchingRegex($, StackTraceExtractor.semiStackTraceRegexPattern, ¢);
		return $;
	}
	
	/**
	 * This functions determines wether an input string represents a stacktrace
	 * @param ¢ the input string 
	 * @return is the string a stacktrace
	 */
	public static boolean isStackTrace(String ¢) {
		return ¢ != null && (StackTraceExtractor.doesExistMatchingRegex(StackTraceExtractor.stackTraceRegexPattern, ¢)
				|| StackTraceExtractor.doesExistMatchingRegex(StackTraceExtractor.semiStackTraceRegexPattern, ¢));
	}
	
	private static void addToListAllMatchingRegex(List<StackTrace> $, Pattern p, String s) {
		for (Matcher ¢ = p.matcher(StackTraceExtractor.removeHtmlTags(s)); ¢.find();)
			$.add(new StackTrace(¢.group(0).trim()));
	}
	
	private static boolean doesExistMatchingRegex(Pattern p, String s) {
		return s != null && p != null
				&& (p.matcher(StackTraceExtractor.removeHtmlTags(s)).find());
	}
	
	public static String removeHtmlTags(String ¢) {
		return ¢.replaceAll(htmlElement,"\n").replaceAll(htmlTag, "").replaceAll(htmlNewLine, "\n");
	}
}
