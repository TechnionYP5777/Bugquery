package test.stacktrace;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bugquery.stacktrace.ExtractTrace;

/**
 * Testing stack extraction of bugquery.stacktrace.ExtractTrace's methods
 * 
 * @author Yosef
 * @since 16-12-05
 */
public class ExtractTraceTest {
	private ExtractTrace et = new ExtractTrace();

	/** parsing of null object is be @no_trace */
	@Test
	public void invalidInput() {
		assertEquals(et.no_trace_en, et.extract(null));
	}

	/** parsing of an empty String is @no_trace */
	@Test
	public void emptyInput() {
		assertEquals(et.no_trace_en, et.extract(""));
	}

	/** parsing of a String without traces is @no_trace */
	@Test
	public void noTraces() {
		assertEquals(et.no_trace_en, et.extract("123"));
		assertEquals(et.no_trace_en, et.extract("123\n\t"));
	}

	/** parsing of a real trace is the trace itself */
	@Test
	public void realTrace() {
		assertEquals(
				"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)",
				et.extract(
						"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)"));
	}

	/**
	 * parsing of a composition: real trace mixed with some other output. should
	 * return the trace exclusively
	 */
	@Test
	public void traceInOutput() {
		assertEquals(
				"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)",
				et.extract(
						"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)\nSome User Output"));
	}

	/**
	 * parsing a real trace which uses some new whitespaces (spaces instead of
	 * tabs, \r\n instead of \n). Example taken from:
	 * http://stackoverflow.com/a/3988794
	 */
	@Test
	public void traceWhitespace() {
		assertEquals(
				"Exception in thread \"main\" java.lang.NullPointerException\r\n        at com.example.myproject.Book.getTitle(Book.java:16)\r\n        at com.example.myproject.Author.getBookTitles(Author.java:25)\r\n        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)",
				et.extract(
						"Exception in thread \"main\" java.lang.NullPointerException\r\n        at com.example.myproject.Book.getTitle(Book.java:16)\r\n        at com.example.myproject.Author.getBookTitles(Author.java:25)\r\n        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\r\n"));
	}

	/**
	 * parsing a trace which includes "Caused by:" should include that part as
	 * well. another example from http://stackoverflow.com/a/3988794
	 */
	@Test
	public void traceWithCausedBy() {
		assertEquals(
				"Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\r\n        at com.example.myproject.Author.getBookIds(Author.java:38)\r\n        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\r\nCaused by: java.lang.NullPointerException\r\n        at com.example.myproject.Book.getId(Book.java:22)\r\n        at com.example.myproject.Author.getBookIds(Author.java:35)",
				et.extract(
						"Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\r\n        at com.example.myproject.Author.getBookIds(Author.java:38)\r\n        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\r\nCaused by: java.lang.NullPointerException\r\n        at com.example.myproject.Book.getId(Book.java:22)\r\n        at com.example.myproject.Author.getBookIds(Author.java:35)\r\n\r\n"));
	}

}
