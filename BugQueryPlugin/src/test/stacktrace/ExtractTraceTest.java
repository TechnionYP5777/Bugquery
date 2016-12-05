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

	@Test
	public void invalidInput() {
		assertEquals(et.no_trace_en, et.extract(null));
	}

	@Test
	public void emptyInput() {
		assertEquals(et.no_trace_en, et.extract(""));
	}

}
