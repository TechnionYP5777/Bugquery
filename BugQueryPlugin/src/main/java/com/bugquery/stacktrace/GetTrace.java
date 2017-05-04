package com.bugquery.stacktrace;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

/**
 * GetTrace is a class for methods that return Strings with stack traces, using
 * several inputs (e.g. consoles).
 *
 * @author Yosef
 * @since Nov 29 2016
 *
 */
public class GetTrace {

	/**
	 * @param exception,
	 *            a Throwable
	 * @return a String from @exception's stack trace
	 */
	public String fromException(final Throwable exception) {
		final StringWriter $ = new StringWriter();
		exception.printStackTrace(new PrintWriter($));
		return $ + "";
	}

	/**
	 * @return system clipboard output [[SuppressWarningsSpartan]] - causes
	 *         issue with Maven
	 */
	public String fromClipboard() {
		Transferable content;
		String $ = "";

		final DataFlavor[] m = Toolkit.getDefaultToolkit().getSystemClipboard().getAvailableDataFlavors();
		for (final DataFlavor n : m)
			System.out.println(n.getPrimaryType() + " " + n.getHumanPresentableName());

		try {
			content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		} catch (final IllegalStateException e) {
			return $;
		}

		System.out.println("size, " + content.getTransferDataFlavors().length);
		System.out.println(content);

		if (content == null || !content.isDataFlavorSupported(DataFlavor.stringFlavor))
			return $;

		try {
			$ += content.getTransferData(DataFlavor.stringFlavor);
		} catch (final UnsupportedFlavorException e) {
			return $;
		} catch (final IOException e) {
			return $;
		}

		return $;
	}

	public String fromClipboard2() {
		final ClipboardDialog $ = new ClipboardDialog(Display.getCurrent().getActiveShell(), "BugQuery Input", "", null,
				null);
		return $.open() != Window.OK ? null : $.getValue();
	}

}
