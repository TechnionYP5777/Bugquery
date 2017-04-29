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
public class LevenshteinDistanceTest {
	/*
	 * A little hacky solution. The levenshtein class works with stack traces so
	 * to check if it works, just set the stack of calls of the StackTrace class
	 * to something basic (example from wikipedia - distance("kitten", "sitting") = 3
	 */
	private LevenshteinDistance getDistancerFromStrings(String s1, String s2) {
		return new LevenshteinDistance(new StackTrace("", "", splitStringToList(s1)),
				new StackTrace("", "", splitStringToList(s2)), new SimpleLevenshteinCost());
	}
	
	private List<String> splitStringToList(String s) {
		return new ArrayList<>(Arrays.asList(s.split("")));
	}
	
	/*
	 * This test is based on the wikipedia page on Levenshtein distance.
	 */
	@Test
	public void levenshteinDistanceIsCorrectForSimpleExample() {
		LevenshteinDistance d = getDistancerFromStrings("kitten","sitting");
		assertEquals(d.getDistance(), 3.0, 0.001);
		d = getDistancerFromStrings("Sunday","Saturday");
		assertEquals(d.getDistance(), 3.0, 0.001);
	}
}
