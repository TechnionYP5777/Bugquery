package com.bugquery.serverside.dbparsing.dbretrieval;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bugquery.serverside.entities.MinSOPost;
import com.bugquery.serverside.entities.Post;

public class DBSearch {
	private static final String JDBC_CONNECTION = "jdbc:mysql://localhost:4488/bugquery?user=root&password=root";

	static List<Post> getAllQuestionsWithTheException(String s) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Post> $ = new ArrayList<>();

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection connection = DriverManager.getConnection(JDBC_CONNECTION)){
  		for (ResultSet ¢ = connection
  				.createStatement().executeQuery(dangerOfSQLInjection(s)); ¢.next();)
  			$.add(new MinSOPost(¢.getString("StackTrace"),¢.getString("Question")));
	  }
		return $;
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		DBSearch.getAllQuestionsWithTheException("java.lang.NullPointerException");
		
	}
	
	private static String dangerOfSQLInjection(String s) {
		return "SELECT * FROM bugquery_index2 WHERE Ex='"+s +"'";
	}
}
