package com.bugquery.serverside.stacktrace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bugquery.serverside.entities.Post;

public class StackTraceRetrieverTest {

	@Test
	public void retrieverReturnsNullForIllegalParameters() {
		assertNull(StackTraceRetriever.getMostRelevantStackTraces(null, "Exception", new JaccardSTDistancer(), 1));
		assertNull(StackTraceRetriever.getMostRelevantStackTraces(new ArrayList<Post>(), null, new JaccardSTDistancer(), 1));
		assertNull(StackTraceRetriever.getMostRelevantStackTraces(new ArrayList<Post>(), "Exception", null, 1));
		assertNull(StackTraceRetriever.getMostRelevantStackTraces(new ArrayList<Post>(), "Exception", new JaccardSTDistancer(), 0));
	}
	
	@Test 
	public void returnsExactStackTraceIfExists() {
		String stackTrace1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		Post p1 = new Post("1",stackTrace1) {};
		String stackTrace2 = "Exception in thread \"submain\" java.lang.NilPointerException\n" + 
				"at com.example.myproject.Movie.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.Producer.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.trap.main(Bootstrap.java:14)";
		Post p2 = new Post("2",stackTrace2) {};
		List<Post> posts = new ArrayList<>();
		posts.add(p2);
		posts.add(p1);
		List<Post> result = new ArrayList<>();
		result.add(p1);
		assertEquals(result,StackTraceRetriever.getMostRelevantStackTraces(posts, stackTrace1, new JaccardSTDistancer(), 1));
		assertEquals(result,StackTraceRetriever.getMostRelevantStackTraces(posts, stackTrace1, new WeightLinesSTDistancer(), 1));
	}
	
	@Test
	public void returnsTwoClosestStackTraces() {
		String stackTrace1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		Post p1 = new Post("1",stackTrace1) {};
		String stackTrace2 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"at com.example.myproject.Movie.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.Producer.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.trap.main(Bootstrap.java:14)";
		Post p2 = new Post("2",stackTrace2) {};
		String stackTrace3 = "Exception in thread \"stam\" java.lang.NilPointerException\n" + 
				"at com.example.myproject.stam.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.stam.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.stam.main(Bootstrap.java:14)";
		Post p3 = new Post("3",stackTrace3) {};
		List<Post> posts = new ArrayList<>();
		posts.add(p2);
		posts.add(p1);
		posts.add(p3);
		List<Post> result = new ArrayList<>();
		result.add(p1);
		result.add(p2);
		assertEquals(result,StackTraceRetriever.getMostRelevantStackTraces(posts, stackTrace1, new JaccardSTDistancer(), 2));
		assertEquals(result,StackTraceRetriever.getMostRelevantStackTraces(posts, stackTrace1, new WeightLinesSTDistancer(), 2));
	}
}
