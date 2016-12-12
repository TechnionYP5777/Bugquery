package com.bugquery.actions;

import org.eclipse.swt.program.Program;

import com.bugquery.stacktrace.ExtractTrace;

/**
 * Handle a task trace: in the future this will forward the trace to the
 * BugQuery website. As of now, it performs extraction and starts a web search
 * in Google.
 * 
 * @author Yosef
 */
public class SendTrace {

	/**
	 * @param trace:
	 *            a stack trace to be prepared as a http GET Request
	 * @return a new string, with "%0A" instead of line breaks and "%09" instead
	 *         of tabs.
	 */
	public String reformatTrace(String trace) {
		return trace.replaceAll("(\r\n|\n)+", "%0A").replace("\t", "%09");
	}

	/**
	 * @param trace
	 * @return sends the extracted trace, reformatted for http, to the server in
	 *         localhost:8080.
	 */
	public void sendBugQuery(String trace) {
		Program.launch("http://localhost:8080/BugQueryServerSide/result.jsp?q=" + reformatTrace(trace));
	}

	SendTrace(String trace) {
		if (trace != null && !trace.isEmpty())
			sendBugQuery(new ExtractTrace().extract(trace));
	}
}
