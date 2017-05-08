package com.bugquery.serverside.usecases;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

import com.bugquery.serverside.dbparsing.dbretrieval.SQLDBConnector;
import com.bugquery.serverside.entities.*;
import com.bugquery.serverside.exceptions.*;
import com.bugquery.serverside.stacktrace.*;
import com.bugquery.serverside.stacktrace.distance.*;
import com.bugquery.serverside.stacktrace.distance.levenshtein.LevenshteinSTDistancer;

/**
 * Class used to demonstrate different use cases, 
 * and seeing the result returned from the server 
 * using the different distancers 
 * @author yonzarecki
 * @since 8.5.2017
 */
public class UseCasesDemonstrator {
	private static final int numOfPosts = 10;
	private static final String filePath = "src\\test\\java\\com\\bugquery\\serverside\\usecases\\usecases.txt";
	private static final String asteriskLine = "*************************";
	private static final String searchingMessage = "Searching stack trace: ";
	
	private static List<Post> getRelevantPosts(StackTraceDistancer d, String stackTrace) {
		StackTraceRetriever retriever = new GeneralStackTraceRetriever(d, new SQLDBConnector());
		try {
			return retriever.getMostRelevantPosts(stackTrace, numOfPosts);
		} catch (GeneralDBException | InvalidStackTraceException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	private static List<StackTraceDistancer> getDistancerList() {
		List<StackTraceDistancer> allDistancers = new ArrayList<>();
		allDistancers.add(new JaccardSTDistancer());
		allDistancers.add(new LevenshteinSTDistancer());
		return allDistancers;
	}
	
	private static List<String> getStackTracesList() {
		List<String> sampleStackTraces = new ArrayList<>();
		String s1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		sampleStackTraces.add(s1);
		return sampleStackTraces;
	}
	
	public static void main(String[] args) {
		List<StackTraceDistancer> allDistancers = getDistancerList();
		List<String> sampleStackTraces = getStackTracesList();
		List<Post> result;
		List<String> relevantPosts;
		Path f = Paths.get(filePath);
		try {
			Files.write(f, new byte[0]);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(StackTraceDistancer d: allDistancers) {	
			relevantPosts = new ArrayList<>();
			relevantPosts.add(asteriskLine);
			relevantPosts.add("Distancer: " + d.toString());
			for (String s : sampleStackTraces) {
				relevantPosts.add(searchingMessage);
				relevantPosts.add(s);
				relevantPosts.add("Found: ");
				result = getRelevantPosts(d, s);
				for(Post p: result)
					relevantPosts.add(((MinSOPost) p).questionString + "\n");
				try {
					Files.write(f, relevantPosts, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
