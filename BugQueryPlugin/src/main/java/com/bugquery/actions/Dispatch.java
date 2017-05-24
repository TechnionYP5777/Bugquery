package com.bugquery.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import org.eclipse.core.resources.IFile;
// import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.program.Program;

import com.bugquery.markers.*;
import com.bugquery.stacktrace.Extract;

/**
 * Handles an input of stack trace: Performs stack extraction and starts a
 * search in the BugQuery server (opens results in Browser)
 *
 * @author Yosef
 * @since Dec 7, 2016
 *
 */
public interface Dispatch {
	/**
	 * Not to be used clients constructor. Instantiates this class with a given
	 * trace. If the parameter is not {#@code null} nor the empty string, input
	 * isn't null or empty, extracts a trace and passes it on to sendBugQuery,
	 * to display online
	 *
	 * @param trace
	 */
	public static void query(final String trace) {
		if (trace != null && !trace.isEmpty())
			sendBugQuery(Extract.trace(trace));
	}

	/**
	 * Sends an extracted trace, reformatted as a byte[] UTF-8, to the server in
	 * {@code localhost:8080}. Request thorough POST to "/stacks", opens a page
	 * with the URL it receives as a response in the default web browser.
	 *
	 * @param trace
	 *            - an extracted trace
	 */
	public static void sendBugQuery(String trace) {
		markersInit(trace);
		
		final String urlStr = "http://localhost:8080/stacks";
		URL url;
		try {
			url = new URL(urlStr);
		} catch (final MalformedURLException e) {
			return; // shouldn't happen
		}

		byte[] post_bytes;
		try {
			trace = "trace=" + trace;
			post_bytes = trace.getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e1) {
			return;
		}

		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(post_bytes.length));
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.getOutputStream().write(post_bytes);
		} catch (final IOException e) {
			return;
		}
		
		Program.launch(conn.getHeaderField("location"));
	}
	
	/**
	 * 
	 */
	public static void markersInit(String trace) {
		MarkerFactory m = MarkerFactory.instance();
		m.deleteAllKnownMarkers();
		m.addMarkers(trace);
	}
}