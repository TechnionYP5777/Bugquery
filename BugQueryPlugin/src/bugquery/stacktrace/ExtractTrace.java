package bugquery.stacktrace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ExtractTrace offers extract(), which gets console output and (given that the
 * output contains one) extracts a stack trace from it.
 * 
 * @author Yosef
 *
 */
public class ExtractTrace {
	public String no_trace_en = "No stack trace detected.";

	/**
	 * @param ¢
	 *            the output from which trace extraction is needed
	 * @return @no_trace if @¢ doesn't contain any stack traces. else, returns
	 *         that trace. [[SuppressWarningsSpartan]] - wants to rename m to ¢,
	 *         creating a bug.
	 */
	public String extract(String ¢) {
		if (¢ == null)
			return no_trace_en;
		String $ = "";
		for (Matcher m = Pattern.compile("Exception(.*)\n(\tat(.*)\n)*\tat(.*)").matcher(¢); m.find();)
			$ += m.group(0);
		return $.length() > 0 ? $ : no_trace_en;
	}
}
