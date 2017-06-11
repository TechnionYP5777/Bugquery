package com.bugquery.serverside.stacktrace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.exceptions.GeneralDBException;
import com.bugquery.serverside.exceptions.InvalidStackTraceException;
import com.bugquery.serverside.repositories.PostRepository;
import com.bugquery.serverside.stacktrace.distance.JaccardSTDistancer;
import com.bugquery.serverside.stacktrace.distance.StackTraceDistancer;

/**
 * Utility class for getting relevant stack traces from the database
 * 
 * @author rodedzats
 * @since 11.12.2016
 */
@Component
public class GeneralStackTraceRetriever implements StackTraceRetriever {

	private StackTraceDistancer d;
	@Autowired
	private PostRepository repo;

	public GeneralStackTraceRetriever() {
		this(new JaccardSTDistancer());
	}
	
	public GeneralStackTraceRetriever(StackTraceDistancer d) {
		if (d == null)
			throw new IllegalArgumentException();
		this.d = d;
	}

	public GeneralStackTraceRetriever(StackTraceDistancer d, PostRepository repo) {
		this(d);
		this.repo = repo;
	}

	/**
	 * This function returns the posts with the closest stack traces to the
	 * input @stackTrace using the @distance distancer to sort the traces. This
	 * function assumes that the database which contains posts with stack traces
	 * was extracted to a in memory list.
	 */
	private static List<Post> getMostRelevantStackTraces(List<Post> allPosts, final StackTrace t, StackTraceDistancer d,
			int numOfPosts) {
		numOfPosts = Math.min(numOfPosts,allPosts.size());
		if (allPosts == null || d == null || numOfPosts <= 0 || t == null) // lazy
			throw new IllegalArgumentException();

		// Can't use lambda function since we need the value to be int!
		// Rounding down is not correct in this case.
		final Comparator<Post> comparator = new Comparator<Post>() {
			@Override
			public int compare(Post p1, Post p2) {
				double $ = d.distance(p1.getStackTrace(), t), d_p2 = d.distance(p2.getStackTrace(), t);
				return $ > d_p2 ? 1 : $ < d_p2 ? -1 : 0; // distance twice
			}
		};
		
		PriorityQueue<Post> pq = new PriorityQueue<>(allPosts.size(), comparator);
		pq.addAll(allPosts); // build PQ with the same init size
		List<Post> $ = new ArrayList<>();
		for (int ¢ = 0; ¢ < numOfPosts; ++¢)
			$.add(pq.poll());
		return $;
	}

	/**
	 * This function completes the pipeline between the server and the database.
	 * 
	 * @param stackTrace
	 *            - the stack trace which the user queried about
	 * @param numOfPosts
	 *            - number of relevant posts needed
	 * @return list of most relevant post to the given stack trace
	 * @throws GeneralDBException
	 * @throws InvalidStackTraceException
	 */
	@Override
	public List<Post> getMostRelevantPosts(String stackTrace, int numOfPosts)
			throws GeneralDBException, InvalidStackTraceException {
		if (stackTrace == null || numOfPosts <= 0)
			throw new IllegalArgumentException();
		StackTrace $ = new StackTrace(stackTrace);
		if ($.getException() == StackTrace.noExceptionFound)
			throw new InvalidStackTraceException("Illegal stack trace - no exception was found.");
		List<Post> allPosts = new ArrayList<>();
		try {
			allPosts = repo.findByStackTraceException($.getException());
		} catch (Exception ¢) {
			throw new GeneralDBException("General db error: " + ¢.getMessage());
		}
		return GeneralStackTraceRetriever.getMostRelevantStackTraces(allPosts, $, d, numOfPosts);
	}

	/**
	 * Get posts from db by posts ids (keeps order)
	 */
	@Override
	public List<Post> getPostsByIds(List<Long> postIds) {
		List<Post> $ = new ArrayList<>();
		for (Long id : postIds)
			$.add(repo.findOne(id));
		return $;
	}
}
