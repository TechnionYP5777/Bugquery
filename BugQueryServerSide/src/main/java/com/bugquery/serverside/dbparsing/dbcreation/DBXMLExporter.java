package com.bugquery.serverside.dbparsing.dbcreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBXMLExporter {
	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	String serverUrl = "jdbc:mysql://gpu7.cs.technion.ac.il:3306/bugquery";
	String serverUsername = "root";
	String serverPassword = "root";
	String xmlLocation = "D:\\\\BugQuery Project\\\\StackOverflow data\\\\Posts.xml";
	
	
	
	public DBXMLExporter(String serverUrl, String serverUsername, String serverPassword, String xmlLocation) {
		this.serverUrl = serverUrl;
		this.serverUsername = serverUsername;
		this.serverPassword = serverPassword;
		this.xmlLocation = xmlLocation;
	}

	private Connection connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class.forName(COM_MYSQL_JDBC_DRIVER).newInstance();
		return DriverManager.getConnection(serverUrl,serverUsername,serverPassword);
	}
	
	public void export() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection con = connect();
		Statement st = con.createStatement();
		createDB(st);
		createTable(st);
		st.close();
		con.close();
	}

	private void createDB(Statement st) throws SQLException {
		try {
  			st.executeUpdate("CREATE DATABASE bugquery");
  		} catch (SQLException ¢) {
  			System.out.println("db already exists " + ¢.getMessage());
  		}
  		
  		st.executeQuery("USE bugquery");
	}
	
	private  void createTable(Statement st) {
		try {
  			
  			st.executeUpdate("CREATE TABLE so_posts(Id int, PostTypeId int, ParentId int,AcceptedAnswerId int, Score int, Body Text,Title Text, Tags varchar(500), AnswerCount int)");
  			st.executeUpdate("LOAD XML INFILE "+xmlLocation+" INTO TABLE so_posts(Id, PostTypeId,ParentId,AcceptedAnswerId,Score,Body,Title,Tags,AnswerCount)");
  			
  		} catch (SQLException ¢) {
  			System.out.println("Table already exists " + ¢.getMessage());
  		}
	}
	

	public static void main(String[] args) {
  			DBXMLExporter exporter = new DBXMLExporter("jdbc:mysql://gpu7.cs.technion.ac.il:3306", "root", "root", "D:\\\\BugQuery Project\\\\StackOverflow data\\\\Posts.xml");
  		
	}
}
