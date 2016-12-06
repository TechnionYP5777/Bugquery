package bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import bugquery.stacktrace.GetTrace;

import org.eclipse.jface.dialogs.MessageDialog;

public class FromConsole extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent e) throws ExecutionException {
		String trace = new GetTrace().fromConsole();
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(e);
		MessageDialog.openInformation(
				window.getShell(),
				"BugQuery",
				trace);
		return null;
	}
}
