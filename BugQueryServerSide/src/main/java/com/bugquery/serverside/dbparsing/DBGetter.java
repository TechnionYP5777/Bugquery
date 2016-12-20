package com.bugquery.serverside.dbparsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.stacktrace.StackTraceExtractor;

public class DBGetter {
	public void getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugquery?user=root&password=root");
	    connection.createStatement().executeUpdate("DROP TABLE bugquery_index");
	    connection.createStatement().executeUpdate("CREATE TABLE bugquery_index(Ex Text,StackTrace Text,Question Text)");
		for (ResultSet ¢ = connection
				.createStatement().executeQuery("SELECT * FROM so_posts LIMIT 100000"); ¢.next();) {
			String question = ¢.getString("Body");
			String tags = ¢.getString("Tags");
			if (tags == null || !tags.toLowerCase().contains("java"))
				continue;
			for (StackTrace ¢1 : StackTraceExtractor.extract(question)) {
				String string = "INSERT INTO bugquery_index(Ex,StackTrace,Question) VALUES(?,?,?)";
				System.out.println(string);
				PreparedStatement ps = connection.prepareStatement(string);
				String ex = (!"".equals(¢1.getException()) ? ¢1.getException() : "No Exception Found!");
				ps.setString(1, ex);
				ps.setString(2, ¢1.getStackTrace());
				ps.setString(3, question);
				ps.executeUpdate();
			}
		}
			
	    
	}
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		new DBGetter().getConnection();
	}
}
