package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bugquery.serverside.entities.Post;

/**
 * Utility class for getting relevant stack traces from the database
 * @author rodedzats
 *
 */
public class StackTraceRetriever {
	/*
	 * This function returns the posts with the closest stack traces to the input @stackTrace
	 * using the @distance distancer to sort the traces.
	 * This function assumes that the database which contains posts with stack traces was
	 * extracted to a in memory list.
	 */
	public static List<Post> getMostRelevantStackTraces(List<Post> allPosts, final String stackTrace, StackTraceDistancer d, int numOfPosts) {
		if(allPosts == null || d == null || numOfPosts <= 0 || stackTrace == null)
			return null;
		Collections.sort(allPosts, new Comparator<Post>(){
			  public int compare(Post p1, Post p2){
				return d.distance(p1.stackTrace, stackTrace) > d.distance(p2.stackTrace, stackTrace) ? 1
						: d.distance(p1.stackTrace, stackTrace) < d.distance(p2.stackTrace, stackTrace) ? -1 : 0;
			}
			});
		return allPosts.subList(0, numOfPosts);
	}
}
