package com.bugquery.stacktrace;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

/**
 * Unit tests of {@link Extract}
 *
 * @author Yosef
 * @since Dec 5, 2016
 *
 */
public class ExtractTraceTest {
	
	// Tests for trace()

	/** parsing of null object is be @no_trace */
	@Test
	public void invalidInput() {
		assertEquals(Extract.notFound, Extract.trace(null));
	}

	/** parsing of an empty String is @no_trace */
	@Test
	public void emptyInput() {
		assertEquals(Extract.notFound, Extract.trace(""));
	}

	/** parsing of a String without traces is @no_trace */
	@Test
	public void noTraces() {
		assertEquals(Extract.notFound, Extract.trace("123"));
		assertEquals(Extract.notFound, Extract.trace("123\n\t"));
	}

	/** parsing of a real trace is the trace itself */
	@Test
	public void realTrace() {
		assertEquals(
				"Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)",
				Extract.trace(
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
				Extract.trace(
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
				Extract.trace(
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
				Extract.trace(
						"Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\r\n        at com.example.myproject.Author.getBookIds(Author.java:38)\r\n        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\r\nCaused by: java.lang.NullPointerException\r\n        at com.example.myproject.Book.getId(Book.java:22)\r\n        at com.example.myproject.Author.getBookIds(Author.java:35)\r\n\r\n"));
	}
	
	// Tests for links()
	/**
	 * parsing of null trace returns an empty links() list
	 */
	@Test
	public void linksInvalid() {
		assertEquals(Extract.links(null).size(), 0);
	}
	
	/**
	 * parsing of an empty trace returns an empty links() list
	 */
	@Test
	public void linksEmpty() {
		assertEquals(Extract.links("").size(), 0);
	}
	
	void assertLists(ArrayList<String> expected, ArrayList<String> actual) {
		// sizes should be the same
		assertEquals(expected.size(), actual.size());
		// contents too:
		for(int i = 0; i < expected.size(); ++i)
			assertEquals(expected.get(i), actual.get(i));		
	}
	
	@Test
	public void linksInTrace() {
		ArrayList<String> res = Extract
				.links("Exception in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)"),
				expected = new ArrayList<>();
		expected.add("Fail.java:5");
		expected.add("main.java:7");
		assertLists(expected, res);
	}
	
	@Test
	public void linksInOutput() {
		ArrayList<String> res = Extract
				.links("Sometimes traces appear after some text, (Makesure:122)\nException in thread \"main\" java.lang.NullPointerException: Message\n\tat test.Fail.karma(Fail.java:5)\n\tat test.main.main(main.java:7)"),
				expected = new ArrayList<>();
		expected.add("Fail.java:5");
		expected.add("main.java:7");
		assertLists(expected, res);		
	}
	

	@Test
	public void lineNumberTest() {
		assertEquals(0, Extract.line("test.java:0"));
		int rnd = new Random().nextInt();
		rnd = (rnd > 0) ? rnd : -rnd;
		assertEquals(rnd, Extract.line("test.java:" + rnd));
	}

}
