package com.bugquery.stacktrace;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.TextConsole;

/**
 * GetTrace is a class for methods that return Strings with stack traces, using
 * several inputs (e.g. consoles).
 * 
 * @author Yosef
 * @since Nov 29 2016
 */
public class GetTrace {

	/**
	 * @param exception,
	 *            a Throwable
	 * @return a String from @exception's stack trace
	 */
	public String fromException(Throwable exception) {
		StringWriter s_writer = new StringWriter();
		PrintWriter writer = new PrintWriter(s_writer);
		exception.printStackTrace(writer);
		return s_writer + "";
	}

	/**
	 * @param console_name
	 * @return a new console, named @console_name.
	 */
	private TextConsole emptyNewConsole(String console_name) {
		MessageConsole new_console = new MessageConsole(console_name, null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { new_console });
		return (TextConsole) new_console;
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
	private TextConsole getConsoleNamed(String name_str) {
		IConsole[] available = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (int ¢ = 0; ¢ < available.length; ++¢)
			if (available[¢].getName().indexOf(name_str) != -1)
				return (TextConsole) available[¢];

		// no console by that name exists yet
		return getFirstConsole();
	}

	/**
	 * @return console output
	 */
	public String fromConsole() {
		return getConsoleNamed("<terminated>").getDocument().get();
	}

	/**
	 * @return system clipboard output
	 */
	public String fromClipboard() {
		Transferable content;
		String $ = "";

		DataFlavor[] m = Toolkit.getDefaultToolkit().getSystemClipboard().getAvailableDataFlavors();
		for (DataFlavor n : m)
			System.out.println(n.getPrimaryType() + " " + n.getHumanPresentableName());

		try {
			content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		} catch (IllegalStateException e) {
			return $;
		}

		if (content == null || !content.isDataFlavorSupported(DataFlavor.stringFlavor))
			return $;

		try {
			$ += content.getTransferData(DataFlavor.stringFlavor);
		} catch (IOException e) {
			return $;
		} catch (UnsupportedFlavorException e) {
			return $;
		}

		return $;
	}

	/**
	 * creates a new input dialog (ExtendedDialog)
	 * 
	 * @param s,
	 *            a shell for our dialog
	 * 
	 * @return null if no window was opened, or if the window was closed without
	 *         the OK button being clicked. otherwise, returns the input from
	 *         the user.
	 */
	public String fromInputDialog(Shell s) {
		ExtendedDialog dialog = new ExtendedDialog(s, "BugQuery Input", "Please Insert Your Output", null, null);
		return dialog.open() != Window.OK ? null : dialog.getValue();
	}

}
