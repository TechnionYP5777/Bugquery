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
	    try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:4488/bugquery?user=root&password=root")){
	      ResultSet r = connection.createStatement().executeQuery("SELECT MAX(Id) as maxId FROM so_posts USE INDEX(Id) LIMIT 10000");
//	     connection.createStatement().executeUpdate("DROP TABLE bugquery_index2");
//      connection.createStatement().executeUpdate("CREATE TABLE bugquery_index2(Id int,Ex Text,StackTrace Text,Question Text)");

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
	      r.close();
	      for  (int i = 6948892; i <numOfRows;i+=10000){
	        System.out.println(i+1);
	        ResultSet rs;
	      for (rs = connection.createStatement()
	          .executeQuery("SELECT * FROM so_posts WHERE Id < " + (i + 10000)+" AND Id > "+i); rs.next();) {
	        String question = rs.getString("Body");
	        String tags = rs.getString("Tags");
	        int id = rs.getInt("Id");
	        if (tags == null || !tags.toLowerCase().contains("java"))
	          continue;
	        insertQusetionToDB(connection, printer, question, id);
	      }
	      rs.close();
	      }
	      printer.close();
	      connection.close();
	    }

	    
	    
	}

	private static void insertQusetionToDB(Connection c, PrintWriter printer, String question,
			int id) throws SQLException {
		List<StackTrace> extract = new ArrayList<>();
		try {
			extract.addAll(StackTraceExtractor.extract(question));
		} catch (Exception e) {
			if (printer == null)
        System.out.println("Failed with:\n" + e.getStackTrace());
      else{
				printer.println("The id is: "+id+"\n\n\n\n");
				printer.println(question+"\n\n\n\n");
			}
			
//			System.out.println(question+"\n\n\n\n");
//			System.out.println("The id is: "+id+"\n\n\n\n");
		}
		for (StackTrace ¢1 : extract) {
			String string = "INSERT INTO bugquery_index2(Id,Ex,StackTrace,Question) VALUES(?,?,?,?)";
			System.out.println("The id is: "+id);
			PreparedStatement ps2 = c.prepareStatement(string);
			String ex = ¢1.getException();
			ps2.setInt(1, id);
			ps2.setString(2, ex);
			ps2.setString(3, ¢1.getString());
			ps2.setString(4, question);
			ps2.executeUpdate();
			ps2.close();
		}
	}
	/**
	 * 
	 * @param question The question to be inserted to the db.
	 * @param logFile If you want to print  to standard input put empty string 
	 */
	public static void insertNewExceptionToDB(String question, String logFile) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(logFile));
      insertQusetionToDB(
					DriverManager.getConnection("jdbc:mysql://localhost:4488/bugquery?user=root&password=root"),
					logFile == "" ? null : printWriter, question, 0);
      printWriter.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		
	}
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		DBGetter.getConnection();
	}
}