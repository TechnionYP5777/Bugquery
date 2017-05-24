package com.bugquery.stacktrace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import com.bugquery.markers.MarkerInformation;

/**
 * offers {@link #trace(String)} which gets some output and (given that the
 * output contains one) extracts a stack trace from it.
 *
 * @author Yosef, Doron
 * @since Dec 5, 2016
 */
public interface Extract {
	static final Pattern tracePattern = Pattern.compile(
			"(([ \t\n\f\r])*Caused by|Exception)(.*)(\n|\r\n)(([ \t\f\r])*at(.*)(\n|\r\n))*([ \t\f\r])*at(.*)");
	static final Pattern linkPattern = Pattern
			.compile("at [^(]*\\([\\w\\.]+:\\d+\\)");
	public String notFound = "No stack trace detected.";

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

	/**
	 * @param ¢
	 *            - a String that includes a trace with links
	 * @return list of links in ¢, in "filename:line_number" format
	 */
	public static ArrayList<String> links(String ¢) {
		ArrayList<String> $ = new ArrayList<>();
		if (¢ == null)
			return $;

		¢ = trace(¢);
		for (final Matcher m = linkPattern.matcher(¢); m.find();) {
			String tmp = m.group(0);
			$.add(tmp.substring(1, tmp.length() - 1));
		}
		return $;
	}

	public static ArrayList<MarkerInformation> markersInfo(
			String ¢) {
		ArrayList<MarkerInformation> $ = new ArrayList<>();
		if (¢ == null)
			return $;

		¢ = trace(¢);
		for (final Matcher m = linkPattern.matcher(¢); m.find();) {
			String tmp = m.group(0);
			tmp = tmp.substring(3, tmp.length());
			String tmpLink = tmp.substring(tmp.indexOf('(') + 1,
					tmp.length() - 1);
			String tmpPackage = tmp.substring(0, tmp.indexOf('('));
			tmpPackage = tmpPackage.substring(0, tmpPackage.lastIndexOf('.'));
			int idx = tmpPackage.lastIndexOf('.');
			if (idx == -1)
				tmpPackage = "";
			else
				tmpPackage = tmpPackage.substring(0, idx);

			tmpPackage = toFolder(tmpPackage);
			$.add(new MarkerInformation(tmpPackage,
					filename(tmpLink), line(tmpLink)));
		}
		return $;
	}

	static int line(String link) {
		return Integer.parseInt(link.substring(link.indexOf(':') + 1));
	}

	static String filename(String link) {
		return link.substring(0, link.indexOf(':'));
	}

	public static List<String> files(String trace) {
		ArrayList<String> $ = new ArrayList<String>();
		for (String l : Extract.links(trace)) {
			$.add(filename(l));
		}
		return $;
	}

	/**
	 * @param trace
	 *            a String that includes a trace with links
	 * @return a list of line numbers in which we need to put our markers
	 */
	public static Map<String, List<Integer>> filesToLines(String trace) {
		HashMap<String, List<Integer>> $ = new HashMap<String, List<Integer>>();
		for (String l : Extract.links(trace)) {
			if (!$.containsKey(filename(l))) {
				List<Integer> lineList = new ArrayList<Integer>();
				lineList.add(line(l));
				$.put(filename(l), lineList);
			} else {
				List<Integer> lineList = $.get(filename(l));
				lineList.add(line(l));
			}
		}
		return $;
	}

	static String toFolder(String packageString) {
		return packageString.replace(".", "\\");
	}
}
