package com.bugquery.serverside.stacktrace.distance.levenshtein;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import com.bugquery.serverside.entities.StackTrace;

/**
 * 
 * @author rodedzats
 * @since 29.4.2017
 */
@SuppressWarnings("static-method")
public class LevenshteinDistanceTest {
	/*
	 * A little hacky solution. The levenshtein class works with stack traces so
	 * to check if it works, just set the stack of calls of the StackTrace class
	 * to something basic (example from wikipedia - distance("kitten",
	 * "sitting") = 3
	 */
	private static LevenshteinDistance getDistancerFromStrings(String s1, String s2) {
		return new LevenshteinDistance(new StackTrace("", "", splitStringToList(s1)),
				new StackTrace("", "", splitStringToList(s2)), new SimpleLevenshteinCost());
	}

	private static List<String> splitStringToList(String ¢) {
		return new ArrayList<>(Arrays.asList(¢.split("")));
	}

	/*
	 * This test is based on the wikipedia page on Levenshtein distance.
	 */
	@Test
	public void levenshteinDistanceIsCorrectForSimpleExample() {
		LevenshteinDistance d = getDistancerFromStrings("kitten", "sitting");
		assertEquals(d.getDistance(), 3.0, 0.001);
		d = getDistancerFromStrings("Sunday", "Saturday");
		assertEquals(d.getDistance(), 3.0, 0.001);
	}

	@Test
	public void levenshteinDistanceIsZeroForEqualString() {
		assertEquals(getDistancerFromStrings("Lilwayne1", "Lilwayne1").getDistance(), 0.0, 0.001);
	}

	/*
	 * This test is based on
	 * http://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/
	 * editdistance/Levenshtein%20Distance.htm
	 */
	@Test
	public void levenshteinDistanceIsCorrectForSimpleExample2() {
		assertEquals(getDistancerFromStrings("GUMBO", "GAMBOL").getDistance(), 2.0, 0.001);
	}

	@Test
	public void levenshteinDistanceMatrixIsCorrect() {
		LevenshteinDistance d = getDistancerFromStrings("ro", "ru");
		d.getDistance();
		assert Arrays.deepEquals(new double[][] { { 0.0, 1.0, 2.0 }, { 1.0, 0.0, 1.0 }, { 2.0, 1.0, 1.0 } },
				d.getDistanceMatrix());
	}
}
