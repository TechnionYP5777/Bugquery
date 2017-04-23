package com.bugquery.fixer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bugquery.stacktrace.Extract;

/**
 * This class should be used for finding and extracting stack traces from
 * stackoverflow questions. It is based on the {@link Extract} class
 * 
 * @author rodedzats
 * @since 7.12.2016
 */
public class StackTraceExtractor {
	private static final String stackTraceRegularExpression = "(\\n|^)([ \\t\\f\\r])*([a-zA-Z0-9\\.]*Exception)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*([ \t\f\r])*at(.*)((\n|\r\n)*([ \t\f\r])*(Caused by:)?(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*(...(.*)(more)(\\n|\\r\\n)))*";
	private static final String semiStackTraceRegularExpression = "((\\n|\\r\\n)*([ \\t\\f\\r])*(Caused by:)?(.*)(\\n|\\r\\n)(([ \\t\\f\\r])*at(.*)(\\n|\\r\\n))*(...(.*)(more)(\\\\n|\\\\r\\\\n))*)";
	private static final Pattern stackTraceRegexPattern = Pattern.compile(stackTraceRegularExpression);
	private static final Pattern semiStackTraceRegexPattern = Pattern.compile(semiStackTraceRegularExpression);

	public static List<StackTrace> extract(final String ¢) {
		final List<StackTrace> $ = new ArrayList<>();
		if (¢ == null)
			return $;
		StackTraceExtractor.addToListAllMatchingRegex($, StackTraceExtractor.stackTraceRegexPattern, ¢);
		if ($.isEmpty())
			StackTraceExtractor.addToListAllMatchingRegex($, StackTraceExtractor.semiStackTraceRegexPattern, ¢);
		return $;
	}

	public static boolean isStackTrace(final String ¢) {
		return ¢ != null && (StackTraceExtractor.doesExistMatchingRegex(StackTraceExtractor.stackTraceRegexPattern, ¢)
				|| StackTraceExtractor.doesExistMatchingRegex(StackTraceExtractor.semiStackTraceRegexPattern, ¢));
	}

	private static void addToListAllMatchingRegex(final List<StackTrace> $, final Pattern p, final String s) {
		for (final Matcher ¢ = p.matcher(StackTraceExtractor.removeHtmlTags(s)); ¢.find();)
			$.add(new StackTrace(¢.group(0).trim()));
	}

	private static boolean doesExistMatchingRegex(final Pattern p, final String s) {
		return s != null && p != null && p.matcher(StackTraceExtractor.removeHtmlTags(s)).find();
	}

	public static String removeHtmlTags(final String ¢) {
		return ¢.replaceAll("<[a-zA-Z0-9/]*>&#xA;&#xA;<[a-zA-Z0-9/]*>", "\n").replaceAll("<[a-zA-Z0-9/]*>", "")
				.replaceAll("&#xA;", "\n");
	}
}
