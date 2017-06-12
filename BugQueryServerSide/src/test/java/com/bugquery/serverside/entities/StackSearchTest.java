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
public class StackSearchTest {

	@Test
	@SuppressWarnings("static-method")
	public void shouldDecodeTrace() throws Exception {
		String legalTrace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		assertThat(getEncodedStackSearch(legalTrace).getTrace())
				.isEqualTo(legalTrace);
	}
	
	@Test(expected=InvalidStackTraceException.class)
	public void shouldThrowForInvalidTrace() throws Exception {
		getEncodedStackSearch("hi");
	}
	
	private StackSearch getEncodedStackSearch(String content) throws InvalidStackTraceException {
		try {
			return new StackSearch(URLEncoder.encode(content, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// Can't happen
			e.printStackTrace();
		}
		return null;
	}
}
