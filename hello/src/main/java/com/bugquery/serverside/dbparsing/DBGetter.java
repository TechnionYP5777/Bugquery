package com.bugquery.serverside.dbparsing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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


	    String path = "log.txt";
	    FileWriter write = null;
		try {
			write = new FileWriter( path , true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PrintWriter printer = new PrintWriter( write );
	    Integer numOfRows = !r.next() ? 0 : r.getInt("maxId");
	    for  (int i = 0; i <numOfRows;i+=10000){
	    	System.out.println(i);
	    	ResultSet rs;
			for (rs = connection.createStatement()
					.executeQuery("SELECT * FROM so_posts WHERE Id < " + (i + 10000)+" AND Id > "+i); rs.next();) {
				String question = rs.getString("Body");
				String tags = rs.getString("Tags");
				int id = rs.getInt("Id");
				if (tags == null || !tags.toLowerCase().contains("java"))
					continue;
				List<StackTrace> extract = new ArrayList<StackTrace>();
				try {
					extract.addAll(StackTraceExtractor.extract(question));
				} catch (Exception e) {
					printer.println("The id is: "+id+"\n\n\n\n");
					printer.println(question+"\n\n\n\n");
					System.out.println(question+"\n\n\n\n");
					System.out.println("The id is: "+id+"\n\n\n\n");
				}
				for (StackTrace ¢1 : extract) {
					String string = "INSERT INTO bugquery_index(Ex,StackTrace,Question) VALUES(?,?,?)";
					System.out.println("The id is: "+id);
					PreparedStatement ps2 = connection.prepareStatement(string);
					String ex = ¢1.getException();
					ps2.setString(1, ex);
					ps2.setString(2, ¢1.getStackTrace());
					ps2.setString(3, question);
					ps2.executeUpdate();
					ps2.close();
				}
				
				
			}
			rs.close();
	    }	
	    
	}
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		DBGetter.getConnection();
	}
}
