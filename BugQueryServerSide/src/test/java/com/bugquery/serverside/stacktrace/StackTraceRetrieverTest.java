package com.bugquery.serverside.stacktrace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.PostStub;
import com.bugquery.serverside.entities.StackTrace;

@SuppressWarnings("static-method")
public class StackTraceRetrieverTest {

  @Rule public ExpectedException thrown = ExpectedException.none();
  
  String stackTrace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
      "at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
      "at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
      "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
  
  @Test
	public void retrieverThrowsExceptionForIllegalParameters1() {
	  thrown.expect(IllegalArgumentException.class);
		StackTraceRetriever.getMostRelevantStackTraces(null, new StackTrace(stackTrace), new JaccardSTDistancer(), 1);
	}
	
  @Test
  public void retrieverThrowsExceptionForIllegalParameters2() {
    thrown.expect(IllegalArgumentException.class);
    StackTraceRetriever.getMostRelevantStackTraces(new ArrayList<Post>(), null, new JaccardSTDistancer(), 1);
 }

  @Test
  public void retrieverThrowsExceptionForIllegalParameters3() {
    thrown.expect(IllegalArgumentException.class);
    StackTraceRetriever.getMostRelevantStackTraces(new ArrayList<Post>(), new StackTrace(stackTrace),  null, 1);
 }

  @Test
  public void retrieverThrowsExceptionForIllegalParameters4() {
    thrown.expect(IllegalArgumentException.class);
    StackTraceRetriever.getMostRelevantStackTraces(new ArrayList<Post>(), new StackTrace(stackTrace), new JaccardSTDistancer(), 0);
 }

	@Test 
	public void returnsExactStackTraceIfExists() {
		String stackTrace1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		Post p1 = new PostStub(stackTrace1);
		String stackTrace2 = "Exception in thread \"submain\" java.lang.NilPointerException\n" + 
				"at com.example.myproject.Movie.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.Producer.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.trap.main(Bootstrap.java:14)";
		Post p2 = new PostStub(stackTrace2);
		List<Post> posts = new ArrayList<>();
		posts.add(p2);
		posts.add(p1);
		List<Post> result = new ArrayList<>();
		result.add(p1);
		assertEquals(result,StackTraceRetriever.getMostRelevantStackTraces(posts, new StackTrace(stackTrace1), new JaccardSTDistancer(), 1));
		assertEquals(result,StackTraceRetriever.getMostRelevantStackTraces(posts, new StackTrace(stackTrace1), new WeightLinesSTDistancer(), 1));
	}
	
	@Test
	public void returnsTwoClosestStackTraces() {
		String stackTrace1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		Post p1 = new PostStub(stackTrace1);
		String stackTrace2 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"at com.example.myproject.Movie.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.Producer.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.trap.main(Bootstrap.java:14)";
		Post p2 = new PostStub(stackTrace2);
		String stackTrace3 = "Exception in thread \"stam\" java.lang.NilPointerException\n" + 
				"at com.example.myproject.stam.getTitle(Book.java:16)\n" + 
				"at com.example.myproject.stam.getBookTitles(Author.java:25)\n" + 
				"at com.example.myproject.stam.main(Bootstrap.java:14)";
		Post p3 = new PostStub(stackTrace3);
		List<Post> posts = new ArrayList<>();
		posts.add(p2);
		posts.add(p1);
		posts.add(p3);
		List<Post> result = new ArrayList<>();
		result.add(p1);
		result.add(p2);
		assertEquals(result,StackTraceRetriever.getMostRelevantStackTraces(posts, new StackTrace(stackTrace1), new JaccardSTDistancer(), 2));
		assertEquals(result,StackTraceRetriever.getMostRelevantStackTraces(posts, new StackTrace(stackTrace1), new WeightLinesSTDistancer(), 2));
	}
}
