package com.bugquery.serverside.stacktrace;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bugquery.serverside.dbparsing.DBConnector;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.PostStub;
import com.bugquery.serverside.exceptions.GeneralDBException;

@SuppressWarnings("static-method")
public class StackTraceRetrieverTest {

  @Rule public ExpectedException thrown = ExpectedException.none();
    
  String stackTrace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
      "at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
      "at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
      "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
 	
	private DBConnector getDummyConnector(List<Post> ps) {
		return new DBConnector() {
			@Override
			public List<Post> getAllQuestionsWithTheException(@SuppressWarnings("unused") String __) {
				return ps;
			}
		};
	}

	
	@Test
	public void retrieverThrowsExceptionForIllegalParameters1() {
		thrown.expect(IllegalArgumentException.class);
		try {
			new GeneralStackTraceRetriever().getMostRelevantPosts(null, 1);
		} catch (GeneralDBException e) {e.printStackTrace();}
	}

	@Test
	public void retrieverThrowsExceptionForIllegalParameters2() {
		thrown.expect(IllegalArgumentException.class);
		try {
			new GeneralStackTraceRetriever().getMostRelevantPosts(stackTrace, 0);
		} catch (GeneralDBException e) {e.printStackTrace();}
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
		try {
			assertEquals(result, new GeneralStackTraceRetriever(new JaccardSTDistancer(), getDummyConnector(posts)).getMostRelevantPosts(stackTrace1, 1));
			assertEquals(result, new GeneralStackTraceRetriever(new WeightLinesSTDistancer(), getDummyConnector(posts)).getMostRelevantPosts(stackTrace1, 1));
		} catch (GeneralDBException e) {
			e.printStackTrace();
		}
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
		try {
			assertEquals(result, new GeneralStackTraceRetriever(new JaccardSTDistancer(), getDummyConnector(posts)).getMostRelevantPosts(stackTrace1, 2));
			assertEquals(result, new GeneralStackTraceRetriever(new WeightLinesSTDistancer(), getDummyConnector(posts)).getMostRelevantPosts(stackTrace1, 2));
		} catch (GeneralDBException e) {
			e.printStackTrace();
		}
	}
}
