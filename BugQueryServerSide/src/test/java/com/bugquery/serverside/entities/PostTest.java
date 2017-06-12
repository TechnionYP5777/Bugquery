package com.bugquery.serverside.entities;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author zivizhar
 */
@SuppressWarnings("static-method")
public class PostTest {

	@Test
	public void testCreatePost() {
		assertEquals(
				"Exception in thread \"main\" java.lang.NullPointerException\n"
						+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
						+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
						+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)",
				(new Post(("Exception in thread \"main\" java.lang.NullPointerException\n"
						+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
						+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
						+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)"))).getStackTrace()
								.getContent());
	}

}
