package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bugquery.serverside.entities.StackTrace;

/**
 * This class should be used for finding and extracting stack traces from stackoverflow
 * questions. It is based on the ExtractTrace class of yosefraisman
 * @author rodedzats
 *
 */
public class StackTraceExtractor {
	private static final String stackTraceRegularExpression = "(\\n|^)([ \\t\\f\\r])*([a-zA-Z0-9\\.]*Exception)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*([ \t\f\r])*at(.*)((\n|\r\n)*([ \t\f\r])*(Caused by:)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*(...(.*)(more)(\\n|\\r\\n)))*";
	private static final String semiStackTraceRegularExpression = "((\\n|\\r\\n)*([ \\t\\f\\r])*(Caused by:)(.*)(\\n|\\r\\n)(([ \\t\\f\\r])*at(.*)(\\n|\\r\\n))*(...(.*)(more)(\\\\n|\\\\r\\\\n))*)";
	public static List<StackTrace> extract(String ¢) {
		List<StackTrace> $ = new ArrayList<>();
		if(¢ == null)
			return $;
		StackTraceExtractor.addToListAllMatchingRegex($, StackTraceExtractor.stackTraceRegularExpression, ¢);
		if($.isEmpty())
			StackTraceExtractor.addToListAllMatchingRegex($, StackTraceExtractor.semiStackTraceRegularExpression, ¢);
		return $;
	}
	
	public static boolean isStackTrace(String ¢) {
		return ¢ != null && (StackTraceExtractor.doesExistMatchingRegex(StackTraceExtractor.stackTraceRegularExpression, ¢)
				|| StackTraceExtractor.doesExistMatchingRegex(StackTraceExtractor.semiStackTraceRegularExpression, ¢));
	}
	
	private static void addToListAllMatchingRegex(List<StackTrace> $, String regex, String s) {
		for (Matcher ¢ = Pattern.compile(regex).matcher(StackTraceExtractor.removeHtmlTags(s)); ¢.find();)
			$.add(new StackTrace(¢.group(0).trim()));
	}
	
	private static boolean doesExistMatchingRegex(String regex, String s) {
		for (Matcher ¢ = Pattern.compile(regex).matcher(StackTraceExtractor.removeHtmlTags(s)); ¢.find();)
			return true;
		return false;
	}
	
	public static String removeHtmlTags(String ¢) {
		return ¢.replaceAll("<[a-zA-Z0-9/]*>&#xA;&#xA;<[a-zA-Z0-9/]*>","\n").replaceAll("<[a-zA-Z0-9/]*>", "").replaceAll("&#xA;", "\n");
	}
}
