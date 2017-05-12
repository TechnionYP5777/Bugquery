package com.bugquery.serverside.dbparsing.dbretrieval;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bugquery.serverside.entities.MinSOPost;
import com.bugquery.serverside.entities.Post;
import com.bugquery.serverside.entities.StackOverflowPost;

/**
 * @author yonzarecki
 * @since 4.1.17
 */
public class SQLDBConnector implements DBConnector {

	@Override
	public List<Post> getAllQuestionsWithTheException(String s)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Post> $ = new ArrayList<>();

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/root?user=root&password=root")){
	  		for (ResultSet ¢ = connection.createStatement().executeQuery("SELECT * FROM bugquery_index3 WHERE Ex='"+s +"'"); 
	  				¢.next();
	  				)
	  			$.add(new StackOverflowPost(¢.getInt("SOId"), ¢.getInt("PostTypeId"), 
	  					¢.getInt("ParentId"), ¢.getInt("AcceptedAnswerId"), ¢.getInt("Score"),
	  					¢.getString("Body"), ¢.getString("Title"), ¢.getString("Tags"), ¢.getInt("AnswerCount")));
	  			
		}
		return $;
	}
	
	
	public static void main (String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		for (Post ¢ : new SQLDBConnector().getAllQuestionsWithTheException("java.lang.NullPointerException"))
			System.out.println(¢.stackTrace.getString());
		
	}

}
