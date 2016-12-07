package bugquery.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import bugquery.stacktrace.GetTrace;

/**
 * a handler class, when a stack trace was printed in an output console
 * 
 * @author Yosef
 */
public class FromConsole extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent ¢) throws ExecutionException {
		new SendTrace(new GetTrace().fromConsole(), ¢);
		return null;
	}
}
