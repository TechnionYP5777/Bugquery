package com.bugquery.serverside.usecases;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

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
	private static final String filePath = "src/test/java/com/bugquery/serverside/usecases/usecases.txt";
	private static final String asteriskLine = "\n*****************************************";
	private static final String plusLine = "\n+++++++++++++++++++++++++++++++++++++++++";
	private static final String percentLine = "\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%";
	private static final String searchingMessage = "Searching stack trace: ";
	
	private static List<Post> getRelevantPosts(StackTraceDistancer d, String stackTrace) {
		StackTraceRetriever $ = new GeneralStackTraceRetriever(d);
		try {
			return $.getMostRelevantPosts(stackTrace, numOfPosts);
		} catch (GeneralDBException | InvalidStackTraceException ¢) {
			¢.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	private static List<StackTraceDistancer> getDistancerList() {
		List<StackTraceDistancer> $ = new ArrayList<>();
		$.add(new JaccardSTDistancer());
		$.add(new LevenshteinSTDistancer());
		return $;
	}
	
	private static List<String> getStackTracesList() {
		List<String> $ = new ArrayList<>();
		$.add("Exception in thread \"main\" java.lang.NullPointerException\n"
				+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
				+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
				+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)");
		$.add("java.sql.SQLException\n" + 
				"        at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:1055)\n" + 
				"        at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:956)\n" + 
				"        at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:926)\n" + 
				"        at com.mysql.jdbc.ResultSetImpl.checkRowPos(ResultSetImpl.java:815)\n" + 
				"        at com.mysql.jdbc.ResultSetImpl.getStringInternal(ResultSetImpl.java:5528)\n" + 
				"        at com.mysql.jdbc.ResultSetImpl.getString(ResultSetImpl.java:5448)\n" + 
				"        at com.mysql.jdbc.ResultSetImpl.getString(ResultSetImpl.java:5488)\n" + 
				"        at Fashion_Footwear.Buy1ActionPerformed(Fashion_Footwear.java:370)\n" + 
				"        at Fashion_Footwear.access$000(Fashion_Footwear.java:19)\n" + 
				"        at Fashion_Footwear$1.actionPerformed(Fashion_Footwear.java:100)\n" + 
				"        at javax.swing.AbstractButton.fireActionPerformed(AbstractButton.java:1995)\n" + 
				"        at javax.swing.AbstractButton$Handler.actionPerformed(AbstractButton.java:2318)\n" + 
				"        at javax.swing.DefaultButtonModel.fireActionPerformed(DefaultButtonModel.java:387)\n" + 
				"        at javax.swing.DefaultButtonModel.setPressed(DefaultButtonModel.java:242)\n" + 
				"        at javax.swing.plaf.basic.BasicButtonListener.mouseReleased(BasicButtonListener.java:236)\n" + 
				"        at java.awt.Component.processMouseEvent(Component.java:6216)\n" + 
				"        at javax.swing.JComponent.processMouseEvent(JComponent.java:3265)\n" + 
				"        at java.awt.Component.processEvent(Component.java:5981)\n" + 
				"        at java.awt.Container.processEvent(Container.java:2041)\n" + 
				"        at java.awt.Component.dispatchEventImpl(Component.java:4583)\n" + 
				"        at java.awt.Container.dispatchEventImpl(Container.java:2099)\n" + 
				"        at java.awt.Component.dispatchEvent(Component.java:4413)\n" + 
				"        at java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4556)\n" + 
				"        at java.awt.LightweightDispatcher.processMouseEvent(Container.java:4220)\n" + 
				"        at java.awt.LightweightDispatcher.dispatchEvent(Container.java:4150)\n" + 
				"        at java.awt.Container.dispatchEventImpl(Container.java:2085)\n" + 
				"        at java.awt.Window.dispatchEventImpl(Window.java:2475)\n" + 
				"        at java.awt.Component.dispatchEvent(Component.java:4413)\n" + 
				"        at java.awt.EventQueue.dispatchEvent(EventQueue.java:599)\n" + 
				"        at java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:269)\n" + 
				"        at java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:184)\n" + 
				"        at java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:174)\n" + 
				"        at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:169)\n" + 
				"        at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:161)\n" + 
				"        at java.awt.EventDispatchThread.run(EventDispatchThread.java:122)");
		return $;
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
			relevantPosts.add("Distancer: " + d);
			for (String s : sampleStackTraces) {
				relevantPosts.add(searchingMessage);
				relevantPosts.add(s);
				relevantPosts.add("Found: ");
				result = getRelevantPosts(d, s);
				for(Post ¢: result){
					relevantPosts.add("Stack trace: ");
					relevantPosts.add(¢.getStackTrace().getContent());
					relevantPosts.add("Question: ");
					relevantPosts.add(¢.getQuestion() + "\n");
					relevantPosts.add(percentLine);
				}
				relevantPosts.add(plusLine);
			}
			try {
				Files.write(f, relevantPosts, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
			} catch (IOException ¢) {
				¢.printStackTrace();
			}
		}
	}
}
