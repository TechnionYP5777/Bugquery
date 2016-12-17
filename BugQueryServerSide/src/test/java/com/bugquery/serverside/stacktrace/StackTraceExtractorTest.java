package com.bugquery.serverside.stacktrace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bugquery.serverside.entities.StackTrace;

/**
 * This test class is based on the answers in 
 * http://stackoverflow.com/questions/3988788/what-is-a-stack-trace-and-how-can-i-use-it-to-debug-my-application-errors
 * @author rodedzats
 *
 */
public class StackTraceExtractorTest {
	
	@Test
	public void emptyListIsReturnedForNullStringAndEmptyString() {
		assertEquals(new ArrayList<StackTrace>(), StackTraceExtractor.extract(null));
		assertEquals(new ArrayList<StackTrace>(), StackTraceExtractor.extract(""));
	}
	
	/*
	 * Checking that for a random stackoverflow question which doesn't contain
	 * exception stack trace, the list returned is empty. using the contents
	 * of this question: http://stackoverflow.com/questions/8938235/java-sort-an-array
	 */
	@Test
	public void emptyListIsReturnedForStringWithoutStackTrace() {
		assertEquals(new ArrayList<StackTrace>(), StackTraceExtractor.extract(
				("I'm trying to make a program that consists of an array of 10 integers which all has a random value, so far so good.\n"
						+ "\n"
						+ "However, now I need to sort them in order from lowest to highest value and then print it onto the screen, how would I go about doing so?\n"
						+ "\n"
						+ "(Sorry for having so much code for a program that small, I ain't that good with loops, just started working with Java)\n"
						+ "\n" + "public static void main(String args[])\n" + "{\n"
						+ "    int [] array = new int[10];\n" + "\n" + "    array[0] = ((int)(Math.random()*100+1));\n"
						+ "    array[1] = ((int)(Math.random()*100+1));\n"
						+ "    array[2] = ((int)(Math.random()*100+1));\n"
						+ "    array[3] = ((int)(Math.random()*100+1));\n"
						+ "    array[4] = ((int)(Math.random()*100+1));\n"
						+ "    array[5] = ((int)(Math.random()*100+1));\n"
						+ "    array[6] = ((int)(Math.random()*100+1));\n"
						+ "    array[7] = ((int)(Math.random()*100+1));\n"
						+ "    array[8] = ((int)(Math.random()*100+1));\n"
						+ "    array[9] = ((int)(Math.random()*100+1));\n" + "\n"
						+ "    System.out.println(array[0] +\" \" + array[1] +\" \" + array[2] +\" \" + array[3]\n"
						+ "    +\" \" + array[4] +\" \" + array[5]+\" \" + array[6]+\" \" + array[7]+\" \" \n"
						+ "    + array[8]+\" \" + array[9] );        \n" + "\n" + "}")));
	}
	
	/*
	 * The string in this test case contains only the stack trace without the whole question
	 */
	@Test
	public void listWithCorrectStackTraceIsReturnedForSimpleTraceWithoutQuestion() {
		String trace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		List<StackTrace> l = new ArrayList<>();
		l.add(new StackTrace(trace));
		assertEquals(l,StackTraceExtractor.extract(trace));
	}
	
	/*
	 * The string in this test case contains the stackoverflow question with the stack trace
	 */
	@Test
	public void listWithCorrectStackTraceIsReturnedForSimpleTraceWithQuestion() {
		String question = "Sometimes when I run my application it gives me an error that looks like:\n" + 
				"\n" + 
				"Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
				"People have referred to this as a \"stack trace\". What is a stack trace? What can it tell me about the error that's happening in my program?";
		String trace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		List<StackTrace> l = new ArrayList<>();
		l.add(new StackTrace(trace));
		assertEquals(l, StackTraceExtractor.extract(question));
	}
	
	
	@Test
	public void listWithCorrectStackTracesIsReturnedForAnswerWithMultipleTraces() {
		String answer = "In simple terms, a stack trace is a list of the method calls that the application was in the middle of when an Exception was thrown.\n" + 
				"\n" + 
				"Simple Example\n" + 
				"\n" + 
				"With the example given in the question, we can determine exactly where the exception was thrown in the application. Let's have a look at the stack trace:\n" + 
				"\n" + 
				"Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
				"This is a very simple stack trace. If we start at the beginning of the list of \"at ...\", we can tell where our error happened. What we're looking for is the topmost method call that is part of our application. In this case, it's:\n" + 
				"\n" + 
				"at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"To debug this, we can open up Book.java and look at line 16, which is:\n" + 
				"\n" + 
				"public String getTitle() {\n" + 
				"    System.out.println(title.toString()); <-- line 16\n" + 
				"    return title;\n" + 
				"}\n" + 
				"This would indicate that something (probably title) is null in the above code.\n" + 
				"\n" + 
				"Example with a chain of exceptions\n" + 
				"\n" + 
				"Sometimes applications will catch an Exception and re-throw it as the cause of another Exception. This typically looks like:\n" + 
				"\n" + 
				"try {\n" + 
				"....\n" + 
				"} catch (NullPointerException e) {\n" + 
				"  throw new IllegalStateException(\"A book has a null property\", e)\n" + 
				"}\n" + 
				"This might give you a stack trace that looks like:\n" + 
				"\n" + 
				"Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\n" + 
				"        at com.example.myproject.Author.getBookIds(Author.java:38)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
				"Caused by: java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getId(Book.java:22)\n" + 
				"        at com.example.myproject.Author.getBookIds(Author.java:35)\n" + 
				"        ... 1 more\n" + 
				"What's different about this one is the \"Caused by\". Sometimes exceptions will have multiple \"Caused by\" sections. For these, you typically want to find the \"root cause\", which will be one of the lowest \"Caused by\" sections in the stack trace. In our case, it's:\n" + 
				"\n" + 
				"Caused by: java.lang.NullPointerException <-- root cause\n" + 
				"        at com.example.myproject.Book.getId(Book.java:22) <-- important line\n" + 
				"Again, with this exception we'd want to look at line 22 of Book.java to see what might cause the NullPointerException here.\n" + 
				"\n" + 
				"More daunting example with library code\n" + 
				"\n" + 
				"Usually stack traces are much more complex than the two examples above. Here's an example (it's a long one, but demonstrates several levels of chained exceptions):\n" + 
				"\n" + 
				"javax.servlet.ServletException: Something bad happened\n" + 
				"    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:60)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at com.example.myproject.ExceptionHandlerFilter.doFilter(ExceptionHandlerFilter.java:28)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at com.example.myproject.OutputBufferFilter.doFilter(OutputBufferFilter.java:33)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)\n" + 
				"    at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\n" + 
				"    at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\n" + 
				"    at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)\n" + 
				"    at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)\n" + 
				"    at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\n" + 
				"    at org.mortbay.jetty.Server.handle(Server.java:326)\n" + 
				"    at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\n" + 
				"    at org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:943)\n" + 
				"    at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:756)\n" + 
				"    at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218)\n" + 
				"    at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\n" + 
				"    at org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228)\n" + 
				"    at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\n" + 
				"Caused by: com.example.myproject.MyProjectServletException\n" + 
				"    at com.example.myproject.MyServlet.doPost(MyServlet.java:169)\n" + 
				"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n" + 
				"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)\n" + 
				"    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:30)\n" + 
				"    ... 27 more\n" + 
				"Caused by: org.hibernate.exception.ConstraintViolationException: could not insert: [com.example.myproject.MyEntity]\n" + 
				"    at org.hibernate.exception.SQLStateConverter.convert(SQLStateConverter.java:96)\n" + 
				"    at org.hibernate.exception.JDBCExceptionHelper.convert(JDBCExceptionHelper.java:66)\n" + 
				"    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:64)\n" + 
				"    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2329)\n" + 
				"    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2822)\n" + 
				"    at org.hibernate.action.EntityIdentityInsertAction.execute(EntityIdentityInsertAction.java:71)\n" + 
				"    at org.hibernate.engine.ActionQueue.execute(ActionQueue.java:268)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:321)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:204)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:130)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.saveWithGeneratedOrRequestedId(DefaultSaveOrUpdateEventListener.java:210)\n" + 
				"    at org.hibernate.event.def.DefaultSaveEventListener.saveWithGeneratedOrRequestedId(DefaultSaveEventListener.java:56)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.entityIsTransient(DefaultSaveOrUpdateEventListener.java:195)\n" + 
				"    at org.hibernate.event.def.DefaultSaveEventListener.performSaveOrUpdate(DefaultSaveEventListener.java:50)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.onSaveOrUpdate(DefaultSaveOrUpdateEventListener.java:93)\n" + 
				"    at org.hibernate.impl.SessionImpl.fireSave(SessionImpl.java:705)\n" + 
				"    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:693)\n" + 
				"    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:689)\n" + 
				"    at sun.reflect.GeneratedMethodAccessor5.invoke(Unknown Source)\n" + 
				"    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n" + 
				"    at java.lang.reflect.Method.invoke(Method.java:597)\n" + 
				"    at org.hibernate.context.ThreadLocalSessionContext$TransactionProtectionWrapper.invoke(ThreadLocalSessionContext.java:344)\n" + 
				"    at $Proxy19.save(Unknown Source)\n" + 
				"    at com.example.myproject.MyEntityService.save(MyEntityService.java:59) <-- relevant call (see notes below)\n" + 
				"    at com.example.myproject.MyServlet.doPost(MyServlet.java:164)\n" + 
				"    ... 32 more\n" + 
				"Caused by: java.sql.SQLException: Violation of unique constraint MY_ENTITY_UK_1: duplicate value(s) for column(s) MY_COLUMN in statement [...]\n" + 
				"    at org.hsqldb.jdbc.Util.throwError(Unknown Source)\n" + 
				"    at org.hsqldb.jdbc.jdbcPreparedStatement.executeUpdate(Unknown Source)\n" + 
				"    at com.mchange.v2.c3p0.impl.NewProxyPreparedStatement.executeUpdate(NewProxyPreparedStatement.java:105)\n" + 
				"    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:57)\n" + 
				"    ... 54 more\n" + 
				"In this example, there's a lot more. What we're mostly concerned about is looking for methods that are from our code, which would be anything in the com.example.myproject package. From the second example (above), we'd first want to look down for the root cause, which is:\n" + 
				"\n" + 
				"Caused by: java.sql.SQLException\n" + 
				"However, all the method calls under that are library code. So we'll move up to the \"Caused by\" above it, and look for the first method call originating from our code, which is:\n" + 
				"\n" + 
				"at com.example.myproject.MyEntityService.save(MyEntityService.java:59)\n" + 
				"Like in previous examples, we should look at MyEntityService.java on line 59, because that's where this error originated (this one's a bit obvious what went wrong, since the SQLException states the error, but the debugging procedure is what we're after).";
		String trace1 = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				"        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		String trace2 = "Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\n" + 
				"        at com.example.myproject.Author.getBookIds(Author.java:38)\n" + 
				"        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n" + 
				"Caused by: java.lang.NullPointerException\n" + 
				"        at com.example.myproject.Book.getId(Book.java:22)\n" + 
				"        at com.example.myproject.Author.getBookIds(Author.java:35)\n" + 
				"        ... 1 more";
		String trace3 = "javax.servlet.ServletException: Something bad happened\n" + 
				"    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:60)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at com.example.myproject.ExceptionHandlerFilter.doFilter(ExceptionHandlerFilter.java:28)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at com.example.myproject.OutputBufferFilter.doFilter(OutputBufferFilter.java:33)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)\n" + 
				"    at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\n" + 
				"    at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\n" + 
				"    at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)\n" + 
				"    at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)\n" + 
				"    at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\n" + 
				"    at org.mortbay.jetty.Server.handle(Server.java:326)\n" + 
				"    at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\n" + 
				"    at org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:943)\n" + 
				"    at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:756)\n" + 
				"    at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218)\n" + 
				"    at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\n" + 
				"    at org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228)\n" + 
				"    at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\n" + 
				"Caused by: com.example.myproject.MyProjectServletException\n" + 
				"    at com.example.myproject.MyServlet.doPost(MyServlet.java:169)\n" + 
				"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n" + 
				"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\n" + 
				"    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)\n" + 
				"    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:30)\n" + 
				"    ... 27 more\n" + 
				"Caused by: org.hibernate.exception.ConstraintViolationException: could not insert: [com.example.myproject.MyEntity]\n" + 
				"    at org.hibernate.exception.SQLStateConverter.convert(SQLStateConverter.java:96)\n" + 
				"    at org.hibernate.exception.JDBCExceptionHelper.convert(JDBCExceptionHelper.java:66)\n" + 
				"    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:64)\n" + 
				"    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2329)\n" + 
				"    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2822)\n" + 
				"    at org.hibernate.action.EntityIdentityInsertAction.execute(EntityIdentityInsertAction.java:71)\n" + 
				"    at org.hibernate.engine.ActionQueue.execute(ActionQueue.java:268)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:321)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:204)\n" + 
				"    at org.hibernate.event.def.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:130)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.saveWithGeneratedOrRequestedId(DefaultSaveOrUpdateEventListener.java:210)\n" + 
				"    at org.hibernate.event.def.DefaultSaveEventListener.saveWithGeneratedOrRequestedId(DefaultSaveEventListener.java:56)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.entityIsTransient(DefaultSaveOrUpdateEventListener.java:195)\n" + 
				"    at org.hibernate.event.def.DefaultSaveEventListener.performSaveOrUpdate(DefaultSaveEventListener.java:50)\n" + 
				"    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.onSaveOrUpdate(DefaultSaveOrUpdateEventListener.java:93)\n" + 
				"    at org.hibernate.impl.SessionImpl.fireSave(SessionImpl.java:705)\n" + 
				"    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:693)\n" + 
				"    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:689)\n" + 
				"    at sun.reflect.GeneratedMethodAccessor5.invoke(Unknown Source)\n" + 
				"    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n" + 
				"    at java.lang.reflect.Method.invoke(Method.java:597)\n" + 
				"    at org.hibernate.context.ThreadLocalSessionContext$TransactionProtectionWrapper.invoke(ThreadLocalSessionContext.java:344)\n" + 
				"    at $Proxy19.save(Unknown Source)\n" + 
				"    at com.example.myproject.MyEntityService.save(MyEntityService.java:59) <-- relevant call (see notes below)\n" + 
				"    at com.example.myproject.MyServlet.doPost(MyServlet.java:164)\n" + 
				"    ... 32 more\n" + 
				"Caused by: java.sql.SQLException: Violation of unique constraint MY_ENTITY_UK_1: duplicate value(s) for column(s) MY_COLUMN in statement [...]\n" + 
				"    at org.hsqldb.jdbc.Util.throwError(Unknown Source)\n" + 
				"    at org.hsqldb.jdbc.jdbcPreparedStatement.executeUpdate(Unknown Source)\n" + 
				"    at com.mchange.v2.c3p0.impl.NewProxyPreparedStatement.executeUpdate(NewProxyPreparedStatement.java:105)\n" + 
				"    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:57)\n" + 
				"    ... 54 more";
		List<StackTrace> l = new ArrayList<>();
		l.add(new StackTrace(trace1));
		l.add(new StackTrace(trace2));
		l.add(new StackTrace(trace3));
		assertEquals(l, StackTraceExtractor.extract(answer));	
	}
	
	@Test
	public void htmlTagsAreRemovedCorrectly() {
		assertEquals(
				("Sometimes when I run my application it gives me an error that looks like:\n"
						+ "Exception in thread \"main\" java.lang.NullPointerException\n"
						+ " at com.example.myproject.Book.getTitle(Book.java:16)\n"
						+ " at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
						+ " at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n\n"
						+ "People have referred to this as a \"stack trace\". What is a stack trace? What can it tell me about the error that's happening in my program?\n"
						+ "\n\n"
						+ "About this question - quite often I see a question come through where a novice programmer is \"getting an error\", and they simply paste their stack trace and some random"
						+ "block of code without understanding what the stack trace is or how they can use it. This question is intended as a reference for novice programmers who might need help understanding the value of a stack trace.\n"),
				StackTraceExtractor.removeHtmlTags(
						("<p>Sometimes when I run my application it gives me an error that looks like:</p>&#xA;&#xA;<pre><code>Exception in thread \"main\" java.lang.NullPointerException&#xA;"
								+ " at com.example.myproject.Book.getTitle(Book.java:16)&#xA; at com.example.myproject.Author.getBookTitles(Author.java:25)&#xA; at com.example.myproject.Bootstrap.main(Bootstrap.java:14)&#xA;</code></pre>&#xA;&#xA;<p>People have referred to this as a \"stack trace\"."
								+ " <strong>What is a stack trace?</strong> What can it tell me about the error that's happening in my program?</p>&#xA;&#xA;<hr/>&#xA;&#xA;<p><em>About this question "
								+ "- quite often I see a question come through where a novice programmer is \"getting an error\", and they simply paste their stack trace and some random"
								+ "block of code without understanding what the stack trace is or how they can use it. This question is intended as a reference for novice"
								+ " programmers who might need help understanding the value of a stack trace.</em></p>&#xA;")));
	}
	
	@Test 
	public void correctStackTraceIsReturnedFromPostWithHtmlTags() {
		String postWithHtmlTags = "<p>Sometimes when I run my application it gives me an error that looks like:</p>&#xA;&#xA;<pre><code>Exception in thread \"main\" java.lang.NullPointerException&#xA;"
				+ " at com.example.myproject.Book.getTitle(Book.java:16)&#xA; at com.example.myproject.Author.getBookTitles(Author.java:25)&#xA; at com.example.myproject.Bootstrap.main(Bootstrap.java:14)&#xA;</code></pre>&#xA;&#xA;<p>People have referred to this as a \"stack trace\"."
				+ " <strong>What is a stack trace?</strong> What can it tell me about the error that's happening in my program?</p>&#xA;&#xA;<hr/>&#xA;&#xA;<p><em>About this question "
				+ "- quite often I see a question come through where a novice programmer is \"getting an error\", and they simply paste their stack trace and some random "
				+ "block of code without understanding what the stack trace is or how they can use it. This question is intended as a reference for novice"
				+ " programmers who might need help understanding the value of a stack trace.</em></p>&#xA;";
		String trace = "Exception in thread \"main\" java.lang.NullPointerException\n" + 
				" at com.example.myproject.Book.getTitle(Book.java:16)\n" + 
				" at com.example.myproject.Author.getBookTitles(Author.java:25)\n" + 
				" at com.example.myproject.Bootstrap.main(Bootstrap.java:14)";
		List<StackTrace> l = new ArrayList<>();
		l.add(new StackTrace(trace));
		assertEquals(l, StackTraceExtractor.extract(postWithHtmlTags));
	}
	
	@Test
	public void correctStackTraceForIssue37_1() {
		String post = "<p>I was hoping someone could help me out with a problem I'm having using the java search function in Eclipse on a particular project.</p>&#xA;&#xA;<p>When using the java search on one particular project, I get an error message saying <code>Class file name must end with .class</code> (see stack trace below). This does not seem to be happening on all projects, just one particular one, so perhaps there's something I should try to get rebuilt?</p>&#xA;&#xA;<p>I have already tried <code>Project -&gt;"
				+ " Clean</code>... and Closing Eclipse, deleting all the built class files and restarting Eclipse to no avail.</p>&#xA;&#xA;<p>The only reference I've been able to find on Google for the problem is at <a href=\"http://www.crazysquirrel.com/computing/java/eclipse/error-during-java-search.jspx\">http://www.crazysquirrel.com/computing/java/eclipse/error-during-java-search.jspx</a>, but unfortunately his solution (closing, deleting class files, restarting) did not work for me.</p>&#xA;&#xA;<p>If anyone can suggest"
				+ " something to try, or there's any more info I can gather which might help track it's down, I'd greatly appreciate the pointers.</p>&#xA;&#xA;<pre><code>Version: 3.4.0&#xA;Build id: I20080617-2000&#xA;</code></pre>&#xA;&#xA;<p>Also just found this thread - <a href=\"http://www.myeclipseide.com/PNphpBB2-viewtopic-t-20067.html\">http://www.myeclipseide.com/PNphpBB2-viewtopic-t-20067.html</a> - which indicates the same problem may occur when the project name contains a period. Unfortunately, that's not the case in my setup,"
				+ " so I'm still stuck.</p>&#xA;&#xA;<pre><code>Caused by: java.lang.IllegalArgumentException: Class file name must end with .class&#xA;at org.eclipse.jdt.internal.core.PackageFragment.getClassFile(PackageFragment.java:182)&#xA;at org.eclipse.jdt.internal.core.util.HandleFactory.createOpenable(HandleFactory.java:109)&#xA;at org.eclipse.jdt.internal.core.search.matching.MatchLocator.locateMatches(MatchLocator.java:1177)&#xA;at org.eclipse.jdt.internal.core.search.JavaSearchParticipant.locateMatches(JavaSearchParticipant.java:94)&#xA;"
				+ "at org.eclipse.jdt.internal.core.search.BasicSearchEngine.findMatches(BasicSearchEngine.java:223)&#xA;at org.eclipse.jdt.internal.core.search.BasicSearchEngine.search(BasicSearchEngine.java:506)&#xA;at org.eclipse.jdt.core.search.SearchEngine.search(SearchEngine.java:551)&#xA;at org.eclipse.jdt.internal.corext.refactoring.RefactoringSearchEngine.internalSearch(RefactoringSearchEngine.java:142)&#xA;at org.eclipse.jdt.internal.corext.refactoring.RefactoringSearchEngine.search(RefactoringSearchEngine.java:129)&#xA;at "
				+ "org.eclipse.jdt.internal.corext.refactoring.rename.RenameTypeProcessor.initializeReferences(RenameTypeProcessor.java:594)&#xA;at org.eclipse.jdt.internal.corext.refactoring.rename.RenameTypeProcessor.doCheckFinalConditions(RenameTypeProcessor.java:522)&#xA;at org.eclipse.jdt.internal.corext.refactoring.rename.JavaRenameProcessor.checkFinalConditions(JavaRenameProcessor.java:45)&#xA;at org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring.checkFinalConditions(ProcessorBasedRefactoring.java:225)&#xA;at org.eclipse.ltk.core.refactoring.Refactoring.checkAllConditions(Refactoring.java:160)&#xA;"
				+ "at org.eclipse.jdt.internal.ui.refactoring.RefactoringExecutionHelper$Operation.run(RefactoringExecutionHelper.java:77)&#xA;at org.eclipse.jdt.internal.core.BatchOperation.executeOperation(BatchOperation.java:39)&#xA;at org.eclipse.jdt.internal.core.JavaModelOperation.run(JavaModelOperation.java:709)&#xA;at org.eclipse.core.internal.resources.Workspace.run(Workspace.java:1800)&#xA;at org.eclipse.jdt.core.JavaCore.run(JavaCore.java:4650)&#xA;at org.eclipse.jdt.internal.ui.actions.WorkbenchRunnableAdapter.run(WorkbenchRunnableAdapter.java:92)&#xA;at org.eclipse.jface.operation.ModalContext$ModalContextThread.run(ModalContext.java:121)"
				+ "&#xA;</code></pre>&#xA;&#xA;<p>Thanks McDowell, closing and opening the project seems to have fixed it (at least for now).</p>&#xA;";
		String trace = "Caused by: java.lang.IllegalArgumentException: Class file name must end with .class\n" + 
			"at org.eclipse.jdt.internal.core.PackageFragment.getClassFile(PackageFragment.java:182)\n" + 
			"at org.eclipse.jdt.internal.core.util.HandleFactory.createOpenable(HandleFactory.java:109)\n" + 
			"at org.eclipse.jdt.internal.core.search.matching.MatchLocator.locateMatches(MatchLocator.java:1177)\n" + 
			"at org.eclipse.jdt.internal.core.search.JavaSearchParticipant.locateMatches(JavaSearchParticipant.java:94)\n" + 
			"at org.eclipse.jdt.internal.core.search.BasicSearchEngine.findMatches(BasicSearchEngine.java:223)\n" + 
			"at org.eclipse.jdt.internal.core.search.BasicSearchEngine.search(BasicSearchEngine.java:506)\n" + 
			"at org.eclipse.jdt.core.search.SearchEngine.search(SearchEngine.java:551)\n" + 
			"at org.eclipse.jdt.internal.corext.refactoring.RefactoringSearchEngine.internalSearch(RefactoringSearchEngine.java:142)\n" + 
			"at org.eclipse.jdt.internal.corext.refactoring.RefactoringSearchEngine.search(RefactoringSearchEngine.java:129)\n" + 
			"at org.eclipse.jdt.internal.corext.refactoring.rename.RenameTypeProcessor.initializeReferences(RenameTypeProcessor.java:594)\n" + 
			"at org.eclipse.jdt.internal.corext.refactoring.rename.RenameTypeProcessor.doCheckFinalConditions(RenameTypeProcessor.java:522)\n" + 
			"at org.eclipse.jdt.internal.corext.refactoring.rename.JavaRenameProcessor.checkFinalConditions(JavaRenameProcessor.java:45)\n" + 
			"at org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring.checkFinalConditions(ProcessorBasedRefactoring.java:225)\n" + 
			"at org.eclipse.ltk.core.refactoring.Refactoring.checkAllConditions(Refactoring.java:160)\n" + 
			"at org.eclipse.jdt.internal.ui.refactoring.RefactoringExecutionHelper$Operation.run(RefactoringExecutionHelper.java:77)\n" + 
			"at org.eclipse.jdt.internal.core.BatchOperation.executeOperation(BatchOperation.java:39)\n" + 
			"at org.eclipse.jdt.internal.core.JavaModelOperation.run(JavaModelOperation.java:709)\n" + 
			"at org.eclipse.core.internal.resources.Workspace.run(Workspace.java:1800)\n" + 
			"at org.eclipse.jdt.core.JavaCore.run(JavaCore.java:4650)\n" + 
			"at org.eclipse.jdt.internal.ui.actions.WorkbenchRunnableAdapter.run(WorkbenchRunnableAdapter.java:92)\n" + 
			"at org.eclipse.jface.operation.ModalContext$ModalContextThread.run(ModalContext.java:121)";
		List<StackTrace> l = new ArrayList<>();
		l.add(new StackTrace(trace));
		assertEquals(l, StackTraceExtractor.extract(post));
	}
}
