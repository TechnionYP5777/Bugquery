package com.bugquery.serverside.entities;

import static org.assertj.core.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

import com.bugquery.serverside.exceptions.InvalidStackTraceException;

/**
 * 
 * @author Amit
 * @since Jan 19, 2017
 *
 */
@SuppressWarnings("static-method")
public class StackSearchTest {

	@Test
	public void shouldDecodeTrace() throws Exception {
		assertThat(getEncodedStackSearch("Exception in thread \"main\" java.lang.NullPointerException\n"
				+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
				+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
				+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)").getTrace())
						.isEqualTo("Exception in thread \"main\" java.lang.NullPointerException\n"
								+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
								+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
								+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)");
	}

	@Test(expected = InvalidStackTraceException.class)
	public void shouldThrowForInvalidTrace() throws Exception {
		getEncodedStackSearch("hi");
	}

	private static StackSearch getEncodedStackSearch(String content) throws InvalidStackTraceException {
		try {
			return new StackSearch(URLEncoder.encode(content, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// Can't happen
			e.printStackTrace();
		}
		return null;
	}
}
