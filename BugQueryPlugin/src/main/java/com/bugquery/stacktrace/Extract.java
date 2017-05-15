package com.bugquery.stacktrace;

import java.util.ArrayList;
import java.util.regex.*;

/**
 * offers {@link #trace(String)} which gets some output and (given that the
 * output contains one) extracts a stack trace from it.
 *
 * @author Yosef
 * @since Dec 5, 2016
 *
 */
public interface Extract {
	Pattern tracePattern = Pattern.compile(
			"(([ \t\n\f\r])*Caused by|Exception)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*([ \t\f\r])*at(.*)");

	String notFound = "No stack trace detected.";

	/**
	 * @param ¢
	 *            the output from which trace extraction is needed
	 * @return {@link #notFound} if @¢ doesn't contain any stack traces. else,
	 *         returns that trace. [[SuppressWarningsSpartan]] - Spartanizer
	 *         wants to rename m "¢", creating a bug (¢ already exists)
	 */
	public static String trace(final String ¢) {
		if (¢ == null)
			return notFound;
		String $ = "";
		/*
		 * TODO: Search in documentation of regular expressions. There are magic
		 * code that can make the regular expression more readable and more
		 * maintainable.
		 *
		 * Also, break the RE into components, and define each in a private
		 * static final String. Do not use String +, but do use String.format
		 *
		 */
		for (final Matcher m = tracePattern.matcher(¢); m.find();)
			$ += m.group(0);
		return $.length() > 0 ? $ : notFound;
	}
	
	static ArrayList<String> links(String trace) {
		return new ArrayList<>();		
	}
}
