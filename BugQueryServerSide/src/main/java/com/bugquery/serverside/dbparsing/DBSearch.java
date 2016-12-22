package com.bugquery.serverside.dbparsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBSearch {
	public static ArrayList getAllQuestionsWithTheException(String s) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		ArrayList<String> $ = new ArrayList<>();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:4488/bugquery?user=root&password=root");
		for (ResultSet ¢ = connection
				.createStatement().executeQuery("SELECT Question FROM bugquery_index WHERE Ex='"+s +"'LIMIT 300000"); ¢.next();)
			$.add(¢.getString("Question"));
		return $;
	}
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		DBSearch.getAllQuestionsWithTheException("java.lang.NullPointerException");
		
	}
}
