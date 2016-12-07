package com.bugquery.serverside.dbparsing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBGetter {
	public Connection getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection $ = DriverManager.getConnection("jdbc:mysql://localhost/stackoverflow?user=root&password=1234");
	    Statement stmt = $.createStatement();
	    for (ResultSet ¢ = stmt.executeQuery("SELECT * FROM posts"); ¢.next();)
			System.out.println(¢.getString("Id"));
	    return $;
	}
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		new DBGetter().getConnection();
	}
}
