package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class should be used for finding and extracting stack traces from stackoverflow
 * questions. It is based on the ExtractTrace class of yosefraisman
 * @author rodedzats
 *
 */
public class StackTraceExtractor {
	private static final String stackTraceRegularExpression = "([ \\t\\n\\f\\r])*(Exception)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*([ \t\f\r])*at(.*)((\n|\r\n)*([ \t\f\r])*(Caused by:)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*(...(.*)(more)(\\n|\\r\\n)))*";
	public static List<String> extract(String s) {
		List<String> $ = new ArrayList<>();
		if(s == null)
			return $;
		Pattern p = Pattern.compile(StackTraceExtractor.stackTraceRegularExpression);
		for (Matcher ¢ = p.matcher(s); ¢.find();)
			$.add(¢.group(0));
		return $;
	}
}
