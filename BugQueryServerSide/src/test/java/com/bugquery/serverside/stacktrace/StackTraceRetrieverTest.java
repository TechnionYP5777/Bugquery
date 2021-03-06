package com.bugquery.serverside.stacktrace;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.PostStub;
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.repositories.PostRepository;
import com.bugquery.serverside.stacktrace.distance.JaccardSTDistancer;
import com.bugquery.serverside.stacktrace.distance.WeightLinesSTDistancer;
import com.bugquery.serverside.stacktrace.distance.levenshtein.LevenshteinSTDistancer;

/**
 * @author  rodedzats
 * @since  11.12.2016
 */
@RunWith(SpringRunner.class)
@SuppressWarnings("static-method")
public class StackTraceRetrieverTest {
	@MockBean
	PostRepository repo;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	String stackTrace = "Exception in thread \"main\" java.lang.NullPointerException\n"
			+ "at com.example.myproject.Book.getTitle(Book.java:16)\n"
			+ "at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
			+ "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";

	@Test
	public void retrieverThrowsExceptionForIllegalConstructorParameters1() {
		thrown.expect(IllegalArgumentException.class);
		try {
			Post p1 = new PostStub(stackTrace);
			new ArrayList<>().add(p1);
			new GeneralStackTraceRetriever(null).getMostRelevantPosts(stackTrace, 1);
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			¢.printStackTrace();
		}
	}

	@Test
	public void retrieverThrowsExceptionForIllegalConstructorParameters2() throws GeneralDBException {
		thrown.expect(GeneralDBException.class);
		try {
			new GeneralStackTraceRetriever(new JaccardSTDistancer(), null).getMostRelevantPosts(stackTrace, 1);
		} catch (InvalidStackTraceException ¢) {
			¢.printStackTrace();
		}
	}

	@Test
	public void retrieverThrowsExceptionForIllegalParameters1() {
		thrown.expect(IllegalArgumentException.class);
		try {
			new GeneralStackTraceRetriever().getMostRelevantPosts(null, 1);
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			¢.printStackTrace();
		}
	}

	@Test
	public void retrieverThrowsExceptionForIllegalParameters2() {
		thrown.expect(IllegalArgumentException.class);
		try {
			new GeneralStackTraceRetriever().getMostRelevantPosts(stackTrace, 0);
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			¢.printStackTrace();
		}
	}

	@Test
	public void retrieverThrowsExceptionForIllegalStackTrace() throws InvalidStackTraceException {
		thrown.expect(InvalidStackTraceException.class);
		try {
			new GeneralStackTraceRetriever().getMostRelevantPosts(":::", 10);
		} catch (GeneralDBException ¢) {
			¢.printStackTrace();
		}
	}

	@Test
	public void retrieverThrowsExceptionForIllegalStackTrace2() throws InvalidStackTraceException {
		thrown.expect(InvalidStackTraceException.class);
		try {
			new GeneralStackTraceRetriever().getMostRelevantPosts("Exception:", 10);
		} catch (GeneralDBException ¢) {
			¢.printStackTrace();
		}
	}

	@Test
	public void retrieverThrowsExceptionForIllegalStackTrace3() throws InvalidStackTraceException {
		thrown.expect(InvalidStackTraceException.class);
		try {
			new GeneralStackTraceRetriever()
					.getMostRelevantPosts("ERROR 2007-09-18 23:30:19,913 error 1294 Something awful happened!\n"
							+ "Traceback (most recent call last):\n" + "  File \"b.py\", line 22, in f\n" + "    g()\n"
							+ "  File \"b.py\", line 14, in g\n" + "    1/0\n"
							+ "ZeroDivisionError: integer division or modulo by zero", 10);
		} catch (GeneralDBException ¢) {
			¢.printStackTrace();
		}
	}

	@Test
	public void returnsExactStackTraceIfExists() {
		String stackTrace1 = "Exception in thread \"main\" java.lang.NullPointerException\n"
				+ "at com.example.myproject.Book.getTitle(Book.java:16)\n"
				+ "at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
				+ "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		Post p1 = new PostStub(stackTrace1);
		String stackTrace2 = "Exception in thread \"submain\" java.lang.NilPointerException\n"
				+ "at com.example.myproject.Movie.getTitle(Book.java:16)\n"
				+ "at com.example.myproject.Producer.getBookTitles(Author.java:25)\n"
				+ "at com.example.myproject.trap.main(Bootstrap.java:14)";
		Post p2 = new PostStub(stackTrace2);
		List<Post> posts = new ArrayList<>();
		posts.add(p2);
		posts.add(p1);
		List<Post> result = new ArrayList<>();
		result.add(p1);
		try {
			assertEquals(result,
					new GeneralStackTraceRetriever(new JaccardSTDistancer()).getMostRelevantPosts(stackTrace1, 1));
			assertEquals(result,
					new GeneralStackTraceRetriever(new WeightLinesSTDistancer()).getMostRelevantPosts(stackTrace1, 1));
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			¢.printStackTrace();
		}
	}

	@Test
	public void returnsTwoClosestStackTraces() {
		String stackTrace1 = "Exception in thread \"main\" java.lang.NullPointerException\n"
				+ "at com.example.myproject.Book.getTitle(Book.java:16)\n"
				+ "at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
				+ "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		Post p1 = new PostStub(stackTrace1);
		String stackTrace2 = "Exception in thread \"main\" java.lang.NullPointerException\n"
				+ "at com.example.myproject.Book.getTitle(Book.java:16)\n"
				+ "at com.example.myproject.Producer.getBookTitles(Author.java:25)\n"
				+ "at com.example.myproject.trap.main(Bootstrap.java:14)";
		Post p2 = new PostStub(stackTrace2);
		String stackTrace3 = "Exception in thread \"stam\" java.lang.NilPointerException\n"
				+ "at com.example.myproject.stam.getTitle(Book.java:16)\n"
				+ "at com.example.myproject.stam.getBookTitles(Author.java:25)\n"
				+ "at com.example.myproject.stam.main(Bootstrap.java:14)";
		Post p3 = new PostStub(stackTrace3);
		List<Post> posts = new ArrayList<>();
		posts.add(p2);
		posts.add(p1);
		posts.add(p3);
		List<Post> result = new ArrayList<>();
		result.add(p1);
		result.add(p2);
		try {
			assertEquals(result,
					new GeneralStackTraceRetriever(new JaccardSTDistancer()).getMostRelevantPosts(stackTrace1, 2));
			assertEquals(result,
					new GeneralStackTraceRetriever(new WeightLinesSTDistancer()).getMostRelevantPosts(stackTrace1, 2));
			assertEquals(result,
					new GeneralStackTraceRetriever(new LevenshteinSTDistancer()).getMostRelevantPosts(stackTrace1, 2));
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			¢.printStackTrace();
		}
	}
}
