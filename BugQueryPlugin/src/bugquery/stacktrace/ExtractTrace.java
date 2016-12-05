package bugquery.stacktrace;

/**
 * ExtractTrace offers extract(), which gets console output and (given that the
 * output contains it) extracts a stack trace from it.
 * 
 * @author Yosef
 *
 */
public class ExtractTrace {
	public String no_trace_en = "No stack trace detected.";

	/**
	 * @param str
	 *            the output from which trace extraction is needed
	 * @return @no_trace if @str doesn't contain any stack traces. else, returns
	 *         that trace.
	 */
	public String extract(String str) {
		return no_trace_en;
	}

}
