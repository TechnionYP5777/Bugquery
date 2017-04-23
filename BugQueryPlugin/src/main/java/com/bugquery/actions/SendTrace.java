package com.bugquery.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
// import org.eclipse.core.resources.IMarker;
// import org.eclipse.swt.program.Program;

import com.bugquery.fixer.ResourcesUtils;
import com.bugquery.fixer.MarkerManager;
import com.bugquery.stacktrace.ExtractTrace;

/**
 * Handles an input of stack trace: Performs stack extraction and starts a
 * search in the BugQuery server (opens results in Browser)
 * 
 * @author Yosef
 * @since Dec 7, 2016
 * 
 */
public class SendTrace {
	/**
	 * Not to be used clients constructor. Instantiates this class with a given
	 * trace. If the parameter is not {#@code null} nor the empty string, input
	 * isn't null or empty, extracts a trace and passes it on to sendBugQuery,
	 * to display online
	 * 
	 * @param trace
	 */
	// TOOD Cumbersome design. Why not just simple static function?
	// --yg
	SendTrace(String trace) {
		if (trace != null && !trace.isEmpty())
			sendBugQuery(new ExtractTrace().extract(trace));
	}

	/**
	 * Sends an extracted trace, reformatted as a byte[] UTF-8, to the server in
	 * {@code localhost:8080}. Request thorough POST to "/stacks", opens a page
	 * with the URL it receives as a response in the default web browser.
	 * 
	 * @param trace
	 *            - an extracted trace
	 */
	public void sendBugQuery(String trace) {
		// TODO: try not leave commented code It is never clear when to
		// uncomment it.
		// String urlStr = "http://localhost:8080/stacks";
		String urlStr = "http://www.google.com";
		URL url;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			return; // shouldn't happen
		}

		byte[] post_bytes;
		try {
			trace = "trace=" + trace;
			post_bytes = trace.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
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
		} catch (IOException e) {
			return;
		}

		// Program.launch(conn.getHeaderField("location"));
		// This parameters should be extracted from the stack trace
		IFile file = ResourcesUtils.getFile("test", "src", "test.java");
		MarkerManager.instance().addMarker(file, trace);
	}
}