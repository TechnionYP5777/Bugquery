package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

import com.bugquery.stacktrace.GetTrace;

/**
 * Handler of event 'stack trace copied to clipboard'
 *
 * @author Yosef
 * @since Dec 6, 2016
 *
 */
public class FromClipboard extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent ¢) {
		new SendTrace(new GetTrace().fromClipboard2());
		return null;
	}
}
