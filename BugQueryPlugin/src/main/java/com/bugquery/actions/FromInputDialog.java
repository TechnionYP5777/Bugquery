package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.bugquery.stacktrace.GetTrace;

/**
 * Handler of event 'trace supplied through via input dialog'
 *
 * @author Yosef
 * @since Dec 8, 2016
 *
 */
public class FromInputDialog extends AbstractHandler {
	String dialog_message = "Please Insert Your Stack Trace Below.";

	@Override
	public Object execute(final ExecutionEvent ¢) throws ExecutionException {
		new SendTrace(new GetTrace().fromInputDialog(HandlerUtil.getActiveWorkbenchWindowChecked(¢).getShell()));
		return null;
	}
}
