package com.bugquery.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.swt.program.Program;

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
	 * @param trace
	 *            - an extracted trace
	 * @return Sends the extracted trace, reformatted as a byte[] UTF-8, to the
	 *         server in localhost:8080 using a POST request to "/stacks", opens
	 *         a page with the url it receives as a response in the default web
	 *         browser.
	 */
	public void sendBugQuery(String trace) {
		URL url;
		try {
			url = new URL("http://localhost:8080/stacks");
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

		Program.launch(conn.getHeaderField("location"));
	}

	/**
	 * @param trace
	 * @return if the input isn't null or empty, extracts a trace and passes it
	 *         on to sendBugQuery, to display online
	 */
	SendTrace(String trace) {
		if (trace != null && !trace.isEmpty())
			sendBugQuery(new ExtractTrace().extract(trace));
	}
}