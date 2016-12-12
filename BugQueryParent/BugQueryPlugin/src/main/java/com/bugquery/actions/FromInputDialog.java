package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.bugquery.stacktrace.GetTrace;

/**
 * a handler class, get output from the user via input dialog
 * 
 * @author Yosef
 */
public class FromInputDialog extends AbstractHandler {
	String dialog_message = "Please Insert Your Stack Trace Below.";

	public Object execute(ExecutionEvent ¢) throws ExecutionException {
		new SendTrace(new GetTrace().fromInputDialog(HandlerUtil.getActiveWorkbenchWindowChecked(¢).getShell()));
		return null;
	}
}
