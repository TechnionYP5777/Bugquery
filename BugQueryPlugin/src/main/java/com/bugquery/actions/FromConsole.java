package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

import com.bugquery.stacktrace.GetTrace;

/**
 * a handler class, when a stack trace was printed in an output console
 * 
 * @author Yosef
 */
public class FromConsole extends AbstractHandler {

	public Object execute(ExecutionEvent ¢) {
		new SendTrace(new GetTrace().fromConsole());
		return null;
	}
}
