package bugquery.actions;

import org.eclipse.swt.program.*;
import bugquery.stacktrace.ExtractTrace;

/**
 * Handle a task trace: in the future this will forward the trace to the
 * BugQuery website. As of now, it performs extraction and starts a web search
 * in Google.
 * 
 * @author Yosef
 */
public class SendTrace {

	public void sendBugQuery(String trace) {
		Program.launch("https://www.google.com/#q=" + trace + " site:stackoverflow.com");
	}

	SendTrace(String trace) {
		sendBugQuery(new ExtractTrace().extract(trace));
	}
}
