package com.bugquery.console;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.console.*;
import com.bugquery.actions.*;

/**
 * sends @console's contents to Dispatch
 * 
 * @author Yosef
 * @since May 10, 2017
 */
public class ConsoleAction extends Action {
	TextConsole console;

	public ConsoleAction(TextConsole console) {
		super("Send to BugQuery");
		this.console = console;
	}

	@Override
	public void run() {
		if (console != null) {
			String input = console.getDocument().get();
			Dispatch.query(input);
		}
	}

}