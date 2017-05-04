package com.bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.TextConsole;

/**
 * Handler of event 'stack trace printed in output console'
 *
 * @author Yosef
 * @since Dec 5, 2016
 *
 */
public class FromConsole extends AbstractHandler {

	/**
	 * @param console_name
	 * @return a new console, named @console_name.
	 */
	private TextConsole emptyNewConsole(final String console_name) {
		final MessageConsole $ = new MessageConsole(console_name, null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { $ });
		return $;
	}

	/**
	 * @return first console within ConsoleManager, or an empty new one if it
	 *         doesn't exist
	 */
	private TextConsole getFirstConsole() {
		return ConsolePlugin.getDefault().getConsoleManager().getConsoles().length <= 0
				? emptyNewConsole("BugQuery Error")
				: (TextConsole) ConsolePlugin.getDefault().getConsoleManager().getConsoles()[0];
	}

	/**
	 * @param name_str
	 *            a substring of the console's name
	 * @return a console with a name that has @name_str as a substring within
	 *         ConsoleManager. if it doesn't exist, returns the first available
	 *         console, or an empty new one (if it doesn't exist either)
	 */
	private TextConsole getConsoleNamed(final String name_str) {
		final IConsole[] $ = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (int ¢ = 0; ¢ < $.length; ++¢)
			if ($[¢].getName().indexOf(name_str) != -1)
				return (TextConsole) $[¢];

		// no console by that name exists yet
		return getFirstConsole();
	}

	/**
	 * @return console output
	 */
	public String fromConsole() {
		return getConsoleNamed("<terminated>").getDocument().get();
	}

	@Override
	public Object execute(final ExecutionEvent ¢) {
		Dispatch.query(fromConsole());
		return null;
	}
}
