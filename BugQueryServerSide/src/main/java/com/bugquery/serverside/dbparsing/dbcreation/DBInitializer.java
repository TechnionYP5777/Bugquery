package com.bugquery.serverside.dbparsing.dbcreation;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		try(Statement st = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=root").createStatement()){
		
  		try {
  			st.executeUpdate("CREATE DATABASE bugquery");
  		} catch (SQLException e) {
  			System.out.println("db already exists " + e.getMessage());
  		}
  		
  		st.executeQuery("USE bugquery");
  		
  		
  		try {
  			
  			st.executeUpdate("CREATE TABLE so_posts(Id int, PostTypeId int, ParentID int,AcceptedAnswerId int, Score int, Body Text,Title Text, Tags varchar(500), AnswerCount int)");
  			st.executeUpdate("LOAD XML LOCAL INFILE 'D:\\BugQuery Project\\StackOverflow data\\Posts.xml' INTO TABLE so_posts(Id, PostTypeId,ParentID,AcceptedAnswerId,Score,Body,Title,Tags,AnswerCount)");
  			
  		} catch (SQLException e) {
  			System.out.println("Table already exists " + e.getMessage());
  		}
		}
		
		
		
		
	}
}
