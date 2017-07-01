package com.bugquery.actions;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Locale;

import org.eclipse.core.commands.*;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import com.bugquery.stacktrace.ClipboardDialog;

/**
 * Handler of event 'stack trace copied to clipboard'
 *
 * @author Yosef
 * @since Dec 6, 2016
 *
 */
public class FromClipboard extends AbstractHandler {
	static final boolean isLinux = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH).indexOf("nux") >= 0; 

	/**
	 * opens a new ClipboardDialog
	 * @return ClipboardDialog contents after the OK button was pressed, null if it exited a different way
	 */
	public String fromClipboardDialog() {
		final ClipboardDialog $ = new ClipboardDialog(Display.getCurrent().getActiveShell(), "BugQuery Input", "", null,
				null);
		return $.open() != Window.OK ? null : $.getValue();
	}
	
	/**
	 * gets clipboard contents from the default system clipboard
	 * @return $, the content of system clipboard (if stringFlavor is supported), null otherwise
	 */
	public String fromClipboard(){
		String $ = null;
		try {
			$ = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return $;		
	}

	@Override
	public Object execute(final ExecutionEvent ¢) {
		String trace = null;
		trace = !isLinux ? fromClipboard() : fromClipboardDialog();
		Dispatch.query(trace);
		return trace;
	}
}
