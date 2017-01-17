package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.bugquery.serverside.dbparsing.dbretrieval.DBConnector;
import com.bugquery.serverside.dbparsing.dbretrieval.SQLDBConnector;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;

/**
 * Utility class for getting relevant stack traces from the database
 * @author rodedzats
 *
 */
public class GeneralStackTraceRetriever implements StackTraceRetriever{
	
	private StackTraceDistancer d;
	private DBConnector connector;
	
	public GeneralStackTraceRetriever() {
		this(new JaccardSTDistancer(), new SQLDBConnector());
	}
	
	public GeneralStackTraceRetriever(StackTraceDistancer d, DBConnector connector) {
		this.d = d;
		this.connector = connector;
	}
	
	/*
	 * This function returns the posts with the closest stack traces to the input @stackTrace
	 * using the @distance distancer to sort the traces.
	 * This function assumes that the database which contains posts with stack traces was
	 * extracted to a in memory list.
	 */
	private static List<Post> getMostRelevantStackTraces(List<Post> allPosts, final StackTrace t, StackTraceDistancer d, int numOfPosts) {
		if(allPosts == null || d == null || numOfPosts <= 0 
				|| t == null || allPosts.size() <= numOfPosts) // lazy evaluation
			throw new IllegalArgumentException();
		
		Comparator<Post> postC = new Comparator<Post>() {
			@Override
			public int compare(Post p1, Post p2) {
				double $ = d.distance(p1.stackTrace, t); // saves time
				double d_p2 = d.distance(p2.stackTrace, t); // computing
				return $ > d_p2 ? 1 : $ < d_p2 ? -1 : 0; // distance twice
			}
		};
			
		PriorityQueue<Post> pq = new PriorityQueue<>(allPosts.size(), postC); 
		pq.addAll(allPosts); // build PQ with the same init size
		ArrayList<Post> $ = new ArrayList<>();
		for (int ¢=0;¢<numOfPosts; ++¢)
			$.add($.size(), pq.poll());
		
		return $;
	}
	

	/**
	 * This function completes the pipeline between the server and the database.
	 * @param stackTrace - the stack trace which the user queried about
	 * @param numOfPosts - number of relevant posts needed
	 * @return list of most relevant post to the given stack trace
	 * @throws GeneralDBException 
	 * @throws InvalidStackTraceException 
	 */
	@Override
	public List<Post> getMostRelevantPosts(String stackTrace, int numOfPosts) throws GeneralDBException, InvalidStackTraceException {
		if(stackTrace == null || numOfPosts <= 0)
			throw new IllegalArgumentException();
		StackTrace $ = new StackTrace(stackTrace);
		if($.getException() == StackTrace.noExceptionFound)
			throw new InvalidStackTraceException("Illegal stack trace ");
		List<Post> allPosts = new ArrayList<>();
		try {
			allPosts = connector.getAllQuestionsWithTheException($.getException());
		} catch(Exception ¢) {
			throw new GeneralDBException("General db error: " + ¢.getMessage());
		}
		return GeneralStackTraceRetriever.getMostRelevantStackTraces(allPosts, $, d, numOfPosts);
	}
}
