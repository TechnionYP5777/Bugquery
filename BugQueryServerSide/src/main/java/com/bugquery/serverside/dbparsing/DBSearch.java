package com.bugquery.serverside.dbparsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bugquery.serverside.entities.MinSOPost;
import com.bugquery.serverside.entities.Post;

public class DBSearch {
	public static List<Post> getAllQuestionsWithTheException(String s) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Post> $ = new ArrayList<>();

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:4488/bugquery?user=root&password=root")){
  		for (ResultSet ¢ = connection
  				.createStatement().executeQuery("SELECT * FROM bugquery_index2 WHERE Ex='"+s +"'"); ¢.next();)
  			$.add(new MinSOPost(¢.getString("StackTrace"),¢.getString("Question")));
	  }
		return $;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		DBSearch.getAllQuestionsWithTheException("java.lang.NullPointerException");
		
	}
}
