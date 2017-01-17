package com.bugquery.serverside.entities;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("static-method")
public class MinSOPostTest {

	// question and trace from StackOverflowPostTest
	static String question = "Sometimes when I run my application it gives me an error that looks like:\n" + 
			"\n" + 
			"Exception in thread \"main\" java.lang.NullPointerException\n" + 
			"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
			"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
			"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
			"People have referred to this as a \"stack trace\". What is a stack trace? What can it tell me about the error that's happening in my program?";
	static String trace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
			"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
			"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
			"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
	
	@Test
	public void testFieldAccessOK_stringConstructor() {
		MinSOPost post = new MinSOPost(trace, question);
		assertEquals(trace,post.stackTrace.getString());
		assertEquals(trace,(post + ""));
	}

	@Test
	public void testFieldAccessOK_StackTraceConstructor() {
		StackTrace st = new StackTrace(trace);
		MinSOPost post = new MinSOPost(st, question);
		assertEquals(st, post.stackTrace);
		assertEquals(trace,(post + ""));
	}
	
}
