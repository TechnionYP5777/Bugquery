package bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import bugquery.stacktrace.GetTrace;

/**
 * a handler class, when a stack trace was copied to the clipboard
 * 
 * @author Yosef
 */
public class FromClipboard extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent ¢) {
		new SendTrace(new GetTrace().fromClipboard());
		return null;
	}
}
