package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

import com.bugquery.stacktrace.GetTrace;

/**
 * Handler of event 'stack trace printed in output console'
 *
 * @author Yosef
 * @since Dec 5, 2016
 *
 */
public class FromConsole extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent ¢) {
		new SendTrace(new GetTrace().fromConsole());
		return null;
	}
}
