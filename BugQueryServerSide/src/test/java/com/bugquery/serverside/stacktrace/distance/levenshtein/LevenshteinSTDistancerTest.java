package com.bugquery.serverside.stacktrace.distance.levenshtein;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bugquery.serverside.entities.StackTrace;
/**
 * 
 * @author rodedzats
 * @since 2.5.2017
 */
public class LevenshteinSTDistancerTest {
	String stackTrace1 = "Exception in thread \"main\" java.lang.NullPointerException\n"
			+ "at com.example.myproject.Book.getTitle(Book.java:16)\n"
			+ "at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
			+ "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
	
	String stackTrace2 = "Exception in thread \"main\" java.lang.NullPointerException\n"
			+ "at com.example.myproject.DIFFERENCE.getTitle(Book.java:16)\n"
			+ "at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
			+ "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
	
	String stackTrace3 = "Exception in thread \"DIFFERENCE\" java.lang.NullPointerException\n"
			+ "at com.example.myproject.DIFFERENCE.getTitle(Book.java:16)\n"
			+ "at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
			+ "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
	
	String stackTrace4 = "Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\n" + 
			"        at com.example.myproject.Author.getBookIds(Author.java:38)\n" + 
			"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
			"Caused by: java.lang.NullPointerException\n" + 
			"        at com.example.myproject.Book.getId(Book.java:22)\n" + 
			"        at com.example.myproject.Author.getBookIds(Author.java:36)\n";
	
	@Test
	public void LevenshteinStackDistanceIsZeroForSameStacks() {
		StackTrace st1 = new StackTrace(stackTrace1);
		assertEquals((new LevenshteinSTDistancer()).distance(st1, st1), 0.0, 0.001);
		assertEquals((new LevenshteinSTDistancer()).distance(stackTrace1, stackTrace1), 0.0, 0.001);
	}
	
	@Test
	public void LevenshteinStackDistanceIsBetweenZeroAndOne() {
		StackTrace st1 = new StackTrace(stackTrace1), st2 = new StackTrace(stackTrace2), 
														st4 = new StackTrace(stackTrace4);
		double distance = new LevenshteinSTDistancer().distance(st1, st2);
		assert distance >= 0 && distance <= 1;
		distance = new LevenshteinSTDistancer().distance(st1, st4);
		assert distance >= 0 && distance <= 1;
	}
	
	@Test
	public void LevenshteinStackDistanceIsSmallerForSimilarStacks() {
		StackTrace st1 = new StackTrace(stackTrace1), st2 = new StackTrace(stackTrace2), 
				st3 = new StackTrace(stackTrace3), st4 = new StackTrace(stackTrace4);
		LevenshteinSTDistancer d = new LevenshteinSTDistancer();
		double distance13 = d.distance(st1, st3), distance14 = d.distance(st1, st4), distance23 = d.distance(st2, st3),
				distance24 = d.distance(st2, st4);
		assert d.distance(st1, st2) < distance13;
		assert distance13 < distance14;
		assert distance23 < distance24;
	}	
}
