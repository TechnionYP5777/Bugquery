package com.bugquery.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
// import org.eclipse.core.resources.IFile;
// import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.program.Program;
import org.osgi.service.prefs.Preferences;

import com.bugquery.markers.*;
import org.eclipse.swt.widgets.Display;

/**
 * Handles an input of stack trace: Performs stack extraction and starts a
 * search in the BugQuery server (opens results in Browser)
 *
 * @author Yosef
 * @author Doron
 * @since Dec 7, 2016
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
	static void query(final String trace) {
		String url = "http://ssdlbugquery.cs.technion.ac.il";
		if (trace == null || trace.isEmpty())
			return;
		url = sendBugQuery(trace);
		markersInit(trace, url);
		Program.launch(url);
	}

	/**
	 * Sends an extracted trace, reformatted as a byte[] UTF-8, to the server in
	 * {@code localhost:8080}. Request thorough POST to "/stacks", opens a page
	 * with the URL it receives as a response in the default web browser.
	 *
	 * @param trace
	 *            - an extracted trace
	 */
	static String sendBugQuery(String trace) {
		final String urlStr = "http://ssdlbugquery.cs.technion.ac.il/stacks";
		URL url;
		try {
			url = new URL(urlStr);
		} catch (final MalformedURLException e) {
			return "http://ssdlbugquery.cs.technion.ac.il"; // shouldn't happen
		}

		byte[] post_bytes;
		try {
			String content = "skiploading=true&trace=" + trace;
			post_bytes = content.getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e1) {
			return "http://ssdlbugquery.cs.technion.ac.il";
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
			return "http://ssdlbugquery.cs.technion.ac.il";
		}

		return conn.getHeaderField("location");
	}

	/**
	 * Get a trace and a url, set all relevant (of this project) markers to link
	 * this url sets default project etc.
	 * 
	 * @param trace
	 * @param url
	 */
	static void markersInit(String trace, String url) {
		MarkerFactory m = MarkerFactory.instance();
		Preferences prefs = InstanceScope.INSTANCE.getNode("com.bugquery.preferences");
		String projectName = prefs.get("projectname", "default");
		if (projectName == "default") {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "BugQuery Error",
					"BugQuery could not generate markers since there is no selected project.");
			return;
		}
		m.deleteMarkerFrom(ResourcesUtils.getProject(projectName));
		m.addMarkers(trace, url);
	}
}