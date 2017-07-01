package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Handler for the choose project menu items
 * @author Doron
 * @since 1 06 2017
 */
public class DynamicItemHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent e) throws ExecutionException {
		System.out.println(e.getParameter("commandParameterID"));
		return null;
	}

}
