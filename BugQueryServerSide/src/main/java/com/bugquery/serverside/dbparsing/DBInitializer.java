package com.bugquery.serverside.dbparsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=1234");
		
		Statement st = con.createStatement();
		
		try {
			st.executeUpdate("CREATE DATABASE stackoverflow");
		} catch (SQLException e) {
			System.out.println("db already exists");
		}
		
		st.executeQuery("USE stackoverflow");
		
		
		try {
			
			st.executeUpdate("CREATE TABLE posts(Id int, PostTypeId int, ParentID int,AcceptedAnswerId int, Score int, Body varchar(255),Title varchar(255), Tags varchar(255), AnswerCount int)");
			st.executeUpdate("LOAD XML LOCAL INFILE 'Posts.xml' INTO TABLE posts(Id, PostTypeId,ParentID,AcceptedAnswerId,Score,Body,Title,Tags,AnswerCount)");
		} catch (SQLException e) {
			System.out.println("Table already exists");
		}
		
		
		
		
	}
}
