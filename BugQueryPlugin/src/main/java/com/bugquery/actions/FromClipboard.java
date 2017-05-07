package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import com.bugquery.stacktrace.ClipboardDialog;

/**
 * Handler of event 'stack trace copied to clipboard'
 *
 * @author Yosef
 * @since Dec 6, 2016
 *
 */
public class FromClipboard extends AbstractHandler {

	public String fromClipboard() {
		final ClipboardDialog $ = new ClipboardDialog(Display.getCurrent().getActiveShell(), "BugQuery Input", "", null,
				null);
		return $.open() != Window.OK ? null : $.getValue();
	}

	@Override
	public Object execute(final ExecutionEvent ¢) {
		Dispatch.query(fromClipboard());
		return null;
	}
}
