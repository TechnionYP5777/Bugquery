package com.bugquery.console;

import org.eclipse.jface.action.*;
import org.eclipse.ui.console.*;
import org.eclipse.ui.part.IPageBookViewPage;

/**
 * Adds BugQuery button to TextConsole toolbar on initialization
 * 
 * @author Yosef
 * @since May 9, 2017
 */
public class ConsoleExtender implements IConsolePageParticipant {

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public void init(IPageBookViewPage page, IConsole console) {
		TextConsole textConsole = (TextConsole) console;
		Action action = new ConsoleQuery(textConsole);
		IToolBarManager toolBarManager = page.getSite().getActionBars()
				.getToolBarManager();
		toolBarManager.appendToGroup(IConsoleConstants.OUTPUT_GROUP,
				new Separator());
		toolBarManager.appendToGroup(IConsoleConstants.OUTPUT_GROUP, action);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void activated() {

	}

	@Override
	public void deactivated() {
	}

}