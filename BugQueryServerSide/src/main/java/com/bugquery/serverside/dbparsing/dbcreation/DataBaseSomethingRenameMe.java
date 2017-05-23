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

public class DataBaseSomethingRenameMe {
	private static String address = "localhost:4488";
	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String INSERT_QUERY = "INSERT INTO bugquery_index3(BugQueryId,SOId,Ex,StackTrace,Question,PostTypeId,ParentID,AcceptedAnswerId, Score,Title, Tags, AnswerCount) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static int uniqueId;

	private static void importFromStackOverflow(String logFile) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName(COM_MYSQL_JDBC_DRIVER).newInstance();
	    try(Connection connection = DriverManager.getConnection("jdbc:mysql://"+address+"/bugquery?user=root&password=root")){
	      int numOfRows = 0;
	      connection.createStatement().executeUpdate("CREATE INDEX so_index ON so_posts10(Id)");
	      try(ResultSet r = connection.createStatement().executeQuery("SELECT MAX(Id) as maxId FROM so_posts10 USE INDEX(Id)")){
	        if (r.next())
	          numOfRows = r.getInt("maxId");
	      }
	     update(connection, "DROP TABLE bugquery_index3");
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
  	  	int id = s.getInt("Id"), 
  	  		postTypeId = s.getInt("PostTypeId"), 
  	  		parentId = s.getInt("ParentId"),
			acceptedAnswerId = s.getInt("AcceptedAnswerId"), 
			score = s.getInt("Score");
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
			// TODO you were supposed to do proper loggin
			System.out.println("The id is: "+id);

			try(PreparedStatement ps2 = c.prepareStatement(INSERT_QUERY)){
	  			String ex = ¢1.getException();
	  			// TODO Convert to static function uniqueId() that returns a new id.
	  			ps2.setInt(1, uniqueId);
	  			uniqueId += 1;
	  			ps2.setInt(2, id);
	  			ps2.setString(3, ex);
	  			ps2.setString(4, ¢1.getContent());
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

	private static void update(Connection c, String sqlQuery) throws SQLException {
		c.createStatement().executeUpdate(sqlQuery);
	}
}