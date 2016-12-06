package test.stacktrace;

import bugquery.stacktrace.ExtractTrace;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Testing stack extraction of bugquery.stacktrace.ExtractTrace's methods
 * 
 * @author Yosef
 * @since 16-12-05
 */
public class ExtractTraceTest {
	private ExtractTrace et = new ExtractTrace();

	// parsing of null object is be @no_trace
	@Test
	public void invalidInput() {
		assertEquals(et.no_trace_en, et.extract(null));
	}

	// parsing of an empty String is @no_trace
	@Test
	public void emptyInput() {
		assertEquals(et.no_trace_en, et.extract(""));
	}

	// parsing of a String without traces is @no_trace
	@Test
	public void noTraces() {
		assertEquals(et.no_trace_en, et.extract("123"));
		assertEquals(et.no_trace_en, et.extract("123\n\t"));
	}

	// parsing of a real trace is the trace itself
	@Test
	public void realTrace() {
		assertEquals(
				"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)",
				et.extract(
						"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)"));
	}

	// parsing of a composition of real trace that has some other output is the
	// trace only
	@Test
	public void traceInOutput() {
		assertEquals(
				"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)",
				et.extract(
						"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)\nSome User Output"));
	}
}
