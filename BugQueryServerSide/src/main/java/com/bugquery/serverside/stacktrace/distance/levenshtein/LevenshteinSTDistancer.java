package com.bugquery.serverside.stacktrace.distance.levenshtein;

import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.stacktrace.distance.StackTraceDistancer;

/**
 * A StackTrace distancer based on the levenshtein algorithm
 * @author rodedzats
 * @since 28.4.2017
 */
public class LevenshteinSTDistancer implements StackTraceDistancer {

	private final LevenshteinCost cost;
	
	public LevenshteinSTDistancer() {
		this.cost = new SimpleLevenshteinCost();
	}
	
	public LevenshteinSTDistancer(LevenshteinCost cost) {
		this.cost = cost;
	}
	
	@Override
	public double distance(String o1, String o2) {
		return this.distance(new StackTrace(o1), new StackTrace(o2));
	}
	
	@Override
	public double distance(StackTrace st1, StackTrace st2) {
		return new LevenshteinDistance(st1, st2, cost).getDistance();
	}
}
