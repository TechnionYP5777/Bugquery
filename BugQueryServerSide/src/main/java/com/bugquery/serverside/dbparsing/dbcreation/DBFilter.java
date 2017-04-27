/**
 * 
 */
package com.bugquery.serverside.dbparsing.dbcreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bugquery.serverside.Application;
import com.bugquery.serverside.dbparsing.dbretrieval.DBConnector;
import com.bugquery.serverside.entities.StackOverflowPost;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.repositories.StackOverflowPostRepository;
import com.bugquery.serverside.stacktrace.StackTraceExtractor;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Tony
 *
 */
public class DBFilter {
	private static final String JDBC_DRIVER_STRING = "com.mysql.jdbc.Driver";
	String srcAddress;
	String srcUsername;
	String srcPassword;
	boolean isCreation;
	StackOverflowPostRepository soRepo;
	private static final Logger log = LoggerFactory.getLogger(DBFilter.class);
	
	public DBFilter(String srcAddress,String srcUsername,String srcPassword, boolean isCreation,StackOverflowPostRepository soRepo) {
		this.srcAddress = "jdbc:mysql://"+srcAddress;
		this.isCreation = isCreation;
		this.srcPassword = srcPassword;
		this.srcUsername = srcUsername;
		this.soRepo = soRepo;
	}

	public void createTheQuestionsDatabase() {
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER_STRING);
			conn = DriverManager.getConnection(srcAddress,srcUsername,srcPassword);
			stmt = conn.createStatement();

			String sql = "SELECT * FROM so_posts";
			ResultSet rs = stmt.executeQuery(sql);

			List<StackOverflowPost> addedPosts = new ArrayList<>();
			while (rs.next()){
				String tags = rs.getString("Tags");
				if (notJavaPost(tags))
                    continue;
				addedPosts.addAll(addAllStackTracesToQuestionsDB(rs));
			}
			soRepo.save(addedPosts);
			
		}catch(SQLException se){

			      se.printStackTrace();
		}catch(Exception e){
			      e.printStackTrace();
	   }finally{

		   try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }
		   try{
	         if(conn!=null)
	        	 conn.close();
		   }catch(SQLException se){
	         se.printStackTrace();
		   }
	   }
	}

	private List<StackOverflowPost> addAllStackTracesToQuestionsDB(ResultSet ¢) throws SQLException {
		String body = ¢.getString("Body");
		List<StackOverflowPost> addedPosts = new ArrayList<StackOverflowPost>();
		List<StackTrace> stackTraces = StackTraceExtractor.extract(body);
		for (StackTrace stackTrace: stackTraces){
			StackOverflowPost $ = new StackOverflowPost();
			$.setStackOverflowId(¢.getInt("Id"));
			$.setPostTypeId(¢.getInt("PostTypeId"));
			$.setParentId(¢.getInt("ParentId"));
			$.setAcceptedAnswerId(¢.getInt("AcceptedAnswerId"));
			$.setScore(¢.getInt("Score"));
			$.setBody(body);
			$.setTitle(¢.getString("Title"));
			$.setTags(¢.getString("Tags"));
			$.setAnswerCount(¢.getInt("AnswerCount"));
			$.setStackTrace(stackTrace);
			addedPosts.add($);
		}
		
		return addedPosts;
	}

	private boolean notJavaPost(String tags) {
		return tags == null || !tags.toLowerCase().contains("java");
	}

	
	
	
	
	
	
}
