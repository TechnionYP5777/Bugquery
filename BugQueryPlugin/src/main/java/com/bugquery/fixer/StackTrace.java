package com.bugquery.fixer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private final String content; // the whole stack-trace
	private int line; // line of the exception

	public StackTrace(final String stackTrace, final String exception, final List<String> stackOfCalls,
			final int line) {
		this.exception = exception;
		this.stackOfCalls = stackOfCalls;
		content = stackTrace;
		this.line = line;
	}

	public static StackTrace of(final String stackTrace) {
		return new StackTrace(stackTrace);
	}

	public StackTrace(final String stackTrace) {
		if (!StackTraceExtractor.isStackTrace(stackTrace)) {
			exception = StackTrace.noExceptionFound;
			stackOfCalls = null;
			line = -1;
		} else {
			exception = this.getException(stackTrace);
			stackOfCalls = StackTrace.getStackOfCalls(stackTrace);
			line = this.getLine(stackTrace);
		}
		content = stackTrace;
	}

	public String getException() {
		return exception;
	}

	public int getLine() {
		return line;
	}

	public List<String> getStackOfCalls() {
		return stackOfCalls;
	}

	public String getString() {
		return content;
	}

	private String getExceptionNameFromExceptionLine(final String exceptionLine) {
		if (exceptionLine.contains("Caused by:"))
			return exceptionLine.split(":")[indexOfExceptionNameInCausedBy].trim();
		final String $ = !exceptionLine.contains(":") ? exceptionLine : exceptionLine.split(":")[0].trim();
		return $.substring($.lastIndexOf(" ") + 1);
	}

	private String getException(final String stackTrace) {
		String $ = "";
		if (stackTrace.contains("Caused by:")) {
			final Pattern p = Pattern.compile(causedByRegex);
			for (final Matcher ¢ = p.matcher(stackTrace); ¢.find();)
				$ = ¢.group(0);
			$ = $.trim();
		} else if (!stackTrace.contains("Exception in"))
			$ = stackTrace.split("\n")[0];
		else {
			final Matcher m = Pattern.compile(exceptionRegex).matcher(stackTrace);
			if (!m.find())
				return StackTrace.noExceptionFound;
			$ = m.group(0).trim();
		}
		return getExceptionNameFromExceptionLine($);
	}

	private static List<String> getStackOfCalls(final String stackTrace) {
		final String[] calls = stackTrace.split("(\\t )*(\n)+");
		final List<String> $ = new ArrayList<>();
		for (final String ¢ : calls)
			$.add(¢.trim());
		return $;
	}

	// TODO: Doron, finish implementing
	/**
	 * ATTENTION: This function is on hold
	 * 
	 * Maybe this should give us List<Integer> of line numbers and List<String>
	 * of files
	 */
	private int getLine(final String stackTrace) {
		final Matcher m = Pattern.compile(atRegex).matcher(stackTrace);
		final boolean f = m.find();
		if (!f)
			return -1;
		return getLineNumberFromExceptionLine(m.group(0));
	}

	private int getLineNumberFromExceptionLine(final String exceptionLine) {
		int i = exceptionLine.length() - 1;
		while (exceptionLine.charAt(i) >= '0' && exceptionLine.charAt(i) <= '9')
			--i;
		return Integer.parseInt(exceptionLine.substring(i + 1, exceptionLine.length()));
	}

	@Override
	public boolean equals(final Object ¢) {
		return ¢ instanceof StackTrace && (¢ == this || getString().equals(((StackTrace) ¢).getString()));
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
