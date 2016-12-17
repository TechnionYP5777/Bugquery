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
	private static final String stackTraceRegularExpression = "([ \\t\\n\\f\\r])*(Exception)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*([ \t\f\r])*at(.*)((\n|\r\n)*([ \t\f\r])*(Caused by:)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*(...(.*)(more)(\\n|\\r\\n)))*";
	/*
	 * @deprecated, will be removed soon
	 */
	public static List<String> extractStringsStackTraces(String s) {
		List<String> $ = new ArrayList<>();
		if(s == null)
			return $;
		Pattern p = Pattern.compile(StackTraceExtractor.stackTraceRegularExpression);
		for (Matcher ¢ = p.matcher(StackTraceExtractor.removeHtmlTags(s)); ¢.find();)
			$.add(¢.group(0));
		return $;
	}
	
	public static List<StackTrace> extract(String s) {
		List<StackTrace> $ = new ArrayList<>();
		if(s == null)
			return $;
		Pattern p = Pattern.compile(StackTraceExtractor.stackTraceRegularExpression);
		for (Matcher ¢ = p.matcher(StackTraceExtractor.removeHtmlTags(s)); ¢.find();)
			$.add(new StackTrace(¢.group(0).trim()));
		return $;
	}
	
	public static String removeHtmlTags(String ¢) {
		return ¢.replaceAll("<[a-zA-Z0-9/]*>&#xA;&#xA;<[a-zA-Z0-9/]*>","\n").replaceAll("<[a-zA-Z0-9/]*>", "").replaceAll("&#xA;", "\n");
	}
}
