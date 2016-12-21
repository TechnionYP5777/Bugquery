package com.bugquery.serverside.stacktrace;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bugquery.serverside.dbparsing.DBSearch;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.exceptions.GeneralDBException;

/**
 * Utility class for getting relevant stack traces from the database
 * @author rodedzats
 *
 */
public class StackTraceRetriever {
	
	/**
	 * This function completes the pipeline between the server and the database.
	 * @param stackTrace - the stack trace which the user queried about
	 * @param numOfPosts - number of relevant posts needed
	 * @return list of most relevant post to the given stack trace
	 * @throws GeneralDBException 
	 */
	public static List<Post> getMostRelevantPosts(String stackTrace, int numOfPosts) throws GeneralDBException {
		if(stackTrace == null || numOfPosts <= 0)
			throw new IllegalArgumentException();
		StackTrace st = new StackTrace(stackTrace);
		List<Post> allPosts = new ArrayList<>();
		try {
			allPosts = DBSearch.getAllStackTracesWithTheException(st.getException());
		} catch(Exception e) {
			System.out.println((e + ""));
			throw new GeneralDBException("General db error");
		}
		return StackTraceRetriever.getMostRelevantStackTraces(allPosts, st, (new JaccardSTDistancer()), numOfPosts);
	}
	
	/*
	 * This function returns the posts with the closest stack traces to the input @stackTrace
	 * using the @distance distancer to sort the traces.
	 * This function assumes that the database which contains posts with stack traces was
	 * extracted to a in memory list.
	 */
	public static List<Post> getMostRelevantStackTraces(List<Post> allPosts, final StackTrace t, StackTraceDistancer d, int numOfPosts) {
		if(allPosts == null || d == null || numOfPosts <= 0 || t == null)
			throw new IllegalArgumentException();
		Collections.sort(allPosts, new Comparator<Post>(){
			  public int compare(Post p1, Post p2){
				return d.distance(p1.stackTrace.getStackTrace(), t.getStackTrace()) > d.distance(p2.stackTrace.getStackTrace(), t.getStackTrace()) ? 1
						: d.distance(p1.stackTrace.getStackTrace(), t.getStackTrace()) < d.distance(p2.stackTrace.getStackTrace(), t.getStackTrace()) ? -1 : 0;
			}
			});
		return allPosts.subList(0, numOfPosts);
	}
}
