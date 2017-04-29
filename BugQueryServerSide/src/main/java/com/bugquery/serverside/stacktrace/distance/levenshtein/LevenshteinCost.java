package com.bugquery.serverside.stacktrace.distance.levenshtein;

import com.bugquery.serverside.entities.StackTrace;

/**
 * An interface which defines the cost for each operation in the levenshtein algorithm.
 * The allowed operations are: insertion, deletion and substitution.
 * The cost is based on the index of the line in the stack trace (the top lines are more 
 * important)
 * @author rodedzats
 * @since 28.4.2017
 */
public interface LevenshteinCost {
	double insert(StackTrace t, int index);
	double delete(StackTrace t, int index);
	double substitute(StackTrace tFirst, int indexFirst, StackTrace tSecond, int indexSecond);
}
