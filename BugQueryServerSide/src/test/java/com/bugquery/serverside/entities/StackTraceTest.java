package com.bugquery.serverside.entities;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author rodedzats
 * @since 14.12.2016
 */
@SuppressWarnings("static-method")
public class StackTraceTest {

	@Test
	public void correctExceptionForSimpleStackTrace() {
		assertEquals("java.lang.NullPointerException",
				(new StackTrace(("Exception in thread \"main\" java.lang.NullPointerException\n"
						+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
						+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
						+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)"))).getException());
	}
	
	@Test
	public void correctExceptionForMediumComplexityStackTrace() {
		assertEquals("java.lang.NullPointerException",
				(new StackTrace(
						("Exception in thread \"main\" java.lang.IllegalStateException: A book has a null property\n"
								+ "        at com.example.myproject.Author.getBookIds(Author.java:38)\n"
								+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n"
								+ "Caused by: java.lang.NullPointerException\n"
								+ "        at com.example.myproject.Book.getId(Book.java:22)\n"
								+ "        at com.example.myproject.Author.getBookIds(Author.java:35)\n"
								+ "        ... 1 more"))).getException());
	}
	
	@Test
	public void correctExceptionForComplexStackTrace() {
		assertEquals("java.sql.SQLException",
				(new StackTrace(("Exception: Something bad happened\n"
						+ "    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:60)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n"
						+ "    at com.example.myproject.ExceptionHandlerFilter.doFilter(ExceptionHandlerFilter.java:28)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n"
						+ "    at com.example.myproject.OutputBufferFilter.doFilter(OutputBufferFilter.java:33)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)\n"
						+ "    at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)\n"
						+ "    at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)\n"
						+ "    at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)\n"
						+ "    at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)\n"
						+ "    at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)\n"
						+ "    at org.mortbay.jetty.Server.handle(Server.java:326)\n"
						+ "    at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)\n"
						+ "    at org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:943)\n"
						+ "    at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:756)\n"
						+ "    at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218)\n"
						+ "    at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)\n"
						+ "    at org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228)\n"
						+ "    at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582)\n"
						+ "Caused by: com.example.myproject.MyProjectServletException\n"
						+ "    at com.example.myproject.MyServlet.doPost(MyServlet.java:169)\n"
						+ "    at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)\n"
						+ "    at javax.servlet.http.HttpServlet.service(HttpServlet.java:820)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)\n"
						+ "    at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)\n"
						+ "    at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:30)\n"
						+ "    ... 27 more\n"
						+ "Caused by: org.hibernate.exception.ConstraintViolationException: could not insert: [com.example.myproject.MyEntity]\n"
						+ "    at org.hibernate.exception.SQLStateConverter.convert(SQLStateConverter.java:96)\n"
						+ "    at org.hibernate.exception.JDBCExceptionHelper.convert(JDBCExceptionHelper.java:66)\n"
						+ "    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:64)\n"
						+ "    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2329)\n"
						+ "    at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:2822)\n"
						+ "    at org.hibernate.action.EntityIdentityInsertAction.execute(EntityIdentityInsertAction.java:71)\n"
						+ "    at org.hibernate.engine.ActionQueue.execute(ActionQueue.java:268)\n"
						+ "    at org.hibernate.event.def.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:321)\n"
						+ "    at org.hibernate.event.def.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:204)\n"
						+ "    at org.hibernate.event.def.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:130)\n"
						+ "    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.saveWithGeneratedOrRequestedId(DefaultSaveOrUpdateEventListener.java:210)\n"
						+ "    at org.hibernate.event.def.DefaultSaveEventListener.saveWithGeneratedOrRequestedId(DefaultSaveEventListener.java:56)\n"
						+ "    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.entityIsTransient(DefaultSaveOrUpdateEventListener.java:195)\n"
						+ "    at org.hibernate.event.def.DefaultSaveEventListener.performSaveOrUpdate(DefaultSaveEventListener.java:50)\n"
						+ "    at org.hibernate.event.def.DefaultSaveOrUpdateEventListener.onSaveOrUpdate(DefaultSaveOrUpdateEventListener.java:93)\n"
						+ "    at org.hibernate.impl.SessionImpl.fireSave(SessionImpl.java:705)\n"
						+ "    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:693)\n"
						+ "    at org.hibernate.impl.SessionImpl.save(SessionImpl.java:689)\n"
						+ "    at sun.reflect.GeneratedMethodAccessor5.invoke(Unknown Source)\n"
						+ "    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n"
						+ "    at java.lang.reflect.Method.invoke(Method.java:597)\n"
						+ "    at org.hibernate.context.ThreadLocalSessionContext$TransactionProtectionWrapper.invoke(ThreadLocalSessionContext.java:344)\n"
						+ "    at $Proxy19.save(Unknown Source)\n"
						+ "    at com.example.myproject.MyEntityService.save(MyEntityService.java:59) <-- relevant call (see notes below)\n"
						+ "    at com.example.myproject.MyServlet.doPost(MyServlet.java:164)\n" + "    ... 32 more\n"
						+ "Caused by: java.sql.SQLException: Violation of unique constraint MY_ENTITY_UK_1: duplicate value(s) for column(s) MY_COLUMN in statement [...]\n"
						+ "    at org.hsqldb.jdbc.Util.throwError(Unknown Source)\n"
						+ "    at org.hsqldb.jdbc.jdbcPreparedStatement.executeUpdate(Unknown Source)\n"
						+ "    at com.mchange.v2.c3p0.impl.NewProxyPreparedStatement.executeUpdate(NewProxyPreparedStatement.java:105)\n"
						+ "    at org.hibernate.id.insert.AbstractSelectingDelegate.performInsert(AbstractSelectingDelegate.java:57)\n"
						+ "    ... 54 more"))).getException());
	}
	
	@Test
	public void correctExceptionForIssue37_2() {
		assertEquals("java.lang.UnsatisfiedLinkError",
				new StackTrace(
						("Exception in thread \"main\" java.lang.UnsatisfiedLinkError: C:\\Program Files\\Java\\jre1.6.0_05\\bin\\awt.dll: The specified procedure could not be found\n"
								+ "at java.lang.ClassLoader$NativeLibrary.load(Native Method)\n"
								+ "    at java.lang.ClassLoader.loadLibrary0(Unknown Source)\n"
								+ "    at java.lang.ClassLoader.loadLibrary(Unknown Source)\n"
								+ "    at java.lang.Runtime.loadLibrary0(Unknown Source)\n"
								+ "    at java.lang.System.loadLibrary(Unknown Source)\n"
								+ "    at sun.security.action.LoadLibraryAction.run(Unknown Source)\n"
								+ "    at java.security.AccessController.doPrivileged(Native Method)\n"
								+ "    at sun.awt.NativeLibLoader.loadLibraries(Unknown Source)\n"
								+ "    at sun.awt.DebugHelper.<clinit>(Unknown Source)\n"
								+ "    at java.awt.EventQueue.<clinit>(Unknown Source)\n"
								+ "    at javax.swing.SwingUtilities.invokeLater(Unknown Source)\n"
								+ "    at ui.sequencer.test.WindowTest.main(WindowTest.java:136)")).getException());
	}
	
	@Test
	public void correctExceptionForIssue37_1() {
		assertEquals("java.lang.IllegalArgumentException",
				new StackTrace(("Caused by: java.lang.IllegalArgumentException: Class file name must end with .class\n"
						+ "at org.eclipse.jdt.internal.core.PackageFragment.getClassFile(PackageFragment.java:182)\n"
						+ "at org.eclipse.jdt.internal.core.util.HandleFactory.createOpenable(HandleFactory.java:109)\n"
						+ "at org.eclipse.jdt.internal.core.search.matching.MatchLocator.locateMatches(MatchLocator.java:1177)\n"
						+ "at org.eclipse.jdt.internal.core.search.JavaSearchParticipant.locateMatches(JavaSearchParticipant.java:94)\n"
						+ "at org.eclipse.jdt.internal.core.search.BasicSearchEngine.findMatches(BasicSearchEngine.java:223)\n"
						+ "at org.eclipse.jdt.internal.core.search.BasicSearchEngine.search(BasicSearchEngine.java:506)\n"
						+ "at org.eclipse.jdt.core.search.SearchEngine.search(SearchEngine.java:551)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.RefactoringSearchEngine.internalSearch(RefactoringSearchEngine.java:142)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.RefactoringSearchEngine.search(RefactoringSearchEngine.java:129)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.rename.RenameTypeProcessor.initializeReferences(RenameTypeProcessor.java:594)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.rename.RenameTypeProcessor.doCheckFinalConditions(RenameTypeProcessor.java:522)\n"
						+ "at org.eclipse.jdt.internal.corext.refactoring.rename.JavaRenameProcessor.checkFinalConditions(JavaRenameProcessor.java:45)\n"
						+ "at org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring.checkFinalConditions(ProcessorBasedRefactoring.java:225)\n"
						+ "at org.eclipse.ltk.core.refactoring.Refactoring.checkAllConditions(Refactoring.java:160)\n"
						+ "at org.eclipse.jdt.internal.ui.refactoring.RefactoringExecutionHelper$Operation.run(RefactoringExecutionHelper.java:77)\n"
						+ "at org.eclipse.jdt.internal.core.BatchOperation.executeOperation(BatchOperation.java:39)\n"
						+ "at org.eclipse.jdt.internal.core.JavaModelOperation.run(JavaModelOperation.java:709)\n"
						+ "at org.eclipse.core.internal.resources.Workspace.run(Workspace.java:1800)\n"
						+ "at org.eclipse.jdt.core.JavaCore.run(JavaCore.java:4650)\n"
						+ "at org.eclipse.jdt.internal.ui.actions.WorkbenchRunnableAdapter.run(WorkbenchRunnableAdapter.java:92)\n"
						+ "at org.eclipse.jface.operation.ModalContext$ModalContextThread.run(ModalContext.java:121)"))
								.getException());
	}
	
	@Test
	public void correctExceptionForIssue37_3() {
		assertEquals("java.sql.BatchUpdateException",
				new StackTrace("java.sql.BatchUpdateException: Incorrect string value: '\\xF1a' for column 'body' at row 1\n" + 
						"    at com.mysql.jdbc.ServerPreparedStatement.executeBatch(ServerPreparedStatement.java:657)\n" + 
						"    at com.mchange.v2.c3p0.impl.NewProxyPreparedStatement.executeBatch(NewProxyPreparedStatement.java:1723)\n" + 
						"    at org.hibernate.jdbc.BatchingBatcher.doExecuteBatch(BatchingBatcher.java:48)\n" + 
						"    at org.hibernate.jdbc.AbstractBatcher.executeBatch(AbstractBatcher.java:242)\n" + 
						"    at org.hibernate.engine.ActionQueue.executeActions(ActionQueue.java:235)\n" + 
						"    at org.hibernate.engine.ActionQueue.executeActions(ActionQueue.java:139)\n" + 
						"    at org.hibernate.event.def.AbstractFlushingEventListener.performExecutions(AbstractFlushingEventListener.java:298)\n" + 
						"    at org.hibernate.event.def.DefaultFlushEventListener.onFlush(DefaultFlushEventListener.java:27)\n" + 
						"    at org.hibernate.impl.SessionImpl.flush(SessionImpl.java:1000)\n" + 
						"    at org.hibernate.impl.SessionImpl.managedFlush(SessionImpl.java:338)\n" + 
						"    at org.hibernate.transaction.JDBCTransaction.commit(JDBCTransaction.java:106)\n" + 
						"    at com.cerebra.cerepedia.item.dao.ItemDAOHibernate.addComment(ItemDAOHibernate.java:505)\n" + 
						"    at com.cerebra.cerepedia.item.ItemManagerPOJOImpl.addComment(ItemManagerPOJOImpl.java:164)\n" + 
						"    at com.cerebra.cerepedia.struts.item.ItemAction.addComment(ItemAction.java:126)\n" + 
						"    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" + 
						"    at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\n" + 
						"    at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\n" + 
						"    at java.lang.reflect.Method.invoke(Unknown Source)\n" + 
						"    at org.apache.struts.actions.DispatchAction.dispatchMethod(DispatchAction.java:269)\n" + 
						"    at org.apache.struts.actions.DispatchAction.execute(DispatchAction.java:170)\n" + 
						"    at org.apache.struts.actions.MappingDispatchAction.execute(MappingDispatchAction.java:166)\n" + 
						"    at org.apache.struts.action.RequestProcessor.processActionPerform(RequestProcessor.java:425)\n" + 
						"    at org.apache.struts.action.RequestProcessor.process(RequestProcessor.java:228)\n" + 
						"    at org.apache.struts.action.ActionServlet.process(ActionServlet.java:1913)\n" + 
						"    at org.apache.struts.action.ActionServlet.doPost(ActionServlet.java:462)\n" + 
						"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:710)\n" + 
						"    at javax.servlet.http.HttpServlet.service(HttpServlet.java:803)\n" + 
						"    at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:290)\n" + 
						"    at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" + 
						"    at com.cerebra.cerepedia.security.AuthorizationFilter.doFilter(AuthorizationFilter.java:78)\n" + 
						"    at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:235)\n" + 
						"    at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" + 
						"    at com.cerebra.cerepedia.hibernate.HibernateSessionRequestFilter.doFilter(HibernateSessionRequestFilter.java:30)\n" + 
						"    at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:235)\n" + 
						"    at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" + 
						"    at filters.UTF8Filter.doFilter(UTF8Filter.java:14)\n" + 
						"    at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:235)\n" + 
						"    at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" + 
						"    at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:230)\n" + 
						"    at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:175)\n" + 
						"    at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:128)\n" + 
						"    at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:104)\n" + 
						"    at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:109)\n" + 
						"    at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:261)\n" + 
						"    at org.apache.coyote.http11.Http11Processor.process(Http11Processor.java:844)\n" + 
						"    at org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler.process(Http11Protocol.java:581)\n" + 
						"    at org.apache.tomcat.util.net.JIoEndpoint$Worker.run(JIoEndpoint.java:447)\n" + 
						"    at java.lang.Thread.run(Unknown Source)")
								.getException());
	}
	
	@Test
	public void checkReturnsNoExceptionFoundForBadlyFormatted_Exception_in() {
		assertEquals(StackTrace.noExceptionFound,
				new StackTrace("Exception in sdfsdf")
								.getException());
	}
	
	@Test
	public void correctExceptionForIssue37_4() {
		assertEquals("java.lang.UnsupportedOperationException",
				new StackTrace("[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R <openjpa-1.2.1-SNAPSHOT-r422266:686069 fatal general error> org.apache.openjpa.persistence.PersistenceException: null\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.BrokerImpl.flush(BrokerImpl.java:1688)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.StateManagerImpl.assignObjectId(StateManagerImpl.java:523)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.StateManagerImpl.assignField(StateManagerImpl.java:608)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.StateManagerImpl.beforeAccessField(StateManagerImpl.java:1494)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.StateManagerImpl.accessingField(StateManagerImpl.java:1477)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at entities.Job.pcGetid(Job.java)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at entities.Job.hashCode(Job.java:402)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at java.util.HashMap.putImpl(Unknown Source)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at java.util.HashMap.put(Unknown Source)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at java.util.HashSet.add(Unknown Source)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.util.java$util$HashSet$proxy.add(Unknown Source)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at entities.controller.JobManager.copyJob(JobManager.java:140)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at entities.controller.JobManagerTest.testCopyJob(JobManagerTest.java:55)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:45)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:37)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at java.lang.reflect.Method.invoke(Method.java:599)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at junit.framework.TestCase.runTest(TestCase.java:154)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at junit.framework.TestCase.runBare(TestCase.java:127)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at junit.framework.TestResult$1.protect(TestResult.java:106)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at junit.framework.TestResult.runProtected(TestResult.java:124)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at junit.framework.TestResult.run(TestResult.java:109)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at junit.framework.TestCase.run(TestCase.java:118)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at junit.framework.TestSuite.runTest(TestSuite.java:208)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at junit.framework.TestSuite.run(TestSuite.java:203)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.cactus.server.runner.ServletTestRunner.run(ServletTestRunner.java:309)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.cactus.server.runner.ServletTestRunner.doGet_aroundBody0(ServletTestRunner.java:187)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.cactus.server.runner.ServletTestRunner.doGet_aroundBody1$advice(ServletTestRunner.java:225)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.cactus.server.runner.ServletTestRunner.doGet(ServletTestRunner.java:1)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at javax.servlet.http.HttpServlet.service(HttpServlet.java:718)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at javax.servlet.http.HttpServlet.service(HttpServlet.java:831)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.servlet.ServletWrapper.service(ServletWrapper.java:1449)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.servlet.ServletWrapper.handleRequest(ServletWrapper.java:790)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.servlet.ServletWrapper.handleRequest(ServletWrapper.java:443)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.servlet.ServletWrapperImpl.handleRequest(ServletWrapperImpl.java:175)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.webapp.WebApp.handleRequest(WebApp.java:3610)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.webapp.WebGroup.handleRequest(WebGroup.java:274)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.WebContainer.handleRequest(WebContainer.java:926)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.WSWebContainer.handleRequest(WSWebContainer.java:1557)\n" + 
						"[11/25/z08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.webcontainer.channel.WCChannelLink.ready(WCChannelLink.java:173)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.http.channel.inbound.impl.HttpInboundLink.handleDiscrimination(HttpInboundLink.java:455)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.http.channel.inbound.impl.HttpInboundLink.handleNewInformation(HttpInboundLink.java:384)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.http.channel.inbound.impl.HttpInboundLink.ready(HttpInboundLink.java:272)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.tcp.channel.impl.NewConnectionInitialReadCallback.sendToDiscriminators(NewConnectionInitialReadCallback.java:214)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.tcp.channel.impl.NewConnectionInitialReadCallback.complete(NewConnectionInitialReadCallback.java:113)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.tcp.channel.impl.AioReadCompletionListener.futureCompleted(AioReadCompletionListener.java:165)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.io.async.AbstractAsyncFuture.invokeCallback(AbstractAsyncFuture.java:217)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.io.async.AsyncChannelFuture.fireCompletionActions(AsyncChannelFuture.java:161)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.io.async.AsyncFuture.completed(AsyncFuture.java:138)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.io.async.ResultHandler.complete(ResultHandler.java:202)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.io.async.ResultHandler.runEventProcessingLoop(ResultHandler.java:766)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.io.async.ResultHandler$2.run(ResultHandler.java:896)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.util.ThreadPool$Worker.run(ThreadPool.java:1527)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R Caused by: java.lang.UnsupportedOperationException\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.DetachedStateManager.getMetaData(DetachedStateManager.java:696)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.meta.strats.UntypedPCValueHandler.toRelationDataStoreValue(UntypedPCValueHandler.java:121)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.sql.RowImpl.setRelationId(RowImpl.java:327)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.sql.SecondaryRow.setRelationId(SecondaryRow.java:106)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.meta.strats.HandlerStrategies.set(HandlerStrategies.java:150)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.meta.strats.HandlerStrategies.set(HandlerStrategies.java:104)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.meta.strats.HandlerCollectionTableFieldStrategy.insert(HandlerCollectionTableFieldStrategy.java:154)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.meta.strats.HandlerCollectionTableFieldStrategy.insert(HandlerCollectionTableFieldStrategy.java:130)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.meta.FieldMapping.insert(FieldMapping.java:579)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.kernel.AbstractUpdateManager.insert(AbstractUpdateManager.java:197)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.kernel.AbstractUpdateManager.populateRowManager(AbstractUpdateManager.java:139)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.persistence.jdbc.kernel.ConstraintUpdateManager.flush(ConstraintUpdateManager.java:73)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at com.ibm.ws.persistence.jdbc.kernel.ConstraintUpdateManager.flush(ConstraintUpdateManager.java:60)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.jdbc.kernel.JDBCStoreManager.flush(JDBCStoreManager.java:655)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.DelegatingStoreManager.flush(DelegatingStoreManager.java:130)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.BrokerImpl.flush(BrokerImpl.java:2010)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.BrokerImpl.flushSafe(BrokerImpl.java:1908)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     at org.apache.openjpa.kernel.BrokerImpl.flush(BrokerImpl.java:1679)\n" + 
						"[11/25/08 8:46:56:546 EST] 00000018 SystemErr     R     ... 52 more")
								.getException());
	}
	
	@Test
	public void noExceptionFoundIsSetForIllegalStackTrace() {
		StackTrace st = new StackTrace("::");
		assertEquals(StackTrace.noExceptionFound,st.getException());
		st = new StackTrace("Exception:");
		assertEquals(StackTrace.noExceptionFound,st.getException());
		st = new StackTrace("Exception: Exception:\n");
		assertEquals(StackTrace.noExceptionFound,st.getException());
		st = new StackTrace("Exception:\n :\n");
		assertEquals(StackTrace.noExceptionFound,st.getException());
	}

	@Test
	public void stackOfCallsIsParsedCorrectly() {
		List<String> calls = new ArrayList<>();
		calls.add("Exception in thread \"main\" java.lang.NullPointerException");
		calls.add("at com.example.myproject.Book.getTitle(Book.java:16)");
		calls.add("at com.example.myproject.Author.getBookTitles(Author.java:25)");
		calls.add("at com.example.myproject.Bootstrap.main(Bootstrap.java:14)");
		assertEquals(calls, new StackTrace("Exception in thread \"main\" java.lang.NullPointerException\n"
				+ "        at com.example.myproject.Book.getTitle(Book.java:16)\n"
				+ "        at com.example.myproject.Author.getBookTitles(Author.java:25)\n"
				+ "        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)").getStackOfCalls());
	}
	
	@Test
	public void checkWindowsNewlinesStackTrace() {
		assertEquals("java.lang.NullPointerException",
				new StackTrace("Exception in thread \"main\" java.lang.NullPointerException\r\n"
						+ "at com.example.myproject.Book.getTitle(Book.java:16)\r\n"
						+ "at com.example.myproject.Author.getBookTitles(Author.java:25)\r\n"
						+ "at com.example.myproject.Bootstrap.main(Bootstrap.java:14)").getException());
	}
}
