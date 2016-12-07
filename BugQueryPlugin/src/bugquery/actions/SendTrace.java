package bugquery.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import bugquery.stacktrace.ExtractTrace;

/**
 * Handle a task trace: in the future this will forward the trace to the
 * BugQuery website. As of now, it performs extraction and presents it in a
 * workbench window.
 * 
 * @author Yosef
 *
 */
public class SendTrace {

	SendTrace(String trace, ExecutionEvent e) throws ExecutionException {
		MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindowChecked(e).getShell(), "BugQuery",
				new ExtractTrace().extract(trace));
	}
}
