package com.bugquery.serverside.dbparsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bugquery.serverside.entities.StackTrace;
import com.bugquery.serverside.stacktrace.StackTraceExtractor;

public class DBGetter {
	public static void getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:4488/bugquery?user=root&password=root");
	    connection.createStatement().executeUpdate("DROP TABLE bugquery_index");
	    connection.createStatement().executeUpdate("CREATE TABLE bugquery_index(Ex Text,StackTrace Text,Question Text)");
	    ResultSet r = connection.createStatement().executeQuery("SELECT MAX(Id) as maxId FROM so_posts USE INDEX(Id) LIMIT 10000");
	    
	    Integer numOfRows = !r.next() ? 0 : r.getInt("maxId");
	    PreparedStatement ps = connection.prepareStatement("SELECT * FROM so_posts USE INDEX(Id) WHERE Id=? LIMIT 10000");
	    for  (int i = 0; i <numOfRows;i+=10000)
			for (ResultSet r3 = connection.createStatement()
					.executeQuery("SELECT Id FROM so_posts USE INDEX(Id) LIMIT " + i + "," + (i + 10000)); r3.next();) {
				ps.setInt(1, r3.getInt("Id"));
				System.out.println(r3.getInt("Id"));
				for (ResultSet rs = ps.executeQuery(); rs.next();) {
					String question = rs.getString("Body");
					String tags = rs.getString("Tags");
					if (tags == null || !tags.toLowerCase().contains("java"))
						continue;
					List<StackTrace> extract = new ArrayList<StackTrace>();
					try {
						extract.addAll(StackTraceExtractor.extract(question));
					} catch (Exception e) {
						System.out.println(question);
					}
					for (StackTrace ¢1 : extract) {
						String string = "INSERT INTO bugquery_index(Ex,StackTrace,Question) VALUES(?,?,?)";
						System.out.println(string);
						PreparedStatement ps2 = connection.prepareStatement(string);
						String ex = ¢1.getException();
						ps2.setString(1, ex);
						ps2.setString(2, ¢1.getStackTrace());
						ps2.setString(3, question);
						ps2.executeUpdate();
					}
				}
			}	
	    
	}
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		DBGetter.getConnection();
	}
}
