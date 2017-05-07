package com.bugquery.stacktrace;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * GetTrace is a class for methods that return Strings with stack traces, using
 * several inputs (e.g. consoles).
 *
 * @author Yosef
 * @since Nov 29 2016
 *
 */
public class GetTrace {

	/**
	 * @param exception,
	 *            a Throwable
	 * @return a String from @exception's stack trace
	 */
	public String fromException(final Throwable exception) {
		final StringWriter $ = new StringWriter();
		exception.printStackTrace(new PrintWriter($));
		return $ + "";
	}

}
