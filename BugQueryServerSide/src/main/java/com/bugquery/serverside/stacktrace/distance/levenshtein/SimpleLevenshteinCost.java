package com.bugquery.serverside.stacktrace.distance.levenshtein;

import com.bugquery.serverside.entities.StackTrace;

/**
 * A simple implementation of the Levenshtein cost, always returns 1 as in the original 
 * algorithm
 * @author rodedzats
 * @since 28.4.2017
 */
public class SimpleLevenshteinCost implements LevenshteinCost {

	@Override
	@SuppressWarnings("unused")
	public double insert(StackTrace __, int index) {
		return 1;
	}

	@Override
	@SuppressWarnings("unused")
	public double delete(StackTrace __, int index) {
		return 1;
	}

	@Override
	public double substitute(StackTrace tFirst, int indexFirst, StackTrace tSecond, int indexSecond) {
		return tFirst.getStackOfCalls().get(indexFirst-1).equals(tSecond.getStackOfCalls().get(indexSecond-1)) ? 0 : 1;
	}
}
