package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.bugquery.stacktrace.ExtendedDialog;

/**
 * Handler of event 'trace supplied through via input dialog'
 *
 * @author Yosef
 * @since Dec 8, 2016
 *
 */
public class FromInputDialog extends AbstractHandler {
	String dialog_message = "Please Insert Your Stack Trace Below.";

	/**
	 * creates a new input dialog (ExtendedDialog)
	 *
	 * @param s,
	 *            a shell for our dialog
	 *
	 * @return null if no window was opened, or if the window was closed without
	 *         the OK button being clicked. otherwise, returns the input from
	 *         the user.
	 */
	public String fromInputDialog(final Shell ¢) {
		final ExtendedDialog $ = new ExtendedDialog(¢, "BugQuery Input", "Insert your stack trace below:", null, null);
		return $.open() != Window.OK ? null : $.getValue();
	}

	@Override
	public Object execute(final ExecutionEvent ¢) throws ExecutionException {
		Dispatch.query(fromInputDialog(HandlerUtil.getActiveWorkbenchWindowChecked(¢).getShell()));
		return null;
	}
}
