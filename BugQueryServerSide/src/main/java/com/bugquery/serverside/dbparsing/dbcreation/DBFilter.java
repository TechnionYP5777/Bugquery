/**
 * 
 */
package com.bugquery.serverside.dbparsing.dbcreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bugquery.serverside.dbparsing.dbretrieval.DBConnector;
import com.bugquery.serverside.entities.StackOverflowPost;
import com.bugquery.serverside.repositories.StackOverflowPostRepository;

import java.sql.Statement;

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
	
	@Autowired
	StackOverflowPostRepository soRepo;
	
	public DBFilter(String srcAddress,String srcUsername,String srcPassword, boolean isCreation) {
		this.srcAddress = "jdbc:mysql://"+srcAddress;
		this.isCreation = isCreation;
		this.srcPassword = srcPassword;
		this.srcUsername = srcUsername;
	}

	public void createTheQuestionsDatabase() {
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER_STRING);
			conn = DriverManager.getConnection(srcAddress,srcUsername,srcPassword);
			stmt = conn.createStatement();
			String sql = "SELECT * FROM so_posts10 LIMIT 15000";
			ResultSet rs = stmt.executeQuery(sql);
			long localId;
			while (rs.next()){
				String tags = rs.getString("Tags");
				if (isJavaPost(tags))
                    continue;
				StackOverflowPost post = addPostToQuestionsDB(rs);
			}
			
		}catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
		}catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
	   }finally{
			      //finally block used to close resources
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

	private StackOverflowPost addPostToQuestionsDB(ResultSet ¢) throws SQLException {
		StackOverflowPost $ = new StackOverflowPost();
		$.setStackOverflowId(¢.getInt("Id"));
		$.setPostTypeId(¢.getInt("PostTypeId"));
		$.setParentId(¢.getInt("ParentId"));
		$.setAcceptedAnswerId(¢.getInt("AcceptedAnswerId"));
		$.setScore(¢.getInt("Score"));
		$.setBody(¢.getString("Body"));
		$.setTitle(¢.getString("Title"));
		$.setTags(¢.getString("Tags"));
		$.setAnswerCount(¢.getInt("AnswerCount"));
		soRepo.save($);
		return $;
	}

	private boolean isJavaPost(String tags) {
		return tags == null || !tags.toLowerCase().contains("java");
	}

	
	
	
	
	
	
}
