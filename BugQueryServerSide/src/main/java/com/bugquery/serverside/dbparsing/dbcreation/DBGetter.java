package com.bugquery.serverside.dbparsing.dbcreation;

import java.io.FileWriter;
import java.io.IOException;
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
	static int uniqueId;
	static String address = "localhost:4488";
	public static void importFromSOToBugQuery(String logFile) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    try(Connection connection = DriverManager.getConnection("jdbc:mysql://"+address+"/bugquery?user=root&password=root")){
	      int numOfRows = 0;
	      connection.createStatement().executeUpdate("CREATE INDEX so_index ON so_posts10(Id)");
	      try(ResultSet r = connection.createStatement().executeQuery("SELECT MAX(Id) as maxId FROM so_posts10 USE INDEX(Id)")){
	        if (r.next())
	          numOfRows = r.getInt("maxId");
	      }
	     connection.createStatement().executeUpdate("DROP TABLE bugquery_index3");
      connection.createStatement().executeUpdate("CREATE TABLE bugquery_index3(BugQueryId int,SOId int,Ex Text,StackTrace Text,Question Text,PostTypeId int, ParentID int,AcceptedAnswerId int, Score int,Title Text, Tags varchar(500), AnswerCount int)");	      
	      FileWriter write = null;
  	    try {
  	      write = new FileWriter( logFile , true);
  	    } catch (IOException e1) {
  	      e1.printStackTrace();
  	    }
  	    try(PrintWriter printer = new PrintWriter( write )){
          for  (int i = 0; i <numOfRows;i+=10000){
            System.out.println(i+1);
            try(ResultSet rs = connection.createStatement()
                .executeQuery("SELECT * FROM so_posts10 USE INDEX(Id) WHERE Id < " + (i + 10000)+" AND Id > "+i)){
              for (ResultSet i_rs = rs; i_rs.next();) {
            	  String tags = rs.getString("Tags");
            	  if (tags == null || !tags.toLowerCase().contains("java"))
                      continue;        	  
            	  insertQusetionToDB(connection, printer, rs);
              }
            }
            
          }
  	    }
	    }    
	}

	private static void insertQusetionToDB(Connection c, PrintWriter printer, ResultSet s) throws SQLException {
		List<StackTrace> extract = new ArrayList<>();
		String body = s.getString("Body");
  	  	int id = s.getInt("Id");
  	  	int postTypeId = s.getInt("PostTypeId");
  	  	int parentId = s.getInt("ParentId");
  	  	int acceptedAnswerId = s.getInt("AcceptedAnswerId");
  	  	int score = s.getInt("Score");
  	  	String title = s.getString("Title");
  	  	int answerCount = s.getInt("AnswerCount");
  	  	String tags = s.getString("Tags");
  	  	
		try {
			extract.addAll(StackTraceExtractor.extract(body));
		} catch (Exception ¢) {
			if (printer == null)
				System.out.println("Failed with:\n" + ¢.getStackTrace());
			else{
				printer.println("The id is: "+id+"\n\n\n\n");
				printer.println(body+"\n\n\n\n");
			}
		}
		
		for (StackTrace ¢1 : extract) {
			String string = "INSERT INTO bugquery_index3(BugQueryId,SOId,Ex,StackTrace,Question,PostTypeId,ParentID,AcceptedAnswerId, Score,Title, Tags, AnswerCount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			System.out.println("The id is: "+id);

			try(PreparedStatement ps2 = c.prepareStatement(string)){
	  			String ex = ¢1.getException();
	  			ps2.setInt(1, uniqueId);
	  			uniqueId += 1;
	  			ps2.setInt(2, id);
	  			ps2.setString(3, ex);
	  			ps2.setString(4, ¢1.getString());
	  			ps2.setString(5, body);
	  			ps2.setInt(6, postTypeId);
	  			ps2.setInt(7, parentId);
	  			ps2.setInt(8,acceptedAnswerId);
	  			ps2.setInt(9, score);
	  			ps2.setString(10, title);
	  			ps2.setString(11, tags);
	  			ps2.setInt(12, answerCount);
	  			ps2.executeUpdate();
			}	
		}
	}
	
	
	/**
	 * 
	 * @param question The question to be inserted to the db.
	 * @param logFile If you want to print  to standard input put empty string 
	 */
//	public static void insertNewExceptionToDB(StackOverflowPost p, String logFile) {
//		try {
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
//		try {
//			try(PrintWriter printWriter = new PrintWriter(new FileWriter(logFile))){
//      insertQusetionToDB(
//					DriverManager.getConnection("jdbc:mysql://localhost:4488/bugquery?user=root&password=root"),
//					logFile == "" ? null : printWriter, p);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	    
//		
//	}
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		DBGetter.importFromSOToBugQuery("log.txt");
		DBGetter.importAnswersFromSO();
	}

	private static void importAnswersFromSO() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
			try(Connection connection = DriverManager.getConnection("jdbc:mysql://gpu7.cs.technion.ac.il:3306/bugquery?user=root&password=root")){
				connection.createStatement().executeUpdate("CREATE TABLE bugquery_answers(BugQueryId int,SOId int,Answer Text,PostTypeId int, ParentID int,AcceptedAnswerId int, Score int,Title Text, Tags varchar(500), AnswerCount int)");
				try(ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM bugquery_index3")){
					for (ResultSet i_rs = rs; i_rs.next();) {
						@SuppressWarnings("unused")
						int soId = rs.getInt("SOId");
						connection.createStatement().executeUpdate("INSERT INTO bugquery_answers(SOId,Answer,PostTypeId,ParentID,AcceptedAnswerId,Score,Title,Tags,AnswerCount) SELECT (so_posts10.Id,)");
						
					}
			}
		}
		
	}
}