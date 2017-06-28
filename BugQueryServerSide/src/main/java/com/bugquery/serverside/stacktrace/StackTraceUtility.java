package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bugquery.serverside.entities.StackTrace;

/**
 * Utility class used by the StackTrace entity for getting data from a stack trace
 * given as a simple string
 * @author rodedzats
 * @since 22.6.2017
 */
public class StackTraceUtility {
	private static final String causedByRegex = "Caused by:.+[((\\r)*\\n)]";
	private static final String exceptionRegex = "([ \t\n\f\r])*(Exception(.)*\"(.)*\"[: ](.)*[: ((\\r)*\\n)])";
	private static final String exceptionIn = "Exception in";
	private static final String causedBy = "Caused by:";
	private static final int indexOfExceptionNameInCausedBy = 1;
	private static final Pattern causedByPattern = Pattern.compile(StackTraceUtility.causedByRegex);
	private static final Pattern exceptionPattern = Pattern.compile(StackTraceUtility.exceptionRegex);
	
	private static String getExceptionNameFromExceptionLine(String exceptionLine) {
		if (exceptionLine.contains(StackTraceUtility.causedBy)) {
			String[] lines = exceptionLine.split(":");
			String exceptionName = lines.length <= indexOfExceptionNameInCausedBy ? ""
					: exceptionLine.split(":")[indexOfExceptionNameInCausedBy];
			return !"".equals(exceptionName) && exceptionName != null ? exceptionName.trim()
					: StackTrace.noExceptionFound;
		}
		String $ = !exceptionLine.contains(":") ? exceptionLine : exceptionLine.split(":")[0].trim();
		int lastSpace = $.lastIndexOf(" ");
		return lastSpace > $.length() - 1 ? StackTrace.noExceptionFound : $.substring(lastSpace + 1);
	}
	
	/**
	 * This function finds and returns the exception type of a given stacktrace.
	 * If the given stacktrace is not in the right format or in fact
	 * not a stacktrace then StackTrace.noExceptionFound is returned
	 * @param stackTrace the stacktrace whose type needs to be found
	 * @return the type of the given stacktrace or StackTrace.noExceptionFound on error
	 */
	public static String getException(String stackTrace) {
		String $ = "";
		if (stackTrace.contains(StackTraceUtility.causedBy)) {
			for (Matcher ¢ = StackTraceUtility.causedByPattern.matcher(stackTrace); ¢.find();)
				$ = ¢.group(0);
			$ = $.trim();
		} else if (!stackTrace.contains(StackTraceUtility.exceptionIn)) 
			$ = stackTrace.split("[\\r\\n]+")[0];
		else {
			Matcher m = StackTraceUtility.exceptionPattern.matcher(stackTrace);
			if (!m.find())
				return StackTrace.noExceptionFound;
			$ = m.group(0).trim();
		}
		return "".equals($) || $ == null ? StackTrace.noExceptionFound : getExceptionNameFromExceptionLine($.trim());
	}
	
	/**
	 * @param stackTrace
	 * @return list of all lines in a given stacktrace
	 */
	public static List<String> getStackOfCalls(String stackTrace) {
		String[] calls = stackTrace.split("(\\t )*(\n)+");
		List<String> $ = new ArrayList<>();
		for(String ¢: calls)
			$.add(¢.trim());
		return $;
	}
}
